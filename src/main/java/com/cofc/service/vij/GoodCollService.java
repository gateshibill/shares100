package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.GoodColl;

public interface GoodCollService {
	public void addGoodColl(GoodColl coll);
	public void updateGoodColl(GoodColl coll);
	public void delGoodColl(@Param("goodCollId")Integer goodCollId);
	public GoodColl getInfoById(@Param("goodCollId")Integer goodCollId);
	public int getGoodCollCount(@Param("gc")GoodColl gc);
	public List<GoodColl> getGoodCollList(@Param("gc")GoodColl gc,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
