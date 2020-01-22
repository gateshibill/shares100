package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.HomeType;

public interface HomeTypeService {

	public void addHomeType(HomeType hType);

	public void updateHomeType(HomeType hType);
	
	public void deleteHomeType(@Param("id")Integer id);
	
	public HomeType getInfoByid(@Param("id")Integer id);
	
	public int getHomeTypeCount(@Param("hType")HomeType hType);
	
	public List<HomeType> queryHomeTypeList(@Param("hType")HomeType hType,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
