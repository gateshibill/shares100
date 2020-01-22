package com.cofc.pojo;

import java.util.Date;

public class TravelComment {
    private Integer commentId;
    private String commentContent;
    private Integer userId;
    private Integer replyuserId;
    private String commentImage;
    private Integer isEffect;
    private Integer travelId;
    private Date createTime;
    private UserCommon user;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getReplyuserId() {
		return replyuserId;
	}
	public void setReplyuserId(Integer replyuserId) {
		this.replyuserId = replyuserId;
	}
	public String getCommentImage() {
		return commentImage;
	}
	public void setCommentImage(String commentImage) {
		this.commentImage = commentImage;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public Integer getTravelId() {
		return travelId;
	}
	public void setTravelId(Integer travelId) {
		this.travelId = travelId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
    
}
