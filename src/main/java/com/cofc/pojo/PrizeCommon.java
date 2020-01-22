package com.cofc.pojo;

import java.util.Date;

public class PrizeCommon {
	private Integer prizeId;
	private String prizeName;
	private String prizeImage;
	private Integer getPrize;
	private Integer prizeCount;
	private double prizePersent;
	private Date createTime;
	private Integer activityId;;
	private Integer orderId;
	private Integer isPrized;
	private Integer prizeType;
	private Integer leftTimes;
	private Double multiple;
	private String backgroundColor;
	private double userBalance;

	public Integer getPrizeId() {
		return prizeId;
	}

	public void setPrizeId(Integer prizeId) {
		this.prizeId = prizeId;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public String getPrizeImage() {
		return prizeImage;
	}

	public void setPrizeImage(String prizeImage) {
		this.prizeImage = prizeImage;
	}

	public Integer getGetPrize() {
		return getPrize;
	}

	public void setGetPrize(Integer getPrize) {
		this.getPrize = getPrize;
	}

	public Integer getPrizeCount() {
		return prizeCount;
	}

	public void setPrizeCount(Integer prizeCount) {
		this.prizeCount = prizeCount;
	}

	public double getPrizePersent() {
		return prizePersent;
	}

	public void setPrizePersent(double prizePersent) {
		this.prizePersent = prizePersent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}


	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getLeftTimes() {
		return leftTimes;
	}

	public void setLeftTimes(Integer leftTimes) {
		this.leftTimes = leftTimes;
	}

	public Integer getIsPrized() {
		return isPrized;
	}

	public void setIsPrized(Integer isPrized) {
		this.isPrized = isPrized;
	}

	public Integer getPrizeType() {
		return prizeType;
	}

	public void setPrizeType(Integer prizeType) {
		this.prizeType = prizeType;
	}
	public Double getMultiple() {
		return multiple;
	}

	public void setMultiple(Double multiple) {
		this.multiple = multiple;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public double getUserBalance() {
		return userBalance;
	}

	public void setUserBalance(double userBalance) {
		this.userBalance = userBalance;
	}

	@Override
	public String toString() {
		return "PrizeCommon [prizeId=" + prizeId + ", prizeName=" + prizeName + ", prizeImage=" + prizeImage
				+ ", getPrize=" + getPrize + ", prizeCount=" + prizeCount + ", prizePersent=" + prizePersent
				+ ", createTime=" + createTime + ", activityId=" + activityId + ", orderId=" + orderId + ", isPrized="
				+ isPrized + ", prizeType=" + prizeType + ", leftTimes=" + leftTimes + "]";
	}
}
