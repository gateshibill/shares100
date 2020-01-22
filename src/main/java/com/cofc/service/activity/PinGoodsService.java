package com.cofc.service.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.activity.CutGoods;
import com.cofc.pojo.activity.PinGoods;



public interface PinGoodsService {

	public PinGoods getPinGoods(@Param("appId") Integer appId,@Param("id") Integer idd);
	public List<PinGoods> getPinGoodsList(@Param("appId") Integer appId);
	//获取拼团商品
	public PinGoods getPinByGoodsId(@Param("id") Integer id);
	//修改拼团状态为0
	public void updatePinByGoodsId(@Param("id") Integer id);
	//修改拼团状态为1
	public void updatePinByPinGoods(PinGoods pinGoods);
	//添加拼团商品
	public void addPinGoods_1(PinGoods pinGoods);
	//查询全部拼团商品总数
	public Integer findAllPinGoodsCount();
	//查询全部拼团商品-管理页
	public List<PinGoods> getAllPinGoodsList(@Param("page") Integer page, @Param("limit") Integer limit);
	//根据id删除拼团商品
	public void deletePinGoodsById(@Param("id") Integer id);
	//根据id查询拼团商品
	public PinGoods getPinByPinGoodsId(@Param("id") Integer id);
}