package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserFootMark;

public interface UserFootMarkService {
	public List<UserFootMark> findMyfootMark(@Param("userId")Integer userId, @Param("rowsId")int rowsId, @Param("pageSize")Integer pageSize);
	public void addNewFootMark(UserFootMark ufm);
	public void reMarkMyfoot(UserFootMark ufm);
	public UserFootMark getMyMakedRecored(UserFootMark ufm);
}
