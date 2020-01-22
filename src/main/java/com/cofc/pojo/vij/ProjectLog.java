package com.cofc.pojo.vij;

import java.util.Date;

public class ProjectLog {

	private Integer plogId;
	private Integer projectId;
	private Integer status;
	private String title;//标题
	private String remark;
	private String detail;
	private Integer userId;
	private Date createTime;
	
	public Integer getPlogId() {
		return plogId;
	}
	public void setPlogId(Integer plogId) {
		this.plogId = plogId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
