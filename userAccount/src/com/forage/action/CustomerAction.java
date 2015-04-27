package com.forage.action;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.CustomerBean;
import com.forage.dao.CustomerDAO;
import com.forage.exception.AlreadyExistException;
import com.forage.exception.NotFoundException;
import com.forage.json.CustomerJSON;
import com.forage.user.Utility;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@Path("/customer")
public class CustomerAction {
	
	@GET
	@Path("/updateGPS")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateGPS(@QueryParam("phone") String phoneNumber, @QueryParam("latitude") Double latitude, @QueryParam("longitude") Double longitude){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "Phone " + phoneNumber +" not registered ");
		}else{
			customer.setLastGPSLatitude(latitude);
			customer.setLastGPSLongitude(longitude);
			custDAO.updateGPSLocation(customer);
			customer = custDAO.getCustomer(phoneNumber);	
		}
		return CustomerJSON.constructStatus("updateGPS", "Success", customer);
	}
	
	
	@GET
	@Path("/customerById")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String customerById(@QueryParam("customerId") BigDecimal customerId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = null;
		customer = custDAO.getCustomer(customerId);
		return CustomerJSON.constructStatus("customerById", "Success", customer);
	}
	
	
	@GET
	@Path("/get")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getCustomer(@QueryParam("phone") String phoneNumber){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("getCustomer", "Phone " + phoneNumber +" not registered ");
		}
		return CustomerJSON.constructStatus("getDetails", "Success", customer);
	}
	
	
	@GET
	@Path("/getcust")  
	@Produces(MediaType.APPLICATION_JSON) 
	public Response getCust(@QueryParam("phone") String phoneNumber){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("getCust", "Phone " + phoneNumber +" not registered ");
		}
		System.out.println("getcust");
		return Response.ok(CustomerJSON.construct(customer), MediaType.APPLICATION_JSON).build();
	}
	
	
	@GET
	@Path("/getCustomerBean")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getCustomerBean(@QueryParam("phone") String phoneNumber){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("getCustomerBean", "Phone " + phoneNumber +" not registered ");
		}
		return CustomerJSON.construct(customer);
	}
	
	
	
	@GET
	@Path("/addCustomer")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String addCustomer(@QueryParam("phone") String phoneNumber, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() != null){
			throw new AlreadyExistException("addCustomer", "Phone is registered with Customer Id : " + customer.getCustomerId());
		}else{
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setPhoneNumber(phoneNumber);
			custDAO.insertNewCustomerNameNumber(customer);
			customer = custDAO.getCustomer(phoneNumber);	
		}
		return CustomerJSON.constructStatus("addCustomer", "Success", customer);
	}
	
	
	@GET
	@Path("/updateEmail")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateEmail(@QueryParam("phone") String phoneNumber, @QueryParam("email") String email){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("updateEmail", "No Customer Exists");
		}else{
			customer.setEmail(email);
			custDAO.updateEmail(customer);
			customer = custDAO.getCustomer(phoneNumber);	
		}
		return CustomerJSON.constructStatus("updateEmail", "Success", customer);
	}
	
	@GET
	@Path("/updateName")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateName(@QueryParam("phone") String phoneNumber, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("updateName", "No Customer Exists");
		}else{
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			custDAO.updateName(customer);
			customer = custDAO.getCustomer(phoneNumber);	
		}
		return CustomerJSON.constructStatus("updateName", "Success", customer);
	}
	
	
	@GET
	@Path("/updateAddress")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateAddress(@QueryParam("phone") String phoneNumber, 
								@QueryParam("addressId") BigDecimal addressId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("updateAddress", "No Customer Exists");
		}else{
			customer.setAddress(addressId);
			custDAO.updateAddress(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateAddress", "Success", customer);
	}
	
	@GET
	@Path("/addAddress")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String addAddress(@QueryParam("phone") String phoneNumber, 
							 @QueryParam("addressId") BigDecimal addressId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("addAddress", "No Customer Exists");
		}else{
			customer.setAddress(addressId);
			custDAO.updateAddress(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("addAddress", "Success", customer);
	}
	
	
	@GET
	@Path("/updateShipAddr")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateShipAddr(@QueryParam("phone") String phoneNumber, 
								@QueryParam("addressId") BigDecimal addressId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("updateShipAddr", "No Customer Exists");
		}else{
			customer.setShippingAddress(addressId);
			custDAO.updateAddress(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateShipAddr", "Success", customer);
	}
	
	@GET
	@Path("/addShipAddr")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String addShipAddr(@QueryParam("phone") String phoneNumber, 
							 @QueryParam("addressId") BigDecimal addressId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("addShipAddr", "No Customer Exists");
		}else{
			customer.setShippingAddress(addressId);
			custDAO.updateAddress(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("addShipAddr", "Success", customer);
	}
	
	
	@GET
	@Path("/updateFacebookId")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateFacebookId(@QueryParam("phone") String phoneNumber, 
							 @QueryParam("facebookId") String facebookId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("updateFacebookId", "No Customer Exists");
		}else{
			customer.setFacebookUniqueId(facebookId);
			custDAO.updateFacebookId(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateFacebookId", "Success", customer);
	}
	
	@GET
	@Path("/updateTwitterId")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateTwitterId(@QueryParam("phone") String phoneNumber, 
							 @QueryParam("twitterId") String twitterId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("updateTwitterId", "No Customer Exists");
		}else{
			customer.setTwitterUniqueId(twitterId);
			custDAO.updateFacebookId(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateTwitterId", "Success", customer);
	}
	
	@GET
	@Path("/updateGoogleId")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateGoogleId(@QueryParam("phone") String phoneNumber, 
							 @QueryParam("googleId") String googleId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("updateGoogleId", "No Customer Exists");
		}else{
			customer.setGoogleUniqueId(googleId);
			custDAO.updateGoogleId(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateGoogleId", "Success", customer);
	}
	
	
	@GET
	@Path("/updatePhoneNumber")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updatePhoneNumber(@QueryParam("old") String phoneNumber, 
							 @QueryParam("new") String newPhoneNumber){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("updatePhoneNumber", "No Customer Exists");
		}else{
			custDAO.updatePhoneNumber(customer, newPhoneNumber);			
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updatePhoneNumber", "Success", customer);
	}
	
	
	@GET
	@Path("/updateLoginDate")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateLoginDate(@QueryParam("phone") String phoneNumber){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			return Utility.constructActionStatus("updateLoginDate", "No Customer Exists");
		}else{
			custDAO.updateLoginDate(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateLoginDate", "Success", customer);
	}
	
	
	
	@POST
	@Path("/addCustomerJSON")  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String addCustomerJSON(CustomerBean customer){
		String custString = this.getCustomer(customer.getPhoneNumber());
		System.out.println("custString : " + custString);
		return CustomerJSON.constructStatus("addCustomerDetails", "Success", customer);
	}
	
	
	
	
	
	public static void main(String[] args) {
			
		// JERSEY CLIENT CODE //
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer("9915088333");
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource("http://localhost:8080/userAccount/customer/addCustomerJSON");
		
		 ClientResponse response = webResource.accept("application/json")
				 .type("application/json").post(ClientResponse.class, customer);

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