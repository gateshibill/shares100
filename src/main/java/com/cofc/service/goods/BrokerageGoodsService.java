package com.cofc.service.goods;


import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.goods.BrokerageGoods;

public interface BrokerageGoodsService {
	public void addBrokerageGoods(BrokerageGoods brokerageGoods);

	public void updateBrokerageGoods(BrokerageGoods brokerageGoods);

	public BrokerageGoods getBrokerageGoods(@Param("appId") Integer appId, @Param("goodsId") Integer goodsId);

}