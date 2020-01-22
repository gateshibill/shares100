package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.Collocation;

public interface CollocationService {
	public void addColl(Collocation coll);
	public void updateColl(Collocation coll);
	public void delColl(@Param("id")Integer id);
	public Collocation getInfoById(@Param("id")Integer id);
	public int getCollCount(@Param("coll")Collocation coll);
	public List<Collocation> getCollList(@Param("coll")Collocation coll,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
