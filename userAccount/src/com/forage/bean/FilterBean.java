package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class FilterBean  implements Serializable {
	
	public FilterBean(String vendorType) {
		super();
		this.vendorType = vendorType;
	}
	
	/**
	 * FilterBean 
	 */
	private static final long serialVersionUID = -5751612311226290279L;
	
	public BigDecimal vendorNumber;
	public String vendorType;
	
	public BigDecimal getVendorNumber() {
		return vendorNumber;
	}
	public void setVendorNumber(BigDecimal vendorNumber) {
		this.vendorNumber = vendorNumber;
	}
	public String getVendorType() {
		return vendorType;
	}
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}

	
}
