package com.cofc.pojo;

import java.util.Date;

public class UserApplyGoods {
	private Integer applyId;
	private Integer userId;
	private Integer goodsId;
	private String realName;
	private String contactWay;
	private String address;
	private Integer needDeposit;
	private double deposit;
	private Date createTime;
	private Integer isAgreed;
	private Integer isReturned;
	private Date returnTime;

	public Integer getApplyId() {
		return applyId;
	}

	public void setApplyId(Integer applyId) {
		this.applyId = applyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getNeedDeposit() {
		return needDeposit;
	}

	public void setNeedDeposit(Integer needDeposit) {
		this.needDeposit = needDeposit;
	}

	public double getDeposit() {
		return deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsAgreed() {
		return isAgreed;
	}

	public void setIsAgreed(Integer isAgreed) {
		this.isAgreed = isAgreed;
	}

	public Integer getIsReturned() {
		return isReturned;
	}

	public void setIsReturned(Integer isReturned) {
		this.isReturned = isReturned;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	@Override
	public String toString() {
		return "UserApplyGoods [applyId=" + applyId + ", userId=" + userId + ", goodsId=" + goodsId + ", realName="
				+ realName + ", contactWay=" + contactWay + ", address=" + address + ", needDeposit=" + needDeposit
				+ ", deposit=" + deposit + ", createTime=" + createTime + ", isAgreed=" + isAgreed + ", isReturned="
				+ isReturned + ", returnTime=" + returnTime + "]";
	}

}
