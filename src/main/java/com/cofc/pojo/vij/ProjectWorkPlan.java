package com.cofc.pojo.vij;

import java.math.BigInteger;
import java.util.Date;

public class ProjectWorkPlan {

	private Integer planId;
	private String planName;
	private BigInteger realStartTime;
	private BigInteger realEndTime;
	private String realStartTimeS;
	private String realEndTimeS;
	private Integer projectId;
	private Integer planTypeId;
	private Integer isFinish;//是否完成
	private BigInteger planStartTime;
	private BigInteger planEndTime;
	private String planStartTimeS;
	private String planEndTimeS;
	private Integer checkStatus;
	private Date checkTime;
	private String checkWhy;	//验收不通过的原因
	
	
	public String getCheckWhy() {
		return checkWhy;
	}
	public void setCheckWhy(String checkWhy) {
		this.checkWhy = checkWhy;
	}
	public String getPlanStartTimeS() {
		return planStartTimeS;
	}
	public void setPlanStartTimeS(String planStartTimeS) {
		this.planStartTimeS = planStartTimeS;
	}
	public String getPlanEndTimeS() {
		return planEndTimeS;
	}
	public void setPlanEndTimeS(String planEndTimeS) {
		this.planEndTimeS = planEndTimeS;
	}
	
	public String getRealStartTimeS() {
		return realStartTimeS;
	}
	public void setRealStartTimeS(String realStartTimeS) {
		this.realStartTimeS = realStartTimeS;
	}
	public String getRealEndTimeS() {
		return realEndTimeS;
	}
	public void setRealEndTimeS(String realEndTimeS) {
		this.realEndTimeS = realEndTimeS;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public BigInteger getRealStartTime() {
		return realStartTime;
	}
	public void setRealStartTime(BigInteger realStartTime) {
		this.realStartTime = realStartTime;
	}
	public BigInteger getRealEndTime() {
		return realEndTime;
	}
	public void setRealEndTime(BigInteger realEndTime) {
		this.realEndTime = realEndTime;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getPlanTypeId() {
		return planTypeId;
	}
	public void setPlanTypeId(Integer planTypeId) {
		this.planTypeId = planTypeId;
	}
	public Integer getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}
	public BigInteger getPlanStartTime() {
		return planStartTime;
	}
	public void setPlanStartTime(BigInteger planStartTime) {
		this.planStartTime = planStartTime;
	}
	public BigInteger getPlanEndTime() {
		return planEndTime;
	}
	public void setPlanEndTime(BigInteger planEndTime) {
		this.planEndTime = planEndTime;
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
	@Override
	public String toString() {
		return "ProjectWorkPlan [planId=" + planId + ", planName=" + planName + ", realStartTime=" + realStartTime
				+ ", realEndTime=" + realEndTime + ", realStartTimeS=" + realStartTimeS + ", realEndTimeS="
				+ realEndTimeS + ", projectId=" + projectId + ", planTypeId=" + planTypeId + ", isFinish=" + isFinish
				+ ", planStartTime=" + planStartTime + ", planEndTime=" + planEndTime + ", planStartTimeS="
				+ planStartTimeS + ", planEndTimeS=" + planEndTimeS + ", checkStatus=" + checkStatus + ", checkTime="
				+ checkTime + ", checkWhy=" + checkWhy + "]";
	}
	
}
