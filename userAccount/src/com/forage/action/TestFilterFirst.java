package com.forage.action;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;

public class TestFilterFirst implements ResourceFilter, ContainerRequestFilter {

	
	@Override
	public ContainerRequest filter(ContainerRequest arg0) {
		//Filter logic goes here.
		System.out.println("Hello asdf2");
		System.out.println("Hello3");
		return arg0;
	}

	@Override
	public ContainerRequestFilter getRequestFilter() {
		return this;
	}

	@Override
	public ContainerResponseFilter getResponseFilter() {
		return null;
	}
}
