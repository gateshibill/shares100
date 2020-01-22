package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.DescoveryLeaveMessage;

public interface DescoveryLeaveMessageService {

	public void newLeaveNewMessage(DescoveryLeaveMessage leavemsg);

	public List<DescoveryLeaveMessage> findLeavemsgByDescoveryId(@Param("descoveryId")Integer descoveryId, @Param("rowsId")int i, @Param("pageSize")Integer pageSize);

	public DescoveryLeaveMessage getLeaveMsgBymsgid(int msgId);

	public List<DescoveryLeaveMessage> getAddedLeaveMsg(Integer messageId);

}
