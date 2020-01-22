package com.cofc.service;

import java.util.List;

import com.cofc.pojo.UserDiary;

public interface UserDiaryService {
	public List<UserDiary> findMyDiary(UserDiary ud);
}
