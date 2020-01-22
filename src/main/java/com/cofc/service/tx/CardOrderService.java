package com.cofc.service.tx;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.tx.CardOrder;

public interface CardOrderService {
	public void addCardData(CardOrder data);
	public void updateCardData(CardOrder data);
	public void delCardData(@Param("id")Integer id);
	public CardOrder getCardDataInfo(@Param("id")Integer id);
	public int getCardDataCount(@Param("data")CardOrder data);
	public List<CardOrder> getCardDataList(@Param("data")CardOrder data,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public CardOrder getCardOrder(@Param("agentCode")String agentCode,
			@Param("agentOrderNum")String agentOrderNum,@Param("isUse")Integer isUse);
}
