package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.AfficheParise;

public interface AffichePariseService {
	public List<AfficheParise> findMyPariseList(@Param("ap")AfficheParise ap, @Param("rowsId")int rowsId, @Param("pageSize")Integer pageSize);
	public void addParisedActive(AfficheParise ap);
}
