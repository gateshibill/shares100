package com.cofc.pojo.aida;

import java.util.Date;

/**
 * 销售术语
 * @author Administrator
 *
 */
public class SalesMessage {
	private int id;
	private int appId;//
	private int userId;
	private int type;// 
	private String message;
	private Date createTime;
	private Date lastTime;
	private Date times;
	private String note;
	
	
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




	public int getUserId() {
		return userId;
	}




	public void setUserId(int userId) {
		this.userId = userId;
	}




	public int getType() {
		return type;
	}




	public void setType(int type) {
		this.type = type;
	}




	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
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




	public Date getTimes() {
		return times;
	}
	public void setTimes(Date times) {
		this.times = times;
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
