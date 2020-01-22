package com.cofc.pojo.vij;

import java.math.BigInteger;

/**
 * 保留装修开始和结束时间
 * @author menghaoran
 *
 */
public class ProjectConstructTime {
	private Integer id;
	private Integer projectId;
	private BigInteger constructStartTime;
	private BigInteger constructEndTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public BigInteger getConstructStartTime() {
		return constructStartTime;
	}
	public void setConstructStartTime(BigInteger constructStartTime) {
		this.constructStartTime = constructStartTime;
	}
	public BigInteger getConstructEndTime() {
		return constructEndTime;
	}
	public void setConstructEndTime(BigInteger constructEndTime) {
		this.constructEndTime = constructEndTime;
	}
}
