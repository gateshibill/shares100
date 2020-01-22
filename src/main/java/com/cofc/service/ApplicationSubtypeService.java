package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationSubtype;

public interface ApplicationSubtypeService {

	List<ApplicationSubtype> findApplicationSubtypeByPlat(@Param("loginPlat")Integer loginPlat, @Param("subType")Integer typeType, @Param("pageNo")int i, @Param("pageSize")Integer pageSize);

}
