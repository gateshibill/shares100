package com.cofc.pojo;

import java.util.Date;

public class RechargeOrder {
    private Integer rechargeId;
    private Integer userId;
    private Integer loginPlat;
    private double rechargeMoney;
    private Integer rechargeStatus;
    private String remark;
    private String userPhone;
    private Integer isRemove;
    private String rechargeOrder;
    private Date createTime;
    private Date payTime;
    private double sendMoney;
	public Integer getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(Integer rechargeId) {
		this.rechargeId = rechargeId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public double getRechargeMoney() {
		return rechargeMoney;
	}
	public void setRechargeMoney(double rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}
	public Integer getRechargeStatus() {
		return rechargeStatus;
	}
	public void setRechargeStatus(Integer rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public Integer getIsRemove() {
		return isRemove;
	}
	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}
	public String getRechargeOrder() {
		return rechargeOrder;
	}
	public void setRechargeOrder(String rechargeOrder) {
		this.rechargeOrder = rechargeOrder;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public double getSendMoney() {
		return sendMoney;
	}
	public void setSendMoney(double sendMoney) {
		this.sendMoney = sendMoney;
	}
    
}
