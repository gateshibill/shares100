package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserVisit;

public interface UserVisitService {
	public void addVisit(UserVisit visit);
	public void updateState(@Param("state")Integer state,@Param("id")Integer id);
	public UserVisit getVisit(@Param("userId")Integer userId,@Param("loginPlat")Integer loginPlat);
	public UserVisit getVisitByUuid(@Param("uuid")String uuid,@Param("state")Integer state);
}
