package com.cofc.pojo;

import java.util.Date;
import java.util.List;

public class DescoveryLeaveMessage {
	private Integer messageId;
	private Integer descoveryId;
	private String messageContent;
	private Integer leaveUserId;
	private Date leaveTime;
	private Integer parentId;
	private String messageImage;
	private String nickName;
	private String headImage;
	private List<DescoveryLeaveMessage> addedMessage;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public Integer getDescoveryId() {
		return descoveryId;
	}

	public void setDescoveryId(Integer descoveryId) {
		this.descoveryId = descoveryId;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Integer getLeaveUserId() {
		return leaveUserId;
	}

	public void setLeaveUserId(Integer leaveUserId) {
		this.leaveUserId = leaveUserId;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getMessageImage() {
		return messageImage;
	}

	public void setMessageImage(String messageImage) {
		this.messageImage = messageImage;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public List<DescoveryLeaveMessage> getAddedMessage() {
		return addedMessage;
	}

	public void setAddedMessage(List<DescoveryLeaveMessage> addedMessage) {
		this.addedMessage = addedMessage;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	@Override
	public String toString() {
		return "DescoveryLeaveMessage [messageId=" + messageId + ", descoveryId=" + descoveryId + ", messageContent="
				+ messageContent + ", leaveUserId=" + leaveUserId + ", leaveTime=" + leaveTime + ", parentId="
				+ parentId + ", messageImage=" + messageImage + ", nickName=" + nickName + ", headImage=" + headImage
				+ ", addedMessage=" + addedMessage + "]";
	}

}
