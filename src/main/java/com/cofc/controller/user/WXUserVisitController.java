package com.cofc.controller.user;

import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserVisit;
import com.cofc.service.UserVisitService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/wx/visit")
public class WXUserVisitController extends BaseUtil{
	@Resource
	private UserVisitService userVisitService;
	@RequestMapping("/addVisit")
	public void addVisit(HttpServletResponse response,UserVisit visit){
		if(visit.getUserId() == null){
			output(response,JsonUtil.buildFalseJson("1","未知用户"));
		}else{
			if(visit.getLoginPlat() == null){
				output(response,JsonUtil.buildFalseJson("1","未知应用"));
			}else{
				UserVisit v = userVisitService.getVisit(visit.getUserId(), visit.getLoginPlat());
				if(v == null){
					visit.setCreateTime(new Date());
					userVisitService.addVisit(visit);
					output(response,JsonUtil.buildFalseJson("0","操作成功"));
				}else{
					output(response,JsonUtil.buildFalseJson("1","已记录"));
				}
				
			}
		}
	}
	@RequestMapping("/getVisit")
	public void getVisit(HttpServletResponse response,Integer userId,Integer loginPlat){
		UserVisit visit = userVisitService.getVisit(userId, loginPlat);
		if(visit == null){
			output(response,JsonUtil.buildFalseJson("1","没有记录"));
		}else{
			output(response,JsonUtil.buildFalseJson("0","有记录"));
		}
	}
}
