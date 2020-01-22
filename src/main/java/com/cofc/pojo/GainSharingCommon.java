package com.cofc.pojo;

public class GainSharingCommon {
	public Integer sharingId;
	public double groupownerPersent;
	public double shareuserPersent;
	public double withdrawPersent;

	public Integer getSharingId() {
		return sharingId;
	}

	public void setSharingId(Integer sharingId) {
		this.sharingId = sharingId;
	}

	public double getGroupownerPersent() {
		return groupownerPersent;
	}

	public void setGroupownerPersent(double groupownerPersent) {
		this.groupownerPersent = groupownerPersent;
	}

	public double getShareuserPersent() {
		return shareuserPersent;
	}

	public void setShareuserPersent(double shareuserPersent) {
		this.shareuserPersent = shareuserPersent;
	}

	public double getWithdrawPersent() {
		return withdrawPersent;
	}

	public void setWithdrawPersent(double withdrawPersent) {
		this.withdrawPersent = withdrawPersent;
	}

	@Override
	public String toString() {
		return "GainSharingCommon [sharingId=" + sharingId + ", groupownerPersent=" + groupownerPersent
				+ ", shareuserPersent=" + shareuserPersent + ", withdrawPersent=" + withdrawPersent + "]";
	}

}
