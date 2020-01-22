package com.cofc.controller.weiaijia.admin;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.vij.HomeType;
import com.cofc.service.vij.HomeTypeService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/homeType")
public class HomeTypeController extends BaseUtil{

	@Resource
	private HomeTypeService hTypeService;
	public static Logger log = Logger.getLogger("HomeTypeController;");
	/***
	 * 进入页面
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletResponse response,ModelAndView mv,HomeType hType) {
		mv.addObject("hType",hType);
		mv.setViewName("weiaijia/hometypes/index");
		return mv;
		
	}
	/***
	 * 页面
	 * @param response
	 * @param hType
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/queryHomeTypeList")
	public void queryHomeTypeList(HttpServletResponse response,HomeType hType,Integer page,Integer limit){
		if (page == null) {
			page =1;
		}
		if (limit == null) {
			limit =20;
		}
		int count = hTypeService.getHomeTypeCount(hType);
		List<HomeType> list = hTypeService.queryHomeTypeList(hType, (page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	
	/***
	 * 添加页面
	 * @param response
	 * @param hType
	 * @return
	 */
	@RequestMapping("/addHomeType")
	public ModelAndView addHomeType(HttpServletResponse response,ModelAndView mv) {
		mv.setViewName("weiaijia/hometypes/add");
		return mv;
		
	}
	/***
	 *执行添加
	 * @param response
	 * @param hType
	 */
	@RequestMapping("/doaddHomeType")
	public void doaddHomeType(HttpServletResponse response,HomeType hType) {
		hType.setCreateTime(new Date());
		hTypeService.addHomeType(hType);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	
	/**
	 * 进入编辑页面
	 * @param response
	 * @param hType
	 */
	@RequestMapping("/updateHomeType")
	public ModelAndView updateHomeType(HttpServletResponse response,ModelAndView mv,Integer id) {
		HomeType hType = hTypeService.getInfoByid(id);
		mv.addObject("hType",hType);
		mv.setViewName("weiaijia/hometypes/edit");
		return mv;
	}
	
	/***
	 * 执行编辑
	 * @param response
	 * @param hType
	 */
	@RequestMapping("/doupdateHomeType")
	public void doupdateHomeType(HttpServletResponse response,HttpServletRequest request,HomeType hType) {
		hTypeService.updateHomeType(hType);
		output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	}
	
	/***
	 * 删除功能
	 * @param response
	 * @param id
	 */
	@RequestMapping("/deleteHomeType")
	public void deleteHomeType(HttpServletResponse response,Integer id) {
		hTypeService.deleteHomeType(id);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
}
