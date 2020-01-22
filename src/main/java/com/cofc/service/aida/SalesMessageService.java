package com.cofc.service.aida;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.SalesMessage;

public interface SalesMessageService {

	public void addSalesMessage(SalesMessage salesMessage);
	
	public void delSalesMessage(@Param("appId") Integer appId,@Param("id") Integer id);
	
	public void updateSalesMessage(@Param("appId") Integer appId,
			@Param("type") Integer type,@Param("id") Integer id,@Param("message") String message,@Param("lastTime") Date lastTime);
	public List<SalesMessage> getSalesMessageList(@Param("appId") Integer appId,
			@Param("userId") Integer userId,@Param("type") Integer type);
	

}