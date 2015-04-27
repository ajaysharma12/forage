package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AddressBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5026191365335647208L;
	BigDecimal addressId;
	
	String line1;
	String line2;
	String line3;
	String line4;
	String line5;
	String line6;
	String line7;
	String line8;
	String line9;
	String line10;
	String locality;
	
	Double latitude;
	Double longitude;
	
	String town;
	String city;
	String stateProvince;
	String pincode;
	String country;
	String tag;
	
	String approveFlag;
	String enabledFlag;
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	
	public BigDecimal getAddressId() {
		return addressId;
	}
	public void setAddressId(BigDecimal addressId) {
		this.addressId = addressId;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public String getLine3() {
		return line3;
	}
	public void setLine3(String line3) {
		this.line3 = line3;
	}
	public String getLine4() {
		return line4;
	}
	public void setLine4(String line4) {
		this.line4 = line4;
	}
	public String getLine5() {
		return line5;
	}
	public void setLine5(String line5) {
		this.line5 = line5;
	}
	public String getLine6() {
		return line6;
	}
	public void setLine6(String line6) {
		this.line6 = line6;
	}
	public String getLine7() {
		return line7;
	}
	public void setLine7(String line7) {
		this.line7 = line7;
	}
	public String getLine8() {
		return line8;
	}
	public void setLine8(String line8) {
		this.line8 = line8;
	}
	public String getLine9() {
		return line9;
	}
	public void setLine9(String line9) {
		this.line9 = line9;
	}
	public String getLine10() {
		return line10;
	}
	public void setLine10(String line10) {
		this.line10 = line10;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
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
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
}
