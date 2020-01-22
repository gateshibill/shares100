package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.GoodsCommon;

public interface GoodsCommonService {

	public List<GoodsCommon> findGoodsList(@Param("goods")GoodsCommon goods, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("rowsId")int rowsId, @Param("pageSize")int pageSize);

	public List<GoodsCommon> findGoodsList2(@Param("loginPlat")Integer loginPlat,@Param("goods")GoodsCommon goods, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("rowsId")int rowsId, @Param("pageSize")int pageSize,@Param("userName") String userName, @Param("appName")String appName);
	
	public int getCountGoods(@Param("loginPlat")Integer loginPlat,@Param("goods")GoodsCommon goods, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("userName")String userName, @Param("appName")String appName);

	public int addNewGoods(GoodsCommon goods);

	public GoodsCommon getGoodsById(Integer goodsId);
	//获得个人促销产品
	public List<GoodsCommon> getDtbtGoodsByUserId(Integer userId);

	public void updateGoodsInfo(GoodsCommon goods);

	public void goodsUndercarriage(@Param("idsList")List idList);
	
	public void managerShenheGoods(Integer goodsId);

	public void addNewBatchGoods(@Param("goodsList")List<GoodsCommon> goodsBatch);

	public void deleteGoodsCommon(GoodsCommon goods);

	public List<GoodsCommon> findGoodsTypeIsNull(@Param("typeId")Integer typeId, @Param("loginPlat")Integer loginPlat);

	public List<GoodsCommon> getMyGoodsList(@Param("goods")GoodsCommon goods, @Param("rowsId")Integer pageNo, @Param("pageSize")Integer pageSize);
    
	public int getMyGoodsCount(@Param("goods")GoodsCommon goods);
	
	public String getLoginPlatSelledCount(@Param("goods")GoodsCommon goods, @Param("startTime")String startTime, @Param("endTime")String endTime);

	public Integer getgoodsBrowseCount(@Param("goods")GoodsCommon goods, @Param("startTime")String startTime, @Param("endTime")String endTime);

	public List<GoodsCommon> getGoodsByIds(@Param("ids")List<String> asList);
    
	//前端更新库存
	public void updateGoodsStock(@Param("goodsId")Integer goodsId,@Param("goodsStock")Integer goodsStock);
//	public List<GoodsCommon> getGoodsTypeCountList(Map<String, Object> map);

//	public int getGoodsTypeCount(Map<String, Object> map);
	
	//区分应用
	public int getCountGoodsByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("goods")GoodsCommon goods, @Param("startTime")String startTime, 
			@Param("endTime")String endTime, @Param("userName")String userName, 
			@Param("appName")String appName);
    public List<GoodsCommon> getGoodsByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
    		@Param("goods")GoodsCommon goods, @Param("startTime")String startTime, 
    		@Param("endTime")String endTime, @Param("page")int page, 
    		@Param("limit")int limit,@Param("userName") String userName, 
    		@Param("appName")String appName);
    public String getSelledCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
    		@Param("goods")GoodsCommon goods,
    		@Param("startTime")String startTime, @Param("endTime")String endTime);
    public void updateIsScore(@Param("goodId")Integer goodId,@Param("isScore")Integer isScore);
    
	public List<GoodsCommon> newfindGoodsList(@Param("goods")GoodsCommon goods, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("rowsId")int rowsId, @Param("pageSize")int pageSize);

}
