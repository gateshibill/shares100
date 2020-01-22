package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.AfficheCommon;

public interface AfficheCommonService {
	public int publishCommonAffiche(AfficheCommon acc);

	public AfficheCommon getafficheById(Integer afficheId);

	public List<AfficheCommon> findafficheList(@Param("affc") AfficheCommon affc,
			@Param("loginPlat1") Integer loginPlat1, @Param("userKeyWords") String userKeyWords,
			@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("rowsId") Integer rowsId,
			@Param("pageSize") Integer pageSize);

	public int getCountAffiche(@Param("affc") AfficheCommon affc, @Param("loginPlat1") Integer loginPlat1,
			@Param("userKeyWords") String userKeyWords, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	public void afficheUndercarriage(@Param("idsList") List idList);

	public void updateAfficheInfo(AfficheCommon ac);

	public void deleteAffiche(@Param("afficheId")Integer afficheId);
	
	//区分应用
	public int getCountAfficheByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("affc") AfficheCommon affc, @Param("loginPlat1") Integer loginPlat1,
			@Param("userKeyWords") String userKeyWords, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
	public List<AfficheCommon> findafficheListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("affc") AfficheCommon affc,
			@Param("loginPlat1") Integer loginPlat1, @Param("userKeyWords") String userKeyWords,
			@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("rowsId") Integer rowsId,
			@Param("pageSize") Integer pageSize);
}
