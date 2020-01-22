package com.cofc.service.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.cofc.pojo.activity.CutGoods;

public interface CutGoodsService {

	//public PingGoods getCutGoods(@Param("appId") Integer appId,@Param("cutGoodsId") Integer cutGoodsId);
	public CutGoods getCutOders(@Param("appId") Integer appId,@Param("id") Integer id);
	public List<CutGoods> getCutGoodsList(@Param("appId") Integer appId);
	//获取砍价数据 -商品编辑
	public CutGoods getCutByGoodsId(@Param("id") Integer id);
	//修改砍价状态为0
	public void updateCutByGoodsId(@Param("id") Integer id);
	//修改砍价状态为1
	public void updateCutByCutGoods(CutGoods cutGoods);
	//添加砍价商品
	public void addCutGoods_1(CutGoods cutGoods);
	//查询全部砍价商品总数
	public Integer findAllCutGoodsCount();
	//查询全部砍价商品-管理页
	public List<CutGoods> getAllCutGoodsList(@Param("page") Integer page, @Param("limit") Integer limit);
	//根据id删除砍价商品
	public void deleteCutGoodsById(@Param("id") Integer id);
	//根据id查询砍价商品
	public CutGoods getCutByCutGoodsId(@Param("id") Integer id);
	
	
	
}