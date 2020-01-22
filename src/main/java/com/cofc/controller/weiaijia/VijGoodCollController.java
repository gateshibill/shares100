package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.vij.Collocation;
import com.cofc.pojo.vij.GoodColl;
import com.cofc.service.vij.CollocationService;
import com.cofc.service.vij.GoodCollService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 主流推荐关联产品
 * 2018-12-15
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/vij/goodcoll")
public class VijGoodCollController extends BaseUtil{
	@Resource
	private CollocationService collService; //搭配
	@Resource
	private GoodCollService gCollService;
	public static Logger log = Logger.getLogger("VijGoodCollController");
	@RequestMapping("/getGoodColl")
	public void getGoodColl(HttpServletResponse response,Collocation collocation,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		List<Collocation> list =  collService.getCollList(collocation, (page-1)*limit, limit);
		if(list.size() > 0){
			for (Collocation c : list) {
				GoodColl gc = new GoodColl();
				gc.setCollId(c.getId());
				List<GoodColl> colls = gCollService.getGoodCollList(gc, null, null);
				c.setGoodCollList(colls);
			}
		}
		output(response,JsonUtil.buildJson(list));
	}
	/**
	 * 详情
	 * @param response
	 * @param collId
	 */
	@RequestMapping("/getCollDetail")
	public void getCollDetail(HttpServletResponse response,Integer collId){
		List<Collocation> lists = new ArrayList<Collocation>();
		Collocation collocation = collService.getInfoById(collId);
		GoodColl gc = new GoodColl();
		gc.setCollId(collId);
		List<GoodColl> colls = gCollService.getGoodCollList(gc, null, null);
		if(colls.size() > 0){
			collocation.setGoodCollList(colls);
		}
		lists.add(collocation);
		output(response,JsonUtil.buildJson(lists));
	}
}
