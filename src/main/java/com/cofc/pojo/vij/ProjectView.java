package com.cofc.pojo.vij;

import java.util.Date;

public class ProjectView {

	private Integer viewId;
	private Integer projectId;
	private  String content;
	private Integer viewType;
	private Date createTime;
	private Integer isDeal;
	public Integer getViewId() {
		return viewId;
	}
	public void setViewId(Integer viewId) {
		this.viewId = viewId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getViewType() {
		return viewType;
	}
	public void setViewType(Integer viewType) {
		this.viewType = viewType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}

}
