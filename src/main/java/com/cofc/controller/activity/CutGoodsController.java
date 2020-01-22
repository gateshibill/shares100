package com.cofc.controller.activity;

import java.math.BigDecimal;
//大转盘抽奖
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.activity.CutGoods;
import com.cofc.pojo.activity.CutOrder;
import com.cofc.pojo.activity.CutRecord;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.activity.CutGoodsService;
import com.cofc.service.activity.CutOrderService;
import com.cofc.service.activity.CutRecordService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
//活动
@Controller
@RequestMapping("/wx/activity")
public class CutGoodsController extends BaseUtil {
	public static Logger log = Logger.getLogger("CutGoodsController");

	@Resource
	private CutGoodsService cutGoodsService;
	@Resource
	private CutOrderService cutOrderService;
	@Resource
	private CutRecordService cutRecordService;
	@Resource
	GoodsCommonService goodsCommonService;
	
	@RequestMapping("/cutPrice")
	public void cutPrice(HttpServletResponse response, Integer appId, Integer orderId, Integer userId) {
		List<CutRecord> list = cutRecordService.getCutRecordList(appId, orderId);
		if (list != null && list.size() > 0) {
			output(response, JsonUtil.buildFalseJson("0", "一个人只能砍一次"));
			return;
		}

		CutOrder cutOrder = cutOrderService.getCutOrder(appId, orderId);
		if(cutOrder.getStatus()>0)
		{
			output(response, JsonUtil.buildFalseJson("0", "已经砍到底了！"));
		}
		double originalPrice = cutOrder.getOriginalPrice()-cutOrder.getCutPrice();
		double cutPrice;// 砍的价
		int times = cutOrder.getTimes();
		int count = cutOrder.getCount();

		if (times - count == 1) {// 最后一次
			cutPrice = originalPrice;
			cutOrder.setStatus(1);
		} else {
			cutPrice = (originalPrice / (times - count) + Math.random() * originalPrice / times);
		}
		BigDecimal b = new BigDecimal(cutPrice);
		cutPrice = b.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();

		BigDecimal c = new BigDecimal(originalPrice);
		originalPrice = c.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();

		//cutOrder.setOriginalPrice(originalPrice);
		cutOrder.setCount(count + 1);
		cutOrderService.updateCutOrder(cutOrder);

		CutRecord cutRecord = new CutRecord();
		cutRecord.setOrderId(orderId);
		cutRecord.setCutPrice(cutPrice);
		cutRecord.setCreateTime(new Date());
		cutRecord.setCout(count + 1);
		cutRecordService.addCutRecord(cutRecord);

		output(response, JsonUtil.bulidObjectJson(cutOrder));
	}

	//砍价商品列表，供砍价商城调用；
	@RequestMapping("/getCutGoodsList")
	public void getCutGoodsList(HttpServletResponse response, Integer appId) {
		List<CutGoods> list = cutGoodsService.getCutGoodsList(appId);
		for(CutGoods cg:list)
		{
			GoodsCommon goodsCommon =goodsCommonService.getGoodsById(cg.getGoodsId());
			cg.setGoodsCommon(goodsCommon);
		}
		output(response, JsonUtil.buildCustomJson("1", "succuss", list));
	}
	
	//用户选一个砍价商品生成一个砍价订单，砍价单不是订单，订单是购买后才是订单
	@RequestMapping("/getCutOrderList")
	public void getCutOrderList(HttpServletResponse response, Integer appId,Integer goodsId) {
		//为0时查整个应用订单，
		if(null==goodsId){
			goodsId=0;
		}
		List<CutOrder> list = cutOrderService.getCutOrderList(appId,goodsId);
		output(response, JsonUtil.buildCustomJson("1", "succuss", list));
	}

	
	@RequestMapping("/getCutRecordList")
	public void getCutRecordList(HttpServletResponse response, Integer appId, Integer orderId) {
		List<CutRecord> list = cutRecordService.getCutRecordList(appId, orderId);
		output(response, JsonUtil.buildCustomJson("1", "succuss", list));
	}
	
	//获取砍价数据 -商品编辑
	@RequestMapping("/getCutByGoodsId")
	public ModelAndView getCutByGoodsId(HttpServletRequest request,Integer id, ModelAndView mv) {
		CutGoods cutGoods=cutGoodsService.getCutByGoodsId(id);
		if(cutGoods==null){
			CutGoods cutGoods_1=new CutGoods();
			cutGoods_1.setGoodsId(id);
			mv.addObject("cutGoods", cutGoods_1);
			mv.setViewName("/goodsManage/updateGoodsCutGoods");
			return mv;
		}else{
			mv.addObject("cutGoods", cutGoods);
			mv.setViewName("/goodsManage/updateGoodsCutGoods");
			return mv;
		}
	}
	
	//去砍价商品-后台管理
	@RequestMapping("/goCutGoodsList")
	public ModelAndView goCutGoodsList(HttpServletRequest request, ModelAndView modelView) {
		modelView.setViewName("goodsManage/cutGoodsList");
		return modelView;
	}
	
	//查询砍价商品信息-后台管理
	@RequestMapping(value="/getAllCutGoodsList")
	public void getAllCutGoodsList(HttpServletResponse response, Integer page, Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 12;
		}
		List<CutGoods> lists = cutGoodsService.getAllCutGoodsList((page - 1) * limit, limit);		
		//数量
		int count = cutGoodsService.findAllCutGoodsCount();
		output(response, JsonUtil.buildJsonByTotalCount(lists, count));

	}
	
	//删除砍价商品
	@RequestMapping(value="/deleteCutGoodsById")
	public void deleteCutGoodsById(HttpServletResponse response, Integer id) {
		CutGoods cutGoods=cutGoodsService.getCutByCutGoodsId(id);
		//删除砍价商品
		cutGoodsService.deleteCutGoodsById(id);
		//改变商品表相关字段状态
		GoodsCommon goodsCommon=new GoodsCommon();
		goodsCommon.setGoodsId(cutGoods.getGoodsId());
		goodsCommon.setIsCut(0);
		goodsCommon.setUpdateTime(new Date());
		goodsCommonService.updateGoodsInfo(goodsCommon);
		output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
	}
	
	//去编辑砍价商品页面
	@RequestMapping("/goUpdateCutGoodsById")
	public ModelAndView goUpdateCutGoodsById(HttpServletRequest request,Integer id, ModelAndView mv) {
		CutGoods cutGoods=cutGoodsService.getCutByCutGoodsId(id);
		mv.addObject("cutGoods", cutGoods);
		mv.setViewName("goodsManage/updateCutGoods");
		return mv;
	}
	
	//编辑砍价商品
	@RequestMapping(value="/updateCutGoodsById")
	public void updateCutGoodsById(HttpServletResponse response, CutGoods cutGoods) {
		cutGoods.getStatus();
		cutGoodsService.updateCutByCutGoods(cutGoods);
		//改变商品表相关字段状态
		GoodsCommon goodsCommon=new GoodsCommon();
		goodsCommon.setGoodsId(cutGoods.getGoodsId());
		goodsCommon.setIsCut(cutGoods.getStatus());
		goodsCommon.setUpdateTime(new Date());
		goodsCommonService.updateGoodsInfo(goodsCommon);
		output(response, JsonUtil.buildFalseJson("0", "编辑成功!"));
	}
	
	
}
