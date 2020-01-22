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
import com.cofc.pojo.vij.GoodBrand;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.vij.GoodBrandService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 2018-12-11
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/goodbrand")
public class GoodBrandController extends BaseUtil{
	@Resource
	private GoodBrandService goodBrandService;
	@Resource
	private ApplicationCommonService applicationService;
	public static Logger log = Logger.getLogger("GoodBrandController");
	/**
	 * 品牌列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv,GoodBrand brand){
		mv.addObject("brand", brand);
		mv.setViewName("weiaijia/goodbrand/index");
		return mv;
	}
	/**
	 * 获取品牌列表数据
	 * @param response
	 * @param brand
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getBrandList")
	public void getBrandList(HttpServletResponse response,GoodBrand brand,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		brand.setIsRemove(0);
		int totalCount = goodBrandService.getGoodBrandCount(brand);
		List<GoodBrand> lists = goodBrandService.getGoodBrandList(brand, (page-1) * limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	/**
	 * 新增页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addGoodBrand")
	public ModelAndView addGoodBrand(HttpServletRequest request,ModelAndView mv){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> commList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			commList = applicationService.findApplicationCommon(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			commList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("commList", commList);
		mv.setViewName("weiaijia/goodbrand/add");
		return mv;
	}
	/**
	 * 执行添加
	 * @param response
	 * @param brand
	 */
	@RequestMapping("/doAddGoodBrand")
	public void doAddGoodBrand(HttpServletResponse response,GoodBrand brand){
		if(brand == null){
			output(response,JsonUtil.buildFalseJson("1", "没有添加数据"));
		}else{
			if(brand.getLoginPlat() == null){
				output(response,JsonUtil.buildFalseJson("1", "系统未能识别到该应用"));
			}else{
				if(brand.getCnBrandName() == null || brand.getCnBrandName().equals("")){
					output(response,JsonUtil.buildFalseJson("1", "请输入品牌名称"));
				}else{
					int count = goodBrandService.checkIsAlready(brand.getCnBrandName(), null);
					if(count > 0){
						output(response,JsonUtil.buildFalseJson("1", "品牌名称已存在，请换一个"));
					}else{
						brand.setCreateTime(new Date());
						brand.setIsRemove(0);
						goodBrandService.addGoodBrand(brand);
						output(response,JsonUtil.buildFalseJson("0", "添加品牌成功"));
					}
				}
			}
		}
	}
	/**
	 * 编辑页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/editGoodBrand")
	public ModelAndView editGoodBrand(HttpServletRequest request,ModelAndView mv,Integer brandId){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> commList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			commList = applicationService.findApplicationCommon(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			commList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		GoodBrand brand = goodBrandService.getInfoById(brandId);
		mv.addObject("brand", brand);
		mv.addObject("commList", commList);
		mv.setViewName("weiaijia/goodbrand/edit");
		return mv;
	}
	/**
	 * 执行编辑
	 * @param response
	 * @param brand
	 */
	@RequestMapping("/doEditBrand")
	public void doEditGoodBrand(HttpServletResponse response,GoodBrand brand){
		if(brand == null){
			output(response,JsonUtil.buildFalseJson("1", "你填写必填字段"));
		}else{
			if(brand.getLoginPlat() == null){
				output(response,JsonUtil.buildFalseJson("1", "系统未能识别到该应用"));
			}else{
				if(brand.getCnBrandName() == null || brand.getCnBrandName().equals("")){
					output(response,JsonUtil.buildFalseJson("1", "请输入品牌名称"));
				}else{
					int count = goodBrandService.checkIsAlready(brand.getCnBrandName(), brand.getBrandId());
					if(count > 0){
						output(response,JsonUtil.buildFalseJson("1", "品牌名称已存在，请换一个"));
					}else{
						goodBrandService.updateGoodBrand(brand);
						output(response,JsonUtil.buildFalseJson("0", "编辑成功"));
					}
				}
			}
		}	
	}
	/**
	 * 删除品牌
	 * @param request
	 * @param brandId
	 */
	@RequestMapping("/delGoodBrand")
	public void delGoodBrand(HttpServletResponse response,Integer brandId){
		if(brandId == null){
			output(response,JsonUtil.buildFalseJson("1", "传递参数非法"));
		}else{
			goodBrandService.delGoodBrand(brandId);
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		}
	}
}
