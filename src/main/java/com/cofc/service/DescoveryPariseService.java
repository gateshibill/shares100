package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.DescoveryParise;

public interface DescoveryPariseService {

	public DescoveryParise comfirmUserParisedDescovery(@Param("userId")Integer userId, @Param("descoveryId")Integer descoveryId,@Param("isCancel")Integer isCancel);
	public void updatePariseStatus(DescoveryParise parise);
	public void userPariseDescovery(DescoveryParise parise);

}
