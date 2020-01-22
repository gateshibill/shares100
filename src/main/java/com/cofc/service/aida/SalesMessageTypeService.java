package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.ActionColumn;
import com.cofc.pojo.aida.SalesMessageType;

public interface SalesMessageTypeService {

	// 个人用户行为信息，按照时间维度显示，用户查看个人.互动。
	public List<SalesMessageType> getSalesMessageTypeList(@Param("appId") Integer appId,
			@Param("userId") Integer userId);
}