package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationLeaveMessage;

public interface ApplicationLeaveMessageService {
	public void userLeaveMessage(ApplicationLeaveMessage alm);

	public List<ApplicationLeaveMessage> findMyAppMessages(@Param("loginPlat") Integer loginPlat,
			@Param("groupId") Integer groupId, @Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize);
    public List<ApplicationLeaveMessage> getUserMessageInfo(@Param("loginPlat")Integer loginPlat,@Param("userId")Integer userId,
    		@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
    public void updateIsEffect(@Param("messageId") Integer messageId);
    public int getMessageCount(@Param("loginPlat")Integer loginPlat);
    public int getMessageCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList);
    public List<ApplicationLeaveMessage> getMessageList(@Param("loginPlat")Integer loginPlat,
    		@Param("page")Integer page,@Param("limit")Integer limit);
    public List<ApplicationLeaveMessage> getMessageListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
    		@Param("page")Integer page,@Param("limit")Integer limit);
}
