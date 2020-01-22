package com.cofc.controller.weiaijia;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
/**
 * 用户相关接口
 * @author 46678
 *
 */
@Controller
@RequestMapping("/vij/user")
public class VijUserController extends BaseUtil{
	@Resource
	private UserCommonService userCommonService;
	public static Logger log = Logger.getLogger("VijUserController");
	/**
	 * 编辑用户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/edituserinfo")
	public void edituserinfo(HttpServletRequest request,HttpServletResponse response){
		
	}
}
