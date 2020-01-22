package com.cofc.service.goods;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.goods.GoodsSpec;

public interface GoodsSpecService {
	public void addGoodsSpec(GoodsSpec GoodsSpec);

	public void updateGoodsSpec(GoodsSpec GoodsSpec);

	public List<GoodsSpec> getGoodsSpec(@Param("appId") Integer appId, @Param("goodsId") Integer goodsId);
	
	public GoodsSpec getGoodsSpecById(@Param("id")Integer id);

}