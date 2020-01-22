package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.WebSite;

public interface WebSiteService {
	public void addWebSite(WebSite web);
	public void updateWebSite(WebSite web);
	public void delWebSite(@Param("id")Integer id);
	public WebSite getWebSiteInfo(@Param("id")Integer id,@Param("appId")Integer appId);
	public int getWebSiteCount(@Param("web")WebSite web);
	public List<WebSite> getWebSiteList(@Param("web")WebSite web,
			@Param("page")Integer page,@Param("limit")Integer limit);
	
	//区分应用
	public int getWebSiteCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("web")WebSite web);
	public List<WebSite> getWebSiteListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("web")WebSite web,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
