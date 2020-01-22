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
import com.cofc.pojo.PushNotice;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.PushNoticeService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/pushnotice")
public class PushNoticeController extends BaseUtil {
	@Resource
	private PushNoticeService noticeService;
	@Resource
	private ApplicationCommonService appService;
	public static Logger log = Logger.getLogger("PushNoticeController");

	/**
	 * 进入设置推送列表页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/gopushnoticelist")
	public ModelAndView goPushNoticeList(HttpServletRequest request, ModelAndView mv) {
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
		mv.setViewName("applicationManage/pushnoticeList");
		return mv;
	}

	/**
	 * 获取列表
	 * 
	 * @param request
	 * @param response
	 * @param notice
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getpushnoticelist")
	public void getPushNoticeList(HttpServletRequest request, HttpServletResponse response, PushNotice notice,
			Integer page, Integer limit) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 15;
		}
		int rowcount = 0;
		List<PushNotice> lists = new ArrayList<PushNotice>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			rowcount = noticeService.getNoticeCount(notice);
			lists = noticeService.getNoticeList(notice,(page-1) * limit, limit);
		}else{
			if(notice.getLoginPlat() == null){
				 String[] idStrings = bu.getLoginPlat().split(",");
				 List<String> loginPlatList = Arrays.asList(idStrings);
				 rowcount = noticeService.getNoticeCountByLoginPlat(loginPlatList, notice);
				 lists = noticeService.getNoticeListByLoginPlat(loginPlatList, notice,(page-1) * limit, limit);
			}else{
				rowcount = noticeService.getNoticeCount(notice);
				lists = noticeService.getNoticeList(notice,(page-1) * limit, limit);
			}
		}
		output(response,JsonUtil.buildJsonByTotalCount(lists, rowcount));
	}

	/**
	 * 进入添加页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goaddnotice")
	public ModelAndView goAddNotice(HttpServletRequest request, ModelAndView mv,Integer loginPlat,Integer noticeType) {
		mv.addObject("loginPlat",loginPlat);
		mv.addObject("noticeType",noticeType);
		mv.setViewName("applicationManage/addpushnotice");
		return mv;
	}

	/**
	 * 执行添加操作
	 * 
	 * @param response
	 * @param notice
	 */
	@RequestMapping("/doaddnotice")
	public void doAddNotice(HttpServletResponse response, PushNotice notice) {
        if(notice.getLoginPlat() == null){
        	output(response,JsonUtil.buildFalseJson("1","应用id不能为空"));
        	return;
        }
        int count = 0;
        if(notice.getNoticeType() == 1){
        	count = noticeService.checkUserForNotice(notice.getLoginPlat(),
        			notice.getUserId(),null,1);
        }else{
        	count = noticeService.checkUserForNotice(notice.getLoginPlat(),
        			null,notice.getOpenId(),2);
        }
        if(count > 0){
        	output(response,JsonUtil.buildFalseJson("1","系统检测到已存在相同的记录，无需重复添加"));
        	return;
        }
        notice.setCreateTime(new Date());
        noticeService.addNoticeInfo(notice);
        try {
        	log.info("添加成功");
			output(response,JsonUtil.buildFalseJson("0","添加成功"));
		} catch (Exception e) {
			log.info("添加失败,失败原因："+e);
			output(response,JsonUtil.buildFalseJson("1","添加失败"));
		}
	}

	/**
	 * 进入修改页面
	 * 
	 * @param request
	 * @param mv
	 * @param noticeId
	 * @return
	 */
	@RequestMapping("/goupdatenotice")
	public ModelAndView goUpdateNotice(HttpServletRequest request, ModelAndView mv, Integer noticeId) {
		PushNotice notice = noticeService.getNoticeDetail(noticeId);
		mv.addObject("notice",notice);
		mv.setViewName("applicationManage/updatepushnotice");
		return mv;
	}

	/**
	 * 执行修改操作
	 * 
	 * @param response
	 * @param notice
	 */
	@RequestMapping("/doupdatenotice")
	public void doUpdateNotice(HttpServletResponse response, PushNotice notice) {
       if(notice.getNoticeId() == null){
    	   output(response,JsonUtil.buildFalseJson("1","id不能为空"));
    	   return;
       }
       noticeService.updateNoticeInfo(notice);
       try {
    	  log.info("修改成功"); 
		  output(response,JsonUtil.buildFalseJson("0","修改成功"));
	   } catch (Exception e) {
		  log.info("修改失败,失败原因："+e);
		  output(response,JsonUtil.buildFalseJson("1","修改失败"));
	   }
	}

	/**
	 * 执行删除操作
	 * 
	 * @param response
	 * @param noticeId
	 */
	@RequestMapping("/delnotice")
	public void delNotice(HttpServletResponse response, Integer noticeId) {
        if(noticeId == null){
        	output(response,JsonUtil.buildFalseJson("1","传递参数有误"));
        	return;
        }else{
        	noticeService.delelteNotice(noticeId);
        	try {
        		log.info("删除成功");
				output(response,JsonUtil.buildFalseJson("0","删除成功"));
			} catch (Exception e) {
				log.info("删除失败,失败原因："+e);
				output(response,JsonUtil.buildFalseJson("1","删除失败"));
			}
        }
	}
}
