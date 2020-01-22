package com.cofc.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.cofc.pojo.aida.WxUserTicket;
import com.google.gson.Gson;

/**
 * 企业号相关信息
 * @author 46678
 *
 */
public class WorkWeixinUtils {
	 public static Logger log = Logger.getLogger("WorkWeixinUtils");
	//缓存token
	 private static Map<String, Token> tokenMap = new HashMap<String, Token>();
	 /**
	  * 获取Accsee_Token :https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRECT
	  * 请求方式：GET
	  * @param corpid :企业id
	  * @param corpsecret:应用密钥
	  * @return
	  */
	 public static String getAccessToken(String corpid,String corpsecret){
		 Token token = tokenMap.get(corpid);
		 Date now = new Date();
		 if(token==null||now.getTime()-token.expires_in.getTime()>7200000){
			 String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
			 String returnInfo = HttpGet.sendGet(url,"corpid="+corpid+"&corpsecret="+corpsecret);
			 JSONObject obj = JSONObject.parseObject(returnInfo);
			 String access_token = obj.getString("access_token");
			 if(access_token!=null&&access_token.length()>0){
					token = new Token();
					token.access_token = access_token;
					token.expires_in = now;
					tokenMap.put(corpid, token);
			 }else{
					return null;
			 }
		 }
		 return token.access_token;
	 }
	 
	 /**
	  * 获取成员信息：https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=ACCESS_TOKEN&code=CODE
	  * 请求方式：GET
	  * @param accesstoken ：调用接口凭证
	  * @param code：通过成员授权获取到的code，最大为512字节。每次成员授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
	  * @return :授权
	  * {
		   "errcode": 0,
		   "errmsg": "ok",
		   "UserId":"USERID",
		   "DeviceId":"DEVICEID",
		   "user_ticket": "USER_TICKET"，
		   "expires_in":7200
		}
	  * 
	  */
	 public static String getUserInfo(String accesstoken,String code){
		 String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
		 String returnInfo = HttpGet.sendGet(url,"access_token="+accesstoken+"&code="+code);
		 return returnInfo;
	 }
	 /**
	  * 使用user_ticket获取成员详情
	  * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=ACCESS_TOKEN
		请求包体：
		
		{
		   "user_ticket": "USER_TICKET"
		}
	  * 请求方式：POST
	  * @param accesstoken
	  * @param userticket
	  * @return
	  * 返回结果：
			{
			   "errcode": 0,
			   "errmsg": "ok",
			   "userid":"lisi",
			   "name":"李四",
			   "mobile":"13050495892",
			   "gender":"1",
			   "email":"xxx@xx.com",
			   "avatar":"http://shp.qpic.cn/bizmp/xxxxxxxxxxx/0",
			   "qr_code":"http://open.work.weixin.qq.com/wwopen/userQRCode?vcode=vcfc13b01dfs78e981c"
			}
	  */
	 public static String getUserDetail(String accesstoken,String userticket){
		 log.info("getUserDetail--"+accesstoken+"_________"+userticket); 
		 String postUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token="+accesstoken;
		 log.info("获取用户详情的url--"+postUrl);
		 WxUserTicket ticket = new WxUserTicket();
		 ticket.setUser_ticket(userticket);
		//2.准备好请求包体
		 Gson gson = new Gson(); 
		 String jsonUserTicket =gson.toJson(ticket);
		 String returnInfo = HttpPost.sendPost(postUrl, jsonUserTicket);
		 return returnInfo;
	 }
	 /**
	  * 获取jsapi_ticket
	  * https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKE
	  * 请求方式 :GET
	  * @param accesstoken
	  * @return
	  */
	 public static String getJSApiTicket(String accesstoken){
		 String apiUrl = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket";
		 String returnInfo = HttpGet.sendGet(apiUrl, "access_token="+accesstoken);
		 log.info("获取返回的jsApiTicket---"+returnInfo);
		 JSONObject obj = JSONObject.parseObject(returnInfo);
		 String jsApiTicket = obj.getString("ticket");
		 return jsApiTicket;
	 }
	 /**
	  * 获取  agent_jsapi_ticket
	  * https://qyapi.weixin.qq.com/cgi-bin/ticket/get?access_token=ACCESS_TOKEN&type=agent_config
	  * 请求方式：GET
	  * @param accesstoken
	  * @return
	  */
	 public static String getAgentJSApiTicket(String accesstoken){
		 String apiUrl = "https://qyapi.weixin.qq.com/cgi-bin/ticket/get";
		 String returnInfo = HttpGet.sendGet(apiUrl,"access_token="+accesstoken+"&type=agent_config");
		 JSONObject obj = JSONObject.parseObject(returnInfo);
		 String agentjsApiTicket = obj.getString("ticket");
		 log.info("获取agentjsapi=="+agentjsApiTicket);
		 return agentjsApiTicket;
	 }
	 /**
	  * 请求方式：POST（HTTPS）
		请求地址： https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=ACCESS_TOKEN
		请求示例：
		
		{
		   "userid": "zhangsan"
		}
	  * @param userid
	  * @param access_token
	  * @return openid
	  */
	 public static String GetUserIdChangeOpenId(String userid,String access_token){
		 String postUrl = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token="+access_token;
		 ChangeOpen open = new ChangeOpen();
		 open.userid = userid;
		//2.准备好请求包体
		 Gson gson = new Gson(); 
		 String jsonuserid =gson.toJson(open);
		 String returnInfo = HttpPost.sendPost(postUrl, jsonuserid);
		 JSONObject obj = JSONObject.parseObject(returnInfo);
		 log.info("获取到的openid---"+obj.getString("openid"));
         return obj.getString("openid");
	 }
	 
	 public static class ChangeOpen{
		 public String userid;
	 }
	 
	 public static class Token{
		 public String access_token;
		 public Date expires_in;
	 }
}
