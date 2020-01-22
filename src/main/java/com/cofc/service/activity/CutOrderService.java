package com.cofc.service.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.activity.CutOrder;
       

public interface CutOrderService {

	public CutOrder getCutOrder(@Param("appId") Integer appId,@Param("id") Integer id);
	public void updateCutOrder(CutOrder cutOrder);
	public List<CutOrder> getCutOrderList(@Param("appId") Integer appId,@Param("goodsId") Integer goodsId);
}