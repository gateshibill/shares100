package com.cofc.pojo.tx;

import java.util.Date;

public class CardOrder {
	private Integer id;
	private String goodsName;
	private Double price;
	private String orderNum;
	private String agentOrderNum;
	private String platOrderNum;
	private String cardList;
	private String agentCode;
	private Integer quantity;
	private String goodsCode;
	private Integer isUse;
	private Integer isUseTime;
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getPlatOrderNum() {
		return platOrderNum;
	}
	public void setPlatOrderNum(String platOrderNum) {
		this.platOrderNum = platOrderNum;
	}
	public String getCardList() {
		return cardList;
	}
	public void setCardList(String cardList) {
		this.cardList = cardList;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	public Integer getIsUseTime() {
		return isUseTime;
	}
	public void setIsUseTime(Integer isUseTime) {
		this.isUseTime = isUseTime;
	}
	public String getAgentOrderNum() {
		return agentOrderNum;
	}
	public void setAgentOrderNum(String agentOrderNum) {
		this.agentOrderNum = agentOrderNum;
	}
}
