package com.cofc.controller.activity;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.activity.DtbtGoods;
import com.cofc.pojo.activity.PinGoods;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.activity.DtbtGoodsService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

//活动
@Controller
@RequestMapping("/wx/activity")
public class DtbtGoodsController extends BaseUtil {
	@Resource
	private DtbtGoodsService DtbtGoodsService;
	@Resource
	GoodsCommonService goodsCommonService;
	public static Logger log = Logger.getLogger("DtbtGoodsController");

	@RequestMapping("/getDtbtGoodsList")
	public void getDtbtGoodsList(HttpServletResponse response, Integer appId, Integer userId) {
		List<GoodsCommon> list= goodsCommonService.getDtbtGoodsByUserId(userId);
	
		//List<DtbtGoods> list = DtbtGoodsService.getDtbtGoodsList(appId, userId);
		output(response, JsonUtil.buildCustomJson("0", "success", list));
	}
	
	//获取拼团数据 -商品编辑
	@RequestMapping("/getDtbtByGoodsId")
	public ModelAndView getDtbtByGoodsId(HttpServletRequest request,Integer id, ModelAndView mv) {
		DtbtGoods dtbtGoods=DtbtGoodsService.getDtbtByGoodsId(id);
		if(dtbtGoods==null){
			DtbtGoods dtbtGoods_1=new DtbtGoods();
			dtbtGoods_1.setGoodsId(id);
			mv.addObject("dtbtGoods", dtbtGoods_1);
			mv.setViewName("/goodsManage/updateGoodsDtbtGoods");
			return mv;
		}else{
			mv.addObject("dtbtGoods", dtbtGoods);
			mv.setViewName("/goodsManage/updateGoodsDtbtGoods");
			return mv;
		}
	}
	
	//去秒杀商品-后台管理
	@RequestMapping("/goDtbtGoodsList")
	public ModelAndView goDtbtGoodsList(HttpServletRequest request, ModelAndView modelView) {
		modelView.setViewName("goodsManage/dtbtGoodsList");
		return modelView;
	}
	
	//查询秒杀商品信息-后台管理
	@RequestMapping(value="/getAllDtbtGoodsList")
	public void getAllDtbtGoodsList(HttpServletResponse response, Integer page, Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 12;
		}
		List<DtbtGoods> lists = DtbtGoodsService.getAllDtbtGoodsList((page - 1) * limit, limit);		
		//数量
		int count = DtbtGoodsService.findAllDtbtGoodsCount();
		output(response, JsonUtil.buildJsonByTotalCount(lists, count));

	}
	
	//删除秒杀商品
	@RequestMapping(value="/deleteDtbtGoodsById")
	public void deleteDtbtGoodsById(HttpServletResponse response, Integer id) {
		DtbtGoods dtbtGoods=DtbtGoodsService.getDtbtByDtbtGoodsId(id);
		//删除秒杀商品
		DtbtGoodsService.deleteDtbtGoodsById(id);
		//改变商品表相关字段状态
		GoodsCommon goodsCommon=new GoodsCommon();
		goodsCommon.setGoodsId(dtbtGoods.getGoodsId());
		goodsCommon.setIsDtbt(0);
		goodsCommon.setUpdateTime(new Date());
		goodsCommonService.updateGoodsInfo(goodsCommon);
		output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
	}
	
	//去编辑秒杀商品页面
	@RequestMapping("/goUpdateDtbtGoodsById")
	public ModelAndView goUpdateDtbtGoodsById(HttpServletRequest request,Integer id, ModelAndView mv) {
		DtbtGoods dtbtGoods=DtbtGoodsService.getDtbtByDtbtGoodsId(id);
		mv.addObject("dtbtGoods", dtbtGoods);
		mv.setViewName("goodsManage/updateDtbtGoods");
		return mv;
	}
	
	//编辑秒杀商品
	@RequestMapping(value="/updateDtbtGoodsById")
	public void updatePinGoodsById(HttpServletResponse response, DtbtGoods dtbtGoods){
		DtbtGoodsService.updateDtbtByDtbtGoods(dtbtGoods);
		//改变商品表相关字段状态
		GoodsCommon goodsCommon=new GoodsCommon();
		goodsCommon.setGoodsId(dtbtGoods.getGoodsId());
		goodsCommon.setIsDtbt(dtbtGoods.getStatus());
		goodsCommon.setUpdateTime(new Date());
		goodsCommonService.updateGoodsInfo(goodsCommon);
		output(response, JsonUtil.buildFalseJson("0", "编辑成功!"));
	}

}
