package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ImageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8614434958519182682L;
	
	BigDecimal imageId;
	BigDecimal parentImageId;
	String imageType;
	BigDecimal imageSize;
	String imageCategory;
	String imageName;
	String imagePath;
	String imageTag;
	BigDecimal menuId;
	BigDecimal customerId;
	BigDecimal vendorId;
	
	String approveFlag;
	String enabledFlag;
	
	String summary;
	
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	
	public BigDecimal getImageId() {
		return imageId;
	}
	public void setImageId(BigDecimal imageId) {
		this.imageId = imageId;
	}
	public BigDecimal getParentImageId() {
		return parentImageId;
	}
	public void setParentImageId(BigDecimal parentImageId) {
		this.parentImageId = parentImageId;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public BigDecimal getImageSize() {
		return imageSize;
	}
	public void setImageSize(BigDecimal imageSize) {
		this.imageSize = imageSize;
	}
	public String getImageCategory() {
		return imageCategory;
	}
	public void setImageCategory(String imageCategory) {
		this.imageCategory = imageCategory;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public BigDecimal getMenuId() {
		return menuId;
	}
	public void setMenuId(BigDecimal menuId) {
		this.menuId = menuId;
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
	public String getImageTag() {
		return imageTag;
	}
	public void setImageTag(String imageTag) {
		this.imageTag = imageTag;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
