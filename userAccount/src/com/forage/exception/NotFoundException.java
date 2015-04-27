package com.forage.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.ErrorBean;
import com.sun.jersey.api.Responses;

public class NotFoundException extends WebApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1277543928048466053L;

	/**
	 * Create a HTTP 404 (Not Found) exception.
	 */
	public NotFoundException() {
		super(Responses.notFound().build());
	}

	/**
	 * Create a HTTP 404 (Not Found) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 404 response.
	 */
	public NotFoundException(String operation, String message) {
		super(Response
				.status(Response.Status.NOT_FOUND)
				.entity(new ErrorBean(operation, Response.Status.NOT_FOUND.getStatusCode(), message ))
				.type(MediaType.APPLICATION_JSON)
				.build());
	}
}
