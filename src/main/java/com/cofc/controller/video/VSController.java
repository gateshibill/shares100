package com.cofc.controller.video;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.alibaba.fastjson.JSONObject;

import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserRoleService;

import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;



@Controller
@RequestMapping("/video")
public class VSController extends BaseUtil {
	@Resource
	private UserRoleService userroleService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private UserBackuserRelationService uburelaService;
	
	public static Logger log = Logger.getLogger("VSController");
	
	//<clientId,token>
	private Map<String,Token> tokenMap= new HashMap<String,Token>();
	private static String md5key="feikan_tv";
	private static String basePlayUrl="rtmp://192.168.1.10/live/";
	private static String baseToken="?time=%s&expire=%s&token=%s";
	
	
	/**
	 * rtmp://192.168.1.10/live?time=1402307089&expire=3600&token=88195f8943e5c944066725df2b1706f8/livestream
	 *token=md5(time+id+私钥+有效期)=88195f8943e5c944066725df2b1706f8
	 */
	// 1.获得播放串。
	@RequestMapping("/authPlayUrl")
	public void authPlayUrl(HttpServletRequest request,HttpServletResponse response,String clientId,String roomId) throws IOException {
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		System.out.println("authPlayUrl body:"+body);
		JSONObject obj=JsonUtil.parse(body);
		System.out.println("obj:"+obj);
		
		long time= new Date().getTime();
		int expire=10*60*1000;
		String tokenStr= DigestUtils.md5Hex(clientId+time+expire + md5key);
		Token token=new Token(time,expire,tokenStr);
		tokenMap.put(clientId, token);
		
		String playUrl=basePlayUrl+roomId+baseToken;
		playUrl=String.format(playUrl, time,expire,tokenStr);
		//逻辑判断待完善
		output(response, playUrl);
	}
	// 2.连接
	@RequestMapping("/on_connect")
	public void on_connect(HttpServletRequest request,HttpServletResponse response) throws IOException {
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		System.out.println("on_connect body:"+body);
		JSONObject obj=JsonUtil.parse(body);
	
		System.out.println("obj:"+obj);
		
		//逻辑判断待完善
		
		output(response, "0");
	}
	
	// 3.推流
	@RequestMapping("/on_publish")
	public void on_publish(HttpServletRequest request,HttpServletResponse response) throws IOException {
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		System.out.println("on_publish body:"+body);
		JSONObject obj=JsonUtil.parse(body);
		System.out.println("obj:"+obj);
		
		//逻辑判断待完善
		
		output(response, "0");
	}
	// 3.1.关闭推流
	@RequestMapping("/on_unpublish")
	public void on_unpublish(HttpServletRequest request,HttpServletResponse response) throws IOException {
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		System.out.println("on_unpublish body:"+body);
		JSONObject obj=JsonUtil.parse(body);
		System.out.println("obj:"+obj);
		
		//逻辑判断待完善
		
		output(response, "0");
	}
	
	// 4.播放
	@RequestMapping("/on_play")
	public void on_play(HttpServletRequest request,HttpServletResponse response) throws IOException {
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		System.out.println("on_play body:"+body);
		JSONObject o=JsonUtil.parse(body);
		System.out.println("obj:"+o);
		
		//String tcUrl=obj.get("tcUrl").toString();
		
		String param=o.get("param").toString();
		System.out.println(param);
		param="{" + param.replace("?", "")+"}";
		String json=param.replace("=", ":");
		System.out.println(json);
		JSONObject obj=(JSONObject) JSONObject.parse(json);
		
		String clientId=obj.get("clientId").toString();
		String tokenStr = obj.get("token").toString();
	
		Token token= tokenMap.get(clientId);
		if(clientId.equals(""))
		{
			log.info("clientId is  null!");
			output(response, "100");
			return;
		}
		else if(null==token)
		{
			log.info(clientId +" token null!");
			output(response, "0");
			return;
		}
		else if(token.isExpire())
		{
			log.info(clientId +" token expire!");
			output(response, "0");
			return;
		}else if(!token.getToken().equals(tokenStr))
		{
			log.debug(token.getToken());
			log.debug(tokenStr);
			log.info(clientId +" token is not valid!");
			output(response, "0");
			return;
		}
		
		output(response, "0");
	}
	// 5.关闭
	@RequestMapping("/on_close")
	public void on_close(HttpServletRequest request,HttpServletResponse response) throws IOException {
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		System.out.println("on_close body:"+body);
		JSONObject obj=JsonUtil.parse(body);
		System.out.println("obj:"+obj);
		
		//逻辑判断待完善
		
		output(response, "0");
	}
	//6.停播
	@RequestMapping("/on_stop")
	public void on_stop(HttpServletRequest request,HttpServletResponse response) throws IOException {
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		System.out.println("on_stop body:"+body);
		JSONObject obj=JsonUtil.parse(body);
		System.out.println("obj:"+obj);
		
		//逻辑判断待完善
		
		output(response, "0");
	}
	
	// 1.播放请求
//	@RequestMapping("/reportFaceGameBehavior")
//	public void reportFaceGameBehavior(HttpServletRequest request,HttpServletResponse response, FaceGameBehavior fgb) throws IOException {       
//		fgb.setIp(getIpAddr(request));
//		log.info(fgb.getId() + "(" + fgb.getName() + ")" + "上报:" + fgb.getType() + "|"
//				+ fgb.getContent() + "|" + fgb.getTime());
//		
//		fgb.setCreateTime(new Date());
//		faceGameBehaviorService.addFaceGameBehavior(fgb);
//		output(response, JsonUtil.buildSuccessJson("0", "succes"));
//	}
//	
//	// 2.播放串校验
//	@RequestMapping("/getPortal")
//	public void getPortal(HttpServletRequest request,HttpServletResponse response, FaceGame fg) throws IOException {
//
//		output(response, JsonUtil.bulidObjectJson(fg));
//	}
//	
//	// 3.白名单
//	@RequestMapping("/getPortal")
//	public void getPortal(HttpServletRequest request,HttpServletResponse response, FaceGame fg) throws IOException {
//
//		output(response, JsonUtil.bulidObjectJson(fg));
//	}
//	
//	// 4.分配服务节点
//	@RequestMapping("/getPortal")
//	public void getPortal(HttpServletRequest request,HttpServletResponse response, FaceGame fg) throws IOException {
//
//		output(response, JsonUtil.bulidObjectJson(fg));
//	}
//	
//	// 5.节点管理，内容分发
//	@RequestMapping("/getPortal")
//	public void getPortal(HttpServletRequest request,HttpServletResponse response, FaceGame fg) throws IOException {
//
//		output(response, JsonUtil.bulidObjectJson(fg));
//	}
//	
//	// 6.视频流连接监控
//	@RequestMapping("/getPortal")
//	public void getPortal(HttpServletRequest request,HttpServletResponse response, FaceGame fg) throws IOException {
//
//		output(response, JsonUtil.bulidObjectJson(fg));
//	}
	public static void main(String[] args) {

        System.out.println(new Date());
        System.out.println(new Date().getTime());
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(new Date().getTime());
	}	
}
