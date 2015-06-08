package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TiffinFilterBean extends FilterBean implements Serializable{
	
	
	public TiffinFilterBean() {
		super("TIFFIN");
	}
	
	/**
	 * Based on vendor_filters table 
	 */
	private static final long serialVersionUID = -7606584371349856603L;
	

	String cuisine1;		// attribute1
	String cuisine2;		// attribute2
	String cuisine3;		// attribute3	
	String cuisine4;		// attribute4
	String cuisine5;		// attribute5
	String mealSize1;		// attribute11
	String mealSize2;		// attribute12
	String mealSize3;		// attribute13
	String mealSize4;		// attribute14
	String mealSize5;		// attribute15
	String priceMealSize1;	// attribute21
	String priceMealSize2;	// attribute22
	String priceMealSize3;	// attribute23
	String priceMealSize4;	// attribute24
	String priceMealSize5;	// attribute25
	String breakfastTime;	// attribute31
	String brunchTime;		// attribute32
	String lunchTime;		// attribute33
	String dinnerTime;		// attribute34
	String menuType;		// attribute35
	
	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	
	
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
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
	public String getCuisine5() {
		return cuisine5;
	}
	public void setCuisine5(String cuisine5) {
		this.cuisine5 = cuisine5;
	}
	public String getMealSize1() {
		return mealSize1;
	}
	public void setMealSize1(String mealSize1) {
		this.mealSize1 = mealSize1;
	}
	public String getMealSize2() {
		return mealSize2;
	}
	public void setMealSize2(String mealSize2) {
		this.mealSize2 = mealSize2;
	}
	public String getMealSize3() {
		return mealSize3;
	}
	public void setMealSize3(String mealSize3) {
		this.mealSize3 = mealSize3;
	}
	public String getMealSize4() {
		return mealSize4;
	}
	public void setMealSize4(String mealSize4) {
		this.mealSize4 = mealSize4;
	}
	public String getMealSize5() {
		return mealSize5;
	}
	public void setMealSize5(String mealSize5) {
		this.mealSize5 = mealSize5;
	}
	public String getPriceMealSize1() {
		return priceMealSize1;
	}
	public void setPriceMealSize1(String priceMealSize1) {
		this.priceMealSize1 = priceMealSize1;
	}
	public String getPriceMealSize2() {
		return priceMealSize2;
	}
	public void setPriceMealSize2(String priceMealSize2) {
		this.priceMealSize2 = priceMealSize2;
	}
	public String getPriceMealSize3() {
		return priceMealSize3;
	}
	public void setPriceMealSize3(String priceMealSize3) {
		this.priceMealSize3 = priceMealSize3;
	}
	public String getPriceMealSize4() {
		return priceMealSize4;
	}
	public void setPriceMealSize4(String priceMealSize4) {
		this.priceMealSize4 = priceMealSize4;
	}
	public String getPriceMealSize5() {
		return priceMealSize5;
	}
	public void setPriceMealSize5(String priceMealSize5) {
		this.priceMealSize5 = priceMealSize5;
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
	@Override
	public BigDecimal getVendorNumber() {
		return vendorNumber;
	}
	@Override
	public void setVendorNumber(BigDecimal vendorNumber) {
		super.vendorNumber = vendorNumber;
	}
	@Override
	public String getVendorType() {
		return vendorType;
	}
	@Override
	public void setVendorType(String vendorType) {
		super.vendorType = vendorType;
	}
}
