package com.cofc.controller.weiaijia.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.vij.Adviser;
import com.cofc.service.vij.AdviserService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 后台--顾问管理
 * @author 46678
 *
 */
@Controller
@RequestMapping("/adviser")
public class AdviserController extends BaseUtil{
	@Resource
	private AdviserService adviserService;
	public static Logger log = Logger.getLogger("AdviserController");
	/**
	 * 顾问列表
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv,Adviser adviser){
		mv.addObject("adviser", adviser);
		mv.setViewName("weiaijia/adviser/index");
		return mv;
	}
	/**
	 * 获取数据
	 * @param response
	 * @param adviser
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getAdviserList")
	public void getAdviserList(HttpServletResponse response,Adviser adviser,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		int totalCount = adviserService.getAdviserCount(adviser);
		List<Adviser> lists = adviserService.getAdviserList(adviser, (page-1) * limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	/**
	 * 添加
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addAdviser")
	public ModelAndView addAdviser(HttpServletRequest request,ModelAndView mv){
		mv.setViewName("weiaijia/adviser/add");
		return mv;
	}
	/**
	 * 添加顾问
	 * @param response
	 * @param request
	 * @param adviser
	 */
	@RequestMapping("/doAddAdviser")
	public void doAddAdviser(HttpServletResponse response,HttpServletRequest request,Adviser adviser){
		adviser.setCreateTime(new Date());
		adviserService.addAdviser(adviser);
		output(response,JsonUtil.buildFalseJson("0", "添加顾问成功"));
	}
	/**
	 * 编辑顾问
	 * @param request
	 * @param mv
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateAdviser")
	public ModelAndView updateAdviser(HttpServletRequest request,ModelAndView mv,Integer id){
		Adviser adviser = adviserService.getInfoById(id);
		mv.addObject("adviser", adviser);
		mv.setViewName("weiaijia/adviser/edit");
		return mv;
	}
	/**
	 * 执行编辑
	 * @param request
	 * @param response
	 * @param adviser
	 */
	@RequestMapping("/doUpdateAdviser")
	public void doUpdateAdviser(HttpServletRequest request,HttpServletResponse response,Adviser adviser){
		adviserService.updateAdviser(adviser);
		output(response,JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	/**
	 * 删除顾问
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/delAdviser")
	public void delAdviser(HttpServletRequest request,HttpServletResponse response,Integer id){
		adviserService.delAdviser(id);
		output(response,JsonUtil.buildFalseJson("0", "删除顾问成功"));
	}
}
