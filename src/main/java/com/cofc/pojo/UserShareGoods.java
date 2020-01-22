package com.cofc.pojo;

import java.util.Date;

public class UserShareGoods {
	private Integer goodsId;
	private Integer sharedUserId;
	private Integer needDeposit;
	private double deposit;
	private String goodsName;
	private String sharedAddress;
	private Date sharedTime;
	private String goodsPictures;
	private String goodsType;
	private UserCommon sharedUser;

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getSharedUserId() {
		return sharedUserId;
	}

	public void setSharedUserId(Integer sharedUserId) {
		this.sharedUserId = sharedUserId;
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

	public String getSharedAddress() {
		return sharedAddress;
	}

	public void setSharedAddress(String sharedAddress) {
		this.sharedAddress = sharedAddress;
	}

	public Date getSharedTime() {
		return sharedTime;
	}

	public void setSharedTime(Date sharedTime) {
		this.sharedTime = sharedTime;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public UserCommon getSharedUser() {
		return sharedUser;
	}

	public void setSharedUser(UserCommon sharedUser) {
		this.sharedUser = sharedUser;
	}

	public String getGoodsPictures() {
		return goodsPictures;
	}

	public void setGoodsPictures(String goodsPictures) {
		this.goodsPictures = goodsPictures;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	@Override
	public String toString() {
		return "UserShareGoods [goodsId=" + goodsId + ", sharedUserId=" + sharedUserId + ", needDeposit=" + needDeposit
				+ ", deposit=" + deposit + ", goodsName=" + goodsName + ", sharedAddress=" + sharedAddress
				+ ", sharedTime=" + sharedTime + ", goodsPictures=" + goodsPictures + ", goodsType=" + goodsType
				+ ", sharedUser=" + sharedUser + "]";
	}

}
