package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class CustomerBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9211991778571145655L;

	public CustomerBean(){
		
	}
		
	BigDecimal customerId;
	String firstName;
	String lastName;
	String phoneNumber;
	String gender;
	Double lastGPSLatitude;
	Double lastGPSLongitude;
	String password;
	String email;
	String facebookUniqueId;
	String twitterUniqueId;
	String googleUniqueId;
	
	BigDecimal address;
	BigDecimal address2;
	BigDecimal address3;
	BigDecimal shippingAddress;
	
	AddressBean addrBean1;
	AddressBean addrBean2;
	AddressBean addrBean3;
	AddressBean shipAddrBean;
	
	ArrayList<FavoriteBean> favoriteVendors;
	
	String activeFlag;
	String approveFlag;
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	Date lastLoginDate;
	
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public BigDecimal getAddress() {
		return address;
	}
	public void setAddress(BigDecimal address) {
		this.address = address;
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
	public BigDecimal getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(BigDecimal shippingAddress) {
		this.shippingAddress = shippingAddress;
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
	public AddressBean getShipAddrBean() {
		return shipAddrBean;
	}
	public void setShipAddrBean(AddressBean shipAddrBean) {
		this.shipAddrBean = shipAddrBean;
	}
	public ArrayList<FavoriteBean> getFavoriteVendors() {
		return favoriteVendors;
	}
	public void setFavoriteVendors(ArrayList<FavoriteBean> favoriteVendors) {
		this.favoriteVendors = favoriteVendors;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
}
