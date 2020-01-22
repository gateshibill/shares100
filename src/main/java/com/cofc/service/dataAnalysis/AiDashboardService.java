package com.cofc.service.dataAnalysis;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.dataAnalysis.AiDashboard;

public interface AiDashboardService {
	public void addAiDashboard(AiDashboard AiDashboard);

	public void updateAiDashboard(AiDashboard AiDashboard);

	public AiDashboard getAiDashboard(@Param("appId") Integer appId, @Param("userId") Integer userId,
			@Param("days") Integer days);

}