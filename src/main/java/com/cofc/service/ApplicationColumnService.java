package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationColumn;

public interface ApplicationColumnService {

	List<ApplicationColumn> getApplicationTypeList();

	ApplicationColumn getApplicationColumnAppTypeById(@Param("typeId")Integer typeId);
}
