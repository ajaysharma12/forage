package com.forage.intercept;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.forage.bean.CustomerBean;
import com.forage.exception.NotFoundException;
import com.forage.exception.UnauthorizedException;

public class AdminContext implements SecurityContext {
	CustomerBean customer;

	public AdminContext(CustomerBean customer) {
		// one of the user has to be null
		if (customer == null)
			throw new UnauthorizedException();
		this.customer = customer;
	}

	@Override
	public String getAuthenticationScheme() {
		// TODO Auto-generated method stub
		return SecurityContext.BASIC_AUTH;
//		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		if (this.customer == null)
			throw new NotFoundException("AdminContext.getUserPrincipal", "Security Context Not Initialized");
		return this.customer;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return (null != customer) ? customer.getActiveFlag().equals("A") : false;
	}

	@Override
	public boolean isUserInRole(String role) {
		if (this.customer == null) {
			throw new NotFoundException("AdminContext.isUserInRole", "Security Context Not Initialized");
		}

		if (this.customer != null && this.customer.getActiveFlag().equals("A") && role.equalsIgnoreCase("ADMIN")) {
			return true;
		} 

		return false;
	}
}
