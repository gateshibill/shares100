package com.cofc.controller.pushmessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.OrderVoice;
import com.cofc.pojo.PushMessage;
import com.cofc.pojo.PushNotice;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.WeixinPush;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.OrderVoiceService;
import com.cofc.service.PushMessageService;
import com.cofc.service.PushNoticeService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.service.WeixinPushService;
import com.cofc.util.BaseUtil;
import com.cofc.util.HttpPost;
import com.cofc.util.JsonUtil;
import com.cofc.util.WeiXinSessionUtils;

@Controller
@RequestMapping("/wx/pushmessage")
public class WXPushMessageController extends BaseUtil {
	@Resource
	private UserCommonService userService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private PushMessageService pushService;
	@Resource
	private OrderVoiceService voiceService;
	@Resource
	private PushNoticeService noticeService;
	@Resource
	private UserOrderService orderService;
	@Resource
	private WeixinPushService weixinPushService;
	public static Logger log = Logger.getLogger("WXPushMessageController");

	/**
	 * 小程序发送模板推送数据
	 * 
	 * @param touser
	 * @param template_id
	 * @param page
	 * @param form_id
	 * @param data
	 * @return
	 */
	public static String sendTemplateTpl(String touser, String template_id, String page, String form_id, String data) {
		String jsonString = "{";
		jsonString += "\"touser\": \"" + touser + "\",";
		jsonString += "\"template_id\": \"" + template_id + "\",";
		jsonString += "\"page\": \"" + page + "\",";
		jsonString += "\"form_id\": \"" + form_id + "\",";
		jsonString += "\"data\": " + data;
		jsonString += "}";
		System.out.println("jsonString==" + jsonString);
		return jsonString;
	}

	/**
	 * 推送模板给买家
	 * 
	 * @param response
	 * @param request
	 * @param userId
	 *            : 用户id
	 * @param loginPlat:应用id
	 * @param page:
	 *            模板跳转的页面
	 * @param form_id
	 *            ： form_id
	 * @param data
	 *            : 模板信息
	 * @param tempType
	 *            ： 模板类型，根据模板类型和loginplat获取模板id
	 */
	@RequestMapping("/sendmessage")
	public void sendMessage(HttpServletResponse response, HttpServletRequest request, Integer userId, Integer loginPlat,
			String page, String form_id, String data, Integer tempType, Integer orderId) {
		// 获取应用token和用户openId
		// 找出该应用所对应得appId,appSecret,如果为空则是百享园的
		String appId = "";
		String appSecret = "";
		ApplicationCommon appinfo = appService.getApplicationCommonById(loginPlat);
		UserCommon userinfo = userService.getUserByUserId(userId);
		if (userinfo == null) {
			log.info("该用户不存在");
			output(response, JsonUtil.buildFalseJson("1", "非法用户"));
			return;
		}
		if (userinfo.getOpenId() == null) {
			log.info("用户未授权或登录失败:为获取到openId");
			output(response, JsonUtil.buildFalseJson("1", "非法用户：用户openId为空"));
			return;
		}
		if (appinfo == null) {
			log.info("应用不存在");
			output(response, JsonUtil.buildFalseJson("1", "应用不存在"));
			return;
		}
		if (appinfo.getAppId() == null || appinfo.getAppId().equals("")) {
			// 百享园
			appId = "wx474aed7d8ee915d9";
			appSecret = "9b4dcb4fd032ae6cef2c25aae0aa1129";
		} else {
			// 直接获取应用appid和appse
			appId = appinfo.getAppId();
			appSecret = appinfo.getAppSecret();
		}
		// 获取access_token
		String access_token = WeiXinSessionUtils.getAccessToken(appId, appSecret);
		String touser = userinfo.getOpenId(); // 获取用户的openId
		// 获取模板id
		PushMessage message = pushService.getTemplateInfo(loginPlat, tempType);
		PushMessage bxymessage = pushService.getTemplateInfo(1, tempType); // 百享园
		String template_id = "";
		if (message == null) {
			if (bxymessage.getIsEffect() != 1) {
				output(response, JsonUtil.buildFalseJson("1", "该模板已被禁用"));
				return;
			} else {
				if (bxymessage.getIsRemove() != 0) {
					output(response, JsonUtil.buildFalseJson("1", "该模板已被删除"));
					return;
				} else {
					template_id = bxymessage.getTemplateId();
				}
			}
		} else {
			if (message.getIsEffect() != 1) {
				output(response, JsonUtil.buildFalseJson("1", "该模板已被禁用"));
				return;
			} else {
				if (message.getIsRemove() != 0) {
					output(response, JsonUtil.buildFalseJson("1", "该模板已被删除"));
					return;
				} else {
					template_id = message.getTemplateId();
				}
			}
		}
		String templateTpl = sendTemplateTpl(touser, template_id, page, form_id, data);
		String returnInfo = HttpPost.sendPost(
				"https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + access_token,
				templateTpl);
		System.out.println("推送结果--" + returnInfo);
		log.info("推送结果" + returnInfo);
		// 获取配置
		WeixinPush weixinPush = weixinPushService.getWXConfig(loginPlat);
		if (weixinPush != null) {
			String wxappId = weixinPush.getAppId();
			String wxappSecret = weixinPush.getAppSecret();
			// 获取微信推送模板
			PushMessage wxmessage = pushService.getTemplateInfo(loginPlat, 10);
			String wxtplId = wxmessage.getTemplateId();
			if (!wxtplId.equals("")) {
				sendWxMessage(orderId, loginPlat, wxappId, wxappSecret, wxtplId);
			}
		}
		try {
			output(response, JsonUtil.buildFalseJson("0", "发送模板成功"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "发送模板失败"));
		}
	}

