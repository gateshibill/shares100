package com.cofc.pojo;

public class TaskPublishContent {
	private Integer contentId;
	private String contentText;

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	@Override
	public String toString() {
		return "TaskPublishContent [contentId=" + contentId + ", contentText=" + contentText + "]";
	}

}
