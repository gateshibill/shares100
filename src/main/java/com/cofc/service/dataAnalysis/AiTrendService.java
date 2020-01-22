package com.cofc.service.dataAnalysis;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.dataAnalysis.AiTrend;

public interface AiTrendService {
	public void addAiTrend(AiTrend AiTrend);

	public void updateAiTrend(AiTrend AiTrend);

	public AiTrend getAiTrend(@Param("appId") Integer appId, @Param("userId") Integer userId,
			@Param("days") Integer days, @Param("type") Integer type);

}