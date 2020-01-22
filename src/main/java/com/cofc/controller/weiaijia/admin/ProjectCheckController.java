package com.cofc.controller.weiaijia.admin;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.NVList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cofc.pojo.vij.ProjectCheck;
import com.cofc.service.vij.ProjectCheckService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/pjCheck")
public class ProjectCheckController extends BaseUtil{

	@Resource
	private ProjectCheckService pCheckService;
	public static Logger log = Logger.getLogger("ProjectCheckController");
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletResponse response,ModelAndView mv,Integer projectId) {
		mv.addObject("projectId",projectId);
		mv.setViewName("weiaijia/check/index");
		return mv;
	}
	/***
	 * 详情页面
	 * @param response
	 * @param pjCheck
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/queryProjectCheckList")
	public void queryProjectCheckList(HttpServletResponse response,
			ProjectCheck pjCheck,Integer page,Integer limit) {
		if (page ==null) {
			page=1;
		}
		if (limit == null) {
			limit =20;
		}
		int coumt =pCheckService.getProjectCheckCount(pjCheck);
		List<ProjectCheck> list = pCheckService.queryProjectCheckList(pjCheck, (page-1)*limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(list, coumt));
	}
	/***
	 * 进入添加页面
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addProjectCheck")
	public ModelAndView addProjectCheck(HttpServletResponse response,ModelAndView mv,Integer projectId) {
		mv.addObject("projectId", projectId);
		mv.setViewName("weiaijia/check/add");
		return mv;
		
	}
	/**
	 * 执行添加
	 * @param response
	 * @param pjCheck
	 */
	@RequestMapping("/doaddProjectCheck")
	public void doaddProjectCheck(HttpServletResponse response,ProjectCheck pjCheck) {
		pjCheck.setCheckTime(new Date());
		pCheckService.addProjectCheck(pjCheck);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	/***
	 * 进入修改页面
	 * @param response
	 * @param mv
	 * @param checkId
	 * @return
	 */
	@RequestMapping("/updateProjectCheck")
	public ModelAndView updateProjectCheck(HttpServletResponse response,ModelAndView mv,Integer checkId) {
		ProjectCheck pjCheck = pCheckService.getinfoByid(checkId);
		mv.addObject("pjCheck",pjCheck);
		mv.setViewName("weiaijia/check/edit");
		return mv;
	}
	/***
	 * 执行修改
	 * @param response
	 * @param pjCheck
	 */
	@RequestMapping("/doupdateProjectCheck")
	public void doupdateProjectCheck(HttpServletResponse response,ProjectCheck pjCheck) {
		pCheckService.updateProjectCheck(pjCheck);
		output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	}
	/***
	 * 删除功能
	 * @param response
	 * @param checkId
	 */
	@RequestMapping("/deleteProjectCheck")
	public void deleteProjectCheck(HttpServletResponse response,Integer checkId) {
		pCheckService.deleteProjectCheck(checkId);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
}

