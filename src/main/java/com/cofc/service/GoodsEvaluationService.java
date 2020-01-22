package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.GoodsEvaluation;

public interface GoodsEvaluationService {
	public List<GoodsEvaluation> findAllParentEvaluation(@Param("goodsId")Integer goodsId,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);
	
	public List<GoodsEvaluation> findAddedEvaluation(List<Integer> idsList);
	
	public GoodsEvaluation findEvaluationById(Integer evaluationId);
	
	public void addNewEvaluation(GoodsEvaluation evaluation);
}
