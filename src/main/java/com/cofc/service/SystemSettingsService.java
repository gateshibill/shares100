package com.cofc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.cofc.pojo.SystemSetting;

/**
 * @author Administrator
 * 获取系统参数
 */
@Service
public class SystemSettingsService implements ApplicationContextAware{

	private Map<String, Object> map;
	
	@Autowired
	private CommonService commonService;
	
	public SystemSettingsService(){
/*
		init();
*/
	}
	
	public Object get(String name){
		if(map==null){
			init();
		}
		return map.get(name);
	}
	
	public void put(String key, Object value, String desc){
		if(map==null){
			init();
		}
		SystemSetting systemSetting = new SystemSetting();
		systemSetting.setKey(key);
		systemSetting.setValue(value.toString());
		if(value instanceof Integer){
			//System.out.println("Integer");
			systemSetting.setType("int");
		}else{
			systemSetting.setType("String");
		}
		systemSetting.setDesc(desc);
		if(map.containsKey(key)){
			commonService.updateSystemSetting(systemSetting);
		}else{
			commonService.addSystemSetting(systemSetting);
		}
		map.put(key, value);  //写入内存
	}
	
	public boolean containsKey(String key){
		if(map==null){
			init();
		}
		return map.containsKey(key);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		//不知道为什么，自动注入没有成功，只能手动注入了。（可能是myBatis扫描注入比较晚吧）
/*		commonService = (CommonService) applicationContext.getBean("commonService");
		//System.out.println(commonService);
		init();
		SystemSetting systemSetting = new SystemSetting();
		systemSetting.setKey("测试key");
		systemSetting.setValue("测试value");
		systemSetting.setDesc("描述");
		commonService.addSystemSetting(systemSetting);   */
	}
	
	private void init(){
		List<SystemSetting> list = commonService.getSystemSettings();
		map = new HashMap<String, Object>();
		for(SystemSetting set : list){
			String type = set.getType();
			if(type!=null&&"int".equalsIgnoreCase(type)){
				map.put(set.getKey(), Integer.valueOf(set.getValue()));  //数字
			}else{
				map.put(set.getKey(), set.getValue());  //字符串
			}
		}
	}
	
	private void test(){
		SystemSetting systemSetting = new SystemSetting();
		systemSetting.setKey("测试key");
		systemSetting.setValue("测试value");
		systemSetting.setDesc("描述");
		commonService.addSystemSetting(systemSetting);
	}
}
