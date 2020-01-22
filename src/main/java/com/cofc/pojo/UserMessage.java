package com.cofc.pojo;

import java.util.Date;

public class UserMessage {
	private Integer messageId;
	private String content;
	private Integer type;
	private Integer userId;
	private Integer loginPlat;
	private Date createTime;
	private Integer isRemove;
	private Integer isRead;
	private Integer score;
	private Integer isTag; //0:消耗，1：增加
	private Double token;
	
	public Integer getMessageId() {
		return messageId;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public Integer getIsRemove() {
		return isRemove;
	}
	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}
	public Integer getIsRead() {
		return isRead;
	}
	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getIsTag() {
		return isTag;
	}
	public void setIsTag(Integer isTag) {
		this.isTag = isTag;
	}
	public Double getToken() {
		return token;
	}
	public void setToken(Double token) {
		this.token = token;
	}
}
