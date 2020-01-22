package com.cofc.pojo.aida;

import java.util.Date;

public class CustomerLabelRecord {
     private int id;
     private int labelId;
     private int appId;
     private int salesPersonId;
     private int userId;
     private Date createTime;//
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLabelId() {
		return labelId;
	}

	public void setLabelId(int labelId) {
		this.labelId = labelId;
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


	public int getSalesPersonId() {
		return salesPersonId;
	}

	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
