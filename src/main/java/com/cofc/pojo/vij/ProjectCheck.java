package com.cofc.pojo.vij;

import java.util.Date;
import java.util.List;

/**
 * 验收大类表
 * @author Administrator
 *
 */
public class ProjectCheck {

	private Integer checkId;
	private String checkName;
	private Integer projectId;
	private Date checkTime;
	private Integer checkStatus;
	private String checkUserStr;
	private Project project;
	private String name;
	private List<ProjectCheckItem> pCheckItems;
	

	public List<ProjectCheckItem> getpCheckItems() {
		return pCheckItems;
	}
	public void setpCheckItems(List<ProjectCheckItem> pCheckItems) {
		this.pCheckItems = pCheckItems;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getCheckId() {
		return checkId;
	}
	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getCheckUserStr() {
		return checkUserStr;
	}
	public void setCheckUserStr(String checkUserStr) {
		this.checkUserStr = checkUserStr;
	}
	
	
}
