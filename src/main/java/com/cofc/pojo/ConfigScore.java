package com.cofc.pojo;

import java.util.Date;

public class ConfigScore {
	private Integer id;
	private Integer dayLoginScore=10;
	private Integer signScore=10;
	private Integer shareScore=10;
	private Integer visitScore=10;
	private Integer twoVisitScore=5;
	private Integer buyScore=100;
	private Integer rechargeScore=50;
	private Integer registerScore=100;
	private Integer myInfoScore=10;
	private Integer loginPlat;
	private Date createTime=new Date();
	private Date updateTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDayLoginScore() {
		return dayLoginScore;
	}
	public void setDayLoginScore(Integer dayLoginScore) {
		this.dayLoginScore = dayLoginScore;
	}
	public Integer getSignScore() {
		return signScore;
	}
	public void setSignScore(Integer signScore) {
		this.signScore = signScore;
	}
	public Integer getShareScore() {
		return shareScore;
	}
	public void setShareScore(Integer shareScore) {
		this.shareScore = shareScore;
	}
	public Integer getVisitScore() {
		return visitScore;
	}
	public void setVisitScore(Integer visitScore) {
		this.visitScore = visitScore;
	}
	public Integer getTwoVisitScore() {
		return twoVisitScore;
	}
	public void setTwoVisitScore(Integer twoVisitScore) {
		this.twoVisitScore = twoVisitScore;
	}
	public Integer getBuyScore() {
		return buyScore;
	}
	public void setBuyScore(Integer buyScore) {
		this.buyScore = buyScore;
	}
	public Integer getRechargeScore() {
		return rechargeScore;
	}
	public void setRechargeScore(Integer rechargeScore) {
		this.rechargeScore = rechargeScore;
	}
	public Integer getRegisterScore() {
		return registerScore;
	}
	public void setRegisterScore(Integer registerScore) {
		this.registerScore = registerScore;
	}
	public Integer getMyInfoScore() {
		return myInfoScore;
	}
	public void setMyInfoScore(Integer myInfoScore) {
		this.myInfoScore = myInfoScore;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
