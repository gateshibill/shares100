package com.cofc.service.aida;


import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.UserFormId;

public interface UserFormIdService {

	public void addUserFormId(UserFormId userFormId);

	public void delUserFormId(@Param("appId") Integer appId, @Param("userId") Integer userId);

	public void updateUserFormId(UserFormId userFormId);

	public UserFormId getUserFormId(@Param("appId") Integer appId,@Param("userId") Integer userId);
}