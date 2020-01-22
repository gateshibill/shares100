package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cofc.pojo.ContractReason;
import com.cofc.pojo.SystemSetting;



public interface CommonService {
	public List<SystemSetting> getSystemSettings();
	public void addSystemSetting(SystemSetting systemSetting);
	public void updateSystemSetting(SystemSetting systemSetting);
	public List<Map> findAllTagMap();
	public List<Map> findAllGroupTypes();
}
