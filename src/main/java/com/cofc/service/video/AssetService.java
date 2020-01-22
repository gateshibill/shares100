package com.cofc.service.video;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.video.Asset;

public interface AssetService {
	public void addAsset(Asset Asset);
	public void updateAsset(Asset Asset);
	public void delAsset(@Param("id")Integer id);
	public Asset getAssetById(@Param("id")Integer id);
	public int getAssetCount(@Param("asset")Asset asset);
	public List<Asset> getAssetList(@Param("asset") Asset asset,@Param("page")Integer page,@Param("limit")Integer limit);
	public List<Asset> getAssetListByColumnId(@Param("columnId")Integer columnId,@Param("page")Integer page,@Param("limit")Integer limit);
}
