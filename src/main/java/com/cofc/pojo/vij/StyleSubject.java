package com.cofc.pojo.vij;

import java.util.Date;

public class StyleSubject {
	private Integer id;
	private String subjectTitle;
	private String subjectAnswer;
	private Date createTime;
	private Integer subjectType;
	private Integer orderStatus;
	private Integer subjectPage;
	private Integer isModelRoom;//是否样板间
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubjectTitle() {
		return subjectTitle;
	}
	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}
	public String getSubjectAnswer() {
		return subjectAnswer;
	}
	public void setSubjectAnswer(String subjectAnswer) {
		this.subjectAnswer = subjectAnswer;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getSubjectType() {
		return subjectType;
	}
	public void setSubjectType(Integer subjectType) {
		this.subjectType = subjectType;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getSubjectPage() {
		return subjectPage;
	}
	public void setSubjectPage(Integer subjectPage) {
		this.subjectPage = subjectPage;
	}
	public Integer getIsModelRoom() {
		return isModelRoom;
	}
	public void setIsModelRoom(Integer isModelRoom) {
		this.isModelRoom = isModelRoom;
	}
}
