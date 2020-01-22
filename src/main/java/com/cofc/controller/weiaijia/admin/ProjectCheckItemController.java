package com.cofc.controller.weiaijia.admin;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.vij.ProjectCheckItem;
import com.cofc.service.vij.ProjectCheckItemService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/pCheckItem")
public class ProjectCheckItemController extends BaseUtil{

	@Resource
	private ProjectCheckItemService pCheckItemService;
	public static Logger log =Logger.getLogger("ProjectCheckItemController");
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletResponse response,ModelAndView mv,Integer checkId) {
		mv.addObject("checkId",checkId);
		mv.setViewName("weiaijia/checkitem/index");
		return mv;
	}
	/***
	 * 首页
	 * @param response
	 * @param pCheckItem
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getProjectCheckItemList")
	public void getProjectCheckItemList(HttpServletResponse response,ProjectCheckItem pCheckItem,
			Integer page,Integer limit) {
		if (page == null) {
			page =1;
		}
		if (limit == null) {
			limit =20;
		}
		int count = pCheckItemService.getProjectCheckItemCount(pCheckItem);
		List<ProjectCheckItem> list = pCheckItemService.getProjectCheckItemList(pCheckItem, (page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	/**
	 * 进入添加页面
	 * @param response
	 * @param mv
	 * @param pCheckItem
	 * @return
	 */
	@RequestMapping("/addProjectCheckItem")
	public ModelAndView addProjectCheckItem(HttpServletResponse response,ModelAndView mv,
			ProjectCheckItem pCheckItem,Integer checkId) {
		mv.addObject("checkId",checkId);
		mv.setViewName("weiaijia/checkitem/add");
		return mv;
	}
	/***
	 * 执行添加
	 * @param response
	 * @param itemId
	 */
	@RequestMapping("/doaddProjectCheckItem")
	public void doaddProjectCheckItem(HttpServletResponse response,ProjectCheckItem pCheckItem) {
		pCheckItemService.addProjectCheckItem(pCheckItem);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	
	/**
	 * 进入修改页面
	 * @param response
	 * @param mv
	 * @param itemId
	 * @return
	 */
	@RequestMapping("/updateProjectCheckItem")
	public ModelAndView updateProjectCheckItem(HttpServletResponse response,ModelAndView mv,Integer itemId) {
			ProjectCheckItem pCheckItem = pCheckItemService.getInfoById(itemId);
			mv.addObject("pCheckItem",pCheckItem);
			mv.setViewName("weiaijia/checkitem/edit");
		return mv;
	}
	/***
	 * 执行修改方法
	 * @param response
	 * @param pCheckItem
	 */
	@RequestMapping("/doupdateProjectCheckItem")
	public void doupdateProjectCheckItem(HttpServletResponse response,ProjectCheckItem pCheckItem) {
		pCheckItemService.updateProjectCheckItem(pCheckItem);
		output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	}
	/**
	 * 删除方法
	 * @param response
	 * @param itemId
	 */
	@RequestMapping("/deleteProjectCheckItem")
	public void deleteProjectCheckItem(HttpServletResponse response,Integer itemId) {
		pCheckItemService.deleteProjectCheckItem(itemId);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
}
