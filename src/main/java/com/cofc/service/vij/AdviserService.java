package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.Adviser;

public interface AdviserService {
	public void addAdviser(Adviser adviser);
	public void updateAdviser(Adviser adviser);
	public void delAdviser(@Param("id")Integer id);
	public Adviser getInfoById(@Param("id")Integer id);
	public int getAdviserCount(@Param("adviser")Adviser adviser);
	public List<Adviser> getAdviserList(@Param("adviser")Adviser adviser,@Param("page")Integer page,@Param("limit")Integer limit);
}
