package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserConcern;

public interface UserConcernService {
	public List<UserConcern> findSHUserConcernedUsers(@Param("userId")Integer userId,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize, @Param("type")Integer type);
	public void addNewUserConcern(UserConcern uc);
	public UserConcern ConfirmIConcernedHim(@Param("userId")Integer userId, @Param("concernedUserId")Integer concernedUserId);
	public void updateConcernUser(UserConcern concerned);
}
