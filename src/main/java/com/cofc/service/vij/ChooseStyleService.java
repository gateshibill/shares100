package com.cofc.service.vij;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ChooseStyle;

public interface ChooseStyleService {
	public void addStyle(ChooseStyle style);
	public void updateStyle(ChooseStyle style);
	public void delStyle(@Param("styleId")Integer styleId);
	public ChooseStyle getStyleById(@Param("projectId")Integer projectId);
}
