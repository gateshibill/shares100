package com.cofc.service;

import java.util.List;
import java.util.Map;

import com.cofc.pojo.SystemSetting;

public interface CommonService {
	public List<SystemSetting> getSystemSettings();

	public void addSystemSetting(SystemSetting systemSetting);

	public void updateSystemSetting(SystemSetting systemSetting);

	public List<Map> findAllTagMap();

	public List<Map> findAllGroupTypes();

	public SystemSetting getValue(String key);
}
