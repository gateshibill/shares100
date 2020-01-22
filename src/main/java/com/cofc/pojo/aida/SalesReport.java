package com.cofc.pojo.aida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalesReport {
	private int customerCount = 328;// 1.客户数
	private int followCustomerCount = 328;// 2.跟踪客户数
	private int visitTimes = 2367;//3.浏览总数

	private int shareTimes = 698;// 4.分享次数
	private int savePhoneTimes = 82;// 5.保存电话
	private int praiseTimes = 82;// 6.被点赞数

	private SalesFunnel salesFunnel;// 客户漏斗
	private List<Integer> visitedAmountList;// 访问量的趋势数据
	private List<Integer> newCustomerList; // 新增用户趋势数据
	private String visitedAmountListStr;
	private String newCustomerListStr;
	private List<ActionColumn> ActionColumnList; // 客户与我的互动数,条形图

	// 客户兴趣
	private int interestMeCount = 298;// 对我感兴趣
	private int interestProductCount = 182;// 对产品兴趣
	private int interestCompany = 382;// 对公司感兴趣

	public SalesReport() {
		ActionColumnList = new ArrayList<ActionColumn>();
		//ActionColumnList.add(new ActionColumn("保存电话", 68));
		//ActionColumnList.add(new ActionColumn("点赞", 63));
		//ActionColumnList.add(new ActionColumn("咨询产品", 61));
		//ActionColumnList.add(new ActionColumn("拨打电话", 33));
		//ActionColumnList.add(new ActionColumn("分享", 32));
		//ActionColumnList.add(new ActionColumn("评论", 12));

		salesFunnel = new SalesFunnel();
		salesFunnel.addColume("0-20", 876, 46);
		salesFunnel.addColume("20-50", 675, 25);
		salesFunnel.addColume("50-100", 275, 11);
		salesFunnel.addColume("成交", 38, 2);

		Integer[] arr = { 688, 788, 899, 565, 234, 567, 777 };
		visitedAmountList = Arrays.asList(arr);

		Integer[] arr1 = { 188, 78, 59, 65, 34, 56, 28 };
		newCustomerList = Arrays.asList(arr1);

	}

	public int getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}

	public int getFollowCustomerCount() {
		return followCustomerCount;
	}

	public void setFollowCustomerCount(int followCustomerCount) {
		this.followCustomerCount = followCustomerCount;
	}

	public int getVisitTimes() {
		return visitTimes;
	}

	public void setVisitTimes(int visitTimes) {
		this.visitTimes = visitTimes;
	}

	public int getShareTimes() {
		return shareTimes;
	}

	public void setShareTimes(int shareTimes) {
		this.shareTimes = shareTimes;
	}

	public int getSavePhoneTimes() {
		return savePhoneTimes;
	}

	public void setSavePhoneTimes(int savePhoneTimes) {
		this.savePhoneTimes = savePhoneTimes;
	}

	public int getPraiseTimes() {
		return praiseTimes;
	}

	public void setPraiseTimes(int praiseTimes) {
		this.praiseTimes = praiseTimes;
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

	public List<ActionColumn> getActionColumnList() {
		return ActionColumnList;
	}

	public void setActionColumnList(List<ActionColumn> actionColumnList) {
		ActionColumnList = actionColumnList;
	}

	public int getInterestMeCount() {
		return interestMeCount;
	}

	public void setInterestMeCount(int interestMeCount) {
		this.interestMeCount = interestMeCount;
	}

	public int getInterestProductCount() {
		return interestProductCount;
	}

	public void setInterestProductCount(int interestProductCount) {
		this.interestProductCount = interestProductCount;
	}

	public int getInterestCompany() {
		return interestCompany;
	}

	public void setInterestCompany(int interestCompany) {
		this.interestCompany = interestCompany;
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



}
