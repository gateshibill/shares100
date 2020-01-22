package com.cofc.controller.pushmessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.PushMessage;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.PushMessageService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/pushmessage")
public class PushMessageController extends BaseUtil {
   @Resource
   private PushMessageService pushService;
   @Resource
   private ApplicationCommonService appService;
   public static Logger log = Logger.getLogger("PushMessageController");
   /**
    * 进入推送模板页面
    * @param request
    * @param mv
    * @return
    */
   @RequestMapping("/gopushmessagepage")
   public ModelAndView goPushMessagePage(HttpServletRequest request,ModelAndView mv){
	   BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
	   List<ApplicationCommon> appList = null;
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = appService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = appService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("appList", appList);
		mv.setViewName("applicationManage/pushList");
	    return mv;
   }
   /**
    * 进入添加模板页面
    * @param request
    * @param mv
    * @return
    */
   @RequestMapping("/goaddtemplate")
   public ModelAndView goAddTemplate(HttpServletRequest request,ModelAndView mv,Integer loginPlat){
	   mv.addObject("loginPlat",loginPlat);
	   mv.setViewName("applicationManage/addpush");
	   return mv;
   }
   /**
    * 进入编辑模板页面
    * @param request
    * @param mv
    * @param id
    * @return
    */
   @RequestMapping("/goedittemplate")
   public ModelAndView goEditTemplate(HttpServletRequest request,ModelAndView mv,Integer id){
	   PushMessage push = pushService.getTemplateDetail(id);
	   mv.addObject("push",push);
	   mv.setViewName("applicationManage/updatepush");
	   return mv;
   }
   @RequestMapping("/gettemplatelist")
   public void getTemplateList(HttpServletRequest request,HttpServletResponse response,PushMessage push,Integer page,Integer limit){
	   BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
	   if(page == null){
		   page = 1;
	   }
	   if(limit == null){
		   limit = 15;
	   }
	   int rowcount = 0;
	   List<PushMessage> lists = new ArrayList<PushMessage>();
	   if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
		   rowcount = pushService.getTemplateCount(push);
		   lists = pushService.getTemplateList(push,(page-1) * limit, limit);
	   }else{
		   if(push.getLoginPlat() == null){
			   String[] idStrings = bu.getLoginPlat().split(",");
			   List<String> loginPlatList = Arrays.asList(idStrings);
			   rowcount = pushService.getTemplateCountByLoginPlat(loginPlatList, push);
			   lists = pushService.getTemplateListByLoginPlat(loginPlatList, push,(page-1) * limit, limit);
		   }else{
			   rowcount = pushService.getTemplateCount(push);
			   lists = pushService.getTemplateList(push,(page-1) * limit, limit);
		   }
	   }
	   output(response,JsonUtil.buildJsonByTotalCount(lists, rowcount));
   }
   @RequestMapping("/doaddpushmessage")
   public void doAddPushMessage(HttpServletResponse response,PushMessage push){
	    if(push.getLoginPlat() == null){
	    	output(response,JsonUtil.buildFalseJson("1","应用id不能为空"));
	    	return;
	    }
	    if(push.getTemplateId()== null || push.getTemplateId().equals("")){
	    	output(response,JsonUtil.buildFalseJson("1","模板id不能为空"));
            return;
	    }
	    if(push.getTempType() == null){
	    	output(response,JsonUtil.buildFalseJson("1","模板类型不能为空"));
	    	return;
	    }
	    PushMessage message = pushService.getTemplateInfo(push.getLoginPlat(),push.getTempType());
	    if(message != null){
	    	output(response,JsonUtil.buildFalseJson("1","该应用下已存在该类型的推送模板"));
	    	return;
	    }
	    push.setIsRemove(0);
	    push.setCreateTime(new Date());
	    pushService.addTemplateInfo(push);
	    try {
	    	log.info("添加模板成功");
			output(response,JsonUtil.buildFalseJson("0","添加模板成功"));
		} catch (Exception e) {
			log.info("添加模板失败,失败原因："+e);
	    	output(response,JsonUtil.buildFalseJson("1","添加模板失败"));
		}
   }
   @RequestMapping("/doupdatetemplate")
   public void doUpdateTemplate(HttpServletResponse response,PushMessage push){
	   int count = pushService.isAreadyTemplateCount(push.getLoginPlat(),push.getTempType(),push.getId());
	   if(count > 0){
		   output(response,JsonUtil.buildFalseJson("1","该应用下已存在该类型的推送模板"));
	       return;
	   }
	   pushService.updateTemplateInfo(push);
	   try {
		   log.info("编辑模板成功");
			output(response,JsonUtil.buildFalseJson("0","编辑模板成功"));
	   } catch (Exception e) {
		   log.info("编辑模板失败，失败原因："+e);
		   output(response,JsonUtil.buildFalseJson("1","编辑模板失败"));
	   }
   }
   @RequestMapping("/deltemplate")
   public void delTemplate(HttpServletResponse response,Integer id){
	   if(id == null){
		   output(response,JsonUtil.buildFalseJson("1","id为空"));
		   return;
	   }
	   pushService.delTemplateInfo(id);
	   try {
		   log.info("删除模板成功"); 
		   output(response,JsonUtil.buildFalseJson("0","删除模板成功"));
		} catch (Exception e) {
		   log.info("删除模板失败，失败原因："+e);
		   output(response,JsonUtil.buildFalseJson("1","删除模板失败"));
		}
   }
}
