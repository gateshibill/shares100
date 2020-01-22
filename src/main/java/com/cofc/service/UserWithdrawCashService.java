package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserWithdrawCash;

public interface UserWithdrawCashService {
	public int userWantWithdrawCash(UserWithdrawCash urecharge);

	public void updateWithDrawInfo(UserWithdrawCash withdraw);

	public List<UserWithdrawCash> findWithRecordByCriteria(@Param("uwcash") UserWithdrawCash uwcash,
			@Param("loginPlat2") Integer loginPlat2, @Param("userKeyWords") String userKeyWords,
			@Param("applyStartTime") String applyStartTime, @Param("applyEndTime") String applyEndTime,
			@Param("dealStartTime") String dealStartTime, @Param("dealEndTime") String dealEndTime,
			@Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize);

	public int countAllWithdrawRecord(@Param("uwcash") UserWithdrawCash uwcash, @Param("loginPlat2") Integer loginPlat2,
			@Param("userKeyWords") String userKeyWords, @Param("applyStartTime") String applyStartTime,
			@Param("applyEndTime") String applyEndTime, @Param("dealStartTime") String dealStartTime,
			@Param("dealEndTime") String dealEndTime);

	public UserWithdrawCash getWithdrawRecordById(Integer withdrawId);

	public List<UserWithdrawCash> findWithdrawRecordById(@Param("idsList")List idsList);

	public List<UserWithdrawCash> confirmCurrUserHasApplyedToday(@Param("userId")Integer userId, @Param("loginPlat")Integer applicationId, @Param("startTime")String startTime,
			@Param("endTime")String endTime);

	public List<UserWithdrawCash> findMyWithdrawList(@Param("userId")Integer userId, @Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);
    
	//区分应用
	public List<UserWithdrawCash> findWithRecordByCriteriaByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("uwcash") UserWithdrawCash uwcash,
			@Param("loginPlat2") Integer loginPlat2, @Param("userKeyWords") String userKeyWords,
			@Param("applyStartTime") String applyStartTime, @Param("applyEndTime") String applyEndTime,
			@Param("dealStartTime") String dealStartTime, @Param("dealEndTime") String dealEndTime,
			@Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize);

	public int countAllWithdrawRecordByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("uwcash") UserWithdrawCash uwcash, @Param("loginPlat2") Integer loginPlat2,
			@Param("userKeyWords") String userKeyWords, @Param("applyStartTime") String applyStartTime,
			@Param("applyEndTime") String applyEndTime, @Param("dealStartTime") String dealStartTime,
			@Param("dealEndTime") String dealEndTime);

}
