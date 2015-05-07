package com.forage.intercept;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.forage.bean.CustomerBean;
import com.forage.dao.CustomerDAO;
import com.forage.exception.BadRequestException;
import com.forage.exception.UnauthorizedException;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

public class AdminFilter implements ResourceFilter, ContainerRequestFilter {

	@Context private UriInfo uriInfo;
	@Context private HttpServletRequest forageRequest;
	
	
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		System.out.println("Inside Admin Filter");
		CustomerBean customer = authenticateAdmin(request);
		request.setSecurityContext(new AdminContext(customer));
		return request;
	}

	@Override
	public ContainerRequestFilter getRequestFilter() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private CustomerBean authenticateAdmin(ContainerRequest request) {
		CustomerBean customer = null;
		String authentication = request .getHeaderValue(ContainerRequest.AUTHORIZATION);
		if (authentication == null) {
			throw new UnauthorizedException("AdminFilter.authenticateAdmin","Please send the authorization information");
		}
		if (!authentication.startsWith("Basic ")) {
			return null;
		}

		authentication = authentication.substring("Basic ".length());
		String[] values = new String(Base64.base64Decode(authentication))
				.split(":");
		if (values.length < 2) {
			throw new BadRequestException("AdminFilter.authenticateAdmin","Invalid security credentials"); // "Invalid syntax for username and password"
		}

		String phone = values[0];
		String password = values[1];
		if ((phone == null) || (password == null)) {
			throw new BadRequestException("AdminFilter.authenticateAdmin","Invalid security credentials"); // "Missing username or password"
		}
		
		CustomerDAO customerDAO = new CustomerDAO();
		customer = customerDAO.getCustomer(phone);
		if(!password.equals(customer.getPassword()))
			throw new UnauthorizedException("AdminFilter.authenticateAdmin","Invalid security credentials");
		if(!"A".equals(customer.getActiveFlag()))
			throw new UnauthorizedException("AdminFilter.authenticateAdmin","User is not authorized");
		
		return customer;
	}

	// private ContainerRequest customerVendorCheck(ContainerRequest request){
	// final HttpSession session = forageRequest.getSession();
	// System.out.println(uriInfo.getAbsolutePath());
	// System.out.println(uriInfo.getPath());
	// System.out.println(uriInfo.getPathSegments());
	//
	// Object sessionObj = session.getAttribute("user");
	// if(sessionObj != null && sessionObj instanceof CustomerBean){
	// System.out.println("Its a customer");
	// }else if(sessionObj != null && sessionObj instanceof VendorBean){
	// System.out.println("Its a vendor");
	// }else {
	// request.getSecurityContext();
	// CustomerBean customer = null;
	// VendorBean vendor = null;
	// final String phone = request.getHeaderValue("phone");
	// CustomerDAO customerDAO = new CustomerDAO();
	// customer = customerDAO.getCustomer(phone);
	// if(customer == null || customer.getCustomerId() == null){
	// VendorDAO vendorDAO = new VendorDAO();
	// vendor = vendorDAO.getVendor(phone);
	// if(vendor == null || vendor.getVendorId() == null){
	// throw new PreconditionFailedException("ForageSecurityFilter.filter",
	// "Phone not registered");
	// }else{
	// session.setAttribute("user", vendor);
	// }
	// }else{
	// session.setAttribute("user", customer);
	// }
	// request.setSecurityContext(new ForageSecurityContext(vendor, customer));
	// }
	// return request;
	// }

	
}
