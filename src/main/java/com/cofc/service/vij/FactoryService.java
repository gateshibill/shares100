package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.Adviser;

public interface FactoryService {

	//增加添加工长
		void addFactory(Adviser adviser);
		
		//获取单个工长的资料
		Adviser getSingleFactory(@Param("id")Integer id);
		//查询所有的工长总数
		int getCountFactory(@Param("adviser")Adviser adviser);
		//查询总的工长
		List<Adviser> queryCountFactory(@Param("adviser")Adviser adviser,
				@Param("page")Integer page,@Param("limit")Integer limit);  
		//修改工长
		void updateFactory(Adviser adviser);
		//删除工长
		int delectFactory(@Param("id") Integer id);
}
