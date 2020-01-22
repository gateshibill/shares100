package com.cofc.pojo;

import java.util.Date;

public class UserWalletDiary {
	private Integer diaryId;
	private Integer goodsId;
	private Integer userId;
	private Date createTime;
	private String diaryTitle;
	private String diaryDetails;
	private double totalFee;
	private Integer incomeExpend;
	private Integer loginPlat;

	public Integer getDiaryId() {
		return diaryId;
	}

	public void setDiaryId(Integer diaryId) {
		this.diaryId = diaryId;
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

	public String getDiaryTitle() {
		return diaryTitle;
	}

	public void setDiaryTitle(String diaryTitle) {
		this.diaryTitle = diaryTitle;
	}

	public String getDiaryDetails() {
		return diaryDetails;
	}

	public void setDiaryDetails(String diaryDetails) {
		this.diaryDetails = diaryDetails;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getIncomeExpend() {
		return incomeExpend;
	}

	public void setIncomeExpend(Integer incomeExpend) {
		this.incomeExpend = incomeExpend;
	}

	@Override
	public String toString() {
		return "UserWalletDiary [diaryId=" + diaryId + ", goodsId=" + goodsId + ", userId=" + userId + ", createTime="
				+ createTime + ", diaryTitle=" + diaryTitle + ", diaryDetails=" + diaryDetails + ", totalFee="
				+ totalFee + ", incomeExpend=" + incomeExpend + "]";
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	

}
