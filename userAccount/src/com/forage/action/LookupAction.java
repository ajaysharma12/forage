package com.forage.action;

import java.math.BigDecimal;
import java.sql.Date;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.forage.bean.LookupTypeBean;
import com.forage.bean.LookupValueBean;
import com.forage.dao.LookupTypeDAO;
import com.forage.dao.LookupValueDAO;
import com.forage.exception.NotFoundException;
import com.forage.json.LookupJSON;

@Path("/lookup")
public class LookupAction {

	@PUT
	@Path("tag/{type}/{code}/{tag}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String tagLookupCode (@PathParam("type") String lookupType,
								@PathParam("code") String lookupCode,
								@PathParam("tag") String tag){
		LookupValueDAO lookupValueDAO = new LookupValueDAO();
		LookupValueBean lookupValueBean = lookupValueDAO.getLookupValue(lookupType, lookupCode);
		lookupValueBean.setTag(tag);
		lookupValueDAO.updateLookup(lookupValueBean);
		return LookupJSON.constructStatus("LookupAction.getLookupList", "Success", lookupValueBean);
	}
	
	
	public String updateLocalityZone(@PathParam("type") String lookupType,
										@PathParam("code") String lookupCode,
										@PathParam("zone") String zone){
		LookupValueDAO lookupValueDAO = new LookupValueDAO();
		LookupValueBean lookupValueBean = lookupValueDAO.getLookupValue(lookupType, lookupCode);
		lookupValueBean.setAttribute1(zone);
		lookupValueDAO.updateLookup(lookupValueBean);
		return LookupJSON.construct(lookupValueBean);
	}
	
	@GET	
	@Path("{type}/{tag}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getLookupListOnTag( @PathParam("type") String lookupType,
									@PathParam("tag") String tag){
		
		LookupTypeDAO lookupTypeDAO = new LookupTypeDAO();
		LookupTypeBean lookupTypeBean = lookupTypeDAO.getLookupType(lookupType, tag);
		
		if (lookupTypeBean == null) {
			throw new NotFoundException("LookupAction.getLookupListOnTag", lookupType + " has no lookup code tagged to " + tag);
		}
		return LookupJSON.constructStatus("LookupAction.getLookupList", "Success", lookupTypeBean);
	}
	
	
	@GET	
	@Path("code/{type}/{tag}/{meaning}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getLookupCode( @PathParam("type") String lookupType,
									@PathParam("tag") String tag,					
									@PathParam("meaning") String meaning){
		
		LookupValueBean rtnLookupValue = null;
		String rtnLookupCode = null;
		LookupValueDAO lookupValueDAO = new LookupValueDAO();
		rtnLookupValue = lookupValueDAO.getLookupValueOnMeaningTag(lookupType, meaning, tag);
		
		if (rtnLookupValue!=null && meaning.toUpperCase().equals(rtnLookupValue.getMeaning().toUpperCase())) {
			rtnLookupCode = rtnLookupValue.getLookupCode();
		}
		return rtnLookupCode;
	}
	
	@GET	
	@Path("{type}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getLookupType( @PathParam("type") String lookupType){
		LookupTypeDAO lookupTypeDAO = new LookupTypeDAO();
		LookupTypeBean lookupTypeBean = null;
		lookupTypeBean = lookupTypeDAO.getLookupType(lookupType, "");	
		return LookupJSON.construct(lookupTypeBean);
	}
	
	@POST	
	@Path("{type}/{meaning}/{user}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String createLookupCode(@PathParam("type") String lookupType, 
								@PathParam("meaning") String value, 
								@PathParam("user") BigDecimal userId){
		String meaning = value;
		String valueUnderScore = value.replaceAll(" ", "_");
		String valueUpperCase = valueUnderScore.toUpperCase();
		
		String valueCode = valueUpperCase;
		if(valueUpperCase.length() > 50){
			valueCode = valueUpperCase.substring(0, 49);
			if(valueCode.endsWith("_")){
				valueCode = valueUpperCase.substring(0, 48);
			}
		}
		
		LookupValueDAO lookupValueDAO = new LookupValueDAO();
		LookupValueBean lookupValueBean = new LookupValueBean();
		
		lookupValueBean.setLookupType(lookupType);
		lookupValueBean.setLanguage("US");
		lookupValueBean.setLookupCode(valueCode);
		lookupValueBean.setMeaning(meaning);
		
		java.util.Date currentDate = new java.util.Date();
		
		lookupValueBean.setStartActiveDate(new Date(currentDate.getTime()));
		
		lookupValueBean.setCreatedBy(userId);
		lookupValueBean.setLastUpdatedBy(userId);
		lookupValueBean.setLastUpdateLogin(userId);		
		lookupValueBean = lookupValueDAO.insertLookup(lookupValueBean, userId);

		return LookupJSON.construct(lookupValueBean);
	}
	
	
}
