package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.vij.HomeType;
import com.cofc.service.vij.HomeTypeService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/vij/homeType")
public class VijHomeTypeController extends BaseUtil{
	@Resource
	private HomeTypeService hTypeService;
	public static Logger log = Logger.getLogger("VijHomeTypeController");
	
	/***
	 * 首页信息
	 * @param response
	 * @param hType
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/queryHomeTypeList")
	public void queryHomeTypeList(HttpServletResponse response,HomeType hType) {
		List<HomeType> list = hTypeService.queryHomeTypeList(hType,null, null);
		output(response, JsonUtil.buildJson(list));
 	}
	/***
	 * 详情
	 * @param response
	 * @param id
	 */
	@RequestMapping("/getInfoByid")
	public void getInfoByid(HttpServletResponse response,Integer id) {
		List<HomeType> list = new ArrayList<>();
		HomeType hType = hTypeService.getInfoByid(id);
		if (hType !=null) {
			list.add(hType);
		}
		output(response, JsonUtil.buildJson(list));
	}
}
