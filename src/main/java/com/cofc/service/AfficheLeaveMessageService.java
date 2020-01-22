package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.AfficheLeaveMessage;

public interface AfficheLeaveMessageService {
	public void addNewMessage(AfficheLeaveMessage alm);

	public List<AfficheLeaveMessage> findLeaveActiveMessage(@Param("afficheId")Integer afficheId,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);
}
