package com.cofc.service.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.activity.DtbtGoods;
import com.cofc.pojo.activity.PinGoods;


public interface DtbtGoodsService {

	public List<DtbtGoods> getDtbtGoodsList(@Param("appId") Integer appId,@Param("userId") Integer userId);
	//获取秒杀商品
	public DtbtGoods getDtbtByGoodsId(@Param("id") Integer id);
	//修改秒杀状态为0
	public void updateDtbtByGoodsId(@Param("id") Integer id);
	//修改秒杀状态为1
	public void updateDtbtByDtbtGoods(DtbtGoods dtbtGoods);
	//添加秒杀商品
	public void addDtbtGoods_1(DtbtGoods dtbtGoods);
	//查询全部秒杀商品总数
	public Integer findAllDtbtGoodsCount();
	//查询全部秒杀商品-管理页
	public List<DtbtGoods> getAllDtbtGoodsList(@Param("page") Integer page, @Param("limit") Integer limit);
	//根据id删除秒杀商品
	public void deleteDtbtGoodsById(@Param("id") Integer id);
	//根据id查询秒杀商品
	public DtbtGoods getDtbtByDtbtGoodsId(@Param("id") Integer id);

}