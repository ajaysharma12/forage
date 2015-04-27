package com.forage.action;

import java.math.BigDecimal;
import java.sql.Date;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.forage.bean.LookupTypeBean;
import com.forage.bean.LookupValueBean;
import com.forage.dao.LookupTypeDAO;
import com.forage.dao.LookupValueDAO;
import com.forage.json.LookupJSON;

@Path("/lookup")
public class LookupAction {

	@GET	
	@Path("/listType")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getLookupType( @QueryParam("type") String lookupType,
								@DefaultValue("") @QueryParam("tag") String tag){
		LookupTypeDAO lookupTypeDAO = new LookupTypeDAO();
		LookupTypeBean lookupTypeBean = null;
		lookupTypeBean = lookupTypeDAO.getLookupType(lookupType, tag);	
		return LookupJSON.construct(lookupTypeBean);
	}
	
	@GET	
	@Path("/create")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String createLookupCode(@QueryParam("type") String lookupType, 
									@QueryParam("meaning") String value, 
									@QueryParam("userId") BigDecimal userId){
		String meaning = value;
		String valueUnderScore = value.replaceAll(" ", "_");
		String valueUpperCase = valueUnderScore.toUpperCase();
		
		String valueCode = valueUpperCase;
		if(valueUpperCase.length() > 50){
			valueCode = valueUpperCase.substring(0, 49);
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

		LookupTypeDAO lookupTypeDAO = new LookupTypeDAO();
		LookupTypeBean lookupTypeBean = lookupTypeDAO.getLookupType(lookupType, "");
		return LookupJSON.construct(lookupTypeBean);
	}
	
}