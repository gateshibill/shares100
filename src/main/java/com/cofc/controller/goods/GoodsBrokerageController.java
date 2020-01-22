package com.cofc.controller.goods;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.UserCollection;
import com.cofc.pojo.goods.BrokerageGoods;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.UserCollectionService;
import com.cofc.service.goods.BrokerageGoodsService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 
 * @author chenxiangyou
 *
 */
@Controller
@RequestMapping("/wx/goods")
public class GoodsBrokerageController extends BaseUtil{
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private BrokerageGoodsService brokerageGoodsService;
	
	// 根据产品ID获得产品佣金
	@RequestMapping("/getGoodsBrokerage")
	public void getGoodsBrokerage(HttpServletResponse response, Integer appId,Integer goodsId) {
		BrokerageGoods bg = brokerageGoodsService.getBrokerageGoods(appId, goodsId);
		
		output(response, JsonUtil.bulidObjectJson(bg));
	}
}
