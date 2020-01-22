package com.cofc.pojo;

import java.util.Date;

public class TaskCompete {
	private Integer competeId;
	private Integer competitorUser;
	private Date competeTime;
	private double competeFee;
	private String competeService;
	private String competeLongitude;
	private String competeLatitude;
	private String competeLacation;
	private Integer competeStatus;
	private Date selectTime;
	private Integer partnerId;
	private Integer starRate;
	private Integer competeTask;

	private UserCommon competerInfo;

	public Integer getCompeteId() {
		return competeId;
	}

	public void setCompeteId(Integer competeId) {
		this.competeId = competeId;
	}

	public Integer getCompetitorUser() {
		return competitorUser;
	}

	public void setCompetitorUser(Integer competitorUser) {
		this.competitorUser = competitorUser;
	}

	public Date getCompeteTime() {
		return competeTime;
	}

	public void setCompeteTime(Date competeTime) {
		this.competeTime = competeTime;
	}

	public double getCompeteFee() {
		return competeFee;
	}

	public void setCompeteFee(double competeFee) {
		this.competeFee = competeFee;
	}

	public String getCompeteService() {
		return competeService;
	}

	public void setCompeteService(String competeService) {
		this.competeService = competeService;
	}

	public String getCompeteLongitude() {
		return competeLongitude;
	}

	public void setCompeteLongitude(String competeLongitude) {
		this.competeLongitude = competeLongitude;
	}

	public String getCompeteLatitude() {
		return competeLatitude;
	}

	public void setCompeteLatitude(String competeLatitude) {
		this.competeLatitude = competeLatitude;
	}

	public String getCompeteLacation() {
		return competeLacation;
	}

	public void setCompeteLacation(String competeLacation) {
		this.competeLacation = competeLacation;
	}

	public Integer getCompeteStatus() {
		return competeStatus;
	}

	public void setCompeteStatus(Integer competeStatus) {
		this.competeStatus = competeStatus;
	}

	public Date getSelectTime() {
		return selectTime;
	}

	public void setSelectTime(Date selectTime) {
		this.selectTime = selectTime;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getStarRate() {
		return starRate;
	}

	public void setStarRate(Integer starRate) {
		this.starRate = starRate;
	}

	public Integer getCompeteTask() {
		return competeTask;
	}

	public void setCompeteTask(Integer competeTask) {
		this.competeTask = competeTask;
	}

	public UserCommon getCompeterInfo() {
		return competerInfo;
	}

	public void setCompeterInfo(UserCommon competerInfo) {
		this.competerInfo = competerInfo;
	}

	@Override
	public String toString() {
		return "TaskCompete [competeId=" + competeId + ", competitorUser=" + competitorUser + ", competeTime="
				+ competeTime + ", competeFee=" + competeFee + ", competeService=" + competeService
				+ ", competeLongitude=" + competeLongitude + ", competeLatitude=" + competeLatitude
				+ ", competeLacation=" + competeLacation + ", competeStatus=" + competeStatus + ", selectTime="
				+ selectTime + ", partnerId=" + partnerId + ", starRate=" + starRate + ", competeTask=" + competeTask
				+ ", competerInfo=" + competerInfo + "]";
	}

}
