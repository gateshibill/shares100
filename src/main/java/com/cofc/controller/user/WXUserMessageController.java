package com.cofc.controller.user;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserMessage;
import com.cofc.service.UserMessageService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/wx/message")
public class WXUserMessageController extends BaseUtil{
	@Resource
	private UserMessageService userMessageService;
	@RequestMapping("/getMessageList")
	public void getMessageList(HttpServletResponse response,UserMessage message,Integer pageNo,Integer pageSize){
		List<UserMessage> lists = userMessageService.getMessageList(message, (pageNo-1) * pageSize, pageSize);
		output(response,JsonUtil.buildJson(lists));
	}
}
