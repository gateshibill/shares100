package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationCode;

public interface ApplicationCodeService {

	public int addApplicationCommon(ApplicationCode code);

	public ApplicationCode getApplicationCodeById(@Param("codeId")Integer codeId);

	public ApplicationCode findLoginPlat(@Param("loginSubplat")Integer loginPlat, @Param("loginPlat")Integer parentId);

}
