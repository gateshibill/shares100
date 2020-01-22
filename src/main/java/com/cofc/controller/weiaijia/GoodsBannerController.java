package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.vij.GoodTypeBanner;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.vij.GoodTypeBannerService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/goodTypebanner")
public class GoodsBannerController extends BaseUtil{

	@Resource
	private GoodTypeBannerService bannerService;
	@Resource
	private ApplicationCommonService aCommonService;
	public static Logger log = Logger.getLogger("GoodTypeBannerController");
	
	/***
	 * 增加分类广告图
	 * @param response
	 * @param banner
	 */
	@RequestMapping("/addGoodTypeBanner")
	public void addGoodTypeBanner(HttpServletResponse response,HttpServletRequest request,GoodTypeBanner banner) {
		BackUser bUser = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> list = new ArrayList<>();
		if (bUser.getLoginPlat() ==null || bUser.getLoginPlat().equals("")) {
			list = aCommonService.findApplicationCommon(null);
		}else {
			String[] str = bUser.getLoginPlat().split(",");
			List<String> lists = Arrays.asList(str);
			list =aCommonService.getApplicationByLoginPlat(lists);
			banner.setCreateTime(new Date());
			bannerService.addGoodTypeBanner(banner);
			output(response, JsonUtil.buildJson(list));
		}
	}
	/***
	 * 搜索广告图
	 * @param response
	 * @param banner
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/queryGoodTypeBanner")
	public void queryGoodTypeBanner(HttpServletResponse response,
			GoodTypeBanner banner,@Param("page")Integer page,@Param("limit")Integer limit) {
		if (page !=null) {
			page =1;
		}
		if (limit !=null) {
			limit =20;
		}
		int count = bannerService.getGoodTypeBannerCount(banner);
		List<GoodTypeBanner> list = bannerService.queryGoodTypeBanner(banner, (page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	
	/***
	 * 修改商品图片
	 * @param response
	 * @param id
	 * @param banner
	 */
	@RequestMapping("/upGoodTypeBanner")
	public void upGoodTypeBanner(HttpServletResponse response,Integer id, GoodTypeBanner banner) {
		
		banner.setCreateTime(new Date());
		bannerService.upGoodTypeBanner(banner);
		output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	}
	/***
	 * 删除功能
	 * @param response
	 * @param id
	 */
	@RequestMapping("/delGoodTypeBanner")
	public void delGoodTypeBanner(HttpServletResponse response,@Param("id")Integer id) {
		bannerService.delGoodTypeBanner(id);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
	
	/***
	 * 获取详情页面
	 * @param response
	 * @param id
	 */
	@RequestMapping("/getinfoBanner")
	public void getinfoBanner(HttpServletResponse response,@Param("id")Integer id) {
		List<GoodTypeBanner> list = new ArrayList<>();
		GoodTypeBanner banner = bannerService.getinfoBanner(id);
		if (banner != null) {
			list.add(banner);
		}
		output(response, JsonUtil.buildJson(list));
	}
}
