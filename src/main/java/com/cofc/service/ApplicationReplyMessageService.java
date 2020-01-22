package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationReplyMessage;

public interface ApplicationReplyMessageService {
   public void addReplyInfo(ApplicationReplyMessage reply);
   public ApplicationReplyMessage isAlreadyReply(@Param("loginPlat")Integer loginPlat,@Param("messageId")Integer messageId);
}
