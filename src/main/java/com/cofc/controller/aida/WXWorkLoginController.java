package com.cofc.controller.aida;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cofc.controller.file.ErWeiCodeImageContoller;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.aida.SalesPerson;
import com.cofc.pojo.aida.UserCard;
import com.cofc.pojo.aida.UserImpress;
import com.cofc.pojo.aida.WorkWeixin;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.aida.SalesPersonService;
import com.cofc.service.aida.UserCardService;
import com.cofc.service.aida.UserImpressService;
import com.cofc.service.aida.WorkWeixinService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.WeiXinSessionUtils;
import com.cofc.util.WorkWeixinUtils;

@Controller
@RequestMapping("/aida/login")
public class WXWorkLoginController extends BaseUtil {
	@Resource
	private WorkWeixinService workWeixinService;
	@Resource
	private UserCommonService userCommonService;
	@Resource
	private UserCardService userCardService;
	@Resource
	private SalesPersonService salesPersonService;// 插入销售表
	@Resource
	private ApplicationCommonService applicationCommonService;// 小程序应用表
	@Resource
	private UserImpressService impressService;
	@Resource
	private UserCardService cardService;
	public static Logger log = Logger.getLogger("WXWorkLoginController");
	public static String DEFAULT_WORK_WEIXIN_ID = "ww6c19a54e88180452";// 默认的启用id
	public static String DEFAULT_APP_SECRET = "vfdKANC_bFbJnlreTzcWcsewzuE9NWRgcb6f-4xamKY";// 默认飞看天眼的密钥

