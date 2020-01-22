package com.cofc.service.vij;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.SystemUpset;

public interface SystemUpsetService {
	public void addSystemUpset(SystemUpset systemUpset);
	public void updateSystemUpset(SystemUpset systemUpset);
	public SystemUpset getInfoById(@Param("systemUpset")SystemUpset systemUpset);
}
