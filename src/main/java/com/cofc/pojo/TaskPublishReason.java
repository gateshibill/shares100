package com.cofc.pojo;

public class TaskPublishReason {
	private Integer reasonId;
	private String reasonName;

	public Integer getReasonId() {
		return reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	@Override
	public String toString() {
		return "TaskPublishReason [reasonId=" + reasonId + ", reasonName=" + reasonName + "]";
	}

}
