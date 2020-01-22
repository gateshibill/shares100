package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.GoodBrand;

public interface GoodBrandService {
	public void addGoodBrand(GoodBrand brand);
	public void updateGoodBrand(GoodBrand brand);
	public void delGoodBrand(@Param("brandId")Integer brandId);
	public GoodBrand getInfoById(@Param("brandId")Integer brandId);
	public int getGoodBrandCount(@Param("brand")GoodBrand brand);
	public List<GoodBrand> getGoodBrandList(@Param("brand")GoodBrand brand,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public int checkIsAlready(@Param("cnBrandName")String cnBrandName,@Param("brandId")Integer brandId);
}
