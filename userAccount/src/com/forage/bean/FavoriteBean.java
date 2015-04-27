package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class FavoriteBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1147363063623535552L;
	BigDecimal favoriteId;
	BigDecimal customerId;
	BigDecimal vendorId;
	String starRating;
	String favoriteFlag;
	String blacklistFlag;
	String searchShowFlag;
	
	String approveFlag;
	String enabledFlag;
	
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	public BigDecimal getFavoriteId() {
		return favoriteId;
	}
	public void setFavoriteId(BigDecimal favoriteId) {
		this.favoriteId = favoriteId;
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
	public String getStarRating() {
		return starRating;
	}
	public void setStarRating(String starRating) {
		this.starRating = starRating;
	}
	public String getFavoriteFlag() {
		return favoriteFlag;
	}
	public void setFavoriteFlag(String favoriteFlag) {
		this.favoriteFlag = favoriteFlag;
	}
	public String getBlacklistFlag() {
		return blacklistFlag;
	}
	public void setBlacklistFlag(String blacklistFlag) {
		this.blacklistFlag = blacklistFlag;
	}
	public String getSearchShowFlag() {
		return searchShowFlag;
	}
	public void setSearchShowFlag(String searchShowFlag) {
		this.searchShowFlag = searchShowFlag;
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
	
		
}
