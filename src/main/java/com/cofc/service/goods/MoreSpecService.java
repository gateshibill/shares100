package com.cofc.service.goods;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.goods.MoreSpec;

public interface MoreSpecService {
	public void addMoreSpec(MoreSpec spec);
	public void updateMoreSpec(MoreSpec spec);
	public void delMoreSpec(@Param("id")Integer id);
	public MoreSpec getInfoById(@Param("id")Integer id);
	public int getMoreSpecCount(@Param("spec")MoreSpec spec);
	public List<MoreSpec> getMoreSpecList(@Param("spec")MoreSpec spec);
}
