package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserBrowseRecord;

public interface UserBrowseRecordService {

	UserBrowseRecord getUserBrowseUserById(@Param("userId")Integer userId,@Param("platformId") Integer parentId,@Param("loginPlt") Integer loginPlt,@Param("goodsId") Object object,
			@Param("descoveryId")Object object2);

	public void addUserBrowseRecord(UserBrowseRecord record);

	public void updateUserBrowseRecord(UserBrowseRecord record);

	public List<UserBrowseRecord> getUserBrowseRecordList(Map<String, Object> map);

	public int getUserBrowseRecordCount(Map<String, Object> map);

}
