package com.cofc.pojo.dataAnalysis;

import java.util.Date;

public class CustomerAction {
	private int id;
	private int appId;// 应用ID；
	private int appName;
	private int userId;
	private String userName;

	private String className;
	private String methodName;
	private String params;

	private int times = 1;
	private Date actionTime;
	private Date createTime;

	// 下面为分享数据
	private String source;
	private int type;
	private String typeName;

	private String content;
	private int objectType;
	private String objectTypeName;
	private int objectId;// 如果是名片，传销售员用户id，如果是产品传产品ID，如果是活动传活动ID，如果是官网，销售ID；
	private String objectName;
	private String note;

	public CustomerAction(int appId, int userId, String className, String methodName, String params) {
		this.appId = appId;
		this.userId = userId;
		this.className = className;
		this.methodName = methodName;
		this.params = params;
		this.actionTime = new Date();
		this.createTime = this.actionTime;
	}

	public CustomerAction() {
	}

	public int getId() {
		return id;
	}

	public int getAppName() {
		return appName;
	}

	public void setAppName(int appName) {
		this.appName = appName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public String getObjectTypeName() {
		return objectTypeName;
	}

	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
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

	public void setTimes(int times) {
		this.times = times;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
