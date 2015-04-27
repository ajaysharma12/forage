package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class UserPreferenceBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2288184178637867547L;

	BigDecimal preferenceId;
	BigDecimal userId;
	BigDecimal searchRadius;
	BigDecimal quantity;
	
	String breakfastTime;
	String brunchTime;
	String lunchTime;
	String dinnerTime;
	
	String cuisine1;
	String cuisine2;
	String cuisine3;
	String cuisine4;
	
	BigDecimal minPrice;
	BigDecimal maxPrice;
	
	String newVendorAlert;
	String vendorUpdate;
	String menuUpdate;
	String reviewUpdate;
	String photoUpdate;
	String friendUpdate;
	String appUpdate;
	
	String approveFlag;
	String enabledFlag;
	
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	public BigDecimal getPreferenceId() {
		return preferenceId;
	}
	public void setPreferenceId(BigDecimal preferenceId) {
		this.preferenceId = preferenceId;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public BigDecimal getSearchRadius() {
		return searchRadius;
	}
	public void setSearchRadius(BigDecimal searchRadius) {
		this.searchRadius = searchRadius;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getBreakfastTime() {
		return breakfastTime;
	}
	public void setBreakfastTime(String breakfastTime) {
		this.breakfastTime = breakfastTime;
	}
	public String getBrunchTime() {
		return brunchTime;
	}
	public void setBrunchTime(String brunchTime) {
		this.brunchTime = brunchTime;
	}
	public String getLunchTime() {
		return lunchTime;
	}
	public void setLunchTime(String lunchTime) {
		this.lunchTime = lunchTime;
	}
	public String getDinnerTime() {
		return dinnerTime;
	}
	public void setDinnerTime(String dinnerTime) {
		this.dinnerTime = dinnerTime;
	}
	public String getCuisine1() {
		return cuisine1;
	}
	public void setCuisine1(String cuisine1) {
		this.cuisine1 = cuisine1;
	}
	public String getCuisine2() {
		return cuisine2;
	}
	public void setCuisine2(String cuisine2) {
		this.cuisine2 = cuisine2;
	}
	public String getCuisine3() {
		return cuisine3;
	}
	public void setCuisine3(String cuisine3) {
		this.cuisine3 = cuisine3;
	}
	public String getCuisine4() {
		return cuisine4;
	}
	public void setCuisine4(String cuisine4) {
		this.cuisine4 = cuisine4;
	}
	public BigDecimal getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}
	public BigDecimal getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}
	public String getNewVendorAlert() {
		return newVendorAlert;
	}
	public void setNewVendorAlert(String newVendorAlert) {
		this.newVendorAlert = newVendorAlert;
	}
	public String getVendorUpdate() {
		return vendorUpdate;
	}
	public void setVendorUpdate(String vendorUpdate) {
		this.vendorUpdate = vendorUpdate;
	}
	public String getMenuUpdate() {
		return menuUpdate;
	}
	public void setMenuUpdate(String menuUpdate) {
		this.menuUpdate = menuUpdate;
	}
	public String getReviewUpdate() {
		return reviewUpdate;
	}
	public void setReviewUpdate(String reviewUpdate) {
		this.reviewUpdate = reviewUpdate;
	}
	public String getPhotoUpdate() {
		return photoUpdate;
	}
	public void setPhotoUpdate(String photoUpdate) {
		this.photoUpdate = photoUpdate;
	}
	public String getFriendUpdate() {
		return friendUpdate;
	}
	public void setFriendUpdate(String friendUpdate) {
		this.friendUpdate = friendUpdate;
	}
	public String getAppUpdate() {
		return appUpdate;
	}
	public void setAppUpdate(String appUpdate) {
		this.appUpdate = appUpdate;
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
