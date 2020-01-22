package com.cofc.service.goods;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.goods.GoodsMyself;

public interface GoodsMyselfService {
	public void addGoodsMyself(GoodsMyself GoodsMyself);

	public void updateGoodsMyself(GoodsMyself GoodsMyself);

	public void delGoodsMyself(@Param("appId") Integer appId, @Param("id") Integer id);

	public GoodsMyself getGoodsMyself(@Param("appId") Integer appId, @Param("id") Integer id);

	public GoodsMyself getGoodsMyselfByUserIdAndGoodId(@Param("appId") Integer appId, @Param("userId") Integer userId,
			@Param("goodsId") Integer goodsId);

	public List<GoodsMyself> getGoodsMyselfList(@Param("appId") Integer appId, @Param("userId") Integer userId);

}