package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.GroupUsers;
import com.cofc.pojo.UserCommon;

public interface GroupUsersService {
	public GroupUsers comfirmIsJoinThisGroup(@Param("userId")Integer userId,@Param("loginPlat")Integer loginPlat);
	public void UserJoinGroup(GroupUsers guser);
	public List<GroupUsers> groupUsersList(@Param("loginPlat")Integer loginPlat,@Param("isExamined")Integer isExamined,@Param("isRenzheng")Integer isRenzheng,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);
//	public List<UserGroups> findMyJoinedGroups(@Param("userId")Integer userId, @Param("rowsId")int i, @Param("pageSize")Integer pageSize);
	public int getGroupUserCount(Integer loginPlat);
	public List<UserCommon> backFindGroupUsersList(@Param("user")UserCommon user, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("rowsId")int i,
			@Param("pageSize")Integer pageSize);
	
	public void changeUserExaminedStatus(GroupUsers joiner);
	
	public void updateGiveVoucher(GroupUsers user);
	public void updateInfoMes(GroupUsers guser);
}
