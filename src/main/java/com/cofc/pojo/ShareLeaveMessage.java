package com.cofc.pojo;

import java.util.Date;

public class ShareLeaveMessage {
	private Integer messageId;
	private String messageDesc;
	private Integer userId;
	private Integer shareId;
	private Date messageTime;
	
	private UserCommon user;
	
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public String getMessageDesc() {
		return messageDesc;
	}
	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getShareId() {
		return shareId;
	}
	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}
	public Date getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
}
