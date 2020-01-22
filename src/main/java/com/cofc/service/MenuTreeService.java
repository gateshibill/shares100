package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.MenuTree;

public interface MenuTreeService {

	public List<MenuTree> getParentMenu(@Param("ids")List tmtList);
}
