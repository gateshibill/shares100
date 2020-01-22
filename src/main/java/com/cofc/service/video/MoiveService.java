package com.cofc.service.video;


import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.video.Moive;

public interface MoiveService {
	public void addMoive(Moive Moive);
	public void updateMoive(Moive Moive);
	public void delMoive(@Param("id")Integer id);
}
