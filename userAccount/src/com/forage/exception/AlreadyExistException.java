package com.forage.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.ErrorBean;
import com.sun.jersey.api.Responses;

public class AlreadyExistException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6809310339676788418L;

	/**
	 * Create a HTTP 409 (Conflict) exception.
	 */
	public AlreadyExistException() {
		super(Responses.conflict().build());
	}

	/**
	 * Create a HTTP 409 (Conflict) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 409 response.
	 */
	public AlreadyExistException(String operation, String message) {
		super(Response
				.status(Response.Status.CONFLICT)
				.entity(new ErrorBean(operation, Response.Status.CONFLICT.getStatusCode(), message ))
				.type(MediaType.APPLICATION_JSON)
				.build());
	}

}
