package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.PrizeCommon;

public interface PrizeCommonService {
	public List<PrizeCommon> findPrizeByCriteria(@Param("activityId")Integer activityId);

	public void addBatchPrizes(List<PrizeCommon> inserList);

	public void updatePrizeInfo(PrizeCommon mygetPrize);
}
