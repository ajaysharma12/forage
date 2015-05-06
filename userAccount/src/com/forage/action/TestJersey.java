package com.forage.action;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.sun.jersey.spi.container.ResourceFilters;

@Path("/helloworld")
public class TestJersey {
	@GET
	@Produces("text/plain")
	@ResourceFilters({ TestFilterFirst.class })
	public String sayHello() {
		System.out.println("Hello");
		return "Hello World";
	}
}
