package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LookupTypeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7955472799366727225L;

	String lookupType;
	String vendorType;
	
	String attribute1;
	String attribute2;
	String attribute3;
	String attribute4;
	String attribute5;
	
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	BigDecimal lastUpdateLogin;
	
	List <LookupValueBean> lookupValues;
	
	public String getLookupType() {
		return lookupType;
	}
	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}
	public BigDecimal getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigDecimal createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public BigDecimal getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(BigDecimal lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public BigDecimal getLastUpdateLogin() {
		return lastUpdateLogin;
	}
	public void setLastUpdateLogin(BigDecimal lastUpdateLogin) {
		this.lastUpdateLogin = lastUpdateLogin;
	}
	public List<LookupValueBean> getLookupValues() {
		return lookupValues;
	}
	public void setLookupValues(List<LookupValueBean> lookupValues) {
		this.lookupValues = lookupValues;
	}
	public String getVendorType() {
		return vendorType;
	}
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}
	public String getAttribute1() {
		return attribute1;
	}
	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}
	public String getAttribute2() {
		return attribute2;
	}
	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}
	public String getAttribute3() {
		return attribute3;
	}
	public void setAttribute3(String attribute3) {
		this.attribute3 = attribute3;
	}
	public String getAttribute4() {
		return attribute4;
	}
	public void setAttribute4(String attribute4) {
		this.attribute4 = attribute4;
	}
	public String getAttribute5() {
		return attribute5;
	}
	public void setAttribute5(String attribute5) {
		this.attribute5 = attribute5;
	}
	
}
