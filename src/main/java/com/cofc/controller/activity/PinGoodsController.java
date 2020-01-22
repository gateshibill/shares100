package com.cofc.controller.activity;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.activity.CutGoods;
import com.cofc.pojo.activity.CutOrder;
import com.cofc.pojo.activity.CutRecord;
import com.cofc.pojo.activity.PinGoods;
import com.cofc.pojo.activity.PinOrder;
import com.cofc.pojo.activity.TurnTable;
import com.cofc.pojo.activity.TurnTableRecord;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.activity.PinGoodsService;
import com.cofc.service.activity.PinOrderService;
import com.cofc.service.activity.TurnTableRecordService;
import com.cofc.service.activity.TurnTableService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

//活动
@Controller
@RequestMapping("/wx/activity")
public class PinGoodsController extends BaseUtil {
	@Resource
	private PinGoodsService pinGoodsService;
	@Resource
	private PinOrderService pinOrderService;
	@Resource
	GoodsCommonService goodsCommonService;
	public static Logger log = Logger.getLogger("TurnTableController");

	@RequestMapping("/getPinGoodsList")
	public void getPinGoods(HttpServletResponse response, Integer appId, Integer goodsId, Integer userId) {
		List<PinGoods> list = pinGoodsService.getPinGoodsList(appId);
		for(PinGoods pg:list)
		{
			GoodsCommon goodsCommon =goodsCommonService.getGoodsById(pg.getGoodsId());
			pg.setGoodsCommon(goodsCommon);
		}

		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	@RequestMapping("/getPinOrderList")
	public void getPinOrderList(HttpServletResponse response, Integer appId,Integer goodsId) {
		List<PinOrder> list = pinOrderService.getPinOrderList(appId,goodsId);
		output(response, JsonUtil.buildCustomJson("1", "succuss", list));
	}
	
	//获取拼团数据 -商品编辑
	@RequestMapping("/getPinByGoodsId")
	public ModelAndView getPinByGoodsId(HttpServletRequest request,Integer id, ModelAndView mv) {
		PinGoods pinGoods=pinGoodsService.getPinByGoodsId(id);
		if(pinGoods==null){
			PinGoods pinGoods_1=new PinGoods();
			pinGoods_1.setGoodsId(id);
			mv.addObject("pinGoods", pinGoods_1);
			mv.setViewName("/goodsManage/updateGoodsPinGoods");
			return mv;
		}else{
			mv.addObject("pinGoods", pinGoods);
			mv.setViewName("/goodsManage/updateGoodsPinGoods");
			return mv;
		}
	}
	
	//去拼团商品-后台管理
	@RequestMapping("/goPinGoodsList")
	public ModelAndView goPinGoodsList(HttpServletRequest request, ModelAndView modelView) {
		modelView.setViewName("goodsManage/pinGoodsList");
		return modelView;
	}
	
	//查询拼团商品信息-后台管理
	@RequestMapping(value="/getAllPinGoodsList")
	public void getAllPinGoodsList(HttpServletResponse response, Integer page, Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 12;
		}
		List<PinGoods> lists = pinGoodsService.getAllPinGoodsList((page - 1) * limit, limit);		
		//数量
		int count = pinGoodsService.findAllPinGoodsCount();
		output(response, JsonUtil.buildJsonByTotalCount(lists, count));

	}
	//删除拼团商品
	@RequestMapping(value="/deletePinGoodsById")
	public void deleteCutGoodsById(HttpServletResponse response, Integer id) {
		PinGoods pinGoods=pinGoodsService.getPinByPinGoodsId(id);
		//删除拼团商品
		pinGoodsService.deletePinGoodsById(id);
		//改变商品表相关字段状态
		GoodsCommon goodsCommon=new GoodsCommon();
		goodsCommon.setGoodsId(pinGoods.getGoodsId());
		goodsCommon.setIsPin(0);
		goodsCommon.setUpdateTime(new Date());
		goodsCommonService.updateGoodsInfo(goodsCommon);
		output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
	}
	
	//去编辑拼团商品页面
	@RequestMapping("/goUpdatePinGoodsById")
	public ModelAndView goUpdatePinGoodsById(HttpServletRequest request,Integer id, ModelAndView mv) {
		PinGoods pinGoods=pinGoodsService.getPinByPinGoodsId(id);
		mv.addObject("pinGoods", pinGoods);
		mv.setViewName("goodsManage/updatePinGoods");
		return mv;
	}
	
	//编辑拼团商品
	@RequestMapping(value="/updatePinGoodsById")
	public void updatePinGoodsById(HttpServletResponse response, PinGoods pinGoods){
		pinGoodsService.updatePinByPinGoods(pinGoods);
		//改变商品表相关字段状态
		GoodsCommon goodsCommon=new GoodsCommon();
		goodsCommon.setGoodsId(pinGoods.getGoodsId());
		goodsCommon.setIsPin(pinGoods.getStatus());
		goodsCommon.setUpdateTime(new Date());
		goodsCommonService.updateGoodsInfo(goodsCommon);
		output(response, JsonUtil.buildFalseJson("0", "编辑成功!"));
	}
}
