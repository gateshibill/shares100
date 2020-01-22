package com.cofc.pojo.vij;

import java.util.Date;

public class CardTicket {
	private Integer ticketId;
	private Integer loginPlat;
	private String ticketName;
	private String ticketDesc;
	private Integer ticketMoney;
	private Integer ticketNumber;
	private Integer ticketType;
	private Date createTime;
	private Integer isEffect;
	private Integer activeTime;
	private Integer cardStatus; //0:未领取  1 ： 已领取
	public Integer getTicketId() {
		return ticketId;
	}
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public String getTicketName() {
		return ticketName;
	}
	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}
	public String getTicketDesc() {
		return ticketDesc;
	}
	public void setTicketDesc(String ticketDesc) {
		this.ticketDesc = ticketDesc;
	}
	public Integer getTicketMoney() {
		return ticketMoney;
	}
	public void setTicketMoney(Integer ticketMoney) {
		this.ticketMoney = ticketMoney;
	}
	public Integer getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(Integer ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public Integer getTicketType() {
		return ticketType;
	}
	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public Integer getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(Integer activeTime) {
		this.activeTime = activeTime;
	}
	public Integer getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(Integer cardStatus) {
		this.cardStatus = cardStatus;
	}
}
