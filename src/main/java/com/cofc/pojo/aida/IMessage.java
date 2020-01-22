package com.cofc.pojo.aida;

import java.util.Date;

//即时通讯数据缓存；
/**
 * 
 * @author Administrator
 * 1.把数据存到本地，标识是否已读；
 * 2.总是去获取最近10条；
 * 3.每次读取后需要跟新状态；
 * 4.每次打开都去数据库拿一次；
 */

public class IMessage {
	private int id;
	private int appId;//
	private int model;//0为用户发，1为销售发，2为系统发
	private int fromId;
	private int userId;
	private int type;// 0为文字，1为表情；
	private String content;
	private int status;// 0为未读，1为已读；
	private Date createTime;
	private Date readTime;//已读时间，未读为NULL；
	
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getAppId() {
		return appId;
	}


	public int getModel() {
		return model;
	}


	public void setModel(int model) {
		this.model = model;
	}


	public void setAppId(int appId) {
		this.appId = appId;
	}


	public int getFromId() {
		return fromId;
	}


	public void setFromId(int fromId) {
		this.fromId = fromId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}




	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getReadTime() {
		return readTime;
	}


	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
