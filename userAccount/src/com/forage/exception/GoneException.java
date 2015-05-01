package com.forage.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.ErrorBean;

public class GoneException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6755928386648383949L;

	/**
	 * Create a HTTP 410 (Gone) exception.
	 */
	public GoneException() {
		super(Response
				.status(Response.Status.GONE)
				.type(MediaType.APPLICATION_JSON)
				.build());
	}

	/**
	 * Create a HTTP 404 (Not Found) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 410 Gone response.
	 */
	public GoneException(String operation, String message) {
		super(Response
				.status(Response.Status.GONE)
				.entity(new ErrorBean(operation, Response.Status.GONE.getStatusCode(), message ))
				.type(MediaType.APPLICATION_JSON)
				.build());
	}
}
