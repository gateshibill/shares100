package com.cofc.service.video;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.video.VodBean;
import com.cofc.pojo.video.VodBeanWithBLOBs;

public interface VodBeanMapper {
	int deleteByPrimaryKey(Integer vodId);

	int insert(VodBeanWithBLOBs record);

	int insertSelective(VodBeanWithBLOBs record);

	VodBeanWithBLOBs selectByPrimaryKey(Integer vodId);

	List<VodBeanWithBLOBs> getVodListBycolumnId(@Param("typeId") Integer typeId, @Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	int updateByPrimaryKeySelective(VodBeanWithBLOBs record);

	int updateByPrimaryKeyWithBLOBs(VodBeanWithBLOBs record);

	int updateByPrimaryKey(VodBean record);
}