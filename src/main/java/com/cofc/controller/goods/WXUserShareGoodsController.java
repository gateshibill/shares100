package com.cofc.controller.goods;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserShareGoods;
import com.cofc.service.UserShareGoodsService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("wx/userShareGoods")
public class WXUserShareGoodsController extends BaseUtil {
	@Resource
	private UserShareGoodsService usgService;
	public static Logger log = Logger.getLogger("WXUserShareGoodsController");

	@RequestMapping("/sharedList")
	public void findAllSharedGoods(HttpServletResponse response, Integer userType,Integer userId, String goodsName, Integer pageNo,
			Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<UserShareGoods> usgList = usgService.findAllsharedGoods(userType, userId,goodsName, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(usgList));
	}

	@RequestMapping("/shareGoods")
	public void userShareGoods(HttpServletResponse response, UserShareGoods usGoods) {
		if (usGoods == null) {
			output(response, JsonUtil.buildFalseJson("1", "请输入要共享的商品信息!"));
		} else {
			usGoods.setSharedTime(new Date());
			if (usGoods.getNeedDeposit() == null) {
				usGoods.setNeedDeposit(1);
			}
			try {
				usgService.addNewShare(usGoods);
				output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
			} catch (Exception e) {
				log.info("用户" + usGoods.getSharedUserId() + "共享商品" + usGoods.getGoodsName() + "失败，失败原因" + e);
				output(response, JsonUtil.buildFalseJson("2", "添加失败!"));
			}
		}
	}
}
