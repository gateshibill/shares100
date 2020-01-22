package com.cofc.pojo.activity;

import java.util.Date;

/**
 * 
 * @author Administrator
 *1.分销是怎么形成的；转发一次就自动进入我的分销里面了吗；
 */
public class DtbtGoods {
    private int id;
    private int appId;
    private int userId;
    private int goodsId;
    private int level;//0,1,2,
    private int status;
    private Date createTime;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getAppId() {
		return appId;
	}


	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getGoodsId() {
		return goodsId;
	}


	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
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
