package com.cofc.controller.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.goods.GoodsMyself;
import com.cofc.pojo.goods.GoodsSpec;
import com.cofc.pojo.goods.GoodsSpecDesc;
import com.cofc.pojo.goods.GoodsSpecType;
import com.cofc.pojo.goods.Specification;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.GoodsTypeService;
import com.cofc.service.goods.GoodsMyselfService;
import com.cofc.service.goods.GoodsSpecService;
import com.cofc.service.goods.GoodsSpecTypeService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/goods")
public class GoodsSpecController extends BaseUtil {
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private GoodsTypeService gtypeService;
	@Resource
	private GoodsSpecTypeService goodsSpecTypeService;
	@Resource
	private GoodsSpecService goodsSpecService;
	@Resource
	private GoodsMyselfService goodsMyselfService;

	public static Logger log = Logger.getLogger("GoodsSpecController");

	// 返回显示产品规格
	@RequestMapping("/getGoodsSpecList")
	public void getGoodsSpecList(HttpServletResponse response, Integer appId, Integer goodsId) {
		GoodsSpecDesc goodsSpecDesc = new GoodsSpecDesc();

		List<GoodsSpecType> typeList = goodsSpecTypeService.getGoodsSpecType(appId, goodsId);
		List<GoodsSpec> list = goodsSpecService.getGoodsSpec(appId, goodsId);
		goodsSpecDesc.setGoodsSpecList(list);
		List<Specification> specList = new ArrayList<Specification>();
		goodsSpecDesc.setSpecList(specList);
		for (int i = 0; i < typeList.size(); i++) {
			GoodsSpecType gst = typeList.get(i);
			Specification spec = new Specification();
			spec.setName(gst.getName());
			for (GoodsSpec gs : list) {
				switch (i) {
				case 0:
					spec.getSet().add(gs.getType1());
					break;
				case 1:
					spec.getSet().add(gs.getType2());
					break;
				case 2:
					spec.getSet().add(gs.getType3());
					break;
				default:
					log.error("产品规则类别太多了，超过系统范围");
					break;
				}
				// 设置价值范围
				if (goodsSpecDesc.getMinPrice() > gs.getPrice() || goodsSpecDesc.getMinPrice() == 0) {
					goodsSpecDesc.setMinPrice(gs.getPrice());
				}
				if (goodsSpecDesc.getMaxPrice() < gs.getPrice()) {
					goodsSpecDesc.setMaxPrice(gs.getPrice());
				}
			}
			specList.add(spec);
		}

		output(response, JsonUtil.bulidObjectJson(goodsSpecDesc));
	}

	// 获得我的推荐商品
	@RequestMapping("/getGoodsMyselfList")
	public void getGoodsMyselfList(HttpServletResponse response, Integer appId, Integer userId) {
		log.info(String.format("%s|%s|%s", appId,0,"getGoodsMyselfList"));
		
		List<GoodsMyself> list = goodsMyselfService.getGoodsMyselfList(appId, userId);
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 增加我的推荐商品
	@RequestMapping("/addGoodsMyself")
	public void addGoodsMyself(HttpServletResponse response, Integer appId, Integer userId, Integer goodsId) {
		
		log.info(String.format("%s|%s|%s", appId,0,"addGoodsMyself"));
		
		GoodsMyself goodsMyself = goodsMyselfService.getGoodsMyselfByUserIdAndGoodId(appId, userId, goodsId);
		if (null != goodsMyself) {
			output(response, JsonUtil.buildFalseJson("2", "商品已经添加过了！"));
			return;
		}
		
		goodsMyself = new GoodsMyself();
		goodsMyself.setAppId(appId);
		goodsMyself.setUserId(userId);
		goodsMyself.setGoodsId(goodsId);
		goodsMyself.setStatus(1);
		goodsMyself.setCreateTime(new Date());
		goodsMyselfService.addGoodsMyself(goodsMyself);

		output(response, JsonUtil.bulidObjectJson(goodsMyself));
	}

	// 更新修改我的推荐商品
	@RequestMapping("/updateGoodsMyself")
	public void updateGoodsMyself(HttpServletResponse response, Integer appId, Integer id, Integer status) {
		GoodsMyself goodsMyself = goodsMyselfService.getGoodsMyself(appId, id);
		if (null == goodsMyself) {
			output(response, JsonUtil.buildFalseJson("2", "我的商品不存在！"));
			return;
		} else {
			goodsMyself.setStatus(status);
		}
		goodsMyselfService.updateGoodsMyself(goodsMyself);
		output(response, JsonUtil.bulidObjectJson(goodsMyself));
	}

	// 删除我的推荐商品
	@RequestMapping("/delGoodsMyself")
	public void delGoodsMyself(HttpServletResponse response, Integer appId, Integer id) {
		log.info(String.format("%s|%s|%s", appId,0,"delGoodsMyself"));
		GoodsMyself goodsMyself = goodsMyselfService.getGoodsMyself(appId, id);
		if (null == goodsMyself) {
			output(response, JsonUtil.buildFalseJson("2", "我的商品不存在！"));
			return;
		}
		goodsMyselfService.delGoodsMyself(appId, id);
		output(response, JsonUtil.buildFalseJson("0", "删除我的商品成功！"));
	}
}
