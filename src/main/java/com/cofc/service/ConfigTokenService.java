package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ConfigToken;

public interface ConfigTokenService {
	public void addToken(ConfigToken token);
	public void updateToken(ConfigToken token);
	public int getTokenCount(@Param("token")ConfigToken token,@Param("loginPlatList")List<String> loginPlatList);
	public List<ConfigToken> getTokenList(@Param("token")ConfigToken token,@Param("loginPlatList")List<String> loginPlatList,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public void delToken(@Param("tokenId")Integer tokenId);
	public ConfigToken getTokenById(@Param("tokenId")Integer tokenId);
	public ConfigToken getTokenByLogin(@Param("loginPlat")Integer loginPlat);
}
