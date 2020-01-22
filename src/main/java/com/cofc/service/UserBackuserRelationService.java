package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserBackuserRelation;

public interface UserBackuserRelationService {

	List<UserBackuserRelation> getUserBackuserList(@Param("buserId")Integer buserId);

}
