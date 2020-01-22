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
import com.cofc.service.GoodsCommonService;
import com.cofc.service.UserCollectionService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/goods")
public class GoodsCollectionController extends BaseUtil{
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private UserCollectionService ucService;
	public static Logger log = Logger.getLogger("GoodsCollectionController");
	
	// 收藏商品
	@RequestMapping("/collectionGoods")
	public void UserCollectionGoods(HttpServletResponse response, UserCollection uc) {
		GoodsCommon returnGoods = goodsService.getGoodsById(uc.getGoodsId());
		if (returnGoods != null) {
			try {
				uc.setCreateTime(new Date());
				uc.setIsCancel(0);
				ucService.addNewCollection(uc);
				output(response, JsonUtil.buildFalseJson("0", "收藏成功!"));
			} catch (Exception e) {
				output(response, JsonUtil.buildFalseJson("2", "收藏失败"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "收藏失败，商品不存在!"));
		}
	}

	// 展示我收藏的商品列表
	@RequestMapping("/myCollection")
	public void appShowMyGoodsCollection(HttpServletResponse response, UserCollection uc) {
		List<UserCollection> ucList = ucService.findMyGoodsCollection(uc);
		output(response, JsonUtil.buildJson(ucList));
	}
}
