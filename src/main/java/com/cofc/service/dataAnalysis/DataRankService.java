package com.cofc.service.dataAnalysis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.dataAnalysis.DataRank;

public interface DataRankService {
	public List<DataRank> getDataRank(@Param("appId") Integer appId, @Param("salesPersonId") Integer salesPersonId,
			@Param("userId") Integer userId, @Param("type") Integer type, @Param("objectType") Integer objectType);
}