package com.cofc.pojo.vij;

import java.util.Date;

public class StyleSubjectAnswer {
	private Integer id;
	private Integer projectId;
	private String answerList;
	private Integer isDeal;
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getAnswerList() {
		return answerList;
	}
	public void setAnswerList(String answerList) {
		this.answerList = answerList;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
