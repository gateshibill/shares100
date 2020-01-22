package com.cofc.controller.group;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cofc.pojo.ApplicationSubtype;
import com.cofc.service.ApplicationSubtypeService;
import com.cofc.service.GroupTypesService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/group")
public class WXGroupTypeController extends BaseUtil{
	@Resource
	private GroupTypesService typeService;
	@Resource
	private ApplicationSubtypeService subtypeService;
	
	public static Logger log = Logger.getLogger("WXGroupType");
	
	@RequestMapping("/showTypes")
	public void showGroupTypes(HttpServletResponse response,Integer loginPlat,Integer isUsing,Integer typeType,Integer pageNo,Integer pageSize){
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=30;
		}
//		List<GroupTypes> tList = typeService.findGroupTypesByplat(loginPlat,isUsing,typeType,(pageNo-1)*pageSize, pageSize);
		List<ApplicationSubtype> typeList = subtypeService.findApplicationSubtypeByPlat(loginPlat,typeType,(pageNo-1)*pageSize, pageSize);
		output(response, JsonUtil.buildJson(typeList));
	}
	
	
	public static void main(String[] args) {
		String companyIdList = "[{id:1111,companyName:'阿里巴巴'},{id:2222,'companyName':'百度'},{id:3333,'companyName':'腾讯'}]";
		JSONArray jsonArray = JSON.parseArray(companyIdList);
		for (Object object : jsonArray) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = JSONObject.parseObject(object.toString(), Map.class);
			System.out.println("key   =" + "\t" + map.get("id").toString());
			System.out.println("value =" + "\t" + map.get("companyName").toString());
		}
	}
}
