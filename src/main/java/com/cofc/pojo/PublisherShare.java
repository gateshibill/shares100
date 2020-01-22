package com.cofc.pojo;

import java.util.Date;

public class PublisherShare {

	private Integer shareId;
	private String shareTitle;
	private String shareContent;
	private String shareImage;
	private Date shareTime;
	private Date updateTime;
	private Integer loginPlat;
	private Integer shareUserId;
	private int praiseCount;
	private int readCount;
	private int collectionCount;
	
	private String publisherName;
	
	private UserCommon user;
	private ApplicationCommon app;
	
	public Integer getShareId() {
		return shareId;
	}
	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public String getShareContent() {
		return shareContent;
	}
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	public String getShareImage() {
		return shareImage;
	}
	public void setShareImage(String shareImage) {
		this.shareImage = shareImage;
	}
	public Date getShareTime() {
		return shareTime;
	}
	public void setShareTime(Date shareTime) {
		this.shareTime = shareTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getShareUserId() {
		return shareUserId;
	}
	public void setShareUserId(Integer shareUserId) {
		this.shareUserId = shareUserId;
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
	public ApplicationCommon getApp() {
		return app;
	}
	public void setApp(ApplicationCommon app) {
		this.app = app;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getCollectionCount() {
		return collectionCount;
	}
	public void setCollectionCount(int collectionCount) {
		this.collectionCount = collectionCount;
	}
}
