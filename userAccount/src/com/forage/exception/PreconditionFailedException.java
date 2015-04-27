package com.forage.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.ErrorBean;

public class PreconditionFailedException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8572768302767456327L;

	/**
	 * Create a HTTP 412 (PRECONDITION_FAILED) exception.
	 */
	public PreconditionFailedException() {
		super(Response.status(Response.Status.PRECONDITION_FAILED).build());
	}

	/**
	 * Create a HTTP 412 (PRECONDITION_FAILED) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 404 response.
	 */
	public PreconditionFailedException(String operation, String message) {
		super(Response
				.status(Response.Status.PRECONDITION_FAILED)
				.entity(new ErrorBean(operation, Response.Status.PRECONDITION_FAILED.getStatusCode(), message ))
				.type(MediaType.APPLICATION_JSON).build());
	}
}
