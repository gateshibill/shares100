package com.cofc.pojo.aida;

import java.util.List;

public class CustomerLabel {
	private int id;
	private int appId;
	private int salesPersonId;
	private int upperId;// 0为一级，1为二级；
	private String name;
	private int count;// 统计人数，主要是返回给前端；

	private List<CustomerLabel> customerLabelList;

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

	public int getSalesPersonId() {
		return salesPersonId;
	}

	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
	}

	public int getUpperId() {
		return upperId;
	}

	public void setUpperId(int upperId) {
		this.upperId = upperId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<CustomerLabel> getCustomerLabelList() {
		return customerLabelList;
	}

	public void setCustomerLabelList(List<CustomerLabel> customerLabelList) {
		this.customerLabelList = customerLabelList;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
