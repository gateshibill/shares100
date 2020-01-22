package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.Adv;

public interface AdvService {
	public void addAdv(Adv adv);
	public void updateAdv(Adv adv);
	public void delAdv(@Param("advId")Integer advId);
	public Adv getInfoById(@Param("advId")Integer advId);
	public List<Adv> getlistByLocation(@Param("advLocation")Integer advLocation,@Param("isEffect")Integer isEffect,
			@Param("source")Integer source,@Param("appId")Integer appId);
	public int getAdvCount(@Param("adv")Adv adv);
	public List<Adv> getAdvList(@Param("adv")Adv adv,@Param("page")Integer page,@Param("limit")Integer limit);
}
