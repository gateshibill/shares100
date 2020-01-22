package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.DescoveryReader;

public interface DescoveryReaderService {
	public DescoveryReader comfirmIsRead(DescoveryReader dreader);

	public void addNewReader(DescoveryReader dreader);

	public void reflashReadTime(DescoveryReader dreader);

	public List<DescoveryReader> findReaderList(@Param("descoveryId")Integer descoveryId,@Param("rowsId")int rowsId, @Param("pageSize")Integer pageSize);
}
