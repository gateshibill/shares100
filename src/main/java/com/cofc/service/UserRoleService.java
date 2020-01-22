package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserRole;

public interface UserRoleService {

   public UserRole getUserRoleById(@Param("userId")Integer userId);

   public void addUserRole(UserRole role);

   public void deleteUserRole(UserRole role);

   public void updateUserRole(UserRole role1);

}
