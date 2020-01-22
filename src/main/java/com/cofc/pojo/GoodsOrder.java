package com.cofc.pojo;

import java.util.Date;

public class GoodsOrder {
	private Integer payId;
	private Integer goodsId;
	private Integer userId;
	private Date createTime;
	private Integer payStatus;
	private Integer payNumber;;
	private double totalFee;
	private double realFee;
	private String outTradeNumber;
	private Date updateTime;
	private Integer loginPlat;
	private Integer isSend;
	private Integer payType;
	private String sendCode;
	private String shoppingAddress;
	private String deliveryName;
	private String deliveryPhone;
	private String postCode;

	private UserCommon orderSeller;
	private Buyer orderBuyer;
	private GoodsCommon orderGoods;

	private int userOrderID;
	
	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
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

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(Integer payNumber) {
		this.payNumber = payNumber;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public double getRealFee() {
		return realFee;
	}

	public void setRealFee(double realFee) {
		this.realFee = realFee;
	}

	public String getOutTradeNumber() {
		return outTradeNumber;
	}

	public void setOutTradeNumber(String outTradeNumber) {
		this.outTradeNumber = outTradeNumber;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public UserCommon getOrderSeller() {
		return orderSeller;
	}

	public void setOrderSeller(UserCommon orderSeller) {
		this.orderSeller = orderSeller;
	}

	public Buyer getOrderBuyer() {
		return orderBuyer;
	}

	public void setOrderBuyer(Buyer orderBuyer) {
		this.orderBuyer = orderBuyer;
	}

	public GoodsCommon getOrderGoods() {
		return orderGoods;
	}

	public void setOrderGoods(GoodsCommon orderGoods) {
		this.orderGoods = orderGoods;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getSendCode() {
		return sendCode;
	}

	public void setSendCode(String sendCode) {
		this.sendCode = sendCode;
	}

	public String getShoppingAddress() {
		return shoppingAddress;
	}

	public void setShoppingAddress(String shoppingAddress) {
		this.shoppingAddress = shoppingAddress;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryPhone() {
		return deliveryPhone;
	}

	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@Override
	public String toString() {
		return "GoodsOrder [payId=" + payId + ", goodsId=" + goodsId + ", userId=" + userId + ", createTime="
				+ createTime + ", payStatus=" + payStatus + ", payNumber=" + payNumber + ", totalFee=" + totalFee
				+ ", realFee=" + realFee + ", outTradeNumber=" + outTradeNumber + ", updateTime=" + updateTime
				+ ", loginPlat=" + loginPlat + ", isSend=" + isSend + ", payType=" + payType + ", sendCode=" + sendCode
				+ ", shoppingAddress=" + shoppingAddress + ", deliveryName=" + deliveryName + ", deliveryPhone="
				+ deliveryPhone + ", postCode=" + postCode + ", orderSeller=" + orderSeller + ", orderBuyer="
				+ orderBuyer + ", orderGoods=" + orderGoods + "]";
	}

	public int getUserOrderID() {
		return userOrderID;
	}

	public void setUserOrderID(int userOrderID) {
		this.userOrderID = userOrderID;
	}

}
