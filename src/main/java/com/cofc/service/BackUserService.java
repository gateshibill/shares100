package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.BackUser;

public interface BackUserService {

	BackUser getUserByUserName(@Param("userName")String userName);

	List<BackUser> showBackUserList(Map<String, Object> map);

	int showBackUserCount(Map<String, Object> map);

	BackUser getBackUserById(@Param("userId")Integer userId);

	void updateBackUser(BackUser user);

	void deleteUserId(@Param("userId")Integer userId);

	int addBackUser(BackUser user);

	void updatePassword(@Param("userId")Integer userId,@Param("password") String password);
	
}
