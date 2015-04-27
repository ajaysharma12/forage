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
	
}
