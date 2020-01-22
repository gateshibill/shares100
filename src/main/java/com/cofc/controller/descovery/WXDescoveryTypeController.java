package com.cofc.controller.descovery;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.DescoveryType;
import com.cofc.pojo.DescoveryloginPlatType;
import com.cofc.pojo.TagCommon;
import com.cofc.service.DescoveryTypeService;
import com.cofc.service.DescoveryloginPlatTypeService;
import com.cofc.service.TagCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/descovType")
public class WXDescoveryTypeController extends BaseUtil{
	@Resource
	private DescoveryTypeService descovTypeService;
	@Resource
	private TagCommonService tcService;
	@Resource
	private DescoveryloginPlatTypeService logPlatTypeService;
	
	@RequestMapping("/showTypes")
	public void showDescoveryTypes(HttpServletResponse response,Integer loginPlat,Integer groupId,Integer pageNo,Integer pageSize){
		List<DescoveryType> typeList = descovTypeService.getAllDescoveryType();
		for(DescoveryType type:typeList){
			List<TagCommon> childTag = tcService.showTagsByqualification(type.getTypeId(),null, (pageNo-1)*pageSize, pageSize);
			type.setComTagList(childTag);
		}
		output(response, JsonUtil.buildJson(typeList));
	}

	@RequestMapping("/showTypesList")
	public void showTypesList(HttpServletResponse response,Integer loginPlat,Integer groupId,Integer pageNo,Integer pageSize){
		List<DescoveryType> typeList = descovTypeService.getDescoveryTypeList();
		output(response, JsonUtil.buildJson(typeList));
	}
	
	//发布类型总表
	@RequestMapping("/descoveryTypes")
	public void showAllDescoveryTypes(HttpServletResponse response){
		List<DescoveryType> dtList = descovTypeService.findAllDescoveryType();
		output(response, JsonUtil.buildJson(dtList));
	}
	
	//发布应用类型列表(子表)
	@RequestMapping("/loginPlatTypeList")
	public void loginPlatTypeList(HttpServletResponse response,Integer loginPlat){
		List<DescoveryloginPlatType> typeList = logPlatTypeService.getloginPlatTypeList(loginPlat);
		if (typeList != null && !typeList.isEmpty()) {
			output(response, JsonUtil.buildJson(typeList));
		} else {
			List<DescoveryType> dtList = descovTypeService.findAllDescoveryType();
			output(response, JsonUtil.buildJson(dtList));
		}
	}
	
	//添加子应用发布类型
	@RequestMapping("/adddyType")
	public void adddyType(HttpServletResponse response,DescoveryloginPlatType type){
		DescoveryloginPlatType platType = logPlatTypeService.getTypePlatName(type.getLoginPlat(),type.getTypeName());
		if (platType != null) {
			output(response, JsonUtil.buildFalseJson("1", "该应用该类型已存在!"));
		} else {
			type.setCreateTime(new Date());
			type.setTypeStatus(1);
			type.setOrderNo(1);
			logPlatTypeService.addDescoveryloginPlatType(type);
			output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
		}
	}
	
	//修改类型
	@RequestMapping("updateDyType")
	public void updateDyType(HttpServletResponse response,DescoveryloginPlatType type){
		DescoveryloginPlatType dylpt = logPlatTypeService.getloginPlatById(type.getId());
		DescoveryloginPlatType platType = logPlatTypeService.getTypePlatName(dylpt.getLoginPlat(),type.getTypeName());
		if (platType != null && dylpt.getTypeName().equals(type.getTypeName())) {
			output(response, JsonUtil.buildFalseJson("1", "该应用该类型已存在!"));
		} else {
			type.setUpdateTime(new Date());
			logPlatTypeService.updateLoginPlatType(type);
			output(response, JsonUtil.buildFalseJson("0", "编辑成功!"));
		}
	}
}
