package com.forage.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NotificationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -143746020572234044L;

	BigDecimal notificationId;
	BigDecimal sendorId;
	BigDecimal recepientId;
	String notificationType;
	
	String title;
	String bodyText;
	String href;
	Date lastRead;
	String hidden;
	Date validTill;

	BigDecimal createdBy;
	Date createdDate;
	BigDecimal lastUpdatedBy;
	Date lastUpdateDate;
	public BigDecimal getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(BigDecimal notificationId) {
		this.notificationId = notificationId;
	}
	public BigDecimal getSendorId() {
		return sendorId;
	}
	public void setSendorId(BigDecimal sendorId) {
		this.sendorId = sendorId;
	}
	public BigDecimal getRecepientId() {
		return recepientId;
	}
	public void setRecepientId(BigDecimal recepientId) {
		this.recepientId = recepientId;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBodyText() {
		return bodyText;
	}
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public Date getLastRead() {
		return lastRead;
	}
	public void setLastRead(Date lastRead) {
		this.lastRead = lastRead;
	}
	public String getHidden() {
		return hidden;
	}
	public void setHidden(String hidden) {
		this.hidden = hidden;
	}
	public Date getValidTill() {
		return validTill;
	}
	public void setValidTill(Date validTill) {
		this.validTill = validTill;
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