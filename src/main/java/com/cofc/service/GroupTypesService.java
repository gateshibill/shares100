package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.GroupTypes;
import com.cofc.pojo.TagCommon;

public interface GroupTypesService {
	public List<GroupTypes> findGroupTypesByplat(@Param("loginPlat")Integer loginPlat,@Param("isUsing")Integer isUsing,@Param("typeType")Integer typeType,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);

	public GroupTypes getGroupBygroupId(Integer groupType);

	public List<TagCommon> findTagCommonList(@Param("ids")List hasTagList);
}
