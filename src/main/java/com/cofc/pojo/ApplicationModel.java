package com.cofc.pojo;

import java.util.Date;

public class ApplicationModel {
	private Integer modelId;
	private String modelUrl;
	private String modelName;
	private Date modelCreateTime;
	private Integer modelUsedCount;
	private Integer isUsing;
	private String modelCarousel;
	private String modelSynopsis;

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelUrl() {
		return modelUrl;
	}

	public void setModelUrl(String modelUrl) {
		this.modelUrl = modelUrl;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Date getModelCreateTime() {
		return modelCreateTime;
	}

	public void setModelCreateTime(Date modelCreateTime) {
		this.modelCreateTime = modelCreateTime;
	}

	public Integer getModelUsedCount() {
		return modelUsedCount;
	}

	public void setModelUsedCount(Integer modelUsedCount) {
		this.modelUsedCount = modelUsedCount;
	}

	@Override
	public String toString() {
		return "ApplicationModel [modelId=" + modelId + ", modelUrl=" + modelUrl + ", modelName=" + modelName
				+ ", modelCreateTime=" + modelCreateTime + ", modelUsedCount=" + modelUsedCount + "]";
	}

	public Integer getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Integer isUsing) {
		this.isUsing = isUsing;
	}

	public String getModelCarousel() {
		return modelCarousel;
	}

	public void setModelCarousel(String modelCarousel) {
		this.modelCarousel = modelCarousel;
	}

	public String getModelSynopsis() {
		return modelSynopsis;
	}

	public void setModelSynopsis(String modelSynopsis) {
		this.modelSynopsis = modelSynopsis;
	}
}
