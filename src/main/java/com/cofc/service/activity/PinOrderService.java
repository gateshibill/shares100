package com.cofc.service.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.activity.PinGoods;
import com.cofc.pojo.activity.PinOrder;



public interface PinOrderService {

	public PinGoods getPinOrder(@Param("appId") Integer appId,@Param("id") Integer id);
	public List<PinOrder> getPinOrderList(@Param("appId") Integer appId,@Param("goodsId") Integer goodsId);
}