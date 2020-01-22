package com.cofc.pojo;

public class AutoWithdrawSet {
	private Integer applicationId;
	private int isAuto;
	private double autoFee;

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public int getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(int isAuto) {
		this.isAuto = isAuto;
	}

	public double getAutoFee() {
		return autoFee;
	}

	public void setAutoFee(double autoFee) {
		this.autoFee = autoFee;
	}

	@Override
	public String toString() {
		return "AutoWithdrawSet [applicationId=" + applicationId + ", isAuto=" + isAuto + ", autoFee=" + autoFee + "]";
	}

}
