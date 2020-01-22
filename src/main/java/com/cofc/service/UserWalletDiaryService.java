package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserWalletDiary;

public interface UserWalletDiaryService {

	public void addNewDiary(UserWalletDiary wdiaryP);

	public List<UserWalletDiary> findMyWalletDiary(@Param("userId")Integer userId, @Param("pageNo")Integer pageNo, @Param("pageSize")Integer pageSize);
}
