package com.cofc.pojo.aida;

import java.util.Date;

import com.cofc.pojo.UserCommon;

/**
 * 销售人员对象
 * 
 * @author Administrator
 *
 */
public class SalesPerson {
	private int id;
	private int userId;
	private int appId;
	private int role;
	private String userName;
	private String headImage;
	private String position;
	private int departmentId;// 区域和部门
	private int visitedCount;// 名片被访问次数；
	private int orderCount;// 订单数量
	private int customerCount;// 客户数量
	private int interactCount;// 互动频次
	private int dealRatio;// 成交率；
	private int newCustomerCount;//7天新增用户
	private int isdefault = 0;
	private Date createTime;
	private Date lastTime;
	private int eVisitedCount=0;//每日被访问的次数；
    private int rank;
	
	private SalesDashboard salesDashboard;// 
	private UserCard userCard;
	
	private Integer relatedUserId;
	
	
    private SalesAbility salesAbility=new SalesAbility();
    
	public SalesAbility getSalesAbility() {
		return salesAbility;
	}
	public void setSalesAbility(SalesAbility salesAbility) {
		this.salesAbility = salesAbility;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAppId() {
		return appId;
	}

	public int getNewCustomerCount() {
		return newCustomerCount;
	}
	public void setNewCustomerCount(int newCustomerCount) {
		this.newCustomerCount = newCustomerCount;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getVisitedCount() {
		return visitedCount;
	}

	public void setVisitedCount(int visitedCount) {
		this.visitedCount = visitedCount;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}

	public int getInteractCount() {
		return interactCount;
	}

	public void setInteractCount(int interactCount) {
		this.interactCount = interactCount;
	}

	public int getDealRatio() {
		return dealRatio;
	}

	public void setDealRatio(int dealRatio) {
		this.dealRatio = dealRatio;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public int getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(int isdefault) {
		this.isdefault = isdefault;
	}

	public int geteVisitedCount() {
		return eVisitedCount;
	}

	public void seteVisitedCount(int eVisitedCount) {
		this.eVisitedCount = eVisitedCount;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public UserCard getUserCard() {
		return userCard;
	}

	public void setUserCard(UserCard userCard) {
		this.userCard = userCard;
	}

	public SalesDashboard getSalesDashboard() {
		return salesDashboard;
	}

	public void setSalesDashboard(SalesDashboard salesDashboard) {
		this.salesDashboard = salesDashboard;
	}
//	public UserCommon getUserCommon() {
//		return userCommon;
//	}
//
//	public void setUserCommon(UserCommon userCommon) {
//		this.userCommon = userCommon;
//	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public Integer getRelatedUserId() {
		return relatedUserId;
	}
	public void setRelatedUserId(Integer relatedUserId) {
		this.relatedUserId = relatedUserId;
	}

}
