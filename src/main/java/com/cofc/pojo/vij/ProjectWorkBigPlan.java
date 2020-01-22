package com.cofc.pojo.vij;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class ProjectWorkBigPlan {
	private Integer id;
	private String bigPlanName;
	private BigInteger bigPlanStartTime;
	private BigInteger bigPlanEndTime;
	private String bigPlanStartTimeS;
	private String bigPlanEndTimeS;
	private Integer projectId;
	private BigInteger bigRealStartTime;
	private BigInteger bigRealEndTime;
	private String bigRealStartTimeS;
	private String bigRealEndTimeS;
	private Integer isFinish;
	private Integer decoType;//装修类型：1：硬装 2 ：软装
	private Integer orderStatus;
	private Integer checkStatus;//验收状态
	private Date checkTime;//验收时间
	private String joinPeople;//参与人员
	private Integer isCheck;//是否需要验收
	private List<ProjectWorkPlan> planList;//计划详细子项
	private Integer isRemove;  //是否移除
	
	
	public Integer getIsRemove() {
		return isRemove;
	}
	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}
	public String getBigPlanStartTimeS() {
		return bigPlanStartTimeS;
	}
	public void setBigPlanStartTimeS(String bigPlanStartTimeS) {
		this.bigPlanStartTimeS = bigPlanStartTimeS;
	}
	public String getBigPlanEndTimeS() {
		return bigPlanEndTimeS;
	}
	public void setBigPlanEndTimeS(String bigPlanEndTimeS) {
		this.bigPlanEndTimeS = bigPlanEndTimeS;
	}
	public String getBigRealStartTimeS() {
		return bigRealStartTimeS;
	}
	public void setBigRealStartTimeS(String bigRealStartTimeS) {
		this.bigRealStartTimeS = bigRealStartTimeS;
	}
	public String getBigRealEndTimeS() {
		return bigRealEndTimeS;
	}
	public void setBigRealEndTimeS(String bigRealEndTimeS) {
		this.bigRealEndTimeS = bigRealEndTimeS;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBigPlanName() {
		return bigPlanName;
	}
	public void setBigPlanName(String bigPlanName) {
		this.bigPlanName = bigPlanName;
	}
	public BigInteger getBigPlanStartTime() {
		return bigPlanStartTime;
	}
	public void setBigPlanStartTime(BigInteger bigPlanStartTime) {
		this.bigPlanStartTime = bigPlanStartTime;
	}
	public BigInteger getBigPlanEndTime() {
		return bigPlanEndTime;
	}
	public void setBigPlanEndTime(BigInteger bigPlanEndTime) {
		this.bigPlanEndTime = bigPlanEndTime;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public BigInteger getBigRealStartTime() {
		return bigRealStartTime;
	}
	public void setBigRealStartTime(BigInteger bigRealStartTime) {
		this.bigRealStartTime = bigRealStartTime;
	}
	public BigInteger getBigRealEndTime() {
		return bigRealEndTime;
	}
	public void setBigRealEndTime(BigInteger bigRealEndTime) {
		this.bigRealEndTime = bigRealEndTime;
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<ProjectWorkPlan> getPlanList() {
		return planList;
	}
	public void setPlanList(List<ProjectWorkPlan> planList) {
		this.planList = planList;
	}
	public Integer getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Integer getDecoType() {
		return decoType;
	}
	public void setDecoType(Integer decoType) {
		this.decoType = decoType;
	}
	public String getJoinPeople() {
		return joinPeople;
	}
	public void setJoinPeople(String joinPeople) {
		this.joinPeople = joinPeople;
	}
	public Integer getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
}
