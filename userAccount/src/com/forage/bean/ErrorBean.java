package com.forage.bean;

import java.io.Serializable;

public class ErrorBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2224431549783764391L;

	private String action;
	private int status;
	private String message;
	
	
	public ErrorBean() {

	}

	public ErrorBean(String action, int status, String message) {
		this.action = action;
		this.status = status;
		this.message = message;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
