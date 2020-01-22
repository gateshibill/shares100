package com.cofc.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.cofc.controller.recommend.MarkItem;
import com.cofc.util.BaseUtil;

@Service
public class ApplicationContextService implements ApplicationContextAware{

	private ApplicationContext applicationContext;
	
	@Resource
	private RecommendCommonService xxoo;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		test2();
	}
	
	public Map test(){
		Map<String, MarkItem> map = applicationContext.getBeansOfType(MarkItem.class);
		//System.out.println(map.size() + map.get("markItem").getName());
		return map;
	}

	
	public void test2(){
		//org.springframework.stereotype.Service
		Map<String, RecommendCommonService> map = applicationContext.getBeansOfType(RecommendCommonService.class);
		System.out.println("结果" + map.size());
	}
}
