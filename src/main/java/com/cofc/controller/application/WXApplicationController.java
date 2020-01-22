package com.cofc.controller.application;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cofc.controller.file.ErWeiCodeImageContoller;
import com.cofc.pojo.ApplicationCode;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.ApplicationLeaveMessage;
import com.cofc.pojo.ApplicationManage;
import com.cofc.pojo.ApplicationModel;
import com.cofc.pojo.ApplicationReplyMessage;
import com.cofc.pojo.CarouselPicture;
import com.cofc.pojo.UserBrowseRecord;
import com.cofc.pojo.UserCommon;
import com.cofc.service.ApplicationLeaveMessageService;
import com.cofc.service.ApplicationManageService;
import com.cofc.service.ApplicationModelService;
import com.cofc.service.ApplicationReplyMessageService;
import com.cofc.service.ApplicationCodeService;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.CarouselPictureService;
import com.cofc.service.UserBrowseRecordService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.WeiXinSessionUtils;
import com.cofc.util.HttpPost;
@Controller
@RequestMapping("/wx/application")
public class WXApplicationController extends BaseUtil {
	@Resource
	private ApplicationModelService modelService;
	@Resource
	private UserCommonService userService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private CarouselPictureService cpicService;
	@Resource
	private ApplicationLeaveMessageService almService;
	@Resource
	private ApplicationManageService appManageService;
	@Resource
	private UserBrowseRecordService userBrowseRecordService;
	@Resource
	private ApplicationCodeService applicationCodeService;
	@Resource
    private ApplicationReplyMessageService replyMessageService;
	public static Logger log = Logger.getLogger("WXApplicationController");

	@RequestMapping("/createApp")
	public void userChooseAppModel(HttpServletResponse response, ApplicationCommon appl) {
		UserCommon u = userService.getUserByUserId(appl.getApplicationOwner());
		ApplicationModel model = modelService.getModelById(appl.getModelId());
		if (u == null) {
			output(response, JsonUtil.buildFalseJson("1", "该用户没有找到"));
		} else if (model == null) {
			output(response, JsonUtil.buildFalseJson("2", "该模型没有找到"));
		} else {
			Date now = new Date();
			appl.setAppCreateTime(now);
			appl.setApplicationStatus(1);// 默认试用期
			appl.setAppUpdateTime(now);
			appService.createNewApplication(appl);
			int mynewAPPid = appl.getApplicationId();
			System.out.println(mynewAPPid);
			model.setModelUsedCount(model.getModelUsedCount() + 1);
			modelService.updateModelInfo(model);
			log.info("用户" + appl.getApplicationOwner() + "选择了" + appl.getModelId() + "作为模板，取名为"
					+ appl.getApplicationName());
			List<CarouselPicture> insertList = new ArrayList<CarouselPicture>();
			String firstP = "http://www.ailefeigou.com/cofC2/carousel/defaultCarousel1.jpg";
			String seccondP = "http://www.ailefeigou.com/cofC2/carousel/defaultCarousel2.jpg";
			for (int i = 1; i <= 2; i++) {
				CarouselPicture cpic = new CarouselPicture();
				cpic.setIsUsing(1);
				cpic.setLoginPlat(mynewAPPid);
				cpic.setOrderId(i);
				cpic.setPictureName("系统设置默认轮播图" + i);
				if (i == 1) {
					cpic.setPictureUrl(firstP);
				}
				if (i == 2) {
					cpic.setPictureUrl(seccondP);
				}
				cpic.setSxjUser(0);
				cpic.setUploadTime(now);
				cpic.setUploadUser(0);
				insertList.add(cpic);
			}
			cpicService.insertPictureBatch(insertList);
			output(response, JsonUtil.buildFalseJson("0", "小程序新建成功"));
		}
	}

	@RequestMapping("/leaveMessage")
	public void userLeageMessageForApp(HttpServletResponse response, ApplicationLeaveMessage alm) {
		alm.setCreateTime(new Date());
		try {
			almService.userLeaveMessage(alm);
			log.info("用户" + alm.getUserId() + "给应用" + alm.getLoginPlat() + "留言成功");
			output(response, JsonUtil.buildFalseJson("0", "留言成功"));
		} catch (Exception e) {
			log.info("用户" + alm.getUserId() + "给应用" + alm.getLoginPlat() + "留言失败，失败原因" + e);
			output(response, JsonUtil.buildFalseJson("1", "留言失败"));
		}
	}
  
