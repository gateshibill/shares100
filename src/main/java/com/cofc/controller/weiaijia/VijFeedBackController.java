package com.cofc.controller.weiaijia;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserCommon;
import com.cofc.pojo.vij.FeedBack;
import com.cofc.service.UserCommonService;
import com.cofc.service.vij.FeedBackService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/vij/feedback")
public class VijFeedBackController extends BaseUtil{
	@Resource
	private FeedBackService feedBackService;
	@Resource
	private UserCommonService userCommonService;
	public static Logger log = Logger.getLogger("VijFeedBackController");
	@RequestMapping("/addFeedback")
	public void addFeedback(HttpServletResponse response,FeedBack back){
		if(back.getUserId() == null){
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			UserCommon info = userCommonService.getUserByUserId(back.getUserId());
			if(info == null){
				output(response,JsonUtil.buildFalseJson("1", "用户不存在"));
			}else{
				if(info.getPhone() == null || info.getPhone().equals("")){
					output(response,JsonUtil.buildFalseJson("1", "请先绑定手机号码"));
				}else{
					 back.setUserPhone(info.getPhone());
					 if(info.getRealName() == null || info.getRealName().equals("")){
						 back.setUserName(info.getNickName());
					 }else{
						 back.setUserName(info.getRealName()); 
					 }
					 back.setCreateTime(new Date());
					 back.setIsDeal(0);
					 feedBackService.addFeedBack(back);
					 output(response,JsonUtil.buildFalseJson("0", "添加反馈成功"));
				}
			}
		}
	}
}
