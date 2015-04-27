package com.forage.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.ErrorBean;

public class ForbiddenException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 427564307231244635L;

	
	/**
	 * Create a HTTP 403 (Forbidden) exception.
	 */
	public ForbiddenException() {
		super(Response.status(Response.Status.FORBIDDEN).build());
	}

	/**
	 * Create a HTTP 403 (Forbidden) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 403 (Forbidden).
	 */
	public ForbiddenException(String operation, String message) {
		super(Response
				.status(Response.Status.FORBIDDEN)
				.entity(new ErrorBean(operation, Response.Status.FORBIDDEN.getStatusCode(), message ))
				.type(MediaType.APPLICATION_JSON)
				.build());
	}
}
