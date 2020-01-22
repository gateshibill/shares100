package com.cofc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
	
public class WeiXinSessionUtils {
	//商会
//	private final static String DEFAULT_APP_ID = "wx5081f88c6c48469d";
//	private final static String DEFAULT_APP_SECRET = "45cf26e0f1448fdec1639074f8db13ee";
	//公司主体商会小程序通用版
	public static String DEFAULT_APP_ID = "";
	public static String DEFAULT_APP_SECRET = "";
	
	//private static HttpClient client = new DefaultHttpClient();
	private static Gson gson = new Gson();
	
	
	private static Map<String, Token> tokenMap = new HashMap<String, Token>();
	private static Map<String, QrcodeTicket> qrcodeTicketMap = new HashMap<String, QrcodeTicket>();
	
	public static String getAccessToken(){
		return getAccessToken(DEFAULT_APP_ID, DEFAULT_APP_SECRET);
	}
	
	
	public static String getAccessToken(String appID, String appSecret){
		Token token = tokenMap.get(appID);
		Date now = new Date();
		if(token==null||now.getTime()-token.create_date.getTime()>7000000){
			//本来过期时间是7200s的，舍掉了尾巴200s
			String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
			HttpGet get = new HttpGet(String.format(url, appID, appSecret));
			try {
				HttpClient client = new DefaultHttpClient();
				HttpResponse res = client.execute(get);
				String body = EntityUtils.toString(res.getEntity());
				//System.out.println(body);
			
				Map map = gson.fromJson(body, Map.class);
				String access_token = (String) map.get("access_token");
				
				if(access_token!=null&&access_token.length()>0){
					token = new Token();
					token.access_token = access_token;
					token.create_date = now;
					tokenMap.put(appID, token);
				}else{
					return null;
				}
			}catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return token.access_token;
	}
	
	
	
	public static String getAccessTokenByCode(String code){
		String template = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
		String url = String.format(template, DEFAULT_APP_ID, DEFAULT_APP_SECRET, code);
		HttpGet get = new HttpGet(url);
		HttpResponse res;
		String body = null;
		try {
			HttpClient client = new DefaultHttpClient();
			res = client.execute(get);
			body = EntityUtils.toString(res.getEntity());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
//	public static String getUnionIdTokenByCode(String code){
//		String template = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
//		String url = String.format(template, DEFAULT_APP_ID, DEFAULT_APP_SECRET, code);
//		HttpGet get = new HttpGet(url);
//		HttpResponse res;
//		String body = null;
//		try {
//			HttpClient client = new DefaultHttpClient();
//			res = client.execute(get);
//			body = EntityUtils.toString(res.getEntity());
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return body;
//	}

	public static String getQrcodeTicket(String accessToken, String sceneID){
		String template = "{\"expire_seconds\": 1800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": \"%s\"}}}";
		String json = String.format(template, sceneID);
		return getQrcodeTicket(accessToken, sceneID, json);
	}	
	
	
	private static String getQrcodeTicket(String accessToken, String sceneID, String json){
		
		QrcodeTicket qrcodeTicket = qrcodeTicketMap.get(sceneID);
		Date now = new Date();
		if(qrcodeTicket==null||now.getTime()-qrcodeTicket.create_date.getTime()>1700000){
			//本来过期时间是1800s的，舍掉了尾巴。
			String url = String.format("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=%s", accessToken);
			HttpPost post = new HttpPost(url); 
			try {
				HttpClient client = new DefaultHttpClient();
				StringEntity entity = new StringEntity(json, "utf-8");
				post.setEntity(entity);
				HttpResponse res = client.execute(post);
				String body = EntityUtils.toString(res.getEntity());
				//System.out.println(body);
				
				Map map = gson.fromJson(body, Map.class);
				String ticket = (String) map.get("ticket");
				if(ticket!=null&&ticket.length()>0){
					qrcodeTicket = new QrcodeTicket();
					qrcodeTicket.ticket = ticket;
					qrcodeTicket.create_date = now;
					qrcodeTicketMap.put(sceneID, qrcodeTicket);
				}else{
					return null;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		return qrcodeTicket.ticket;
	}
	

	/**
	 * @param ticket
	 * @return 二维码地址url
	 */
	public static String showQrcode(String ticket){
		String template = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";
		String url = String.format(template, ticket);
		return url;
	}
	
	
	/**
	 * @param ticket
	 * 保存二维码图片
	 */
	public static void saveQrcode(String ticket){
		String template = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=%s";
		String url = String.format(template, ticket);
		HttpGet get = new HttpGet(url);
		try {
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(get);
			byte[] bs = EntityUtils.toByteArray(res.getEntity());
			File storeFile = new File("c:/2008sohu.gif");  
		    FileOutputStream output = new FileOutputStream(storeFile);  
		    //得到网络资源的字节数组,并写入文件  
		    output.write(bs);  
		    output.close();  
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @param accessToken
	 * @return jssdk用的ticket
	 */
	public static String getJSTicket(String accessToken){
		
		String template = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
		String url = String.format(template, accessToken);
		HttpGet get = new HttpGet(url);
		
		String ticket = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(get);
			String body = EntityUtils.toString(res.getEntity());
			Map map = gson.fromJson(body, Map.class);
			ticket = (String) map.get("ticket");
//			System.out.println(ticket);			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ticket;
	}
	
	
	public static String getUserinfo(String openID){
		//return getUserinfo(openID, getAccessToken());  这个方法应该题，应该是不同一个taken
		return null;
	}
	
	
	public static String getUserinfo(String openID, String token){
		String template = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";
		String url = String.format(template, token, openID);
		HttpGet get = new HttpGet(url);
		String body = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(get);
			body = EntityUtils.toString(res.getEntity(), "utf-8");
//			System.out.println(body);			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	public static String getUserinfo2(String openID, String token){
		//跟getUserinfo的url不同
		String template = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
		String url = String.format(template, token, openID);
		HttpGet get = new HttpGet(url);
		String body = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(get);
			body = EntityUtils.toString(res.getEntity(), "utf-8");
//			System.out.println(body);			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}	
	
	
	
	public static String getAppID(){
		return DEFAULT_APP_ID;
	}
	
	
	private static class Token{
		public String access_token;
		public Date create_date;
	}
	
	private static class QrcodeTicket{
		public String ticket;
		public Date create_date;
	}
	
	
	public static void clearToken(){
		tokenMap.remove(DEFAULT_APP_ID);
	}
	
	
	public static void clearToken(String appID){
		tokenMap.remove(appID);
	}	
	
	
	public static String refreshToken(String token){
		return refreshToken(DEFAULT_APP_ID, token);
	}
	
	
	public static String refreshToken(String appID, String token){
		String template = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
		String url = String.format(template, appID, token);
		HttpGet get = new HttpGet(url);
		String body = null;
		try {
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(get);
			body = EntityUtils.toString(res.getEntity(), "utf-8");
	//		System.out.println(body);			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
}
