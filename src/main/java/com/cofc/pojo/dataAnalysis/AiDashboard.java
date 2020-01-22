package com.cofc.pojo.dataAnalysis;

import java.util.Date;
//BOSS AI 端
public class AiDashboard {
	private int id;
	private int appId;
	private int userId;
	private int days;
	
	private int orderCount;//订单数
	private int salesAmount;//销售额
	private int customerCount;//客户数
	private int visitedCount;//访客数
	private int followOrderCount;// 跟单数
	private int sharedCount;//分享数
	private int savedCount;//保存数
	private int praisedCount;//点赞数
	private int consultCount;//咨询数
	private int shareMyselfCount;// 自己分享数
	
	private int visitedProductCount;//访问产品
	private int visitedWebsiteCount;//访问官网
	 
	
	private Date lastTime;

	public AiDashboard()
	{
		
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSalesAmount() {
		return salesAmount;
	}
	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}
	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getDays() {
		return days;
	}

	public int getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}
	public int getConsultCount() {
		return consultCount;
	}
	public void setConsultCount(int consultCount) {
		this.consultCount = consultCount;
	}
	public int getShareMyselfCount() {
		return shareMyselfCount;
	}
	public void setShareMyselfCount(int shareMyselfCount) {
		this.shareMyselfCount = shareMyselfCount;
	}
	public void setDays(int days) {
		this.days = days;
	}

	public int getVisitedProductCount() {
		return visitedProductCount;
	}
	public void setVisitedProductCount(int visitedProductCount) {
		this.visitedProductCount = visitedProductCount;
	}
	public int getVisitedWebsiteCount() {
		return visitedWebsiteCount;
	}
	public void setVisitedWebsiteCount(int visitedWebsiteCount) {
		this.visitedWebsiteCount = visitedWebsiteCount;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}

	public int getVisitedCount() {
		return visitedCount;
	}

	public void setVisitedCount(int visitedCount) {
		this.visitedCount = visitedCount;
	}

	public int getFollowOrderCount() {
		return followOrderCount;
	}

	public void setFollowOrderCount(int followOrderCount) {
		this.followOrderCount = followOrderCount;
	}

	public int getSharedCount() {
		return sharedCount;
	}

	public void setSharedCount(int sharedCount) {
		this.sharedCount = sharedCount;
	}

	public int getSavedCount() {
		return savedCount;
	}

	public void setSavedCount(int savedCount) {
		this.savedCount = savedCount;
	}

	public int getPraisedCount() {
		return praisedCount;
	}

	public void setPraisedCount(int praisedCount) {
		this.praisedCount = praisedCount;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
