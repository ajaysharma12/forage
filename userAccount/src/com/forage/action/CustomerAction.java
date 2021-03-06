package com.forage.action;

import java.math.BigDecimal;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.forage.bean.CustomerBean;
import com.forage.dao.CustomerDAO;
import com.forage.exception.AlreadyExistException;
import com.forage.exception.NotFoundException;
import com.forage.json.CustomerJSON;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@Path("/customer")
public class CustomerAction {
	
	
	@POST  
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.APPLICATION_JSON)
	public String addCustomerJSON(CustomerBean customer){
		String custString = this.getCustomer(customer.getPhoneNumber());
		System.out.println("custString : " + custString);
		return CustomerJSON.constructStatus("addCustomerDetails", "Success", customer);
	}
	
	
	@POST
	@Path("{phone}/{fname}/{lname}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String addCustomer(@PathParam("phone") String phoneNumber, 
								@PathParam("fname") String firstName, 
								@PathParam("lname") String lastName){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() != null){
			throw new AlreadyExistException("CustomerAction.addCustomer", "Phone is registered with Customer Id : " + customer.getCustomerId());
		}else{
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			customer.setPhoneNumber(phoneNumber);
			custDAO.insertNewCustomerNameNumber(customer);
			customer = custDAO.getCustomer(phoneNumber);	
		}
		return CustomerJSON.constructStatus("CustomerAction.addCustomer", "Success", customer);
	}
		
	
	@GET
	@Path("{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String findById(@PathParam("id") BigDecimal customerId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = null;
		customer = custDAO.getCustomer(customerId);
		return CustomerJSON.constructStatus("CustomerAction.findById", "Success", customer);
	}
	
	
	@GET
	@Path("phone/{phone}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getCustomer(@PathParam("phone") String phoneNumber){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer == null || customer.getCustomerId() == null){
			String customerString = this.addCustomer(phoneNumber, "Forager", null);
			return customerString;
//			throw new NotFoundException("getCustomer", "customer Phone " + phoneNumber +" not registered ");
		}

		return CustomerJSON.constructStatus("CustomerAction.getDetails", "Success", customer);
	}
	
	
	@GET
	@Path("/getCustomerBean")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String getCustomerBean(@QueryParam("phone") String phoneNumber){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("CustomerAction.getCustomerBean", "Phone " + phoneNumber +" not registered ");
		}
		return CustomerJSON.construct(customer);
	}
	
	
	@PUT
	@Path("{phone}/{latitude}/{longitude}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateGPS(@PathParam("phone") String phoneNumber, 
							@PathParam("latitude") Double latitude, 
							@PathParam("longitude") Double longitude){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			customer.setLastGPSLatitude(latitude);
			customer.setLastGPSLongitude(longitude);
			custDAO.updateGPSLocation(customer);
			customer = custDAO.getCustomer(phoneNumber);	
		}
		return CustomerJSON.constructStatus("updateGPS", "Success", customer);
	}
	
	@PUT
	@Path("{phone}/email/{email}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateEmail(@PathParam("phone") String phoneNumber, 
							  @PathParam("email") String email){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			customer.setEmail(email);
			custDAO.updateEmail(customer);
			customer = custDAO.getCustomer(phoneNumber);	
		}
		return CustomerJSON.constructStatus("updateEmail", "Success", customer);
	}
	
	@PUT
	@Path("{phone}/name/{fname}/{lname}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateName(@PathParam("phone") String phoneNumber, 
							 @PathParam("fname") String firstName, 
							 @PathParam("lname") String lastName){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			customer.setFirstName(firstName);
			customer.setLastName(lastName);
			custDAO.updateName(customer);
			customer = custDAO.getCustomer(phoneNumber);	
		}
		return CustomerJSON.constructStatus("updateName", "Success", customer);
	}
	
	
	@PUT
	@Path("{phone}/addr/{addr}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateAddress(@PathParam("phone") String phoneNumber, 
								@PathParam("addr") BigDecimal addressId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			customer.setAddress(addressId);
			custDAO.updateAddress(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateAddress", "Success", customer);
	}
	
	@PUT
	@Path("{phone}/shipaddr/{addr}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateShipAddr(@PathParam("phone") String phoneNumber, 
							@PathParam("addr") BigDecimal addressId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			customer.setShippingAddress(addressId);
			custDAO.updateAddress(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateShipAddr", "Success", customer);
	}
		
	@PUT
	@Path("{phone}/fb/{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateFacebookId(@PathParam("phone") String phoneNumber, 
									@PathParam("id") String facebookId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			customer.setFacebookUniqueId(facebookId);
			custDAO.updateFacebookId(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateFacebookId", "Success", customer);
	}
	
	@PUT
	@Path("{phone}/twitter/{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateTwitterId(@PathParam("phone") String phoneNumber, 
								@PathParam("id") String twitterId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			customer.setTwitterUniqueId(twitterId);
			custDAO.updateFacebookId(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateTwitterId", "Success", customer);
	}
	
	@PUT
	@Path("{phone}/google/{id}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateGoogleId(@PathParam("phone") String phoneNumber, 
								@PathParam("id") String googleId){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			customer.setGoogleUniqueId(googleId);
			custDAO.updateGoogleId(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateGoogleId", "Success", customer);
	}
	
	
	@PUT
	@Path("{oldphone}/phone/{newphone}")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updatePhoneNumber(@PathParam("oldphone") String phoneNumber, 
							 		@PathParam("newphone") String newPhoneNumber){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			custDAO.updatePhoneNumber(customer, newPhoneNumber);			
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updatePhoneNumber", "Success", customer);
	}
	
	
	@PUT
	@Path("{phone}/logindate")  
	@Produces(MediaType.APPLICATION_JSON) 
	public String updateLoginDate(@PathParam("phone") String phoneNumber){
		CustomerDAO custDAO = new CustomerDAO();
		CustomerBean customer = custDAO.getCustomer(phoneNumber);
		if(customer.getCustomerId() == null){
			throw new NotFoundException("updateGPS", "customer Phone " + phoneNumber +" not registered");
		}else{
			custDAO.updateLoginDate(customer);
			customer = custDAO.getCustomer(phoneNumber);
		}
		return CustomerJSON.constructStatus("updateLoginDate", "Success", customer);
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
