package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.vij.Adviser;
import com.cofc.service.vij.AdviserService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/***
 *App顾问接口（提供为前端调用）
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/vij/adviser")
public class VijAdviserController extends BaseUtil{

	@Resource
	private AdviserService adviserService;
	public static Logger log = Logger.getLogger("VijAdviserController");
	
	/***
	 * 添加顾问
	 * @param response
	 * @param adviser
	 */
	@RequestMapping("/addAdviser")
	public void addAdviser(HttpServletResponse response, Adviser adviser,HttpServletRequest request) {
		adviser.setCreateTime(new Date());
		adviserService.addAdviser(adviser);
		output(response,JsonUtil.buildFalseJson("0", "添加成功"));
	}
	/***
	 * 查询所有的顾问
	 * @param request
	 * @param response
	 * @param adviser
	 */
	@RequestMapping("/queryAdviser")
	public void queryAdviser(HttpServletRequest request,HttpServletResponse response,Adviser adviser,
			Integer page, Integer limit) {
		if (page == null) {
			page =1;
		}
		if (limit == null) {
			limit =10;
		}
		//查询总数
		int count = adviserService.getAdviserCount(adviser);
		List<Adviser> list = adviserService.getAdviserList(adviser, (page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	
	/***
	 * 查询顾问资料
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getInfoById")
	public void getInfoById(HttpServletRequest request,HttpServletResponse response,Integer id) {
		List<Adviser> list = new ArrayList<Adviser>();
		//创建对象
		Adviser adviser = adviserService.getInfoById(id);
		if (adviser !=null) {
			list.add(adviser);
		}
		output(response, JsonUtil.buildJson(list));
	}
	
	/***
	 * 修改个人顾问资料
	 * @param request
	 * @param response
	 * @param adviser
	 */
	@RequestMapping("/updateAdviser")
	public void updateAdviser(HttpServletRequest request, HttpServletResponse response,Adviser adviser) {
		adviser.setCreateTime(new Date());
		adviserService.updateAdviser(adviser);
		output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	}
	
	/***
	 * 删除顾问
	 * @param request
	 * @param response
	 * @param adviser
	 * @param id
	 */
	@RequestMapping("/deleteAdviser")
	public void deleteAdviser(HttpServletRequest request,HttpServletResponse response,Adviser adviser,Integer id) {
			adviserService.delAdviser(id);
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		
	}
}
