package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.GoodsType;

public interface GoodsTypeService {
	
	public List<GoodsType> findAllGoodsType(@Param("loginPlat")Integer loginPlat, @Param("page")Integer page, @Param("limit")Integer limit, @Param("typeName")String typeName,@Param("userId") Integer userId,
			@Param("applicationName")String applicationName,@Param("isRemove")Integer isRemove);
	
	public int addNewGoodsType(GoodsType gt);
	
	public GoodsType confirmThisTypeExists(@Param("typeName")String typeName, @Param("loginPlat")Integer integer);
	
	public int findAllGoodsTypeCount(@Param("loginPlat")Integer loginPlat, @Param("typeName")String typeName, @Param("userId")Integer userId,
			@Param("applicationName")String applicationName,@Param("isRemove")Integer isRemove);
	
	public GoodsType getLoginPlatTypeName(@Param("typeName")String typeName, @Param("loginPlat")Integer loginPlat, @Param("userId")Integer integer);

	public void deleteGoodsType(@Param("typeId")Integer typeId);

	public GoodsType getGoodsTypeById(@Param("typeId")Integer typeId);

	public void updateGoodsType(GoodsType type);

	public List<GoodsType> showGoodsTypeList(@Param("loginPlat")Integer loginPlat, @Param("userId")Integer userId);
    
	//区分平台
	public List<GoodsType> getGoodTypeByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("loginPlat")Integer loginPlat, @Param("page")Integer page, @Param("limit")Integer limit, @Param("typeName")String typeName,@Param("userId") Integer userId,
			@Param("applicationName")String applicationName,@Param("isRemove")Integer isRemove);
	public int getGoodTypeCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("loginPlat")Integer loginPlat, @Param("typeName")String typeName, @Param("userId")Integer userId,
			@Param("applicationName")String applicationName,@Param("isRemove")Integer isRemove);
	
	//优化查询
	public List<GoodsType> getNewTypeList(@Param("loginPlatList")List<String> loginPlatList);
	
	//唯爱家递归分类
	public List<GoodsType> getVijTypeList(@Param("loginPlat")Integer loginPlat);
	
	//唯爱家小程序用父级类型
	public List<GoodsType> getVijParentList(@Param("loginPlat")Integer loginPlat);
	
	//根据父级找子集
	public List<GoodsType> getVijChildList(@Param("parentId")Integer parentId,@Param("page")Integer page,
			@Param("limit")Integer limit);
	public int getVijChildCount(@Param("parentId")Integer parentId);
	
	//批量真删除
	public void delGoodsType(@Param("goodsId") Integer goodsId);

}
