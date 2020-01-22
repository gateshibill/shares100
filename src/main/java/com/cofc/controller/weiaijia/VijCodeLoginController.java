package com.cofc.controller.weiaijia;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.controller.file.ErWeiCodeImageContoller;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserVisit;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserVisitService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MultiModelLoginUtil;
import com.cofc.util.WeiXinSessionUtils;

/**
 * 唯爱家小程序扫码登陆PC端
 * 
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/wx/vijcodelogin")
public class VijCodeLoginController extends BaseUtil {
	@Resource
	private UserCommonService userCommonService;
	@Resource
	private ApplicationCommonService applicationCommonService;// 小程序应用表
	@Resource
	private UserVisitService userVisitService;
	public static Logger log = Logger.getLogger("VijCodeLoginController");
	/**
	 * 生成小程序二维码
	 * 
	 * @param response
	 * @param request
	 * @param loginPlat
	 * @throws Exception
	 */
	@RequestMapping("/makeUserLoginCode")
	public void makeUserLoginCode(HttpServletResponse response, HttpServletRequest request, Integer loginPlat)
			throws Exception {
		if (loginPlat == null) {
			loginPlat = 709;
		}
		String codepath = "";
		String appId = "";
		String appSecret = "";
		ApplicationCommon appinfo = applicationCommonService.getApplicationById(loginPlat);
		if (appinfo.getAppId() != null && appinfo.getAppSecret() != null) {
			appId = appinfo.getAppId();
			appSecret = appinfo.getAppSecret();
		}
		log.debug("生成二维码appId--"+appId+"--appSecret--"+appSecret);
		String token = WeiXinSessionUtils.getAccessToken(appId, appSecret);
		log.info("生成二维码token--" + token);
		String page = "pages/pclogin/pclogin";
		String uuid = get16UUID();
		// 备注 scene最多不超过32个字符
		String scene = "uuid/" + uuid;
		log.info("uuid--"+uuid);
		Integer width = 430;
		codepath = ErWeiCodeImageContoller.vijLoginQR(page, width, scene, token, request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codeUrl", codepath);
		map.put("vijuuid", uuid);
		output(response, JsonUtil.MapToJson(map));
	}

	/**
	 * 获得16个长度的十六进制的UUID
	 * 
	 * @return UUID
	 */
	public static String get16UUID() {
		UUID id = UUID.randomUUID();
		String[] idd = id.toString().split("-");
		return idd[0] + idd[1] + idd[2];
	}

	/**
	 * 实现小程序扫码登陆pc ： 保存数据
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 * @param uuid
	 */
//	@RequestMapping("/vijLogin")
//	public void vijLogin(HttpServletRequest request, HttpServletResponse response, Integer userId, String uuid) {
//		log.debug(String.format("vijLogin():uuid:%s|userId:%s",uuid,userId));
//		if (userId != null) {
//			UserCommon user = userCommonService.getUserByUserId(userId);
//			if(user != null){
//				MultiModelLoginUtil.getInstance().putLoginUUID(uuid, user.getOpenId());
//				log.debug(String.format("vijLogin():openId:%s",user.getOpenId()));
//				output(response,JsonUtil.buildFalseJson("0", "登陆成功"));
//			}else{
//				output(response, JsonUtil.buildFalseJson("1", "非法用户"));
//			}
//		} else {
//			output(response, JsonUtil.buildFalseJson("1", "非法用户"));
//		}
//	}

	@RequestMapping("/vijLogin")
	public void vijLogin(HttpServletRequest request, HttpServletResponse response, Integer userId, String uuid,Integer loginPlat) {
		log.debug(String.format("vijLogin():uuid:%s|userId:%s",uuid,userId));
		if (userId != null) {
			UserCommon user = userCommonService.getUserByUserId(userId);
			if(user != null){
				if(loginPlat == null){
					loginPlat = 709;
				}
				UserVisit visit = new UserVisit();
				visit.setUuid(uuid);
				visit.setState(0);
				visit.setLoginPlat(loginPlat);
				visit.setCreateTime(new Date());
				visit.setUserId(userId);
				userVisitService.addVisit(visit);
				output(response,JsonUtil.buildFalseJson("0", "登陆成功"));
			}else{
				output(response, JsonUtil.buildFalseJson("1", "非法用户"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "非法用户"));
		}
	}
	
	/**
	 * 检测微信扫码登陆
	 * 
	 * @param request
	 * @param response
	 * @param uuid
	 */
//	@RequestMapping("/checkLogin")
//	public void checkLogin(HttpServletRequest request, HttpServletResponse response, String uuid) {
//		log.debug("checkLogin():uuid--"+uuid);
//        if(uuid.equals("")){
//        	log.debug("checkLogin():uuid为空");
//        	output(response,JsonUtil.buildFalseJson("1", "非法标识"));
//        }else{
//        	String openId = MultiModelLoginUtil.getInstance().getLoginUUID(uuid);
//        	log.debug("checkLogin(): openId="+openId);
//        	if(openId == null || openId == ""){
//            	log.debug("checkLogin():openId为空");
//        		output(response,JsonUtil.buildFalseJson("1", "非法标识"));
//        	}else{
//        		MultiModelLoginUtil.getInstance().removeLoginUUID(uuid);//移除
//        		UserCommon user = userCommonService.getUserByOpenId(openId);
//        		request.getSession().setAttribute("vijMallUserSession", user);
//				output(response,JsonUtil.buildFalseJson("0","登陆成功"));
//        	}
//        }
//	}
	
	@RequestMapping("/checkLogin")
	public void checkLogin(HttpServletRequest request, HttpServletResponse response, String uuid) {
		log.debug("checkLogin():uuid--"+uuid);
        if(uuid.equals("")){
        	log.debug("checkLogin():uuid为空");
        	output(response,JsonUtil.buildFalseJson("1", "非法标识"));
        }else{
        	UserVisit visit = userVisitService.getVisitByUuid(uuid, 0);
            if(visit == null){
            	log.debug("checkLogin():visit is null");
            	output(response,JsonUtil.buildFalseJson("1", "登陆失败"));
            }else{
            	UserCommon user = userCommonService.getUserByUserId(visit.getUserId());
            	if(user == null){
            		log.debug("checkLogin():user is null");
                	output(response,JsonUtil.buildFalseJson("1", "登陆失败"));	
            	}else{
            		//更新状态
            		userVisitService.updateState(1, visit.getId());
            		request.getSession().setAttribute("vijMallUserSession", user);
        			output(response,JsonUtil.buildFalseJson("0","登陆成功"));
            	}
            	
            }	
        }
	}
	/**
	 * 测试Map
	 * @param response
	 */
	@RequestMapping("/test")
	public void test(HttpServletResponse response){
		output(response,JsonUtil.MapToJsonString(MultiModelLoginUtil.getInstance().getMap()));
	}
}
