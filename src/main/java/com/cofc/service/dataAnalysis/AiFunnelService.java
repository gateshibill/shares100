package com.cofc.service.dataAnalysis;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.dataAnalysis.AiFunnel;

public interface AiFunnelService {
	public void addAiFunnel(AiFunnel AiFunnel);

	public void updateAiFunnel(AiFunnel AiFunnel);

	public AiFunnel getAiFunnel(@Param("appId") Integer appId, @Param("userId") Integer userId,
			@Param("days") Integer days);

}