package com.cofc.pojo.vij;

import java.util.Date;

public class ProjectComment {
	private Integer commentId;
	private String commentContent;
	private Integer projectId;
	private Double commentScore;
	private Integer userId;
	private String tagStr;
	private Date createTime;
	private Integer isDeal;
	
	public Integer getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Double getCommentScore() {
		return commentScore;
	}
	public void setCommentScore(Double commentScore) {
		this.commentScore = commentScore;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTagStr() {
		return tagStr;
	}
	public void setTagStr(String tagStr) {
		this.tagStr = tagStr;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
