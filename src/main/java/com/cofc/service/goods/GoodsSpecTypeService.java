package com.cofc.service.goods;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.cofc.pojo.goods.GoodsSpecType;

public interface GoodsSpecTypeService {
	public void addGoodsSpecType(GoodsSpecType goodsSpecType);

	public void updateGoodsSpecType(GoodsSpecType goodsSpecType);

	public List<GoodsSpecType> getGoodsSpecType(@Param("appId") Integer appId, @Param("goodsId") Integer goodsId);
	
}