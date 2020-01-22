package com.cofc.pojo.aida;

import java.util.Date;

import com.cofc.pojo.UserCommon;

public class ActionColumn {
	private int id;
	private int userId = 0;
	private String userName = "";
	private String headImage = "";
	private String typeName = "";
	private String objectTypeName = "";
	private String objectName = "";
	private int salesPersonId;
	private String salesPersonName;
	private int times = 3;
	private String tips = "觉得你很靠谱";
	private Date date = new Date();

	//private UserCommon userCommon = new UserCommon();

	public ActionColumn() {
	}

	public ActionColumn(String typeName, int times) {
		this.typeName = typeName;
		this.times = times;
	}

	public String toString() {
		return userName + "第" + times + "次" + typeName + "你的" + objectName + tips + " " + date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public int getTimes() {
		return times;
	}

	public String getObjectTypeName() {
		return objectTypeName;
	}

	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public String getTips() {
		return tips;
	}

	public String getSalesPersonName() {
		return salesPersonName;
	}

	public void setSalesPersonName(String salesPersonName) {
		this.salesPersonName = salesPersonName;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getSalesPersonId() {
		return salesPersonId;
	}

	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

}
