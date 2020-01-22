package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.Express;

public interface ExpressService {
	public void addExpress(Express exp);
	public void updateExpress(Express exp);
	public void getInfoById(@Param("expressId")Integer expressId);
	public int getExpressCount(@Param("exp")Express exp);
	public List<Express> getExpressList(@Param("exp")Express exp,@Param("page")Integer page,
			@Param("limit")Integer limit);
}
