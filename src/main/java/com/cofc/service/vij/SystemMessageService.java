package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;
//import org.junit.runners.Parameterized.Parameters;

import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.GoodsType;
import com.cofc.pojo.vij.SystemMessage;

public interface SystemMessageService {
	public void addSysMes(SystemMessage message);
	public void updateSysMes(SystemMessage message);
	public void delSysMes(@Param("mesId")Integer mesId);
	public int getSysMesCount(@Param("message")SystemMessage message);
	public List<SystemMessage> getSysMesList(@Param("message")SystemMessage message,
			@Param("page")Integer page,@Param("limit")Integer limit);
	//优化查询
		public List<SystemMessage> getNewMessageList(@Param("loginPlatList")List<String> loginPlatList);


		public int getCountMessage(@Param("loginPlat")Integer loginPlat,SystemMessage message);
		public List<SystemMessage> findMessageList2(@Param("loginPlat")Integer loginPlat,
				@Param("message")SystemMessage message, @Param("page")int page, @Param("limit")int limit);
		
		
		//区分应用
		public int getCountMessageByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
				@Param("message")SystemMessage message);
		public List<SystemMessage> getMessageByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
	    		@Param("message")SystemMessage message,@Param("page")int page, @Param("limit")int limit);
}
