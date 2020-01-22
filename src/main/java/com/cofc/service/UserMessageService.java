package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserMessage;

public interface UserMessageService {
	public void addMessage(UserMessage message);
	public Integer getMessageCount(@Param("message")UserMessage message);
	public List<UserMessage> getMessageList(@Param("message")UserMessage message,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public void delMessage(@Param("messageId")Integer messageId);
}
