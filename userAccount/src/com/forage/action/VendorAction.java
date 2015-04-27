package com.forage.action;

import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.VendorBean;
import com.forage.dao.VendorDAO;
import com.forage.exception.AlreadyExistException;
import com.forage.exception.NotFoundException;
import com.forage.exception.PreconditionFailedException;
import com.forage.json.VendorJSON;
import com.forage.user.Utility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@Path("/vendor")
public class VendorAction {


	@POST
	@Path("{phone}/{name}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String create(@PathParam("phone") String phoneNumber, 
						 @PathParam("name") String name){
		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor != null && vendor.getVendorId() != null){
			throw new AlreadyExistException("create", "Phone " + phoneNumber +" already registered ");
		}else{
			vendor.setName(name);
			vendor.setPhoneNumber(phoneNumber);
			vendorDAO.insertNewVendorNameNumber(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);	
		}
		return VendorJSON.constructStatus("create", "Success", vendor);
		
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(VendorBean vendor){		
		VendorDAO vendorDAO = new VendorDAO();
		if(vendor == null || vendor.getPhoneNumber() == null){
			throw new PreconditionFailedException("create", "Phone number not provided");
		}else{
			VendorBean vendorCheck = vendorDAO.getVendor(vendor.getPhoneNumber());
			if(vendorCheck != null && vendorCheck.getVendorId() != null)
				throw new AlreadyExistException("create", "Phone " + vendor.getPhoneNumber() +" already registered");	
			vendorDAO.insertNewVendorNameNumber(vendor);
			vendorDAO.update(vendor);
		}
		return Response.ok(VendorJSON.constructStatus("create", Response.Status.CREATED.toString(), vendor), MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path("{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String findById(@PathParam("id") BigDecimal vendorId){
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(vendorId);
		if(vendor.getVendorId() == null){
			throw new NotFoundException("findById", "Vendor " + vendorId +" not registered ");
		}
		return VendorJSON.constructStatus("findById", "Success", vendor);
	}
	
	
	@GET
	@Path("/phone/{phone}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String findByPhone(@PathParam("phone") String phoneNumber){
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			throw new NotFoundException("findByPhone", "Phone " + phoneNumber +" not registered ");
		}
		return VendorJSON.constructStatus("findByPhone", "Success", vendor);
	}
	
	
	@GET
	@Path("/getVendorBean")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getVendorBean(@QueryParam("phone") String phoneNumber){
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			throw new NotFoundException("getVendorBean", "Phone " + phoneNumber +" not registered ");
		}
		return VendorJSON.construct(vendor);
	}
	
	
	@PUT	
	@Path("/{phone}/{latitude}/{longitude}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateGPS(@PathParam("phone") String phoneNumber, 
							@PathParam("latitude") Double latitude, 
							@PathParam("longitude") Double longitude){
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			throw new NotFoundException("updateGPS", "Phone " + phoneNumber +" not registered ");
		}else{
			vendor.setLastGPSLatitude(latitude);
			vendor.setLastGPSLongitude(longitude);
			vendorDAO.updateGPSLocation(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);
		}
		return VendorJSON.constructStatus("updateGPS", "Success", vendor);
	}
	
	
	
	@PUT 
	@Path("{id}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response update(VendorBean vendor) {
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendorCheck = vendorDAO.getVendor(vendor.getVendorId());
		if(vendorCheck != null && vendorCheck.getVendorId() != null)
			throw new NotFoundException("update", "Vendor " + vendor.getVendorId() +" not registered ");
		vendorDAO.update(vendor);
		return Response.ok(VendorJSON.constructStatus("update", Response.Status.OK.toString(), vendor), MediaType.APPLICATION_JSON).build();        
    }
	
	
	@PUT
	@Path("{phone}/email/{email}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateEmail(@PathParam("phone") String phoneNumber, 
							 @PathParam("email") String email){
		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			throw new NotFoundException("updateEmail", "Phone " + phoneNumber +" not registered ");
		}else{
			vendor.setEmail(email);
			vendorDAO.updateEmail(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);	
		}
		return VendorJSON.constructStatus("updateEmail", "Success", vendor);
		
	}
	
	@PUT
	@Path("{phone}/name/{name}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateName(@PathParam("phone") String phoneNumber, 
							@PathParam("name") String name){
		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			throw new NotFoundException("updateName", "Phone " + phoneNumber +" not registered ");
		}else{
			vendor.setEmail(name);
			vendorDAO.updateEmail(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);	
		}
		return VendorJSON.constructStatus("updateName", "Success", vendor);
		
	}
	
	
	@PUT
	@Path("{phone}/addr1/{addr}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateAddress1(@PathParam("phone") String phoneNumber, 
								 @PathParam("addr") BigDecimal addressId){
		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			throw new NotFoundException("updateAddress1", "Phone " + phoneNumber +" not registered ");
		}else{
			vendor.setAddress1(addressId);
			vendorDAO.updateEmail(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);	
		}
		return VendorJSON.constructStatus("updateAddress1", "Success", vendor);
	}
	
	@PUT
	@Path("{phone}/addr2/{addr}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateAddress2(@PathParam("phone") String phoneNumber, 
			@PathParam("addr") BigDecimal addressId){
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			throw new NotFoundException("updateAddress2", "Phone " + phoneNumber +" not registered ");
		}else{
			vendor.setAddress2(addressId);
			vendorDAO.updateEmail(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);	
		}
		return VendorJSON.constructStatus("updateAddress2", "Success", vendor);
	}
	
	@PUT
	@Path("{phone}/addr3/{addr}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateAddress3(@PathParam("phone") String phoneNumber, 
			@PathParam("addr") BigDecimal addressId){
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			throw new NotFoundException("updateAddress3", "Phone " + phoneNumber +" not registered ");
		}else{
			vendor.setAddress3(addressId);
			vendorDAO.updateEmail(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);	
		}
		return VendorJSON.constructStatus("updateAddress3", "Success", vendor);
	}
	
	@PUT
	@Path("{phone}/fb/{facebookId}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateFacebookId(@PathParam("phone") String phoneNumber, 
			@PathParam("facebookId") String facebookId){
		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			return Utility.constructActionStatus("updateFacebookId", "No Vendor Exists");
		}else{
			vendor.setFacebookUniqueId(facebookId);
			vendorDAO.updateEmail(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);	
		}
		return VendorJSON.constructStatus("updateFacebookId", "Success", vendor);
		
	}
	
	@PUT
	@Path("/updateTwitterId")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateTwitterId(@QueryParam("phone") String phoneNumber, 
							 @QueryParam("twitterId") String twitterId){
		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			return Utility.constructActionStatus("updateTwitterId", "No Vendor Exists");
		}else{
			vendor.setTwitterUniqueId(twitterId);
			vendorDAO.updateEmail(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);	
		}
		return VendorJSON.constructStatus("updateTwitterId", "Success", vendor);
		
		
	}
	
	@GET
	@Path("/updateGoogleId")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateGoogleId(@QueryParam("phone") String phoneNumber, 
							 @QueryParam("googleId") String googleId){
		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			return Utility.constructActionStatus("updateGoogleId", "No Vendor Exists");
		}else{
			vendor.setGoogleUniqueId(googleId);
			vendorDAO.updateEmail(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);	
		}
		return VendorJSON.constructStatus("updateGoogleId", "Success", vendor);
		
	}
	
	
	@GET
	@Path("/updatePhoneNumber")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updatePhoneNumber(@QueryParam("phone") String phoneNumber, 
							 @QueryParam("googleId") String newPhoneNumber){
		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			return Utility.constructActionStatus("updatePhoneNumber", "No Vendor Exists");
		}else{
			vendorDAO.updatePhoneNumber(vendor, newPhoneNumber);
			vendor = vendorDAO.getVendor(newPhoneNumber);
		}
		return VendorJSON.constructStatus("updatePhoneNumber", "Success", vendor);
		
	}
	
	
	@GET
	@Path("/updateLoginDate")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateLoginDate(@QueryParam("phone") String phoneNumber){
		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor(phoneNumber);
		if(vendor.getVendorId() == null){
			return Utility.constructActionStatus("updateLoginDate", "No Vendor Exists");
		}else{
			vendorDAO.updateLoginDate(vendor);
			vendor = vendorDAO.getVendor(phoneNumber);
		}
		return VendorJSON.constructStatus("updateLoginDate", "Success", vendor);
		
	}
	
	
	
	@GET	
	@Path("/latlangsearch")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String locationSearch(@QueryParam("latitude") Double latitude, @QueryParam("longitude") Double longitude, @QueryParam("radius") BigDecimal radius){
		VendorDAO vendorDAO = new VendorDAO();
		List<VendorBean> vendorList = vendorDAO.getVendorByLatLong(latitude, longitude, radius);
		if(vendorList.isEmpty()){
			return Utility.constructActionStatus("latlangsearch", "No Vendor Exists Nearby");
		}
		return VendorJSON.constructListStatus("latlangsearch", "Pass", vendorList);
	}
	
	
	@GET	
	@Path("/localitysearch")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String locationSearch(@QueryParam("locality") String locality){
		VendorDAO vendorDAO = new VendorDAO();
		List<VendorBean> vendorList = vendorDAO.getVendorByLocality(locality);
		if(vendorList.isEmpty()){
			return Utility.constructActionStatus("localitysearch", "No Vendor Exists Nearby");
		}
		return VendorJSON.constructListStatus("localitysearch", "Pass", vendorList);
	}
	
	
	
	
	
	
	public static void main(String[] args) {
			
		// JERSEY CLIENT CODE //		
		VendorDAO vendorDAO = new VendorDAO();
		VendorBean vendor = vendorDAO.getVendor("9915088333");
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource("http://localhost:8080/userAccount/vendor/addVendorJSON");
		
		 ClientResponse response = webResource.accept("application/json")
				 .type("application/json").post(ClientResponse.class, vendor);

		 if (response.getStatus() != 200) {
			 throw new RuntimeException("Failed : HTTP error code : "
					 + response.getStatus());
		 }
		 
		 String output = response.getEntity(String.class);
		 
		 System.out.println("Server response .... \n");
		 System.out.println(output);

		
		// JERSEY CLIENT CODE //
	}
}