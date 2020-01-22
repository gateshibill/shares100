package com.cofc.pojo;

import java.util.Date;

public class RechargeAmount {
    private Integer discountId;
    private Integer loginPlat;
    private double rechargeAmount;
    private double sendAmount;
    private Integer isEffect;
    private Date createTime;
	public Integer getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public double getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public double getSendAmount() {
		return sendAmount;
	}
	public void setSendAmount(double sendAmount) {
		this.sendAmount = sendAmount;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}
