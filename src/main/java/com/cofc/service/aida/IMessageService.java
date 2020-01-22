package com.cofc.service.aida;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.IMessage;

public interface IMessageService {

	public void addIMessage(IMessage IMessage);

	public void addIMessageList(List<IMessage> iMessageList);

	public void delIMessage(@Param("appId") Integer appId, @Param("id") Integer id);

	public void updateIMessage(IMessage IMessage);
	
	public void updateIMessageBatch(List<IMessage> list);
	//未读消息条数
	public int getUnreadIMessageCount(@Param("appId") Integer appId, @Param("fromId") Integer fromId,
			@Param("userId") Integer userId);
	
	public List<IMessage> getIMessageList(@Param("appId") Integer appId, @Param("salesPersonId") Integer salesPersonId,
			@Param("userId") Integer userId, @Param("page") Integer page, @Param("limit") Integer limit);

}