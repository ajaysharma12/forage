package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReviewBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 761670380960530615L;
	
	BigDecimal reviewId;
	BigDecimal customerId;
	BigDecimal vendorId;
	String inTimeDelivery;
	String foodQuality;
	String remarks;
	String approveFlag;
	String enabledFlag;
	
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	
	public BigDecimal getReviewId() {
		return reviewId;
	}
	public void setReviewId(BigDecimal reviewId) {
		this.reviewId = reviewId;
	}
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getVendorId() {
		return vendorId;
	}
	public void setVendorId(BigDecimal vendorId) {
		this.vendorId = vendorId;
	}
	public String getInTimeDelivery() {
		return inTimeDelivery;
	}
	public void setInTimeDelivery(String inTimeDelivery) {
		this.inTimeDelivery = inTimeDelivery;
	}
	public String getFoodQuality() {
		return foodQuality;
	}
	public void setFoodQuality(String foodQuality) {
		this.foodQuality = foodQuality;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getApproveFlag() {
		return approveFlag;
	}
	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
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
	
}