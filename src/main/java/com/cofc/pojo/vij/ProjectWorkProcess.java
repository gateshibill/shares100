package com.cofc.pojo.vij;

import java.util.Date;

/**
 * 施工流程
 * @author menghaoran
 *
 */
public class ProjectWorkProcess {
	private Integer workId;
	private String workType;
	private String workTitle;
	private String workDesc;
	private String workImagesStr;
	private Integer orderStatus;
	private Date createTime;
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getWorkTitle() {
		return workTitle;
	}
	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}
	public String getWorkDesc() {
		return workDesc;
	}
	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}
	public String getWorkImagesStr() {
		return workImagesStr;
	}
	public void setWorkImagesStr(String workImagesStr) {
		this.workImagesStr = workImagesStr;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
