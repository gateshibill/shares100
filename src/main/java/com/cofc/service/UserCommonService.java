package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserCommon;

public interface UserCommonService {

	public UserCommon getUserByOpenId(String openid);

	public int addNewUserCommon(UserCommon comUser);

	public UserCommon getUserByUserId(Integer userId);

	public int commonInfoUpdate(UserCommon commonInfo);
	
	public int getCountUser(@Param("user")UserCommon user,@Param("userStatus")Integer userStatus, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("loginPlat1")Integer loginPlat1);
	
	public List<UserCommon> findUserList(@Param("user")UserCommon user, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("rowsNo")int i, @Param("pageSize")Integer pageSize,@Param("loginPlat1") Integer loginPlat1);
	
	public void userUndercarriage(@Param("idsList")List idList);

	public void userRecovery(@Param("idsList")List idList);
	
	public void changeUserStatus(@Param("userId")Integer userId,@Param("userStatus")Integer userStatus);

	public List<UserCommon> findByVagueName(String buyerWords);

	public void changeUserRenzheng(@Param("userId")Integer userId, @Param("isRenzheng")Integer isRenzheng);

	public void changeUserIsManager(@Param("userId")Integer userId,@Param("isManager")Integer isManager);

	public List<UserCommon> findManagerList(UserCommon user, Object object, String startTime, String endTime, int i,
			Integer pageSize);

	public void deleteUserId(@Param("userId")Integer userId);

	public List<UserCommon> getUserIdList();
	
	//区别平台
	public int getUserCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("user")UserCommon user,
			@Param("userStatus")Integer userStatus,@Param("startTime")String startTime,
			@Param("endTime")String endTime);
	public List<UserCommon> getUserListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("user")UserCommon user,
			@Param("userStatus")Integer userStatus,@Param("startTime")String startTime,
			@Param("endTime")String endTime,@Param("page")Integer page,@Param("limit")Integer limit);//获取用户列表
    public List<UserCommon> getVisitList(@Param("introducer")Integer introducer,@Param("loginPlat")Integer loginPlat,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
    //代理相关
    public void setAgent(@Param("userId")Integer userId,@Param("isAgent")Integer isAgent);
    public List<UserCommon> getAgentList(@Param("user")UserCommon user,@Param("page")Integer page,@Param("limit")Integer limit);//所有代理
    public int getAgentCount(@Param("user")UserCommon user);
    public List<UserCommon> getAgentListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
    		@Param("user")UserCommon user,@Param("page")Integer page,@Param("limit")Integer limit);
    public int getAgentCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
    		@Param("user")UserCommon user);
    //某个用户邀请的
    public int getVisitCountByUserId(@Param("loginPlat")Integer loginPlat,@Param("introducer")Integer introducer);
    public List<UserCommon> getVisitListByUserId(@Param("loginPlat")Integer loginPlat,@Param("introducer")Integer introducer,
    		@Param("page")Integer page,@Param("limit")Integer limit);
    
    public UserCommon nearlyLogin(@Param("userId")Integer userId);
    
    //判断该企业用户是否存在
   // public UserCommon getInfoByqyUserId(@Param("appId")Integer appId,@Param("qyUserId")String qyUserId);
    public UserCommon getInfoByqyOpenId(@Param("appId")Integer appId,@Param("qyOpenId")String qyOpenId);
    
    /**唯爱家登陆注册接口**/
    public int checkAccount(@Param("account")String account,@Param("phone")String phone,@Param("appId")Integer appId);
    public UserCommon getUserByAccount(@Param("account")String account,@Param("password")String password,
    		@Param("appId")Integer appId);
    public UserCommon findPwd(@Param("phone")String phone,@Param("loginPlat")Integer loginPlat);

    
    List<UserCommon> getUserCommonList(@Param("uCommon")UserCommon uCommon);
    
    public UserCommon getUserByPhone(@Param("phone")String phone);
    
    public List<UserCommon> getUserByKeyWord(@Param("keyword")String keyword,
    		@Param("loginPlatList")List<String> loginPlatList);
    public List<UserCommon> getUserRankList(@Param("user") UserCommon user,@Param("page")Integer page,@Param("limit")Integer limit);
    
    //获取某个人的排名
    public UserCommon getRankByUserId(@Param("userId")Integer userId,@Param("loginPlat")Integer loginPlat);
    
    //查看手机号码是否被占用
    public UserCommon getPhoneIsAlready(@Param("phone")String phone,@Param("userId")Integer userId,
    		@Param("loginPlat")Integer loginPlat);
    
    //第三方 QQ 微信登陆
    public UserCommon getUserInfoByQQOrWXOpenId(@Param("qqOpenId")String qqOpenId,
    		@Param("wxOpenId")String wxOpenId,@Param("loginPlat")Integer loginPlat);
}