	// /**
	// * 授权
	// *
	// * @throws IOException
	// */
	// @RequestMapping("/authorize")
	// public void authorize(HttpServletResponse response, HttpServletRequest
	// request, Integer workId, String state)
	// throws IOException {
	// //workId=3;//正式临时
	// String port = request.getServerPort() == 80 ? "" : ":" +
	// request.getServerPort();
	// String contextPath = request.getContextPath();// 项目名
	// String url = request.getScheme() + "://" + request.getServerName() + port
	// + contextPath;
	// WorkWeixin workWeixin = workWeixinService.getInfoByWorkId(workId);
	// String corpid = "ww6c19a54e88180452";
	// String agentid = "1000021";
	// if (workWeixin != null) {
	// corpid = workWeixin.getQyId();
	// agentid = workWeixin.getAgentId();
	// }
	// String scope = "snsapi_privateinfo";
	// String callbackUrl = url + "/aida/login/callback.do?workId=" + workId;
	// log.info("callbackUrl:" + callbackUrl);
	// try {
	// callbackUrl = URLEncoder.encode(callbackUrl, "utf-8");
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// }
	// String authUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
	// "appid=" + corpid + "&redirect_uri="
	// + callbackUrl + "&response_type=code&scope=" + scope + "&agentid=" +
	// agentid + "&state=" + state
	// + "#wechat_redirect";
	//
	// log.info("authUrl:" + authUrl);
	// response.sendRedirect(authUrl);
	// }
	//
	// /**
	// * 微信企业号授权回调方法
	// *
	// * @param response
	// * @param workId:应用id
	// * @param appId:对应小程序id
	// * @param code:
	// * @throws IOException
	// * @throws ServletException
	// */
	// @RequestMapping("/callback")
	// public ModelAndView callback(HttpServletRequest request,
	// HttpServletResponse response, Integer workId, String code,
	// String state, ModelAndView mv) {
	// log.info("workId===" + workId + "--appId=" + 701 + "--code=" + code);
	// WorkWeixin workWeixin = workWeixinService.getInfoByWorkId(workId);
	// String corpid = "ww6c19a54e88180452";
	// String corpsecret = "vfdKANC_bFbJnlreTzcWcsewzuE9NWRgcb6f-4xamKY";
	// // Integer feikanUserId = null;
	// if (workWeixin != null) {
	// corpid = workWeixin.getQyId();
	// corpsecret = workWeixin.getAppSecret();
	// }
	// try {
	// // 获取token
	// String accesstoken = WorkWeixinUtils.getAccessToken(corpid, corpsecret);
	// log.info("获取accesstoken=" + accesstoken);
	// String userInfo = WorkWeixinUtils.getUserInfo(accesstoken, code);
	// JSONObject userobj = JSONObject.parseObject(userInfo);
	// String user_ticket = userobj.getString("user_ticket");
	// log.info("获取user_ticket--" + user_ticket);
	// if (user_ticket == null || user_ticket.equals("")) { // 不是企业成员
	// // 该步骤应该不会存在,按照我们的设计模式不是企业成员应该不能进入
	// log.info("不是该企业的成员");
	// } else { // 已授权：可以获取用户详情
	// String userDetailInfo = WorkWeixinUtils.getUserDetail(accesstoken,
	// user_ticket);
	// log.info("授权用户获取的信息-----" + userDetailInfo);
	// // 用户信息转码：防止中文乱码
	// // userDetailInfo = new
	// // String(userDetailInfo.getBytes("ISO-8859-1"),"UTF-8");
	// // 重新编码后的数据
	// // log.info("重新编码后的数据-----" + userDetailInfo);
	// JSONObject udetailobj = JSONObject.parseObject(userDetailInfo);
	// if (udetailobj.getString("errmsg").equals("ok")) {// 获取用户信息成功
	// String userid = udetailobj.getString("userid");
	// String name = udetailobj.getString("name");
	// String mobile = udetailobj.getString("mobile");
	// Integer gender = udetailobj.getIntValue("gender");
	// String email = udetailobj.getString("email");
	// String avatar = udetailobj.getString("avatar");
	//
	// // String qr_code = udetailobj.getString("qr_code");
	// // //这个必须得私密授权
	// // 判断是否存在
	// String openId = "";
	// try {
	// openId = WorkWeixinUtils.GetUserIdChangeOpenId(userid, accesstoken);
	// log.info("openId--" + openId);
	// } catch (Exception e) {
	// log.error("获取openid失败,失败原因：" + e.getMessage());
	// }
	// // UserCommon uinfo =
	// // userCommonService.getInfoByqyUserId(workWeixin.getXcxAppId(),
	// // userid);
	// // 此处用qy_open_id 做查询条件才能区分一个企业号的多应用
	// UserCommon uinfo =
	// userCommonService.getInfoByqyOpenId(workWeixin.getXcxAppId(), openId);
	// UserCommon uu = new UserCommon();
	// uu.setQyUserId(userid);
	// uu.setNickName(name);
	// uu.setPhone(mobile);
	// uu.setHeadImage(avatar);
	// uu.setEmail(email);
	// uu.setUserSex(gender);
	// uu.setQyOpenId(openId);
	// // 获取openid
	// if (uinfo == null) {// 插入数据
	// uu.setLoginPlat(workWeixin.getXcxAppId());
	// uu.setCreateTime(new Date());
	// userCommonService.addNewUserCommon(uu);
	// // 注册到即时通讯,暂时不需要
	// // registToIM(uu);
	//
	// Integer feikanUserId = uu.getUserId();
	// String codeUrl = getCircleCode(feikanUserId, request); // 生成二维码
	//
	// // 更新二维码信息到用户表
	// UserCommon uuu = new UserCommon();
	// uuu.setUserCodeUrl(codeUrl);
	// uuu.setUserId(feikanUserId);
	// userCommonService.commonInfoUpdate(uuu);
	//
	// // 插入一条名片记录
	// UserCard card = new UserCard();
	// card.setAppId(workWeixin.getXcxAppId());
	// card.setEmail(email);
	// card.setRealName(name);
	// card.setGender(gender);
	// card.setHeadImage(avatar);
	// card.setPhone(mobile);
	// card.setUserId(feikanUserId);
	// card.setIsEffect(1);
	// card.setCodeUrl(codeUrl);
	// card.setCreateTime(new Date());
	// userCardService.addUserCard(card);
	//
	// // 插入销售
	// SalesPerson sale = new SalesPerson();
	// sale.setAppId(workWeixin.getXcxAppId());
	// sale.setUserName(name);
	// sale.setCreateTime(new Date());
	// // sale.setHeadImage(accesstoken);
	// sale.setUserId(feikanUserId);
	// sale.setRole(0);
	// // sale.setVisitedCount(0);
	// // sale.setOrderCount(0);
	// // sale.setCustomerCount(0);
	// // sale.setInteractCount(0);
	// // sale.setDealRatio(0);
	// // sale.setDepartmentId(0);// 默认销售
	// salesPersonService.addSalesPerson(sale);
	//
	// Cookie cookie = new Cookie("feikan_qiyehao_" + workId,
	// feikanUserId.toString());
	// cookie.setMaxAge(60 * 60 * 24 * 365);
	// cookie.setPath("/");
	// response.addCookie(cookie);
	// } else {// 该更新用户信息
	// try {
	// if (uinfo.getUserCodeUrl() == null || uinfo.getUserCodeUrl().equals(""))
	// {
	// String codeUrl = getCircleCode(uinfo.getUserId(), request); // 生成二维码
	// uu.setUserCodeUrl(codeUrl);
	// }
	// } catch (Exception e2) {
	// log.error("获取二维码：" + e2.getMessage());
	// // TODO: handle exception
	// }
	// uu.setUserId(uinfo.getUserId());
	// userCommonService.commonInfoUpdate(uu);
	// Cookie cookie = new Cookie("feikan_qiyehao_" + workId,
	// uinfo.getUserId().toString());
	// cookie.setMaxAge(60 * 60 * 24 * 365);
	// cookie.setPath("/");
	// response.addCookie(cookie);
	// }
	//
	// } else {// 获取用户信息失败
	// log.info("获取用户信息失败");
	// }
	// }
	// } catch (Exception e) {
	// log.error("微信授权登陆失败,失败原因：" + e.getMessage());
	// }
	// mv.setViewName("../../weixin/index");
	// return mv;
	// }

