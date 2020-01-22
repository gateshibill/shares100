package com.cofc.controller.publishershare;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cofc.pojo.PublisherShare;
import com.cofc.pojo.PublisherShareCollection;
import com.cofc.pojo.PublisherShareParise;
import com.cofc.pojo.ShareLeaveMessage;
import com.cofc.pojo.UserCommon;
import com.cofc.service.PublisherShareCollectionService;
import com.cofc.service.PublisherSharePariseService;
import com.cofc.service.PublisherShareService;
import com.cofc.service.ShareLeaveMessageService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/share")
public class WXPublisherShareController extends BaseUtil{

	@Resource
	private PublisherShareService shareService;
	@Resource
	private ShareLeaveMessageService messageService;
	@Resource
	private PublisherSharePariseService pariseService;
	@Resource
	private UserCommonService userService;
	@Resource
	private PublisherShareCollectionService collectionService;
	
	//前端发布共享
	@RequestMapping("/publisherShare")
	public void publisherShare(HttpServletResponse response,PublisherShare share){
		share.setShareTime(new Date());
		shareService.addPublisherShare(share);
		output(response, JsonUtil.buildFalseJson("0", "发布成功!"));
	}
	
	@RequestMapping("/shareList")
	public void shareList(HttpServletResponse response,Integer loginPlat,Integer pageNo,Integer pageSize){
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<PublisherShare> shareList = shareService.getMyPublisherShareList(loginPlat,(pageNo-1)*pageSize,pageSize);
		output(response, JsonUtil.buildJson(shareList));
	}
	
	@RequestMapping("/updateShare")
	public void updateShare(HttpServletResponse response,PublisherShare share){
		share.setUpdateTime(new Date());
		shareService.updatePublisherShare(share);
		output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
	}
	
	//添加共享留言
	@RequestMapping("/addLeaveMessage")
	public void addLeaveMessage(HttpServletResponse response,ShareLeaveMessage message){
		message.setMessageTime(new Date());
		messageService.addShareLeaveMessage(message);
		output(response, JsonUtil.buildFalseJson("0", "留言成功!"));
	}
	
	@RequestMapping("/leaveMessageList")
	public void leaveMessageList(HttpServletResponse response,Integer shareId,Integer pageNo,Integer pageSize){
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<ShareLeaveMessage> messageList = messageService.getShareLeaveMessageList(shareId,(pageNo-1)*pageSize,pageSize);
		output(response, JsonUtil.buildJson(messageList));
	}
	
	//用户(点赞、取消)共享
	@RequestMapping("/addShareParise")
	public void addShareParise(HttpServletResponse response, PublisherShareParise parise) {
		PublisherShareParise shareParise = pariseService.getUserIsParise(parise.getUserId(), parise.getShareId());
		PublisherShare share = shareService.getShareById(parise.getShareId());
		if (shareParise == null) {// 该用户没有点赞共享
			parise.setPariseTime(new Date());
			parise.setPariseStatus(1);
			pariseService.addPublisherShareParise(parise);
			//往共享的点赞数加一
			share.setUpdateTime(new Date());
			share.setPraiseCount(share.getPraiseCount()+1);
			shareService.updatePublisherShare(share);
			output(response, JsonUtil.buildFalseJson("0", "点赞成功!"));
		} else {// 该用户已点赞共享
			if (shareParise.getPariseStatus() == 1) {// 该用户目前已点赞，再点就取消点赞状态
				shareParise.setUpdateTime(new Date());
				shareParise.setPariseStatus(2);
				shareParise.setPariseId(shareParise.getPariseId());
				pariseService.updatePublisherShareParise(shareParise);
				//往共享的点赞数减一
				share.setUpdateTime(new Date());
				share.setPraiseCount(share.getPraiseCount()-1);
				shareService.updatePublisherShare(share);
				output(response, JsonUtil.buildFalseJson("0", "取消点赞成功!"));
			} else {//该用户目前是取消点赞，再点击变成点赞状态
				shareParise.setUpdateTime(new Date());
				shareParise.setPariseStatus(1);
				shareParise.setPariseId(shareParise.getPariseId());
				pariseService.updatePublisherShareParise(shareParise);
				//往共享的点赞数加一
				share.setUpdateTime(new Date());
				share.setPraiseCount(share.getPraiseCount()+1);
				shareService.updatePublisherShare(share);
				output(response, JsonUtil.buildFalseJson("0", "点赞成功!"));
			}
		}
	}
	
	//共享详情
	@RequestMapping("/shareDetails")
	public void shareDetails(HttpServletResponse response,Integer shareId,Integer loginPlat){
		PublisherShare share = shareService.getPublisherShareLoginPlatById(loginPlat,shareId);
		UserCommon user = userService.getUserByUserId(share.getShareUserId());
		List<PublisherShare> shareList =new ArrayList<PublisherShare>();
		share.setPublisherName(user.getNickName());
		shareList.add(share);
		output(response, JsonUtil.buildJson(shareList));
	}
	
	//共享阅读数
	@RequestMapping("/shareReadCount")
	public void shareReadCount(HttpServletResponse response,Integer shareId){
		PublisherShare share = shareService.getShareById(shareId);
		if (share == null) {
			output(response, JsonUtil.buildFalseJson("1", "该共享已不存在!"));
		} else {
			share.setUpdateTime(new Date());
			share.setReadCount(share.getReadCount()+1);
			shareService.updatePublisherShare(share);
			output(response, JsonUtil.buildFalseJson("0", "阅读成功!"));
		}
	}
	
	//共享收藏，取消收藏接口
	@RequestMapping("/userSharecollection")
	public void userSharecollection(HttpServletResponse response,PublisherShareCollection collection){
		PublisherShareCollection collection2 = collectionService.getUserisCollection(collection.getUserId(),collection.getShareId());
		PublisherShare share = shareService.getShareById(collection.getShareId());
		if (collection2 == null) {//没有收藏该共享的记录
			collection.setCollectionTime(new Date());
			collection.setIsCollection(1);
			collectionService.addPublisherShareCollection(collection);
			//收藏成功后共享的收藏数加一
			share.setUpdateTime(new Date());
			share.setCollectionCount(share.getCollectionCount()+1);
			share.setShareId(share.getShareId());
			shareService.updatePublisherShare(share);
			output(response, JsonUtil.buildFalseJson("0", "收藏成功!"));
		} else {
			if (collection2.getIsCollection() == 1) {//已收藏该共享，则取消收藏
				collection2.setUpdateTime(new Date());
				collection2.setIsCollection(2);
				collectionService.updateIsCollection(collection2);
				//取消收藏成功后共享的收藏数减一
				share.setUpdateTime(new Date());
				share.setShareId(share.getShareId());
				share.setCollectionCount(share.getCollectionCount()-1);
				shareService.updatePublisherShare(share);
				output(response, JsonUtil.buildFalseJson("0", "取消收藏成功!"));
			}else {
				collection2.setUpdateTime(new Date());
				collection2.setIsCollection(1);
				collectionService.updateIsCollection(collection2);
				//收藏成功后共享的收藏数加一
				share.setUpdateTime(new Date());
				share.setShareId(share.getShareId());
				share.setCollectionCount(share.getCollectionCount()+1);
				shareService.updatePublisherShare(share);
				output(response, JsonUtil.buildFalseJson("0", "收藏成功!"));
			}
		}
	}
}
