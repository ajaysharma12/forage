package com.forage.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.forage.bean.ErrorBean;

public class UnauthorizedException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6776843462695858138L;
	
	/**
	 * Create a HTTP 401 Unauthorized exception.
	 */
	public UnauthorizedException() {
		super(Response.status(Response.Status.UNAUTHORIZED).build());
	}

	/**
	 * Create a HTTP 403 (Forbidden) exception.
	 * 
	 * @param message
	 *            the String that is the entity of the 401 Unauthorized.
	 */
	public UnauthorizedException(String operation, String message) {
		super(Response
				.status(Response.Status.UNAUTHORIZED)
				.entity(new ErrorBean(operation, Response.Status.UNAUTHORIZED.getStatusCode(), message ))
				.type(MediaType.APPLICATION_JSON)
				.build());
	}

}
