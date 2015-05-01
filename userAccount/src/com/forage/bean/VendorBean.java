package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VendorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 557431948904918619L;
	
	BigDecimal vendorId;
	BigDecimal parentVendorId;
	String name;
	String phoneNumber;
	Double lastGPSLatitude;
	Double lastGPSLongitude;
	String password;
	String email;
	String facebookUniqueId;
	String twitterUniqueId;
	String googleUniqueId;
	
	BigDecimal address1;
	BigDecimal address2;
	BigDecimal address3;
	
	BigDecimal profileImageId;
	
	AddressBean addrBean1;
	AddressBean addrBean2;
	AddressBean addrBean3;
	
	String activeFlag;
	String approveFlag;
	
	String status;
	String menuType;
	String cuisine;
	String cuisine2;
	String cuisine3;
	String cuisine4;
	
	BigDecimal minPriceMeal;
	BigDecimal maxPriceMeal;
	
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	Date lastLoginDate;
	
	List<MenuBean> menuList;
	
	public BigDecimal getVendorId() {
		return vendorId;
	}
	public void setVendorId(BigDecimal vendorId) {
		this.vendorId = vendorId;
	}
	public BigDecimal getParentVendorId() {
		return parentVendorId;
	}
	public void setParentVendorId(BigDecimal parentVendorId) {
		this.parentVendorId = parentVendorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Double getLastGPSLatitude() {
		return lastGPSLatitude;
	}
	public void setLastGPSLatitude(Double lastGPSLatitude) {
		this.lastGPSLatitude = lastGPSLatitude;
	}
	public Double getLastGPSLongitude() {
		return lastGPSLongitude;
	}
	public void setLastGPSLongitude(Double lastGPSLongitude) {
		this.lastGPSLongitude = lastGPSLongitude;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFacebookUniqueId() {
		return facebookUniqueId;
	}
	public void setFacebookUniqueId(String facebookUniqueId) {
		this.facebookUniqueId = facebookUniqueId;
	}
	public String getTwitterUniqueId() {
		return twitterUniqueId;
	}
	public void setTwitterUniqueId(String twitterUniqueId) {
		this.twitterUniqueId = twitterUniqueId;
	}
	public String getGoogleUniqueId() {
		return googleUniqueId;
	}
	public void setGoogleUniqueId(String googleUniqueId) {
		this.googleUniqueId = googleUniqueId;
	}
	public BigDecimal getAddress1() {
		return address1;
	}
	public void setAddress1(BigDecimal address1) {
		this.address1 = address1;
	}
	public BigDecimal getAddress2() {
		return address2;
	}
	public void setAddress2(BigDecimal address2) {
		this.address2 = address2;
	}
	public BigDecimal getAddress3() {
		return address3;
	}
	public void setAddress3(BigDecimal address3) {
		this.address3 = address3;
	}
	public AddressBean getAddrBean1() {
		return addrBean1;
	}
	public void setAddrBean1(AddressBean addrBean1) {
		this.addrBean1 = addrBean1;
	}
	public AddressBean getAddrBean2() {
		return addrBean2;
	}
	public void setAddrBean2(AddressBean addrBean2) {
		this.addrBean2 = addrBean2;
	}
	public AddressBean getAddrBean3() {
		return addrBean3;
	}
	public void setAddrBean3(AddressBean addrBean3) {
		this.addrBean3 = addrBean3;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getApproveFlag() {
		return approveFlag;
	}
	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getCuisine() {
		return cuisine;
	}
	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
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
	public BigDecimal getMinPriceMeal() {
		return minPriceMeal;
	}
	public void setMinPriceMeal(BigDecimal minPriceMeal) {
		this.minPriceMeal = minPriceMeal;
	}
	public BigDecimal getMaxPriceMeal() {
		return maxPriceMeal;
	}
	public void setMaxPriceMeal(BigDecimal maxPriceMeal) {
		this.maxPriceMeal = maxPriceMeal;
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
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public List<MenuBean> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<MenuBean> menuList) {
		this.menuList = menuList;
	}
	public BigDecimal getProfileImageId() {
		return profileImageId;
	}
	public void setProfileImageId(BigDecimal profileImageId) {
		this.profileImageId = profileImageId;
	}
	
}
