package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.service.vij.CollocationService;
import com.cofc.pojo.vij.Collocation;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/***
 * 选择风格
 * @author Administrator
 *
 */
@Controller
@RequestMapping("vij/collocation")
public class VijCollocationController extends BaseUtil{

	@Resource
	private CollocationService cService;
	public static Logger log = Logger.getLogger("VijCollocationController");
	
	/***
	 * 查看选择风格
	 * @param response
	 * @param page
	 * @param limit
	 * @param collocation
	 */
	@RequestMapping("/getCollList")
	public void getCollList(HttpServletResponse response,Integer page,Integer limit,Collocation collocation) {
		if (page ==null) {
			page =1;
		}
		if (limit == null) {
			limit =10;
		}
		List<Collocation> list = cService.getCollList(collocation, (page-1)*limit, limit);
		output(response, JsonUtil.buildJson(list));
	}
	
	/***
	 *单个风格详情 
	 * @param response
	 * @param id
	 */
	@RequestMapping("/getInfoByid")
	public void getInfoByid(HttpServletResponse response, Integer id) {
		List<Collocation> list = new ArrayList<>();
		Collocation collocation = cService.getInfoById(id);
		if (collocation!=null) {
			list.add(collocation);
		}
		output(response, JsonUtil.buildJson(list));
	}
	/***
	 * 如果选的风格不喜欢，就删除从新选择
	 */
	@RequestMapping("/deleteGetCollList")
	public void deleteGetCollList(HttpServletResponse response,Integer id) {
		cService.delColl(id);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
}
