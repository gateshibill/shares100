package com.cofc.pojo;

import java.util.Date;

public class PrizeRecord {
	private Integer recordId;
	private Integer userId;
	private Integer prizeId;
	private String prizeName;
	private Date prizeTime;
	private Integer activityId;
	private double currFee;
	private double prizeFee;
	private PrizeCommon prizeInfo;
	private UserCommon userInfo;
	private Integer status;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Date getPrizeTime() {
		return prizeTime;
	}

	public void setPrizeTime(Date prizeTime) {
		this.prizeTime = prizeTime;
	}

	public PrizeCommon getPrizeInfo() {
		return prizeInfo;
	}

	public void setPrizeInfo(PrizeCommon prizeInfo) {
		this.prizeInfo = prizeInfo;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public double getCurrFee() {
		return currFee;
	}

	public void setCurrFee(double currFee) {
		this.currFee = currFee;
	}

	@Override
	public String toString() {
		return "PrizeRecord [recordId=" + recordId + ", userId=" + userId + ", prizeId=" + prizeId + ", prizeName="
				+ prizeName + ", prizeTime=" + prizeTime + ", activityId=" + activityId + ", currFee=" + currFee
				+ ", prizeFee=" + prizeFee + ", prizeInfo=" + prizeInfo + ", userInfo=" + userInfo + "]";
	}

	public UserCommon getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserCommon userInfo) {
		this.userInfo = userInfo;
	}

	public double getPrizeFee() {
		return prizeFee;
	}

	public void setPrizeFee(double prizeFee) {
		this.prizeFee = prizeFee;
	}

}
