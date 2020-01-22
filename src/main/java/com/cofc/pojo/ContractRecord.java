package com.cofc.pojo;

import java.util.Date;
import java.util.List;

public class ContractRecord {
	private Integer recordId;
	private Integer contractId;
	private Integer rewardUser;
	private Integer publisherId;
	private Double totalFee; 
	private Date createTime;
	private Date updateTime;
	private Integer payStatus;
	private Integer loginPlat;
	private String outTradeNumber;
	private Double realFee;
	private Integer partnerId;
	
	private List<ShareContract> shareList;
	private UserCommon user;
	
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getContractId() {
		return contractId;
	}
	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}
	public Integer getRewardUser() {
		return rewardUser;
	}
	public void setRewardUser(Integer rewardUser) {
		this.rewardUser = rewardUser;
	}
	public Integer getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	public Double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<ShareContract> getShareList() {
		return shareList;
	}
	public void setShareList(List<ShareContract> shareList) {
		this.shareList = shareList;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public String getOutTradeNumber() {
		return outTradeNumber;
	}
	public void setOutTradeNumber(String outTradeNumber) {
		this.outTradeNumber = outTradeNumber;
	}
	public Double getRealFee() {
		return realFee;
	}
	public void setRealFee(Double realFee) {
		this.realFee = realFee;
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	
}
