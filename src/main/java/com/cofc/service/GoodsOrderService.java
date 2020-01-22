package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.GoodsOrder;

public interface GoodsOrderService {
	public List<GoodsOrder> findMyGoodsOrder(@Param("go")GoodsOrder go,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);

	public int addNewGoodsOrder(GoodsOrder go);

	public void deleteGoodsOrder(GoodsOrder go);

	public void insertOutTradeNo(GoodsOrder go);

	public List<GoodsOrder> findThisGoodsOrder(Integer goodsId);

	public GoodsOrder getOrderByPayId(int prepayId);

	public void userPayOrder(GoodsOrder go);
	
	public List<GoodsOrder> findAllOrder(@Param("order") GoodsOrder order, @Param("goods") GoodsCommon goods,@Param("sellerWords")String sellerWords,@Param("buyerWords")String buyerWords,
			@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("rowsId") int rowsId,
			@Param("pageSize") Integer pageSize);

	public int getCountOrders(@Param("order") GoodsOrder order, @Param("goods") GoodsCommon goods,@Param("sellerWords")String sellerWords,@Param("buyerWords")String buyerWords,@Param("startTime") String startTime,@Param("endTime") String endTime);

	public String getCountOfTradeMoney(@Param("startTime")String startTime, @Param("endTime")String endTime);
	public String getCountOfTradeGoods(@Param("startTime")String startTime, @Param("endTime")String endTime);

	public List<GoodsOrder> findOrderInfoByGoodsId(@Param("goodsId")Integer goodsId,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);
	public List<Map> findOrderInfoByGoodsId2(@Param("goodsId")Integer goodsId,@Param("payStatus")Integer payStatus,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);
	
	public List<GoodsOrder> findOrderInfoBySellerId(@Param("sellerId")Integer sellerId, @Param("rowsId")int i, @Param("pageSize")Integer pageSize);

	public void sellerSendGoods(@Param("orderId")Integer orderId,@Param("sendCode")String sendCode);

	public void buyerTakeGoods(Integer orderId);
	
	public int getOrderCount(@Param("appId") Integer appId, @Param("startTime") String startTime, @Param("endTime") String endTime);
	public int getSalesAmount(@Param("appId") Integer appId, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
