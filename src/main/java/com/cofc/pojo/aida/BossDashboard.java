package com.cofc.pojo.aida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BossDashboard {
	private int orderCount = 0;// 1.商城订单数，         可以从商城获取
	private int salesAmount = 0;// 2.商城销售金额   可以从商城获取
	private int customerAmount = 0;// 3.客户总数，可以从sales_customer 获取；
	private int merchandiserAmount = 0;// 4.跟进总数  //可以从sales_customer 获取；增加一个状态字段；
	
	private int visitedAmount = 0;// 5.浏览总数               从action_record表获取；
	private int sharedAmount = 0;// 6.被转发总数            从action_record表获取；
	private int savedAmount = 0;// 7.电话被保存总数      从action_record表获取；
	private int praisedAmount = 0;// 8.被点赞总数         从action_record表获取；
	private int gameAmount = 0;// 9.游戏点击数                 从action_record表获取；
	
	private SalesFunnel salesFunnel;
	private List<Integer> visitedAmountList;//访问量的趋势数据
	private List<Integer> newCustomerList;	//新增用户趋势数据
	private String visitedAmountListStr;
	private String newCustomerListStr;
	
	private List<ActionColumn> ActionColumnList=new ArrayList<ActionColumn>(); // 客户与我的互动数,条形图
	
	
	public BossDashboard()
	{
		Integer[] arr = {0,0,0,0,0,0,0};
		visitedAmountList=Arrays.asList(arr);
		Integer[] arr1 = {0,0,0,0,0,0,0};
		newCustomerList=Arrays.asList(arr1);
		salesFunnel= new SalesFunnel();
		salesFunnel.addColume("0-20", 8763, 126);
		salesFunnel.addColume("20-50", 1675, -35);
		salesFunnel.addColume("50-100", 675, 16);
		salesFunnel.addColume("成交", 128, 6);
	}
	
	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(int salesAmount) {
		this.salesAmount = salesAmount;
	}

	public List<ActionColumn> getActionColumnList() {
		return ActionColumnList;
	}

	public void setActionColumnList(List<ActionColumn> actionColumnList) {
		ActionColumnList = actionColumnList;
	}

	public int getCustomerAmount() {
		return customerAmount;
	}

	public void setCustomerAmount(int customerAmount) {
		this.customerAmount = customerAmount;
	}

	public int getMerchandiserAmount() {
		return merchandiserAmount;
	}

	public void setMerchandiserAmount(int merchandiserAmount) {
		this.merchandiserAmount = merchandiserAmount;
	}

	public int getVisitedAmount() {
		return visitedAmount;
	}

	public void setVisitedAmount(int visitedAmount) {
		this.visitedAmount = visitedAmount;
	}

	public int getSharedAmount() {
		return sharedAmount;
	}

	public void setSharedAmount(int sharedAmount) {
		this.sharedAmount = sharedAmount;
	}

	public int getSavedAmount() {
		return savedAmount;
	}

	public void setSavedAmount(int savedAmount) {
		this.savedAmount = savedAmount;
	}

	public int getPraisedAmount() {
		return praisedAmount;
	}

	public void setPraisedAmount(int praisedAmount) {
		this.praisedAmount = praisedAmount;
	}

	public int getGameAmount() {
		return gameAmount;
	}

	public void setGameAmount(int gameAmount) {
		this.gameAmount = gameAmount;
	}

	public SalesFunnel getSalesFunnel() {
		return salesFunnel;
	}

	public void setSalesFunnel(SalesFunnel salesFunnel) {
		this.salesFunnel = salesFunnel;
	}

	public List<Integer> getVisitedAmountList() {
		return visitedAmountList;
	}

	public void setVisitedAmountList(List<Integer> visitedAmountList) {
		this.visitedAmountList = visitedAmountList;
	}

	public List<Integer> getNewCustomerList() {
		return newCustomerList;
	}

	public void setNewCustomerList(List<Integer> newCustomerList) {
		this.newCustomerList = newCustomerList;
	}

	public String getVisitedAmountListStr() {
		return visitedAmountListStr;
	}

	public void setVisitedAmountListStr(String visitedAmountListStr) {
		this.visitedAmountListStr = visitedAmountListStr;
	}

	public String getNewCustomerListStr() {
		return newCustomerListStr;
	}

	public void setNewCustomerListStr(String newCustomerListStr) {
		this.newCustomerListStr = newCustomerListStr;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
