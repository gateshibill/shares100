package com.cofc.controller.weiaijia;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.GoodsType;
import com.cofc.pojo.vij.GoodTypeBanner;
import com.cofc.service.GoodsTypeService;
import com.cofc.service.vij.GoodTypeBannerService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * APP新接口
 * 2018-12-11
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/vij/app/goodtype")
public class VijGoodTypeController extends BaseUtil{
	@Resource
	private GoodsTypeService goodsTypeService; //商品分类
	@Resource
	private GoodTypeBannerService typeBannerService;//一级分类对应的banner图
	public static Logger log = Logger.getLogger("VijGoodTypeController");
	/**
	 * 1：有banner图的，2：没有banner图
	 * @param response
	 * @param type
	 * @param appId
	 * 后续优化:可能效率会低
	 */
	@RequestMapping("/getGoodTypeList")
	public void getGoodTypeList(HttpServletResponse response,Integer type,Integer loginPlat){
		if(type == null){
			type = 2;
		}
		if(loginPlat == null){
			 output(response,JsonUtil.buildFalseJson("1", "传递参数非法"));
		 }else{
			 List<GoodsType> lists = goodsTypeService.getVijParentList(loginPlat);
			 if(lists.size() > 0){
				  for (GoodsType g : lists) {
					  List<GoodsType> list = goodsTypeService.getVijChildList(g.getTypeId(), null, null);
					  g.setChildList(list);
					  if(type == 1){
						 List<GoodTypeBanner> bannerlist = typeBannerService.getTypeBannerByTypeId(g.getTypeId());
						 g.setBannerList(bannerlist);
					  }
				  }	
				  output(response,JsonUtil.buildJson(lists));
			 }else{
				 output(response,JsonUtil.buildFalseJson("1", "暂无分类数据"));
			 }
		 }
	}
}
