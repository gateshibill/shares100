package com.cofc.controller.activity;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.activity.Brokerage;
import com.cofc.service.activity.BrokerageService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

//活动
@Controller
@RequestMapping("/wx/activity")
public class BkgeController extends BaseUtil {
	@Resource
	private BrokerageService BkgeService;
	public static Logger log = Logger.getLogger("BkgeController");

	//用户所有佣金
	@RequestMapping("/getBrokerageList")
	public void getBrokerageList(HttpServletResponse response, Integer appId, Integer userId) {
		List<Brokerage> list = BkgeService.getBrokerageList(appId, userId);
		output(response, JsonUtil.buildCustomJson("0", "success", list));
	}

	//用户佣金，status:0为未到帐佣金；1为到帐可用佣金；
	@RequestMapping("/getBrokerageListByStatus")
	public void getBrokerageListByStatus(HttpServletResponse response, Integer appId, Integer userId, Integer status) {
		List<Brokerage> list = BkgeService.getBrokerageListByStatus(appId, userId, status);
		output(response, JsonUtil.buildCustomJson("0", "success", list));
	}

}
