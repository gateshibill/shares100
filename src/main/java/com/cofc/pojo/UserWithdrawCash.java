package com.cofc.pojo;

import java.util.Date;

public class UserWithdrawCash {
	private Integer withdrawId;
	private Integer userId;
	private double withdrawFee;
	private Date createTime;
	private Date dealTime;
	private Integer withdrawType;
	private Integer withdrawState;
	private Integer loginPlat;
	private String withdrawIp;
	private String withdrawRealName;
	private String outTradeNo;

	public Integer getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(Integer withdrawId) {
		this.withdrawId = withdrawId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public double getWithdrawFee() {
		return withdrawFee;
	}

	public void setWithdrawFee(double withdrawFee) {
		this.withdrawFee = withdrawFee;
	}

	public Integer getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(Integer withdrawType) {
		this.withdrawType = withdrawType;
	}

	public Integer getWithdrawState() {
		return withdrawState;
	}

	public void setWithdrawState(Integer withdrawState) {
		this.withdrawState = withdrawState;
	}

	public String getWithdrawIp() {
		return withdrawIp;
	}

	public void setWithdrawIp(String withdrawIp) {
		this.withdrawIp = withdrawIp;
	}

	public String getWithdrawRealName() {
		return withdrawRealName;
	}

	public void setWithdrawRealName(String withdrawRealName) {
		this.withdrawRealName = withdrawRealName;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	@Override
	public String toString() {
		return "UserWithdrawCash [withdrawId=" + withdrawId + ", userId=" + userId + ", withdrawFee=" + withdrawFee
				+ ", createTime=" + createTime + ", dealTime=" + dealTime + ", withdrawType=" + withdrawType
				+ ", withdrawState=" + withdrawState + ", loginPlat=" + loginPlat + ", withdrawIp=" + withdrawIp
				+ ", withdrawRealName=" + withdrawRealName + ", outTradeNo=" + outTradeNo + "]";
	}

}
