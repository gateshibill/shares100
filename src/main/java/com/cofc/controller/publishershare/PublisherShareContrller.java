package com.cofc.controller.publishershare;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.PublisherShare;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.PublisherShareService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/share")
public class PublisherShareContrller extends BaseUtil{

	@Resource
	private PublisherShareService shareService;
	@Resource
	private ApplicationCommonService appService;
	
	@RequestMapping("/publisherShareList")
	public ModelAndView publisherShareList(ModelAndView mView){
		mView.setViewName("publisherShareManage/shareList");
		return mView;
	}
	
	@RequestMapping("/showShareList")
	public void showShareList(HttpServletResponse response,Integer page,Integer limit,Integer shareUserId){
		if (page == null) {
			page = 1;
		}
		if(limit == null){
			limit = 20;
		}
		List<PublisherShare> shareList = shareService.findPublisherShareList(shareUserId,(page-1)*limit,limit);
		int count = shareService.getPublisherShareCount(shareUserId);
		output(response, JsonUtil.buildJsonByTotalCount(shareList, count));
	}
	
	@RequestMapping("/deleteShare")
	public void deleteShare(HttpServletResponse response,Integer shareId){
		try {
			shareService.deletePublisherShare(shareId);
			output(response, JsonUtil.buildFalseJson("0", "删除共享成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "删除共享失败!"));
		}
	}
	
	@RequestMapping("/goAddShare")
	public ModelAndView goAddShare(ModelAndView mView){
		List<ApplicationCommon> appList = appService.findApplicationsByCriteria(null, null, null, null, null, null, null, null, null,null, null);
		mView.addObject("appList", appList);
		mView.setViewName("publisherShareManage/addShare");
		return mView;
	}
	
	@RequestMapping("/addPublisherShare")
	public void addPublisherShare(HttpServletResponse response,PublisherShare share,HttpServletRequest request){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		share.setShareTime(new Date());
		share.setShareUserId(bu.getUserId());
		shareService.addPublisherShare(share);
		output(response, JsonUtil.buildFalseJson("0", "添加共享成功!"));
	}
	@RequestMapping("/updateShare")
	public void updateShare(HttpServletResponse response,PublisherShare share){
		share.setUpdateTime(new Date());
		shareService.updatePublisherShare(share);
		output(response, JsonUtil.buildFalseJson("0", "修改共享成功!"));
	}
	@RequestMapping("/goSeeShare")
	public ModelAndView goSeeShare(ModelAndView mAndView,Integer shareId){
		PublisherShare share = shareService.getShareById(shareId);
		mAndView.addObject("share", share);
		mAndView.setViewName("publisherShareManage/updateShare");
		return mAndView;
	}
}
