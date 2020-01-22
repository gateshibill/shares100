package com.cofc.pojo;

import java.util.Date;
import java.util.List;

public class TaskCommon {
	private Integer taskId;
	private Integer publisherId;
	private String publishReason;
	private String taskContent;
	private double supplyFee;
	private Integer isAnonymous;
	private Date taskStartTime;
	private Date taskEndTime;
	private Integer taskStatus;
	private String longitude;
	private String latitude;
	private String lacationName;
	private String taskImages;
	private Integer loginPlat;
	private String satisfiedService;
	private Integer competedCount;

	// 连表
	private UserCommon usercommon;
	private List<TaskCompete> competeList;
	private Integer hasCompeted;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublishReason() {
		return publishReason;
	}

	public void setPublishReason(String publishReason) {
		this.publishReason = publishReason;
	}

	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public double getSupplyFee() {
		return supplyFee;
	}

	public void setSupplyFee(double supplyFee) {
		this.supplyFee = supplyFee;
	}

	public Integer getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(Integer isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public Integer getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(Integer taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLacationName() {
		return lacationName;
	}

	public void setLacationName(String lacationName) {
		this.lacationName = lacationName;
	}

	public String getTaskImages() {
		return taskImages;
	}

	public void setTaskImages(String taskImages) {
		this.taskImages = taskImages;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public String getSatisfiedService() {
		return satisfiedService;
	}

	public void setSatisfiedService(String satisfiedService) {
		this.satisfiedService = satisfiedService;
	}

	@Override
	public String toString() {
		return "TaskCommon [taskId=" + taskId + ", publisherId=" + publisherId + ", publishReason=" + publishReason
				+ ", taskContent=" + taskContent + ", supplyFee=" + supplyFee + ", isAnonymous=" + isAnonymous
				+ ", taskStartTime=" + taskStartTime + ", taskEndTime=" + taskEndTime + ", taskStatus=" + taskStatus
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", lacationName=" + lacationName
				+ ", taskImages=" + taskImages + ", loginPlat=" + loginPlat + ", satisfiedService=" + satisfiedService
				+ ", competedCount=" + competedCount + ", usercommon=" + usercommon + ", competeList=" + competeList
				+ ", hasCompeted=" + hasCompeted + "]";
	}

	public UserCommon getUsercommon() {
		return usercommon;
	}

	public void setUsercommon(UserCommon usercommon) {
		this.usercommon = usercommon;
	}

	public List<TaskCompete> getCompeteList() {
		return competeList;
	}

	public void setCompeteList(List<TaskCompete> competeList) {
		this.competeList = competeList;
	}

	public Integer getHasCompeted() {
		return hasCompeted;
	}

	public void setHasCompeted(Integer hasCompeted) {
		this.hasCompeted = hasCompeted;
	}

	public Integer getCompetedCount() {
		return competedCount;
	}

	public void setCompetedCount(Integer competedCount) {
		this.competedCount = competedCount;
	}

}
