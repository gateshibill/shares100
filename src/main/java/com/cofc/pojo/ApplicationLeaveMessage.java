package com.cofc.pojo;

import java.util.Date;

public class ApplicationLeaveMessage {
	private Integer messageId;
	private Integer userId;
	private String userRealName;
	private String userPhone;
	private String messageContent;
	private Integer loginPlat;
	private Date createTime;
	private Integer isEffect;
	private String headImage;
    private ApplicationReplyMessage replyMessage;
	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
    
	public ApplicationReplyMessage getReplyMessage() {
		return replyMessage;
	}

	public void setReplyMessage(ApplicationReplyMessage replyMessage) {
		this.replyMessage = replyMessage;
	}

	@Override
	public String toString() {
		return "ApplicationLeaveMessage [messageId=" + messageId + ", userId=" + userId + ", userRealName="
				+ userRealName + ", userPhone=" + userPhone + ", messageContent=" + messageContent + ", loginPlat="
				+ loginPlat + ", createTime=" + createTime + ", headImage=" + headImage + "]";
	}

}
