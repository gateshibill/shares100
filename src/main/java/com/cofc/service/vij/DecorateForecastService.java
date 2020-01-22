package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.DecorateForecast;

public interface DecorateForecastService {
	public void addVijbudget(DecorateForecast vijbudget);
	public DecorateForecast getRoomByProjectId(@Param("projectId")Integer projectId);
	public void updateDecorateForecast(DecorateForecast vijbudget);
	public int getDecorateForecastCount(@Param("projectId")Integer projectId, @Param("homeImages")String homeImages);
	public DecorateForecast getDecorateForecastById(@Param("id")Integer id);
	public List<DecorateForecast> getDecorateForecastList(@Param("dForecast")DecorateForecast dForecast,@Param("projectId")Integer projectId);
}
