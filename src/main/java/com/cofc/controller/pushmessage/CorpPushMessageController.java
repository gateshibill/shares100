package com.cofc.controller.pushmessage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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
import com.cofc.util.WorkWeixinUtils;
import com.google.gson.Gson;

@Controller
@RequestMapping("/wx/pushmessage")
public class CorpPushMessageController extends BaseUtil {
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
	public static Logger log = Logger.getLogger("CorpPushMessageController");

	public static String corpid = "ww6c19a54e88180452";// 企业ID
	public static String corpsecret = "2QYBbqwxaBVrYn3P-Q7dTO8Xg_VX41CdG26yJIdTm0g";// 企业中的子应用ID
	public static String agentId = "1000021";// 子应用ID，可以动态获取

	public static void main(String[] args) {
		String PartyID1 = "1";// 部门信息，公司级别默认为1，特别注意：给第三方用户用时候可能会出现问题，需要保存用户的部门信息才能发送成功。
		String UserID1 = "TianYingGuo";// 来自于企业联系人名单上，用户的账号。需要保存到数据库，方便每次使用；
		String content = "测试：您有新消息，请及时处理";//

		String reponse = sendCorpMessage(corpid, corpsecret, agentId, UserID1, null, content);
		log.debug("send corp message reponse:" + reponse);

		String openId = "oJJYd0dTNvUvLyVGHoOd2K_RdPRE";// 对应13074,Bill.chen
		String form_id = "2cefdf5ce21d89a7c49b18821436fb82";//
		String name = "飞看科技";
		int salesPersonId = 13074;
		// String res =
		// WXPushMessageController.sendWxMessageEx(corpid,corpsecret,openId,
		// salesPersonId, form_id, name);
		// log.debug("send minProgram message res:" + res);
	}

	// 发送微信企业号消息
	public static String sendCorpMessage(String corpId, String corpsecret, String agentId, String UserID1,
			String PartyID1, String content) {
		log.debug("corpId:" + corpId + "|corpsecret:" + corpsecret + "|agentId" + agentId + "|UserID1：" + UserID1
				+ "|PartyID1" + PartyID1 + "|content:" + content);
		String token = WorkWeixinUtils.getAccessToken(corpId.toString(), corpsecret);
		String url = " https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + token;
		String message = getCorpMessage(agentId, UserID1, PartyID1, content);
		String reponse = HttpPost.sendPost(url, message);
		return reponse;
	}

	// 发送微信企业号消息
	public static String getCorpMessage(String agentId, String UserID1, String PartyID1, String content) {
		String jsonString = "{";
		jsonString += "\"touser\": \"" + UserID1 + "\",";
		jsonString += "\"toparty\": \"" + PartyID1 + "\",";
		jsonString += "\"totag\": \"\",";
		jsonString += "\"msgtype\": \"text\",";
		jsonString += "\"agentid\": " + agentId + ",";
		jsonString += "\"text\": " + "{\"content\":\"" + content + "\"" + "},";
		jsonString += "\"safe\": " + 0;
		jsonString += "}";
		return jsonString;
	}
}