	/**
	 * 授权
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/authorize")
	public void authorize(HttpServletResponse response, HttpServletRequest request, Integer workId, Integer appId,
			String state) throws IOException {
		
		log.info(String.format("%s|%s|%s", appId,0,"authorize"));
		
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();// 项目名
		String url = request.getScheme() + "://" + request.getServerName() + port + contextPath;
		WorkWeixin workWeixin = workWeixinService.getInfoByWorkId(workId, appId);
		String corpid = "ww6c19a54e88180452";
		String agentid = "1000021";
		if (workWeixin != null) {
			corpid = workWeixin.getQyId();
			agentid = workWeixin.getAgentId();
		}
		String scope = "snsapi_privateinfo";
		String appStr = appId.toString() + "_" + workId.toString();
		System.out.println("appStr--" + appStr);
		log.info("appStr===" + appStr);
		String callbackUrl = url + "/aida/login/callback.do?appStr=" + appStr;
		log.info("callbackUrl:" + callbackUrl);
		try {
			callbackUrl = URLEncoder.encode(callbackUrl, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String authUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + corpid + "&redirect_uri="
				+ callbackUrl + "&response_type=code&scope=" + scope + "&agentid=" + agentid + "&state=" + state
				+ "#wechat_redirect";

		log.info("authUrl:" + authUrl);
		response.sendRedirect(authUrl);
	}

	/**
	 * 微信企业号授权回调方法
	 * 
	 * @param response
	 * @param workId:应用id
	 * @param appId:对应小程序id
	 * @param code:
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping("/callback")
	public ModelAndView callback(HttpServletRequest request, HttpServletResponse response, String appStr, String code,
			String state, ModelAndView mv) {
		System.out.println("callback():appStr==" + appStr);
		log.info("callback():appStr==" + appStr);
		String[] appArr = appStr.split("_");
		Integer appId = Integer.valueOf(appArr[0]);
		Integer workId = Integer.valueOf(appArr[1]);
		log.info("callback():workId===" + workId + "--appId=" + 701 + "--code=" + code);
		WorkWeixin workWeixin = workWeixinService.getInfoByWorkId(workId, appId);
		String corpid = "ww6c19a54e88180452";
		String corpsecret = "vfdKANC_bFbJnlreTzcWcsewzuE9NWRgcb6f-4xamKY";
		// Integer feikanUserId = null;
		if (workWeixin != null) {
			corpid = workWeixin.getQyId();
			corpsecret = workWeixin.getAppSecret();
		}
		Integer feikanUserId = null;
		try {
			// 获取token
			String accesstoken = WorkWeixinUtils.getAccessToken(corpid, corpsecret);
			log.info("callback():获取accesstoken=" + accesstoken);
			String userInfo = WorkWeixinUtils.getUserInfo(accesstoken, code);
			JSONObject userobj = JSONObject.parseObject(userInfo);
			String user_ticket = userobj.getString("user_ticket");
			log.info("callback():获取user_ticket--" + user_ticket);
			if (user_ticket == null || user_ticket.equals("")) { // 不是企业成员
				// 该步骤应该不会存在,按照我们的设计模式不是企业成员应该不能进入
				log.info("callback():不是该企业的成员");
			} else { // 已授权：可以获取用户详情
				String olduserDetailInfo = WorkWeixinUtils.getUserDetail(accesstoken, user_ticket);
				log.info("callback():授权用户获取的信息-----" + olduserDetailInfo);
				// 用户信息转码：防止中文乱码
				String userDetailInfo = new String(olduserDetailInfo.getBytes("ISO-8859-1"),"UTF-8");
				// 重新编码后的数据
				log.info("callback():重新编码后的数据-----" + userDetailInfo);
				JSONObject udetailobj = JSONObject.parseObject(userDetailInfo);
				if (udetailobj.getString("errmsg").equals("ok")) {// 获取用户信息成功
					String userid = udetailobj.getString("userid");
					String name = udetailobj.getString("name");
					String mobile = udetailobj.getString("mobile");
					Integer gender = udetailobj.getIntValue("gender");
					String email = udetailobj.getString("email");
					String avatar = udetailobj.getString("avatar");

					// String qr_code = udetailobj.getString("qr_code");
					// //这个必须得私密授权
					// 判断是否存在
					String openId = "";
					try {
						openId = WorkWeixinUtils.GetUserIdChangeOpenId(userid, accesstoken);
						log.info("callback():openId--" + openId);
					} catch (Exception e) {
						log.error("callback():获取openid失败,失败原因：" + e.getMessage());
					}
					// UserCommon uinfo =
					// userCommonService.getInfoByqyUserId(workWeixin.getXcxAppId(),
					// userid);
					// 此处用qy_open_id 做查询条件才能区分一个企业号的多应用
					UserCommon uinfo = userCommonService.getInfoByqyOpenId(workWeixin.getXcxAppId(), openId);
					
					// 获取openid
					if (uinfo == null) {// 插入数据
						UserCommon uu = new UserCommon();
						uu.setQyUserId(userid);
						uu.setNickName(name);
						uu.setPhone(mobile);
						uu.setCompony(workWeixin.getWorkName());
						uu.setHeadImage(avatar);
						uu.setEmail(email);
						uu.setUserSex(gender);
						uu.setQyOpenId(openId);
						uu.setLoginPlat(workWeixin.getXcxAppId());
						uu.setCreateTime(new Date());
						userCommonService.addNewUserCommon(uu);
						// 注册到即时通讯,暂时不需要
						// registToIM(uu);

						feikanUserId = uu.getUserId();
						String codeUrl = getCircleCode(request,response,appId,feikanUserId); // 生成二维码

						// 更新二维码信息到用户表
						UserCommon uuu = new UserCommon();
						uuu.setUserCodeUrl(codeUrl);
						uuu.setUserId(feikanUserId);
						userCommonService.commonInfoUpdate(uuu);

						// 插入一条名片记录
						UserCard card = new UserCard();
						card.setAppId(workWeixin.getXcxAppId());
						card.setEmail(email);
						card.setRealName(name);
						card.setCompany(workWeixin.getWorkName());
						card.setGender(gender);
						card.setHeadImage(avatar);
						card.setPhone(mobile);
						card.setWechat(mobile);
						card.setUserId(feikanUserId);
						card.setIsEffect(1);
						card.setCodeUrl(codeUrl);
						card.setCreateTime(new Date());
						card.initIntroduce();
						userCardService.addUserCard(card);

						// 增加默认印象
						UserImpress impress1 = new UserImpress();
						impress1.setAppId(card.getAppId());
						impress1.setUserId(card.getUserId());
						impress1.setTagName("贴心服务");
						impress1.setIsEffect(1);
						impress1.setNumber(0);
						impressService.addImpress(impress1);

						// 增加默认印象
						UserImpress impress2 = new UserImpress();
						impress2.setAppId(card.getAppId());
						impress2.setUserId(card.getUserId());
						impress2.setTagName("阳光");
						impress2.setIsEffect(1);
						impress2.setNumber(0);
						impressService.addImpress(impress2);

						// 插入销售
						SalesPerson sale = new SalesPerson();
						sale.setAppId(workWeixin.getXcxAppId());
						sale.setUserName(name);
						sale.setCreateTime(new Date());
						// sale.setHeadImage(accesstoken);
						sale.setUserId(feikanUserId);
						sale.setRole(0);
						// sale.setVisitedCount(0);
						// sale.setOrderCount(0);
						// sale.setCustomerCount(0);
						// sale.setInteractCount(0);
						// sale.setDealRatio(0);
						// sale.setDepartmentId(0);//
						salesPersonService.addSalesPerson(sale);

						Cookie cookie = new Cookie("feikan_qiyehao_" + appId + "_" + workId, feikanUserId.toString());
						cookie.setMaxAge(60 * 5);
						cookie.setPath("/");
						response.addCookie(cookie);
					} else {// 该更新用户信息							
						try {
							SalesPerson salesPerson = salesPersonService.getSalesPersonByUserId(uinfo.getUserId());
							if(salesPerson == null){
								// 插入销售
								SalesPerson sale = new SalesPerson();
								sale.setAppId(workWeixin.getXcxAppId());
								sale.setUserName(uinfo.getNickName());
								sale.setCreateTime(new Date());
								sale.setUserId(uinfo.getUserId());
								sale.setRole(0);
								log.info("插入默认销售成功");
								salesPersonService.addSalesPerson(sale);
							}
						} catch (Exception e) {
							log.info("callback():插入销售错误");
						}
						feikanUserId = uinfo.getUserId();
						UserCard cardInfo = userCardService.getUserCard(feikanUserId, appId);
						if(cardInfo == null){
							// 插入一条名片记录
							UserCard card = new UserCard();
							card.setAppId(workWeixin.getXcxAppId());
							card.setEmail(email);
							card.setRealName(name);
							card.setCompany(workWeixin.getWorkName());
							card.setGender(gender);
							card.setHeadImage(avatar);
							card.setPhone(mobile);
							card.setWechat(mobile);
							card.setUserId(feikanUserId);
							card.setIsEffect(1);
							card.setCodeUrl(uinfo.getUserCodeUrl());
							card.setCreateTime(new Date());
							card.initIntroduce();
							userCardService.addUserCard(card);
						}
						log.info("feikanUserId----"+feikanUserId);
						log.info("callback():uinfo.getUserCodeUrl()= "+uinfo.getUserCodeUrl());
						if (uinfo.getUserCodeUrl() == null || uinfo.getUserCodeUrl().equals("")) {
							String codeUrl = getCircleCode(request,response,appId,uinfo.getUserId()); // 生成二维码
							UserCommon nn = new UserCommon();
							nn.setUserCodeUrl(codeUrl);
							nn.setUserId(uinfo.getUserId());
							userCommonService.commonInfoUpdate(nn);
							log.info("更新数据成功");
						}					
						
						Cookie cookie = new Cookie("feikan_qiyehao_" + appId + "_" + workId,
								uinfo.getUserId().toString());
						cookie.setMaxAge(60 * 5);
						cookie.setPath("/");
						response.addCookie(cookie);
					}

				} else {// 获取用户信息失败
					log.info("获取用户信息失败");
				}
			}
		} catch (Exception e) {
			log.error("微信授权登陆失败,失败原因：" + e.getMessage());
		}
		mv.setViewName("../../weixin/index");
		return mv;
	}

	@RequestMapping("/delCookies")
	public void delCookies(HttpServletRequest request, HttpServletResponse response,Integer appId,Integer workId) {
		Cookie cookie = new Cookie("feikan_qiyehao_"+appId+"_"+ workId, null);
		log.info("清除缓存--" + "feikan_qiyehao_" + workId);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}

	// private void registToIM(UserCommon comUser) throws XMPPException {
	// // 1.注册到即时通讯
	// if (IMUtils.getInstance().regist(comUser.getUserId().toString(),
	// comUser.getUserId().toString())) {
	// log.info(comUser.getUserId() + "注册IM成功！");
	//
	// // 2.把头像消息和用户名设置到IM
	// if (IMUtils.getInstance().updateUserInfo(comUser.getUserId().toString(),
	// comUser.getUserId().toString(),
	// comUser.getHeadImage(), comUser.getNickName())) {
	// log.info(comUser.getUserId() + "用户信息设置到IM成功！");
	// } else {
	// log.info(comUser.getUserId() + "用户信息设置到IM失败！");
	// }
	//
	// // 2.发送消息
	// if (IMUtils.getInstance().sendMessage("feikan", "feikan", "欢迎加入飞看",
	// comUser.getUserId().toString())) {
	// log.info(comUser.getUserId() + "系统IM发送成功");
	// } else {
	// log.info(comUser.getUserId() + "系统IM发送失败");
	// }
	//
	// } else {
	// log.info(comUser.getUserId() + "注册IM失败！");
	// }
	// }

	/**
	 * 获取签名
	 * 
	 * @param response
	 * @param request
	 * @param workId:企业号id
	 * @param type:
	 *            1 企业配置 2应用配置
	 */
	@RequestMapping("/getWxConfig")
	public void getWxConfig(HttpServletResponse response, HttpServletRequest request, Integer workId, Integer appId,
			String url, Integer type) {
		try {
			if (type == null) {
				type = 1;
			}
			WorkWeixin workWeixin = workWeixinService.getInfoByWorkId(workId, appId);
			String corpid = "ww6c19a54e88180452";
			String corpsecret = "vfdKANC_bFbJnlreTzcWcsewzuE9NWRgcb6f-4xamKY";
			if (workWeixin != null) {
				corpid = workWeixin.getQyId();
				corpsecret = workWeixin.getAppSecret();
			}
			String accesstoken = WorkWeixinUtils.getAccessToken(corpid, corpsecret);
			log.info("获取签名，access_token-" + accesstoken);
			String jsiApiTicket = "";
			if (type == 1) {
				jsiApiTicket = WorkWeixinUtils.getJSApiTicket(accesstoken);
			} else {
				jsiApiTicket = WorkWeixinUtils.getAgentJSApiTicket(accesstoken);
			}
			Date now = new Date();
			String noncestr = getNonceStr();
			String timestamp = String.valueOf(now.getTime());
			String signature = getSignature(noncestr, jsiApiTicket, timestamp, url);
			ResBody res = new ResBody();
			res.appId = corpid; // 企业号
			res.timestamp = timestamp;
			res.nonceStr = noncestr;
			res.signature = signature;
			res.agentid = workWeixin.getAgentId();// 企业号应用id
			log.info("signature--" + signature + "");
			output(response, JsonUtil.objectToJson(res));
		} catch (Exception e) {
			log.error("获取配置失败,失败原因--" + e.getMessage());
			output(response, JsonUtil.buildFalseJson("1", "获取配置失败"));
		}
	}

