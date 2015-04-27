package com.forage.bean;

import java.io.Serializable;

public class ErrorBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2224431549783764391L;

	private String operation;
	private int errorCode;
	private String errorMsg;
	
	
	public ErrorBean() {

	}

	public ErrorBean(String operation, int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.operation = operation;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
