package com.cofc.pojo.dataAnalysis;

import java.util.Date;
import java.util.List;

public class AiTrend {
	private int id;
	private int appId;
	private int userId;
	private int days;
	private int type;//1为访客；2为新增用户
	private String list;
	private Date lastTime;
//	private List<Integer> consultList;
//	private List<Integer> visitedCountList;
//	private List<Integer> newCustomerCountList;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

//	public List<Integer> getConsultList() {
//		return consultList;
//	}
//
//	public void setConsultList(List<Integer> consultList) {
//		this.consultList = consultList;
//	}
//
//	public List<Integer> getVisitedCountList() {
//		return visitedCountList;
//	}
//
//	public void setVisitedCountList(List<Integer> visitedCountList) {
//		this.visitedCountList = visitedCountList;
//	}
//
//	public List<Integer> getNewCustomerCountList() {
//		return newCustomerCountList;
//	}

//	public void setNewCustomerCountList(List<Integer> newCustomerCountList) {
//		this.newCustomerCountList = newCustomerCountList;
//	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
