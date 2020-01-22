package com.cofc.pojo;

import java.util.Date;

public class UserActivity {
	private Integer recoredId;
	private Integer recoredUser;
	private Integer activityId;
	private Integer activityCount;
	private Integer leftCount;
	private Date joinTime;
	private Date updateTime;
	private Integer activityStatus;

	public Integer getRecoredId() {
		return recoredId;
	}

	public void setRecoredId(Integer recoredId) {
		this.recoredId = recoredId;
	}

	public Integer getRecoredUser() {
		return recoredUser;
	}

	public void setRecoredUser(Integer recoredUser) {
		this.recoredUser = recoredUser;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public Integer getActivityCount() {
		return activityCount;
	}

	public void setActivityCount(Integer activityCount) {
		this.activityCount = activityCount;
	}

	public Integer getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public Integer getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "UserActivity [recoredId=" + recoredId + ", recoredUser=" + recoredUser + ", activityId=" + activityId
				+ ", activityCount=" + activityCount + ", leftCount=" + leftCount + ", joinTime=" + joinTime
				+ ", updateTime=" + updateTime + ", activityStatus=" + activityStatus + "]";
	}

}
