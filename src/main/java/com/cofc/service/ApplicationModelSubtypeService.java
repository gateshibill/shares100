package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationModelSubtype;

public interface ApplicationModelSubtypeService {

	public ApplicationModelSubtype getModelSubtypeModelId(@Param("typeId")Integer groupType);

}
