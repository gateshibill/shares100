package com.cofc.pojo.vij;

import java.util.Date;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.UserCommon;

public class SystemMessage {
	private Integer mesId;
	private String title;
	private String content;
	private Integer type;
	private Integer objectId;
	private Date createTime;
	private Integer userId;
	private Integer isRead;
	private Integer loginPlat;
	
	private ApplicationCommon application;
	private UserCommon user;
	public ApplicationCommon getApplication() {
		return application;
	}
	public void setApplication(ApplicationCommon application) {
		this.application = application;
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
	public Integer getMesId() {
		return mesId;
	}
	public void setMesId(Integer mesId) {
		this.mesId = mesId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getObjectId() {
		return objectId;
	}
	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	@Override
	public String toString() {
		return "SystemMessage [mesId=" + mesId + ", title=" + title + ", content=" + content + ", type=" + type
				+ ", objectId=" + objectId + ", createTime=" + createTime + ", userId=" + userId + ", isRead=" + isRead
				+ ", loginPlat=" + loginPlat + "]";
	}
}
