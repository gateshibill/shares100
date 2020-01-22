package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.Adviser;


public interface DesignerService {
	//增加设计师 
	void addDesigner(Adviser adviser);
	
	//获取单个设计师的资料
	Adviser getSingleDesigner(@Param("id")Integer id);
	//查询所有的设计师总数
	int getCountDesigner(@Param("adviser")Adviser adviser);
	//查询总的设计师
	List<Adviser> queryCountDesigner(@Param("adviser")Adviser adviser,
			@Param("page")Integer page,@Param("limit")Integer limit);  
	//修改设计师资料
	void updateDesigner(Adviser adviser);
	//删除设计师
	int delectDesigner(@Param("id") Integer id);
}
