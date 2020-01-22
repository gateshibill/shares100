package com.cofc.controller.weiaijia.admin;


import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.vij.Adviser;
import com.cofc.service.vij.FactoryService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/factory")
public class FactoryController extends BaseUtil{

	@Resource
	private FactoryService factoryService;		//工长
	public static Logger log = Logger.getLogger("FactoryController");
	/***
	 * 进入工长页面
	 * @param response
	 * @param mv
	 * @param adviser
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletResponse response,ModelAndView mv,Adviser adviser) {
		mv.addObject("adviser",adviser);
		mv.setViewName("weiaijia/factory/index");
		return mv;
	}
	
	/***
	 * 搜索页面
	 * @param response
	 * @param adviser
	 * @param id
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/queryCountFactory")
	public void queryCountFactory(HttpServletResponse response,Adviser adviser,
			Integer id,Integer page,Integer limit) {
		if (page !=null) {
			page =1;
		}
		if (limit != null) {
			limit =20;
		}
		int count =factoryService.getCountFactory(adviser);
		List<Adviser> list = factoryService.queryCountFactory(adviser, (page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	
	/***
	 * 进入添加页面
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addFactory")
	public ModelAndView addFactory(HttpServletResponse response,ModelAndView mv) {
		mv.setViewName("weiaijia/factory/add");
		return mv;
	}
	/***
	 * 执行添加
	 * @param response
	 * @param adviser
	 */
	@RequestMapping("/doaddFactory")
	public void doaddFactory(HttpServletResponse response,Adviser adviser) {
		if (adviser.getRealName() == null) {
			output(response, JsonUtil.buildFalseJson("1", "名字不能为空"));
		}else {
			adviser.setCreateTime(new Date());
			factoryService.addFactory(adviser);
			output(response, JsonUtil.buildFalseJson("0","添加成功"));
		}
	}
	
	/***
	 * 进入修改
	 * @param response
	 * @param adviser
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateFactory")
	public ModelAndView updateFactory(HttpServletResponse response,ModelAndView mv,
			@Param("id")Integer id) {
		Adviser adviser = factoryService.getSingleFactory(id);
		mv.addObject("adviser",adviser);
		mv.setViewName("weiaijia/factory/edit");
		return mv;
		
	}
	/***
	 * 修改工长
	 * @param response
	 * @param adviser
	 */
	@RequestMapping("/doupdateFactory")
	public void doupdateFactory(HttpServletResponse response,Adviser adviser) {
		adviser.setCreateTime(new Date());
		factoryService.updateFactory(adviser);
		output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	}
	/***
	 * 删除
	 * @param response
	 * @param adviser
	 * @param id
	 */
	@RequestMapping("/deleteFactory")
	public void deleteFactory(HttpServletResponse response,Integer id) {
		factoryService.delectFactory(id);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
}