	/* ==========================微信服务号推送=================================== */
	public static String sendWxTpl(String openId, String tplId, String data) {
		String jsonString = "{";
		jsonString += "\"touser\": \"" + openId + "\",";
		jsonString += "\"template_id\": \"" + tplId + "\",";
		jsonString += "\"url\": \"\",";
		jsonString += "\"topcolor\": \"#FF0000\",";
		jsonString += "\"data\": " + data;
		jsonString += "}";
		return jsonString;
	}

	// 给用户服务通知信息
	public static String sendWxMessageEx(String appId, String appSecret, String touser, int salesPersonId,
			String form_id, String name,String msg,String tplId) {
		//String tplId = "y1EpqxgJBzgUD6BG-f43gzgbKZOwcmqqtwuQBn8gF0Q";// 固定通知消息模板
		
		String page = "pages/message/message?source=message&salerId=" + salesPersonId;
		String data = getMinProgrameNotifyMessage(name,msg);
		// 获取配置
		String access_token = WeiXinSessionUtils.getAccessToken(appId, appSecret);
		String tpl = sendTemplateTpl(touser, tplId, page, form_id, data);
		String res = HttpPost.sendPost(
				"https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + access_token, tpl);
		System.out.println("res:" + res);
		return res;
	}

	private static String getMinProgrameNotifyMessage(String name,String msg) {
		String data = "{";
		data += "\"keyword1\":{\"value\":\"" + msg + "\"},";
		data += "\"keyword2\":{\"value\":\"" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "\"},";
		data += "\"keyword3\":{\"value\":\"你有"+name+"未读消息\"}";
		data += "}";
		return data;
	}

	public boolean sendWxMessage(Integer orderId, Integer loginPlat, String appId, String appSecret, String tplId) {
		boolean isSend = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		UserOrder order = orderService.findByID(orderId);
		String data = "{";
		data += "\"first\":{";
		data += "\"value\":\"你有新的订单\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"Day\":{";
		data += "\"value\":\"" + sdf.format(new Date()) + "\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"orderId\":{";
		data += "\"value\":\"" + orderId + "\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"orderType\":{";
		data += "\"value\":\"消费\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"customerName\":{";
		data += "\"value\":\"" + order.getConsignee() + "\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"customerPhone\":{";
		data += "\"value\":\"" + order.getPhone() + "\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"remark\":{";
		data += "\"value\":\"收到新订单，请及时处理\",";
		data += "\"color\":\"#173177\"";
		data += "}";
		data += "}";
		// 获取配置
		String access_token = WeiXinSessionUtils.getAccessToken(appId, appSecret);
		List<PushNotice> lists = noticeService.getSengMessage(loginPlat);
		if (lists == null) {
			isSend = false;
		} else {
			JSONArray pushArray = null;
			pushArray = new JSONArray(lists);
			for (int i = 0; i < pushArray.length(); i++) {
				JSONObject json = pushArray.getJSONObject(i);
				String openId = json.optString("openId");
				String tpl = sendWxTpl(openId, tplId, data);
				HttpPost.sendPost(
						"https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token, tpl);
			}
			isSend = true;
		}
		return isSend;
	}

	/*
	 * =====================================测试微信服务号推送模板
	 * start===================================================
	 */

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String data = "{";
		data += "\"first\":\"你有新的订单提醒:\",";
		data += "\"keyword1\":\"123456789\",";
		data += "\"keyword2\":\"wxh\",";
		data += "\"keyword3\":\"" + sdf.format(new Date()) + "\",";
		data += "\"remark\":\"请尽快处理:\"";
		data += "}";
		String tpl = sendWxTpl("o7bk5w4c1Aw4zAnqSQ7SKW_Hw7Nc", "FkHiqPQOefVIUW7NlRJPIIlNr_Cef8lB47EfJShnRFM", data);
		System.out.println(tpl);
	}

	@RequestMapping("/testsend")
	public void testsend(HttpServletResponse response, HttpServletRequest request) {
		String data = "{";
		data += "\"first\":{";
		data += "\"value\":\"你有新的订单\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"Day\":{";
		data += "\"value\":\"" + new Date() + "\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"orderId\":{";
		data += "\"value\":\"1\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"orderType\":{";
		data += "\"value\":\"消费\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"customerName\":{";
		data += "\"value\":\"梦浩然\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"customerPhone\":{";
		data += "\"value\":\"13428282211\",";
		data += "\"color\":\"#173177\"";
		data += "},";
		data += "\"remark\":{";
		data += "\"value\":\"收到新订单，请及时处理\",";
		data += "\"color\":\"#173177\"";
		data += "}";
		data += "}";
		String tpl = sendWxTpl("ofaBk1PL9T0Eju-6xOwWeJ7SAKLc", "OCocjFeKbjZyRR_0DL2kUI4oJFpk3tKDp9Hh9PnlzHA", data);
		System.out.println(tpl);
		String appId = "wxfdb960c15de2e3b2";
		String appSecret = "263439a32c4749343a0575f2f4868ef7";
		String access_token = WeiXinSessionUtils.getAccessToken(appId, appSecret);
		System.out.println(access_token);
		// HttpPost.sendPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+access_token,tpl);
		// https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN
		HttpPost.sendPost("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token, tpl);
		try {
			output(response, JsonUtil.buildFalseJson("0", "发送模板成功"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "发送模板失败"));
		}
	}

	/*
	 * =====================================测试微信服务号推送模板
	 * end===================================================
	 */

	/*
	 * ==================================================语音接收通知
	 * start=======================================================
	 */

	/**
	 * 检测该用户是否有接收语音的权限
	 * 
	 * @param response
	 * @param loginPlat
	 * @param userId
	 */
	@RequestMapping("/checkpower")
	public void checkPower(HttpServletResponse response, Integer loginPlat, Integer userId) {
		if (loginPlat == null) {
			output(response, JsonUtil.buildFalseJson("1", "传递参数有误"));
			return;
		}
		if (userId == null) {
			output(response, JsonUtil.buildFalseJson("1", "传递参数有误"));
			return;
		}
		int rowscount = noticeService.checkUserForNotice(loginPlat, userId, null, 1);
		if (rowscount > 0) {
			log.info("用户有接收语音的权限");
			output(response, JsonUtil.buildFalseJson("0", "有接收语音权限"));
		} else {
			log.info("用户id:" + userId + "在平台id:" + loginPlat + "无接收语音消息权限");
			output(response, JsonUtil.buildFalseJson("1", "没有接收语音权限"));
		}
	}

	/**
	 * 检测是否有未处理订单
	 * 
	 * @param response
	 * @param loginPlat
	 */
	@RequestMapping("/checkdealorder")
	public void checkDealOrder(HttpServletResponse response, Integer loginPlat) {
		int count = voiceService.getOrderVoiceCount(loginPlat);
		if (count > 0) {
			output(response, JsonUtil.buildFalseJson("0", "有未处理订单"));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有未处理订单"));
		}
	}

	/**
	 * 更新语音播放
	 * 
	 * @param response
	 * @param loginPlat
	 */
	@RequestMapping("/updatevoice")
	public void updateOrderVoice(HttpServletResponse response, Integer loginPlat, Integer orderId) {
		OrderVoice voice = new OrderVoice();
		voice.setLoginPlat(loginPlat);
		voice.setOrderId(orderId);
		voiceService.updateOrderVoice(voice);
	}

}
