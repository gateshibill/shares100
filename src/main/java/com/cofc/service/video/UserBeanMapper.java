package com.cofc.service.video;

import com.cofc.pojo.video.UserBean;

public interface UserBeanMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(UserBean record);

    int insertSelective(UserBean record);

    UserBean selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(UserBean record);

    int updateByPrimaryKey(UserBean record);
}