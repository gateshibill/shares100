package com.cofc.controller.weiaijia.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.GoodsType;
import com.cofc.pojo.vij.GoodTypeBanner;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GoodsTypeService;
import com.cofc.service.vij.GoodTypeBannerService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/goodTypeBanner")
public class GoodTypeBannerController extends BaseUtil{
	@Resource
    private GoodsTypeService typeService;
	@Resource 
	private GoodTypeBannerService bannerService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private GoodsTypeService goodTypesService;
	public static Logger log = Logger.getLogger("GoodTypeBannerController");
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletResponse response,GoodTypeBanner banner, ModelAndView mv) {
		mv.addObject("banner",banner);
		mv.setViewName("weiaijia/goodtypebanner/index");
		return mv;
		
	}
	/***
	 * 搜索
	 * @param response
	 * @param banner
	 * @param page
	 * @param limit
	 * @param id
	 */
	@RequestMapping("/queryGoodTypeBanner")
	public void queryGoodTypeBanner(HttpServletResponse response,GoodTypeBanner banner,
			Integer page,Integer limit) {
		if (page !=null) {
		page =1;	
		}
		if (limit!=null) {
			limit =20;
		}
		int count = bannerService.getGoodTypeBannerCount(banner);
		List<GoodTypeBanner> list =bannerService.queryGoodTypeBanner(banner,(page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
		/***
		 * 进入添加页面
		 * @param response
		 * @param mv
		 * @return
		 */
		@RequestMapping("/addGoodTypeBanner")
	public ModelAndView addGoodTypeBanner(HttpServletRequest request, ModelAndView mv) {
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
		mv.setViewName("weiaijia/goodtypebanner/add");
		return mv;
	}

		/***
		 *执行添加 
		 * @param response
		 * @param banner
		 */
		@RequestMapping("/doaddGoodTypeBanner")
	public void doaddGoodTypeBanner(HttpServletResponse response,GoodTypeBanner banner) {
		banner.setCreateTime(new Date());
		bannerService.addGoodTypeBanner(banner);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
		}
		
		/***
		 * 进入编辑页面
		 * @param response
		 * @param mv
		 * @param id
		 * @return
		 */
		@RequestMapping("/upGoodTypeBanner")
	public ModelAndView upGoodTypeBanner(HttpServletResponse response,HttpServletRequest request,ModelAndView mv,
			Integer id ) {
		GoodTypeBanner banner = bannerService.getinfoBanner(id);
		 List<GoodsType> lists =  new ArrayList<GoodsType>();
		if(banner.getAppId() != null){
		   lists = goodTypesService.getVijParentList(banner.getAppId());	
		}
		mv.addObject("lists", lists);
		mv.addObject("banner",banner);
		mv.setViewName("weiaijia/goodtypebanner/edit");
		return mv;
	}
		
		/***
		 * 执行修改页面
		 * @param response
		 * @param banner
		 */
		@RequestMapping("/doupGoodTypeBanner")
		public void doupGoodTypeBanner(HttpServletResponse response,GoodTypeBanner banner) {
			bannerService.upGoodTypeBanner(banner);
			output(response,  JsonUtil.buildFalseJson("0", "修改成功"));
		}
		
		/***
		 * 删除
		 * @param response
		 * @param id
		 */
		@RequestMapping("/delGoodTypeBanner")
		public void delGoodTypeBanner(HttpServletResponse response,Integer id) {
			bannerService.delGoodTypeBanner(id);
			output(response, JsonUtil.buildFalseJson("0","删除成功"));
			
		}
}