	/**
	 * 生成签名
	 * 
	 * @param noncestr
	 * @param jsapi_ticket
	 * @param timestamp
	 * @param url
	 * @return
	 */
	public static String getSignature(String noncestr, String jsapi_ticket, String timestamp, String url) {
		String signatureStr = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp
				+ "&url=" + url;
		String signture = DigestUtils.shaHex(signatureStr);
		return signture;
	}

	/**
	 * 获取生成签名的随机串
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/*
	 * appId: '', // 必填，企业号的唯一标识，此处填写企业号corpid timestamp: , // 必填，生成签名的时间戳
	 * nonceStr: '', // 必填，生成签名的随机串 signature: '',// 必填，签名，见附录1
	 */
	private static class ResBody {
		public String appId; // 企业号id
		public String timestamp;
		public String nonceStr;
		public String signature;
		public String agentid;// 必填，企业微信的应用id
	}

	/**
	 * 生成方形二维码
	 * 
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getShareCode(Integer userId, HttpServletRequest request) throws Exception {
		String codepath = "";
		UserCommon user = userCommonService.getUserByUserId(userId);
		if (user != null) {
			String appId = "wx93c16bc6111ff9e8";
			String appSecret = "3b2f0416e80ed4821958ee7da189cf81";
			ApplicationCommon appinfo = applicationCommonService.getApplicationById(user.getLoginPlat());
			if (appinfo.getAppId() != null && appinfo.getAppSecret() != null) {
				appId = appinfo.getAppId();
				appSecret = appinfo.getAppSecret();
			}
			String token = WeiXinSessionUtils.getAccessToken(appId, appSecret);
			String path = "/pages/index/index?introducer=" + userId + "&salerId=" + userId;
			codepath = ErWeiCodeImageContoller.upload(token, path, request);
		}
		return codepath;
	}

	/**
	 * 生成圆形二维码
	 * 
	 * @param userId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCircleCode")
	public String getCircleCode(HttpServletRequest request, HttpServletResponse response, Integer appId, Integer userId)
			throws Exception {
		String codepath = "";
		String wxappId="";
		String appSecret = "3b2f0416e80ed4821958ee7da189cf81";
		ApplicationCommon appinfo = applicationCommonService.getApplicationById(appId);
		if (appinfo.getAppId() != null && appinfo.getAppSecret() != null) {
			wxappId=appinfo.getAppId();
			appSecret = appinfo.getAppSecret();
		}
		String token = WeiXinSessionUtils.getAccessToken(wxappId, appSecret);
		String page = "pages/index/index";
		String scene = "introducer/" + userId + "*salerId/" + userId;
		Integer width = 430;
		codepath = ErWeiCodeImageContoller.getMiniqrQR(page, width, scene, token, request);
		
		UserCard card=userCardService.getUserCard(userId, appId);
		if(card != null){
			card.setCodeUrl(codepath);
			userCardService.updateUserCard(card);
		}
		return codepath;
	}

	/**
	 * 测试获取token
	 * 
	 * @param response
	 */
	@RequestMapping("/test")
	public void test(HttpServletResponse response) {
		String accesstoken = WorkWeixinUtils.getAccessToken("ww6c19a54e88180452",
				"vfdKANC_bFbJnlreTzcWcsewzuE9NWRgcb6f-4xamKY");
		output(response, JsonUtil.buildFalseJson("0", accesstoken));
	}

	/**
	 * 测试重定向:明天做后台授权比较安全
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/test1")
	public void testJump(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.sendRedirect("http://192.168.0.156:8080/cofC/aida/login/test2.do");
	}

	@RequestMapping("/test2")
	public ModelAndView test2(ModelAndView mv) {
		mv.setViewName("../../weixin/index");
		return mv;
	}

	@RequestMapping("/test3")
	public void test3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/weixin/index.jsp").forward(request, response);
	}
	@RequestMapping("/test4")
    public void test4(HttpServletRequest request,HttpServletResponse response,Integer userId){
    	UserCommon uinfo = userCommonService.getUserByUserId(userId);
    	if (uinfo.getUserCodeUrl() == null || uinfo.getUserCodeUrl().equals("")) {
			output(response,JsonUtil.buildFalseJson("0", "成功"));
			
		}else{
			output(response,JsonUtil.buildFalseJson("1", "失败"));
		}
    }
	public static void main(String[] args) {
		String aString = "{\"name\":\"小米\",\"sex\":\"男\"}";
		JSONObject userobj = JSONObject.parseObject(aString);
		String aString2 = userobj.getString("aaa");
		if (aString2 == null || aString2.equals("")) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
	}
}
