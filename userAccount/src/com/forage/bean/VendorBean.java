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
	String vendorType;
	String name;
	String contactPerson;
	String phoneNumber;
	String phoneNumber2;
	String phoneNumber3;
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
	
	String summary;
	
	String status;
	
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	Date lastLoginDate;
	
	//calculated columns
	BigDecimal searchDistance;
	
	// Service offered by the vendor
	List<? extends ServiceBean> serviceList;
	
	// Vendor filters
	FilterBean filterBean;
	
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
	public BigDecimal getProfileImageId() {
		return profileImageId;
	}
	public void setProfileImageId(BigDecimal profileImageId) {
		this.profileImageId = profileImageId;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getPhoneNumber2() {
		return phoneNumber2;
	}
	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}
	public String getPhoneNumber3() {
		return phoneNumber3;
	}
	public void setPhoneNumber3(String phoneNumber3) {
		this.phoneNumber3 = phoneNumber3;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public BigDecimal getSearchDistance() {
		return searchDistance;
	}
	public void setSearchDistance(BigDecimal searchDistance) {
		this.searchDistance = searchDistance;
	}
	public FilterBean getFilterBean() {
		return filterBean;
	}
	public void setFilterBean(FilterBean filterBean) {
		this.filterBean = filterBean;
	}
	public String getVendorType() {
		return vendorType;
	}
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}
	public List<? extends ServiceBean> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<? extends ServiceBean> serviceList) {
		this.serviceList = serviceList;
	}
}