package com.cofc.pojo.vij;

import java.util.Date;

public class ProjectOrder {
	private Integer porderId;
	private Double realMoney;
	private Integer projectId;
	private Integer userId;
	private Date createTime;
	private String projectOrderNo;
	private Integer isDeal;
	private Integer isPay;
	private Integer payType;//支付方式
	public Integer getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}
	public Integer getPorderId() {
		return porderId;
	}
	public void setPorderId(Integer porderId) {
		this.porderId = porderId;
	}
	public Double getRealMoney() {
		return realMoney;
	}
	public void setRealMoney(Double realMoney) {
		this.realMoney = realMoney;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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
	public String getProjectOrderNo() {
		return projectOrderNo;
	}
	public void setProjectOrderNo(String projectOrderNo) {
		this.projectOrderNo = projectOrderNo;
	}
	public Integer getIsPay() {
		return isPay;
	}
	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
}	
