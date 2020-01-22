package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.GoodTypeBanner;

public interface GoodTypeBannerService {

	//添加
	public void addGoodTypeBanner(GoodTypeBanner banner);
	//修改
	public void upGoodTypeBanner(GoodTypeBanner banner);
	//删除
	public void delGoodTypeBanner(@Param("id")Integer id);
	//详情
	public GoodTypeBanner getinfoBanner(@Param("id")Integer id);
	//总数
	public int getGoodTypeBannerCount(@Param("banner")GoodTypeBanner banner);
	
	public List<GoodTypeBanner> queryGoodTypeBanner(@Param("banner")GoodTypeBanner banner,
			@Param("page")Integer page,@Param("limit")Integer limit);
	
	public List<GoodTypeBanner> getTypeBannerByTypeId(@Param("goodTypeId")Integer goodTypeId);
	
}
