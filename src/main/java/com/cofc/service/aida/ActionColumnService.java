package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.ActionColumn;

public interface ActionColumnService {

	
	public List<ActionColumn> getActionColumnListBySpAndType(@Param("appId") Integer appId,
			@Param("salesPersonId") Integer salesPersonId, @Param("type") Integer type,
			@Param("objectType") Integer objectType, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	
	public List<ActionColumn> getCustomerActionColumnList(@Param("appId") Integer appId,
			@Param("userId") Integer userId, @Param("page") Integer pageNo, @Param("pageSize") Integer pageSize);

	
	public List<ActionColumn> getSalesCustomerActionColumnList(@Param("appId") Integer appId,
			@Param("salesPersonId") Integer salesPersonId, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);
	
	
	public List<ActionColumn> getOneCustomerActionColumnList(@Param("appId") Integer appId,@Param("salesPersonId") Integer salesPersonId,
			@Param("userId") Integer userId, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);
	

}