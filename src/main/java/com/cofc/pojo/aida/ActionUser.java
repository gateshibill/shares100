package com.cofc.pojo.aida;

import java.util.ArrayList;
import java.util.List;

public class ActionUser {
	private int id;
	private int userId=0;
	private String userName="";
	private String headImage="";
	private String typeName = "互动";
	private String objectTypeName = "名片";
	//private String objectName = "飞看智能名片";
	private int times=0;
	

	private List<ActionColumn> list=new ArrayList<ActionColumn>();
	public ActionUser() {
	}

	public String toString() {
		return userName + "跟你在"+"名片"+"上"+typeName + times + "次";
	}

	public void addActionColumn(int type, int times) {

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

	public String getObjectTypeName() {
		return objectTypeName;
	}

	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
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

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public List<ActionColumn> getList() {
		return list;
	}

	public void setList(List<ActionColumn> list) {
		this.list = list;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
