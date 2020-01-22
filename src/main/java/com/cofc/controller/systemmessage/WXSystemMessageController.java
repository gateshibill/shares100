package com.cofc.controller.systemmessage;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.vij.SystemMessage;
import com.cofc.service.vij.SystemMessageService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 消息
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/wx/sysmes")
public class WXSystemMessageController extends BaseUtil{
	@Resource
	private SystemMessageService mesService;
	public static Logger log = Logger.getLogger("WXSystemMessageController");
	/**
	 * 获取通知列表
	 * @param response
	 * @param message
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getMesList")
	public void getMesList(HttpServletResponse response,SystemMessage message,Integer page,Integer limit){
		if(page == null){
			page =1;
		}
		if(limit == null){
			limit = 10;
		}
		List<SystemMessage> lists = mesService.getSysMesList(message, (page-1)*limit, limit);
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 删除通知
	 * @param response
	 * @param mesId
	 */
	@RequestMapping("/delMes")
	public void delMes(HttpServletResponse response,Integer mesId){
		mesService.delSysMes(mesId);
		output(response,JsonUtil.buildFalseJson("0", "删除成功"));
	}
}
