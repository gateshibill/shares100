package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.DescoveryCommon;

public interface DescoveryCommonService {

	public int addNewDesCommon(DescoveryCommon dc);

	public List<DescoveryCommon> publishedDescoveryList(@Param("publisherId") Integer publisherId,@Param("type") Integer type,
			@Param("isPass") Integer isPass, @Param("loginPlat") Integer loginPlat,@Param("rowsId") Integer rowsId,
			@Param("pageSize") Integer pageSize, @Param("groupType")Integer group_type,
			@Param("descoveryId")Integer descoveryId);

	public void passThisDescovery(Integer descoveryId);

	public DescoveryCommon getDescoveryById(@Param("descoveryId")Integer descoveryId);

	public void updateDescoveryCommonInfo(DescoveryCommon currDescovery);

	public List<DescoveryCommon> findDescveryCommonInfo(@Param("descovery") DescoveryCommon descovery,
			@Param("userKeyWords") String userKeyWords, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("rowsId") int i, @Param("pageSize") Integer pageSize);

	public int getDescoveryCount(@Param("descovery") DescoveryCommon descovery,
			@Param("userKeyWords") String userKeyWords, @Param("startTime") String startTime,
			@Param("endTime") String endTime);

	//项目详情
	public DescoveryCommon getPublisherDetails(@Param("descoveryId")Integer descoveryId, @Param("type")Integer type);

	public List<DescoveryCommon> findRecommendToAllDecovery();

	public List<DescoveryCommon> findRecommendToSameDescovery(@Param("applicationType")Integer applicationType);

	public void deleteDescoveryCommon(DescoveryCommon descovery);
	
	//会显示别的应用的资讯
	public List<DescoveryCommon> findRecommendDecoveryByApplication(@Param("applicationID")Integer applicationID, @Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);
	
	//只显示自己应用里的资讯
	public List<DescoveryCommon> findRecommendDecoveryByApplication2(@Param("applicationID")Integer applicationID, @Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);
	
	public List<DescoveryCommon> findDecovery (@Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);
	
	public List<DescoveryCommon> getDescoveryListByPlat(@Param("login_plat")Integer login_plat, @Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);

	public List<DescoveryCommon> getLoginPlatTypeIdList(@Param("loginPlat")Integer loginPlat,@Param("type") int i,@Param("pageNo") int pageNo, @Param("pageSize")Integer pageSize);
    
	//区分应用
	public int getDescoveryCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("descovery") DescoveryCommon descovery,
			@Param("userKeyWords") String userKeyWords, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
	public List<DescoveryCommon> findDescveryCommonInfoByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("descovery") DescoveryCommon descovery,
			@Param("userKeyWords") String userKeyWords, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("rowsId") int i, @Param("pageSize") Integer pageSize);
}
