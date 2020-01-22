package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.DescoveryloginPlatType;

public interface DescoveryloginPlatTypeService {

	public List<DescoveryloginPlatType> getloginPlatTypeList(@Param("loginPlat")Integer loginPlat);

	public void addDescoveryloginPlatType(DescoveryloginPlatType type);

	public DescoveryloginPlatType getTypePlatName(@Param("loginPlat")Integer loginPlat, @Param("typeName")String typeName);

	public void updateLoginPlatType(DescoveryloginPlatType type);

	public DescoveryloginPlatType getloginPlatById(@Param("id")Integer id);

	public int findLoginPplatCount(@Param("type")DescoveryloginPlatType type);

	public List<DescoveryloginPlatType> findLoginPplatList(@Param("type")DescoveryloginPlatType type, @Param("page")int page, @Param("limit")Integer limit);

    //区分应用
	public int findLoginPplatCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("type")DescoveryloginPlatType type);
	public List<DescoveryloginPlatType> findLoginPplatListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("type")DescoveryloginPlatType type, @Param("page")int page, @Param("limit")Integer limit);
}
