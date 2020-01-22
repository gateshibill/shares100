package com.cofc.controller.weiaijia.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.GoodsType;
import com.cofc.pojo.vij.Collocation;
import com.cofc.pojo.vij.GoodColl;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GoodsTypeService;
import com.cofc.service.vij.CollocationService;
import com.cofc.service.vij.GoodCollService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 后台--搭配管理
 * @author 46678
 *
 */
@Controller
@RequestMapping("/coll")
public class CollocationController extends BaseUtil{
	@Resource
	private ApplicationCommonService applicationService; //应用
	@Resource
	private CollocationService collocationService;
	@Resource
	private GoodCollService goodCollService; //搭配关联商品
	@Resource
	private GoodsTypeService typeService;//商品类型
	public static Logger log = Logger.getLogger("CollocationController");
	/**
	 * 搭配列表
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv,Collocation coll){
		mv.addObject("coll", coll);
		mv.setViewName("weiaijia/coll/index");
		return mv;
	}
	/**
	 * 获取数据
	 * @param response
	 * @param coll
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getCollList")
	public void getCollList(HttpServletResponse response,Collocation coll,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		int totalCount = collocationService.getCollCount(coll);
		List<Collocation> lists = collocationService.getCollList(coll, (page-1) * limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	/**
	 * 增加搭配
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addColl")
	public ModelAndView addColl(HttpServletRequest request,ModelAndView mv){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> commList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat() == "") {
			commList = applicationService.findApplicationCommon(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			commList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("commList", commList);
		mv.setViewName("weiaijia/coll/add");
		return mv;
	}
	/**
	 * 执行增加搭配
	 * @param response
	 * @param request
	 * @param coll
	 */
	@RequestMapping("/doAddColl")
	public void doAddColl(HttpServletResponse response,HttpServletRequest request,Collocation coll){
		coll.setCreateTime(new Date());
		collocationService.addColl(coll);
		output(response,JsonUtil.buildFalseJson("0", "添加搭配成功"));
	}
	/**
	 * 编辑
	 * @param request
	 * @param mv
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateColl")
	public ModelAndView updateColl(HttpServletRequest request,ModelAndView mv,Integer id){
		Collocation coll = collocationService.getInfoById(id);
		mv.addObject("coll", coll);
		mv.setViewName("weiaijia/coll/edit");
		return mv;
	} 
	/**
	 * 执行编辑
	 * @param response
	 * @param request
	 * @param coll
	 */
	@RequestMapping("/doUpdateColl")
	public void doUpdateColl(HttpServletResponse response,HttpServletRequest request,Collocation coll){
		collocationService.updateColl(coll);
		output(response,JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	/**
	 * 执行删除
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/delColl")
	public void delColl(HttpServletRequest request,HttpServletResponse response,Integer id){
		collocationService.delColl(id);
		output(response,JsonUtil.buildFalseJson("0", "删除成功"));
	}
	/**
	 * 去搭配关联商品页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goodColl")
	public ModelAndView goodColl(HttpServletRequest request,ModelAndView mv,Integer collId,Integer loginPlat){
		mv.addObject("collId", collId);
		mv.addObject("loginPlat", loginPlat);
		mv.setViewName("weiaijia/coll/goodindex");
		return mv;
	}
	/**
	 * 获取关联产品列表
	 * @param response
	 * @param gc
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getGoodColl")
	public void getGoodColl(HttpServletResponse response,GoodColl gc,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		int totalCount = goodCollService.getGoodCollCount(gc);
		List<GoodColl> lists = goodCollService.getGoodCollList(gc, (page-1) * limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	/**
	 * 搭配关联产品
	 * @param request
	 * @param mv
	 * @param collId
	 * @return
	 */
	@RequestMapping("/addGoodColl")
	public ModelAndView addGoodColl(HttpServletRequest request,ModelAndView mv,Integer collId){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		mv.addObject("collId", collId);
		List<GoodsType> typeList = new ArrayList<GoodsType>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			typeList = typeService.getNewTypeList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			typeList = typeService.getVijParentList(Integer.valueOf(loginPlatList.get(0)));
		}
		mv.addObject("typeList", typeList);
		mv.setViewName("weiaijia/coll/goodsList");
		return mv;
	}
	/**
	 * 绑定关联商品
	 * @param response
	 * @param coll
	 */
	@RequestMapping("/chooseGoodColl")
	public void chooseGoodColl(HttpServletResponse response,GoodColl coll){
		int count = goodCollService.getGoodCollCount(coll);
		if(count > 0){
			output(response,JsonUtil.buildFalseJson("1", "该商品已关联,请换一个"));
		}else{
			goodCollService.addGoodColl(coll);
			output(response,JsonUtil.buildFalseJson("0", "关联成功"));
		}
	}
	/**
	 * 编辑关联商品
	 * @param response
	 * @param coll
	 */
	@RequestMapping("/updateGoodColl")
	public void updateGoodColl(HttpServletResponse response,GoodColl coll){
		goodCollService.updateGoodColl(coll);
		output(response,JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	/**
	 * 删除搭配
	 * @param response
	 * @param goodCollId
	 */
	@RequestMapping("/delGoodColl")
	public void delGoodColl(HttpServletResponse response,Integer goodCollId){
		if(goodCollId == null){
			output(response, JsonUtil.buildFalseJson("1", "传递参数非法"));
		}else{
			goodCollService.delGoodColl(goodCollId);
			output(response,JsonUtil.buildFalseJson("0", "删除成功"));
		}
	}
}
