package com.forage.action;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.forage.bean.TiffinFilterBean;
import com.forage.bean.VendorBean;
import com.forage.dao.TiffinFilterDAO;
import com.forage.dao.VendorDAO;
import com.forage.exception.AlreadyExistException;
import com.forage.exception.NotFoundException;
import com.forage.json.TiffinFilterJSON;

@Path("/tiffinfilter")
public class TiffinFilterAction {

	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String create(TiffinFilterBean tiffinFilter){
		
		// check if the vendor exists
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(tiffinFilter.getVendorNumber());
		
		if(vendor == null || vendor.getVendorId() == null){
			throw new NotFoundException("TiffinFilterAction.create", "Vendor " + tiffinFilter.getVendorNumber() +" do not exists. Please register the vendor before registering the filters ");
		}
		
		TiffinFilterDAO tiffinFilterDAO = new TiffinFilterDAO();
		TiffinFilterBean tiffinCheck = tiffinFilterDAO.getTiffinFilters(tiffinFilter.getVendorNumber());
		if(tiffinCheck != null && tiffinCheck.getVendorNumber() != null){
			throw new AlreadyExistException("TiffinAction.create", "Vendor Tiffin already set. ");
		}else{
			tiffinFilterDAO.insertFilters(tiffinFilter);	
		}
		return TiffinFilterJSON.constructStatus("create", "Success", tiffinFilter);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON) 
	public String findById(@PathParam("id") BigDecimal vendorNumber){
		TiffinFilterDAO tiffinFilterDAO = new TiffinFilterDAO();
		TiffinFilterBean tiffinFilter = tiffinFilterDAO.getTiffinFilters(vendorNumber);
		if(tiffinFilter != null && tiffinFilter.getVendorNumber() != null){
			throw new NotFoundException("findById", "Vendor " + vendorNumber +" has no filter set. ");
		}
		return TiffinFilterJSON.constructStatus("findById", "Success", tiffinFilter);
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String update(TiffinFilterBean tiffinFilter){
		
		TiffinFilterDAO tiffinFilterDAO = new TiffinFilterDAO();
		TiffinFilterBean tiffinCheck = tiffinFilterDAO.getTiffinFilters(tiffinFilter.getVendorNumber());
		if(tiffinCheck == null || tiffinCheck.getVendorNumber() != null){
			throw new NotFoundException("TiffinAction.update", "Vendor Tiffin Filters not found. ");
		}else{
			tiffinFilterDAO.updateFilters(tiffinFilter);	
		}
		return TiffinFilterJSON.constructStatus("TiffinAction.update", "Success", tiffinFilter);
	}
	
}
