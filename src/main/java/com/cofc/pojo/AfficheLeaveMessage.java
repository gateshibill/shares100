package com.cofc.pojo;

import java.util.Date;

public class AfficheLeaveMessage {
	private Integer messageId;
	private Integer afficheId;
	private Integer userId;
	private String messageDesc;
	private Date createTime;

	private UserCommon user;
	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getAfficheId() {
		return afficheId;
	}

	public void setAfficheId(Integer afficheId) {
		this.afficheId = afficheId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMessageDesc() {
		return messageDesc;
	}

	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "AfficheLeaveMessage [messageId=" + messageId + ", afficheId=" + afficheId + ", userId=" + userId
				+ ", messageDesc=" + messageDesc + ", createTime=" + createTime + "]";
	}

	public UserCommon getUser() {
		return user;
	}

	public void setUser(UserCommon user) {
		this.user = user;
	}
}
