package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.DescoveryType;

public interface DescoveryTypeService {
	public List<DescoveryType> findAllDescoveryType();

	public void addNewDescoveryType(DescoveryType dt);
	
	public List<DescoveryType> getAllDescoveryType();

	public List<DescoveryType> getDescoveryTypeList();

	public List<DescoveryType> getfilterDescoveryType();
	
	//根据loginPlat
	public List<DescoveryType> getTypeByLoginPlat(@Param("loginPlatList")List<String> loginPlatList);
	
	public List<DescoveryType> getListByAppId(@Param("loginPlat")Integer loginPlat);
}
