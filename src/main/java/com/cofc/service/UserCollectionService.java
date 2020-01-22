package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserCollection;

public interface UserCollectionService {
	public List<UserCollection> findMyGoodsCollection(UserCollection uc);

	public int addNewCollection(UserCollection uc);

	public UserCollection comfirmUserCollected(@Param("descoveryId") Integer descoveryId,
			@Param("goodsId") Integer goodsId, @Param("userId") Integer userId,@Param("loginPlat")Integer loginPlat);

	public int cancelCollection(UserCollection collection);

	public List<UserCollection> findMyDescoveryCollection(@Param("rowsId")int rowsId, @Param("pageSize")Integer pageSize, @Param("loginPlat")Integer loginPlat);

	public List<UserCollection> findMyGoodsCollectionList(@Param("userId")Integer userId, @Param("loginPlat")Integer loginPlat,  @Param("rowsId")int i,  @Param("pageSize")Integer pageSize);

	public UserCollection getUserCollectionById(@Param("collectionId")Integer collectionId);


}
