package com.cofc.pojo;

import java.util.Date;

public class ScoreRecord {
	private Integer recordId;
	private Integer score;
	private Integer userId;
	private Integer loginPlat;
	private String content;
	private Integer isRemove;
	private Date createTime;
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsRemove() {
		return isRemove;
	}
	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
