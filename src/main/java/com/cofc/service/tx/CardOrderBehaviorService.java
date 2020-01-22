package com.cofc.service.tx;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.tx.CardOrderBehavior;

public interface CardOrderBehaviorService {
	public void addCardOrderBehavior(CardOrderBehavior behavior);
	public CardOrderBehavior getBehaviorInfo(@Param("orderNum")String orderNum,
			@Param("agentOrderNum")String agentOrderNum,@Param("agentCode")String agentCode);
}
