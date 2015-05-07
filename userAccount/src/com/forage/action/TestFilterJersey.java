package com.forage.action;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import com.forage.bean.CustomerBean;
import com.forage.intercept.AdminFilter;
import com.forage.intercept.TestFilterFirst;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/helloworld")
public class TestFilterJersey {
	@GET
	@Produces("text/plain")
	@ResourceFilters({ AdminFilter.class, TestFilterFirst.class})
	public String sayHello(@Context SecurityContext sc ) {
		if(sc.isUserInRole("ADMIN")){
			System.out.println("User is in admin role");	
		}
		CustomerBean admin = (CustomerBean)sc.getUserPrincipal();
		System.out.println("Admin Name = " + admin.getName());
		System.out.println("Hello");
		return "Hello World";
	}
}
