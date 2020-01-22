package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ShareLeaveMessage;

public interface ShareLeaveMessageService {

	public void addShareLeaveMessage(ShareLeaveMessage message);

	public List<ShareLeaveMessage> getShareLeaveMessageList(@Param("shareId")Integer shareId, @Param("pageNo")int pageNo, @Param("pageSize")Integer pageSize);

}
