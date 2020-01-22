package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserShareGoods;

public interface UserShareGoodsService {
	public List<UserShareGoods> findAllsharedGoods(@Param("userType")Integer userType,@Param("userId")Integer userId, @Param("goodsName")String goodsName, @Param("rowsId")Integer rowId,@Param("pageSize")Integer pageSize);

	public void addNewShare(UserShareGoods usGoods);
}
