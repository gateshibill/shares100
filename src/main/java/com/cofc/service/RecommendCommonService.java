package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.RecommendCommon;

public interface RecommendCommonService {

	void addRecommendCommon(RecommendCommon recommend);

	public void insertBatchRecommend(List<RecommendCommon> listbatch);

	RecommendCommon confirmIsRecommendtoThis(@Param("applicationId")Integer applicationId, @Param("applicationId")Integer descoveryId);

}
