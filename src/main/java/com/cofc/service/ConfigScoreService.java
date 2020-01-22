package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ConfigScore;

public interface ConfigScoreService {
	public void addConfigScore(ConfigScore score);
	public void updateConfigScore(ConfigScore score);
	public ConfigScore getConfigScoreById(@Param("id")Integer id);
	public ConfigScore getConfigScoreByLoginPlat(@Param("loginPlat")Integer loginPlat);
	public List<ConfigScore> getConfigList(@Param("score")ConfigScore score,@Param("loginPlatList")List<String> loginPlatList,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public int getConfigCount(@Param("score")ConfigScore score,@Param("loginPlatList")List<String> loginPlatList);
	public void delConfig(@Param("id")Integer id);
}
