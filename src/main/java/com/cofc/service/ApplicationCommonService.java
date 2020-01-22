package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationCommon;

public interface ApplicationCommonService {

	// List<Application> findApplicationsByCriteria(@Param("rowsId")int i,
	// @Param("pageSize")Integer pageSize);

	public List<ApplicationCommon> findApplicationsByCriteria(@Param("modelName") String modelName,
			@Param("applicationName") String applicationName, @Param("applicationStatus") Integer applicationStatus,
			@Param("userId") Integer userId, @Param("userKeyWords") String userKeyWords,
			@Param("applicationType") Integer applicationId, @Param("rowsId") Integer i,
			@Param("pageSize") Integer pageSize, @Param("type")Integer type, @Param("isRecommend")Integer isRecommend, @Param("groupType")Integer groupType);

	public ApplicationCommon getApplicationsByCriteria(Integer appId);

	public void updateApplicationStatus(ApplicationCommon appli);

	public int createNewApplication(ApplicationCommon appl);

	public ApplicationCommon comfirmIsCreated(@Param("groupType") Integer groupType,
			@Param("groupName") String applicationName, @Param("userId") Integer applicationOwner);

	public List<ApplicationCommon> findGroupsByCriteria(@Param("aplic") ApplicationCommon aplic, @Param("rowsId") int i,
			@Param("pageSize") Integer pageSize);

	public List<ApplicationCommon> findMyJoinedGroups(@Param("userId") Integer userId,
			@Param("applicationType") Integer applicationType, @Param("loginPlat") Integer loginPlat,
			@Param("rowsId") int i, @Param("pageSize") Integer pageSize, @Param("applicationName")String appName);

	public List<ApplicationCommon> findOthersCreatedByCriteria(@Param("loginPlat") Integer loginPlat,
			@Param("applicationType") Integer typeType, @Param("userId") Integer userId, @Param("rowsId") int i,
			@Param("pageSize") Integer pageSize);

	public ApplicationCommon getApplicationById(@Param("applicId") Integer groupId);

	public int countMyCreatedGoups(@Param("appowner") Integer appowner, @Param("apptype") Integer apptype,
			@Param("parentId") Integer parentId);

	public List<ApplicationCommon> findApplicationCommon(@Param("smallRoutine") Integer loginPlat);

	public List<ApplicationCommon> findMyCreatedGroups(@Param("loginPlat") Integer loginPlat,
			@Param("createrId") Integer createrId, @Param("applicationType") Integer applicationType,
			@Param("rowsId") Integer i, @Param("pageSize") Integer pageSize, @Param("applicationName")String appName);

	public List<ApplicationCommon> findMyCreatedChildApplication(@Param("createrId") Integer createrId,
			@Param("smallRoutine") Integer smallRoutine, @Param("applicationType") Integer applicationType,
			@Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize, @Param("applicationName")String appName);

	public List<ApplicationCommon> findMyJoinedChildApplication(@Param("userId") Integer userId,
			@Param("smallRoutine") Integer smallRoutine, @Param("applicationType") Integer applicationType,
			@Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize, @Param("applicationName")String appName);

	public List<ApplicationCommon> findOthersCreatedChildApplication(@Param("smallRoutine") Integer smallRoutine,
			@Param("applicationType") Integer applicationType, @Param("userId") Integer userId,
			@Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize);

	public List<ApplicationCommon> findAllxiaochengxuByCriteria(List<String> userIds);

	public List<ApplicationCommon> getApplicationCommonList(Map<String, Object> map);

	public int getApplicationCommonCount(Map<String, Object> map);

	public ApplicationCommon confirmCurrAPPbelong(@Param("loginPlat") Integer loginPlat,
			@Param("idsList") List<String> asList);

	public List<ApplicationCommon> findMyCreatedAllAppications(@Param("apuids") List<String> idsl);

	public List<ApplicationCommon> findCurrTypeApplication(@Param("applicationType") Integer applicationType,
			@Param("loginPlat") Integer exceptloginPlat);

	public List<ApplicationCommon> getApplicationAndGroup(@Param("applicationType") Integer applicationType,
			@Param("groupType") Integer groupType);

	public ApplicationCommon getApplicationCommonById(@Param("applicationId") Integer applicationId);

	public List<ApplicationCommon> findAllApplicationTypeis2(@Param("applicationType") Integer applicationType,
			@Param("loginPlat") Integer loginPlat, @Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize, @Param("applicationName")String appName);

	public List<ApplicationCommon> findChildApplicationByParentId(@Param("loginPlat") Integer loginPlat,
			@Param("applicationType") Integer applicationType, @Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	public List<ApplicationCommon> findApptypeis3bySmallroutine(@Param("smallRoutine") Integer smallRoutine,
			@Param("pageNo") Integer i, @Param("pageSize") Integer pageSize);

	public List<ApplicationCommon> findApplication(@Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize);

	public int findApplicationsByCount(@Param("modelName") String modelName,
			@Param("applicationName") String applicationName, @Param("applicationStatus") Integer applicationStatus,
			@Param("userId") Integer userId, @Param("userKeyWords") String userKeyWords,
			@Param("applicationType") Integer applicationId,@Param("type") Integer type, @Param("isRecommend")Integer isRecommend,@Param("groupType") Integer type11);

	public void deleteApplication(ApplicationCommon app);

	public List<ApplicationCommon> getRecommendList(@Param("rowsId")int i, @Param("pageSize")Integer pageSize, @Param("loginPlat")Integer loginPlat, @Param("applicationName")String appName);

	public Integer getAppVisitorCount(@Param("loginPlat")Integer loginPlat);

	public List<ApplicationCommon> getAppIsExpire(@Param("userId")Integer userId);

	public List<ApplicationCommon> getAppClassifyList(@Param("type")Integer typeId, @Param("loginPlat")Integer loginPlat,@Param("pageNo") int i,@Param("pageSize") Integer pageSize, @Param("applicationName")String appName);

	public ApplicationCommon getApplicationName(@Param("applicationName")String applicationName);

	public void updateApplicationOwner(ApplicationCommon app);

	public List<ApplicationCommon> getAppList(@Param("type")Integer i, @Param("userId")Integer object);
	
	public int getAllAppcalitionCount();
	//分应用管理平台
	
	public List<ApplicationCommon> getApplicationByLoginPlat(@Param("loginPlatList")List<String> loginPlatList);
	public List<ApplicationCommon> findApplicationsByCriteriaByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("modelName") String modelName,
			@Param("applicationName") String applicationName, @Param("applicationStatus") Integer applicationStatus,
			@Param("userId") Integer userId, @Param("userKeyWords") String userKeyWords,
			@Param("applicationType") Integer applicationId, @Param("rowsId") Integer i,
			@Param("pageSize") Integer pageSize, @Param("type")Integer type, @Param("isRecommend")Integer isRecommend, @Param("groupType")Integer groupType); 
	public int findApplicationsByCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("modelName") String modelName,
			@Param("applicationName") String applicationName, @Param("applicationStatus") Integer applicationStatus,
			@Param("userId") Integer userId, @Param("userKeyWords") String userKeyWords,
			@Param("applicationType") Integer applicationId,@Param("type") Integer type, @Param("isRecommend")Integer isRecommend,@Param("groupType") Integer type11);
	
	//优化后的查询
	public List<ApplicationCommon> getNewApplicationList(@Param("loginPlatList")List<String> loginPlatList);
}
