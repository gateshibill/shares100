package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.GoodShareCode;

public interface GoodShareCodeService {
	public void addGoodCode(GoodShareCode code);
	public GoodShareCode getGoodCode(@Param("userId")Integer userId,
			@Param("appId")Integer appId,@Param("goodId")Integer goodId);
}
