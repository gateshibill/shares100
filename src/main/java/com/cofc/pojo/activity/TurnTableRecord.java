package com.cofc.pojo.activity;

import java.util.Date;

public class TurnTableRecord {
	private int id;
	private int appId;
	private int userId;
	private int turntableId;
	private int columnId;
	private int price;
	private int win=0;//0未中，1为中；
	private int prizeType;
	private int prizeNum;
	private Date createTime;

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






	public int getColumnId() {
		return columnId;
	}


	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}


	public int getWin() {
		return win;
	}


	public int getTurntableId() {
		return turntableId;
	}


	public void setTurntableId(int turntableId) {
		this.turntableId = turntableId;
	}


	public void setWin(int win) {
		this.win = win;
	}


	public int getPrizeType() {
		return prizeType;
	}


	public void setPrizeType(int prizeType) {
		this.prizeType = prizeType;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getPrizeNum() {
		return prizeNum;
	}


	public void setPrizeNum(int prizeNum) {
		this.prizeNum = prizeNum;
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