	@RequestMapping("/levmsgList")
	public void showUserLeageMessages(HttpServletResponse response, Integer groupId, Integer loginPlat, Integer pageNo,
			Integer pageSize) {
		List<ApplicationLeaveMessage> aplmList = almService.findMyAppMessages(loginPlat, groupId,
				(pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(aplmList));
	}
    /**
     * 获取用户的咨询列表
     * @param response
     * @param loginPlat
     * @param userId
     * @param pageNo
     * @param pageSize
     */
	@RequestMapping("/getusermessage")
	public void getuserMessage(HttpServletResponse response,Integer loginPlat,Integer userId,
			Integer pageNo,Integer pageSize){
		if(pageNo == null){
			pageNo = 1;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		List<ApplicationLeaveMessage> lists = almService.getUserMessageInfo(loginPlat, userId,(pageNo-1)*pageSize, pageSize);
	    output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 回复咨询
	 * @param response
	 * @param reply
	 */
	@RequestMapping("/addreplymessage")
	public void addReplyMessage(HttpServletResponse response,ApplicationReplyMessage reply){
		if(reply.getMessageId() == null){
			output(response,JsonUtil.buildFalseJson("1","回复失败：参数有误"));
			return;
		}
		if(reply.getReplyUserId() == null){
			output(response,JsonUtil.buildFalseJson("1","回复失败：参数有误"));
			return;
		}
		if(reply.getContent()==null && reply.getContent().equals("")){
			output(response,JsonUtil.buildFalseJson("1","请输入回复内容"));
			return;
		}
		ApplicationReplyMessage message = replyMessageService.isAlreadyReply(reply.getLoginPlat(),reply.getMessageId());
		if(message != null){
			output(response,JsonUtil.buildFalseJson("1","该条咨询已做出回应,无需重复回复"));	
			return;
		}
		//获取回复者昵称
		UserCommon userCommon = userService.getUserByUserId(reply.getReplyUserId());
		reply.setReplyNickName(userCommon.getNickName());
		reply.setCreateTime(new Date());
		try {
			replyMessageService.addReplyInfo(reply);
			log.info("用户"+reply.getReplyUserId()+"给咨询"+reply.getMessageId()+"回复成功");
			output(response,JsonUtil.buildFalseJson("0","回复成功"));
		} catch (Exception e) {
			log.info("用户"+reply.getReplyUserId()+"给咨询"+reply.getMessageId()+"回复失败");
			output(response, JsonUtil.buildFalseJson("1", "回复失败"));
		}
	}
	
	/**
	 * 前端假删除咨询
	 * @param response
	 * @param messageId
	 */
	@RequestMapping("/deletemessage")
	public void deleteMessage(HttpServletResponse response,Integer messageId){
	   if(messageId == null){	
		   output(response,JsonUtil.buildFalseJson("1","删除咨询失败:传递参数有误"));
		   return;
	   }
	   try {
		   almService.updateIsEffect(messageId);
           log.info("删除咨询成功");
           output(response,JsonUtil.buildFalseJson("0","删除咨询成功"));
		} catch (Exception e) {
			 log.info("删除咨询失败");
	         output(response,JsonUtil.buildFalseJson("1","删除咨询失败"));
		}
	}
	
	// 查看用户是该社区或应用的管理员
	@RequestMapping("/checkIsManage")
	public void checkIsManage(HttpServletResponse response, Integer loginPlat, Integer userId) {
		ApplicationManage manage = appManageService.getIsManageUser(userId, loginPlat, 1);
		if (manage != null) {
			List<ApplicationManage> manageList = new ArrayList<ApplicationManage>();
			manageList.add(manage);
			output(response, JsonUtil.buildJson(manageList));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "您没有管理该应用的权限!"));
		}
	}

	// 检测应用是否已过期
//	@RequestMapping("/testingAppIsExpire")
//	public void testingAppIsExpire(HttpServletResponse response, Integer userId) {
//		List<ApplicationCommon> appList = appService.getAppIsExpire(userId);
//		if (appList == null || appList.isEmpty()) {
//			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
//		} else {
//			output(response, JsonUtil.buildJson(appList));
//		}
//	}

	// 应用统计
	@RequestMapping("/statisticsVisitors")
	public void appStatistics(HttpServletResponse response, Integer loginPlt, int count, Integer userId) {
		ApplicationCommon app = appService.getApplicationById(loginPlt);
		app.setAppUpdateTime(new Date());
		app.setVisitorCount(count);
		appService.updateApplicationStatus(app);
		UserBrowseRecord record = userBrowseRecordService.getUserBrowseUserById(userId, app.getParentId(), loginPlt,
				null, null);
		if (record == null) {
			record = new UserBrowseRecord();
			record.setBrowseCount(1);
			record.setBrowseTime(new Date());
			record.setUserId(userId);
			record.setLoginPlat(loginPlt);
			record.setPlatformId(app.getParentId());
			userBrowseRecordService.addUserBrowseRecord(record);
		} else {
			record.setBrowseCount(record.getBrowseCount() + 1);
			record.setId(record.getId());
			userBrowseRecordService.updateUserBrowseRecord(record);
		}
		output(response, JsonUtil.buildFalseJson("0", "访客量增加成功!"));
	}

	// 生成应用的二维码
	@RequestMapping("/addAppCode")
	public synchronized void addAppCode(HttpServletResponse response, Integer loginPlat, String path, HttpServletRequest request) throws Exception {
		String respJson = null;
		ApplicationCommon appl = appService.getApplicationsByCriteria(loginPlat);
		if (appl.getParentId() == null) {
			appl.setParentId(loginPlat);
		}
		ApplicationCode code = applicationCodeService.findLoginPlat(loginPlat, appl.getParentId());
		if (code != null) {
			List<ApplicationCode> codeLiat = new ArrayList<ApplicationCode>();
			codeLiat.add(code);
			log.info("该应用已有二维码直接返回二维码：" + codeLiat);
			output(response, JsonUtil.buildJson(codeLiat));
		} else {
			try {
				log.info("进入生成二维码前");
				if (appl.getAppId() == null && appl.getAppSecret() == null) {
					ApplicationCommon app = appService.getApplicationsByCriteria(appl.getParentId());
					log.info("重新赋值appId:"+app.getAppId());
					log.info("重新赋值appSecret:"+app.getAppSecret());
					appl.setAppId(app.getAppId());
					appl.setAppSecret(app.getAppSecret());
				}
				// 根据appId,appSecret拿到token
				String authJson = WeiXinSessionUtils.getAccessToken(appl.getAppId(), appl.getAppSecret());
				respJson = ErWeiCodeImageContoller.upload(authJson, path, request);
				log.info("返回下载二维码图片路径：" + respJson);
				// 记录对应应用的二维码
				code = new ApplicationCode();
				code.setCreateTime(new Date());
				if (appl.getParentId() != null) {
					code.setLoginPlat(appl.getParentId());
				} else {
					code.setLoginPlat(loginPlat);
				}
				code.setLoginSubplat(loginPlat);
				code.setPath(path);
				code.setDownloadUrl(respJson);
				applicationCodeService.addApplicationCommon(code);
				ApplicationCode appcode = applicationCodeService.getApplicationCodeById(code.getCodeId());
				List<ApplicationCode> codeLiat = new ArrayList<ApplicationCode>();
				codeLiat.add(appcode);
				log.info("拿到对应的Code的值：" + codeLiat);
				output(response, JsonUtil.buildJson(codeLiat));
			} catch (Exception e) {
				log.info("生成二维码失败,失败原因 = " + e);
				output(response, JsonUtil.buildFalseJson("1", "生成二维码失败!"));
			}
		}
	}
	//获取应用token值和openId
	@RequestMapping("/gettoken")
	public  void getToken(HttpServletResponse response,Integer loginPlatId,Integer userId){
	    //找出该应用所对应得appId,appSecret,如果为空则是百享园的
		String appId = "";
		String appSecret = "";
	    ApplicationCommon appinfo = appService.getApplicationCommonById(loginPlatId);
	    UserCommon userinfo = userService.getUserByUserId(userId);
	    if(userinfo == null){
	    	log.info("该用户不存在");
	    	output(response,JsonUtil.buildFalseJson("1","非法用户"));
	    	return;
	    }
	    if(userinfo.getOpenId() == null){
	    	log.info("用户未授权或登录失败:为获取到openId");
	    	output(response,JsonUtil.buildFalseJson("1","非法用户：用户openId为空"));
	    	return;
	    }
	    if(appinfo.getAppId() == null || appinfo.getAppId().equals("")){
	    	//百享园
	    	 appId = "wx474aed7d8ee915d9";
	    	 appSecret = "9b4dcb4fd032ae6cef2c25aae0aa1129";
	    }else{
	    	//直接获取应用appid和appse
	    	 appId = appinfo.getAppId();
	    	 appSecret = appinfo.getAppSecret();
	    }
	    //获取access_token
	    String access_token = WeiXinSessionUtils.getAccessToken(appId,appSecret);
	    //System.out.println(access_token);
	    Map<String,Object> map = new HashMap<String,Object>();
	    map.put("access_token",access_token);
	    map.put("openId",userinfo.getOpenId());
	    map.put("createTime",getStringDate());
	    output(response,JsonUtil.objectToJson(map));	           	
	}
	
	public static String getStringDate() {  
		  Date currentTime = new Date();
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String dateString = formatter.format(currentTime);
		  return dateString; 
	}
	
	/**
	 * 测试模板推送 
	 * @param response
	 * @param data
	 * @param loginPlat
	 */
	@RequestMapping("/messagepush")
	public void MessagePush(HttpServletResponse response,String data,Integer loginPlat){
	   ApplicationCommon appinfo = appService.getApplicationCommonById(loginPlat);
	   String appId = "";
	   String appSecret = "";
	   if(appinfo.getAppId() == null || appinfo.getAppId().equals("")){
	    	//百享园
	    	 appId = "wx474aed7d8ee915d9";
	    	 appSecret = "9b4dcb4fd032ae6cef2c25aae0aa1129";
	    }else{
	    	//直接获取应用appid和appse
	    	 appId = appinfo.getAppId();
	    	 appSecret = appinfo.getAppSecret();
	    }
	    //获取access_token
	   String access_token = WeiXinSessionUtils.getAccessToken(appId,appSecret);
	   HttpPost.sendPost("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+access_token,data);
       try {
    	   output(response,JsonUtil.buildFalseJson("0","发送模板成功"));
		} catch (Exception e) {
	       output(response,JsonUtil.buildFalseJson("1","发送模板失败"));
		}
	}
		
	
}
