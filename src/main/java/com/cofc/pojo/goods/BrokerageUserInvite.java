package com.cofc.pojo.goods;

import java.util.Date;

/**
 * 记录佣金产品,用户间的邀请关系，用户结算，
 * 
 * @author chenxiangyou
 *
 */
public class BrokerageUserInvite {
	private int id;
	private int appId;
	private int goodsId;
	private int userId;
	private int inviteUserId;
	private int period;
	private Date lastTime;
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

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(int inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
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
