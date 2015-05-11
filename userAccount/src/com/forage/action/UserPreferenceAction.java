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

import com.forage.bean.CustomerBean;
import com.forage.bean.UserPreferenceBean;
import com.forage.dao.CustomerDAO;
import com.forage.dao.UserPreferenceDAO;
import com.forage.exception.NotFoundException;
import com.forage.json.UserPreferenceJSON;


@Path("/preference")
public class UserPreferenceAction {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String createPreference(UserPreferenceBean preference){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		if(preference.getUserId() == null){
			throw new NotFoundException("UserPreferenceAction.createMenu", "User not provided.");
		}
		CustomerDAO customerDAO = new CustomerDAO();
		CustomerBean customer = customerDAO.getCustomer(preference.getUserId());
		if(customer == null || customer.getCustomerId() == null)
			throw new NotFoundException("UserPreferenceAction.createMenu", "User not found.");
		
		preference = preferenceDAO.insert(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updatePreference(UserPreferenceBean preference){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("radius/{phone}/{radius}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateSearchRadius(@PathParam("phone") String phone, @PathParam("radius") BigDecimal radius){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		preference.setSearchRadius(radius);
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("breakfasttime/{phone}/{timelookup}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateBreakfast(@PathParam("phone") String phone, @PathParam("timelookup") String timelookup){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		preference.setBreakfastTime(timelookup);
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("lunchtime/{phone}/{timelookup}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateLunchTime(@PathParam("phone") String phone, @PathParam("timelookup") String timelookup){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		preference.setLunchTime(timelookup);
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("dinnertime/{phone}/{timelookup}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateDinnerTime(@PathParam("phone") String phone, @PathParam("timelookup") String timelookup){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		preference.setDinnerTime(timelookup);
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("brunchtime/{phone}/{timelookup}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateBrunchTime(@PathParam("phone") String phone, @PathParam("timelookup") String timelookup){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		preference.setBrunchTime(timelookup);
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("cuisine1/{phone}/{cuisinetype}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCuisine1(@PathParam("phone") String phone, @PathParam("cuisinetype") String cuisine){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		preference.setCuisine1(cuisine);
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("cuisine2/{phone}/{cuisinetype}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCuisine2(@PathParam("phone") String phone, @PathParam("cuisinetype") String cuisine){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		preference.setCuisine2(cuisine);
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("cuisine3/{phone}/{cuisinetype}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCuisine3(@PathParam("phone") String phone, @PathParam("cuisinetype") String cuisine){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		preference.setCuisine3(cuisine);
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("cuisine4/{phone}/{cuisinetype}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCuisine4(@PathParam("phone") String phone, @PathParam("cuisinetype") String cuisine){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		preference.setCuisine4(cuisine);
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("newvendoralert/{phone}/{onoroff}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String setNewVendorAlert(@PathParam("phone") String phone, @PathParam("onoroff") String onOff){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		if("ON".equals(onOff)){
			preference.setNewVendorAlert("Y");
		}else if("OFF".equals(onOff)){
			preference.setNewVendorAlert("N");
		}
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("vendorupdate/{phone}/{onoroff}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String setVendorUpdate(@PathParam("phone") String phone, @PathParam("onoroff") String onOff){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		if("ON".equals(onOff)){
			preference.setVendorUpdate("Y");
		}else if("OFF".equals(onOff)){
			preference.setVendorUpdate("N");
		}
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("menualert/{phone}/{onoroff}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String setMenuAlert(@PathParam("phone") String phone, @PathParam("onoroff") String onOff){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		if("ON".equals(onOff)){
			preference.setMenuUpdate("Y");
		}else if("OFF".equals(onOff)){
			preference.setMenuUpdate("N");
		}
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("reviewalert/{phone}/{onoroff}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String setReviewAlert(@PathParam("phone") String phone, @PathParam("onoroff") String onOff){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		if("ON".equals(onOff)){
			preference.setReviewUpdate("Y");
		}else if("OFF".equals(onOff)){
			preference.setReviewUpdate("N");
		}
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("photoalert/{phone}/{onoroff}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String setPhotoAlert(@PathParam("phone") String phone, @PathParam("onoroff") String onOff){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		if("ON".equals(onOff)){
			preference.setPhotoUpdate("Y");
		}else if("OFF".equals(onOff)){
			preference.setPhotoUpdate("N");
		}
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("friendalert/{phone}/{onoroff}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String setFriendAlert(@PathParam("phone") String phone, @PathParam("onoroff") String onOff){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		if("ON".equals(onOff)){
			preference.setFriendUpdate("Y");
		}else if("OFF".equals(onOff)){
			preference.setFriendUpdate("N");
		}
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@PUT
	@Path("appalert/{phone}/{onoroff}")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String setAppAlert(@PathParam("phone") String phone, @PathParam("onoroff") String onOff){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(phone);
		if("ON".equals(onOff)){
			preference.setAppUpdate("Y");
		}else if("OFF".equals(onOff)){
			preference.setAppUpdate("N");
		}
		preferenceDAO.updateUserPreference(preference);
		return UserPreferenceJSON.construct(preference);
	}
	
	@GET
	@Path("{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getPreference(@PathParam("id") BigDecimal preferenceId){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(preferenceId);
		if(preference == null || preference.getPreferenceId() == null){
			throw new NotFoundException("UserPreferenceAction.getPreference", "Preference <"+ preference + "> not found.");
		}
		return UserPreferenceJSON.construct(preference);
	}
	
	@GET
	@Path("/user/{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getUserPreference(@PathParam("id") BigDecimal userId){
		UserPreferenceDAO preferenceDAO = new UserPreferenceDAO();
		UserPreferenceBean preference = preferenceDAO.getPreference(userId);
		if(preference == null || preference.getPreferenceId() == null){
			throw new NotFoundException("UserPreferenceAction.getUserPreference", "User <"+userId+"> has no preferences.");
		}
		return UserPreferenceJSON.construct(preference);
	}
	
}
