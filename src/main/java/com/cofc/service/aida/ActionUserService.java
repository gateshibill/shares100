package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.ActionUser;

public interface ActionUserService {

	// 根据销售找客户对象
	public List<ActionUser> getActionUserList(@Param("appId") Integer appId,@Param("salesPersonId") Integer salesPersonId, @Param("page") Integer page,
			@Param("pageSize") Integer pageSize);
}