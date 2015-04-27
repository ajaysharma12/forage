package com.forage.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.ErrorBean;

public class BadRequestException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3983274363702117079L;

	/**
	 * Create a HTTP 400 (Bad Request) exception.
	 */
	public BadRequestException() {
		super(Response.status(Response.Status.BAD_REQUEST).build());
	}

	/**
	 * Create a HTTP 400 (Bad Request) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 400 Bad Request.
	 */
	public BadRequestException(String operation, String message) {
		super(Response
				.status(Response.Status.BAD_REQUEST)
				.entity(new ErrorBean(operation, Response.Status.BAD_REQUEST.getStatusCode(), message ))
				.type(MediaType.APPLICATION_JSON)
				.build());
	}
}
