package com.cofc.pojo.vij;

import java.util.Date;
import java.util.List;

public class GoodComment {
	private Integer cid;
	private Integer userId;
	private Integer goodId;
	private String content;
	private Integer parentId;
	private Integer level;
	private Date createTime;
	private Integer score;
	private String images;
	private Integer praiseCount;//点赞数
	private List<GoodComment> childList;//二级评论
	
	//用户表
	private String userName;
	private String nickName;
	private String headImage;
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGoodId() {
		return goodId;
	}
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public List<GoodComment> getChildList() {
		return childList;
	}
	public void setChildList(List<GoodComment> childList) {
		this.childList = childList;
	}
	@Override
	public String toString() {
		return "GoodComment [cid=" + cid + ", userId=" + userId + ", goodId=" + goodId + ", content=" + content
				+ ", parentId=" + parentId + ", level=" + level + ", createTime=" + createTime + ", score=" + score
				+ "]";
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public Integer getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}
}
