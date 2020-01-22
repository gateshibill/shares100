package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.HomeSetup;

public interface HomeSetupService {
	//添加预约量房
	public void addHomeSetup(HomeSetup homeSetup);
	//查询某个量房
	HomeSetup getInfoByHome(@Param("id")Integer id);
	//查询所有的量房的总数
	int getCountHomeSetup(@Param("hSetup")HomeSetup hSetup);
	//查询所有的量房
	List<HomeSetup> queryHomeSetup(@Param("hSetup")HomeSetup hSetup,@Param("page")Integer page, 
			@Param("limit")Integer limit);
	//修改量房
	void updateHomeSetup(@Param("hSetup")HomeSetup hSetup);
	//删除量房
	void deleteHomeSetup(@Param("id")Integer id);
}
