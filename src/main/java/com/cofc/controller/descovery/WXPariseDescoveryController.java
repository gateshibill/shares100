package com.cofc.controller.descovery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.DescoveryCommon;
import com.cofc.pojo.DescoveryLeaveMessage;
import com.cofc.pojo.DescoveryParise;
import com.cofc.pojo.UserCollection;
import com.cofc.service.DescoveryCommonService;
import com.cofc.service.DescoveryLeaveMessageService;
import com.cofc.service.DescoveryPariseService;
import com.cofc.service.UserCollectionService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/descoery")
public class WXPariseDescoveryController extends BaseUtil {
	@Resource
	private DescoveryCommonService decommonService;
	@Resource
	private DescoveryPariseService dpariseService;
	@Resource
	private DescoveryLeaveMessageService dleavemsgService;
	@Resource
	private UserCollectionService collectionService;
	@Resource
	private SqlSessionFactory sqlSessionFactory;

	public static Logger log = Logger.getLogger("WXPariseDescoveryController");

	/**
	 * 前端点赞接口
	 * @param response
	 * @param userId
	 * @param descoveryId
	 */
	@RequestMapping("/pariseDescovery")
	public void userPariseDescovery(HttpServletResponse response, Integer userId, Integer descoveryId) {
		if(userId == null){
			output(response,JsonUtil.buildFalseJson("1","请先登录"));
			return;
		}
		if(descoveryId == null){
			output(response,JsonUtil.buildFalseJson("1", "非法数据"));
			return;
		}
		// 先找到该用户有没有点赞
		DescoveryParise parise = dpariseService.comfirmUserParisedDescovery(userId, descoveryId,null);
		DescoveryCommon thisdc = decommonService.getDescoveryById(descoveryId);
		if (parise == null) {
			try {
				parise = new DescoveryParise();
				parise.setParisedUserId(userId);
				parise.setIsCancel(0);
				parise.setParisedDescoveryId(descoveryId);
				parise.setPariseTime(new Date());
				dpariseService.userPariseDescovery(parise);
				thisdc.setPariseCount(thisdc.getPariseCount() + 1);
				decommonService.updateDescoveryCommonInfo(thisdc);
				output(response, JsonUtil.buildFalseJson("0", "点赞成功!"));
			} catch (Exception e) {
				output(response, JsonUtil.buildFalseJson("2", "对不起，点赞失败！"));
				log.info("用户" + userId + "点赞'发现'" + descoveryId + "失败，失败原因" + e);
			}
		} else {
			if (parise.getIsCancel() == 0) {// 该点赞未取消，触发接口则取消点赞
				try {
					parise.setIsCancel(1);
					parise.setCancelTime(new Date());
					dpariseService.updatePariseStatus(parise);
					thisdc.setPariseCount(thisdc.getPariseCount() - 1);
					decommonService.updateDescoveryCommonInfo(thisdc);
					output(response, JsonUtil.buildFalseJson("1", "取消点赞成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("3", "对不起，取消点赞失败！"));
					log.info("用户" + userId + "取消点赞'发现'" + descoveryId + "失败，失败原因" + e);
				}
			} else {
				try {
					parise.setIsCancel(0);
					parise.setPariseTime(new Date());
					dpariseService.updatePariseStatus(parise);
					thisdc.setPariseCount(thisdc.getPariseCount() + 1);
					decommonService.updateDescoveryCommonInfo(thisdc);
					output(response, JsonUtil.buildFalseJson("0", "点赞成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("2", "对不起，点赞失败！"));
					log.info("用户" + userId + "点赞'发现'" + descoveryId + "失败，失败原因" + e);
				}
			}
		}
	}

	/**
	 * 
	 * @param response
	 * @param userId
	 * 留言者的用户id
	 * @param messageContent
	 * 留言内容
	 */
	// 留言
	@RequestMapping("/leaveMsg")
	public void leaveMessageToDescovery(HttpServletResponse response, Integer userId, Integer descoveryId,
			String messageContent, String messageImage, Integer parentId) {
		DescoveryLeaveMessage leavemsg = new DescoveryLeaveMessage();
		try {
			if(messageContent == null || messageContent.equals("")){
				output(response,JsonUtil.buildFalseJson("1", "评论内容不能为空"));
			}else{
				if(userId == null){
					output(response,JsonUtil.buildFalseJson("1", "请先登录"));
					return;
				}
				if(descoveryId == null){
					output(response, JsonUtil.buildFalseJson("1", "参数非法"));
					return;
				}
				leavemsg.setLeaveTime(new Date());
				leavemsg.setDescoveryId(descoveryId);
				leavemsg.setLeaveUserId(userId);
				leavemsg.setParentId(parentId);
				leavemsg.setMessageContent(messageContent);
				leavemsg.setMessageImage(messageImage);
				dleavemsgService.newLeaveNewMessage(leavemsg);
				output(response, JsonUtil.buildFalseJson("0", "留言成功!"));
			}
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "留言失败!"));
		}
	}

	// 获取留言列表
	@RequestMapping("/leaveMsgList")
	public void findLeaveMessagesList(HttpServletResponse response, Integer descoveryId, Integer pageNo,
			Integer pageSize) {
		// SqlSession session = sqlSessionFactory.openSession();
		// DescoveryLeaveMessageService dleavemsgService =
		// session.getMapper(DescoveryLeaveMessageService.class);
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 5;
		}
		List<DescoveryLeaveMessage> returnList = new ArrayList<DescoveryLeaveMessage>();
		List<DescoveryLeaveMessage> leaveMsgList = dleavemsgService.findLeavemsgByDescoveryId(descoveryId,
				(pageNo - 1) * pageSize, pageSize);
		for (DescoveryLeaveMessage lm : leaveMsgList) {
			List<DescoveryLeaveMessage> addedmsg = dleavemsgService.getAddedLeaveMsg(lm.getMessageId());
			lm.setAddedMessage(addedmsg);
			returnList.add(lm);
		}
		if (returnList != null && !returnList.isEmpty()) {
			output(response, JsonUtil.buildJson(returnList));
		}else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 收藏或取消收藏
	@RequestMapping("/collect")
	private void userCollectDescovery(HttpServletResponse response, Integer descoveryId, Integer userId,
			Integer loginPlat) {
		DescoveryCommon descovery = decommonService.getDescoveryById(descoveryId);
		if (descovery == null) {
			output(response, JsonUtil.buildFalseJson("4", "对不起,该发现不存在或已删除!"));
		} else {
			UserCollection collection = collectionService.comfirmUserCollected(descoveryId, null, userId,
					loginPlat);
			if (collection == null) {
				try {
					collection = new UserCollection();
					collection.setCreateTime(new Date());
					collection.setDescoveryId(descoveryId);
					collection.setIsCancel(0);
					collection.setUserId(userId);
					collection.setLoginPlat(loginPlat);
					collectionService.addNewCollection(collection);
					descovery.setCollectCount(descovery.getCollectCount() + 1);
					decommonService.updateDescoveryCommonInfo(descovery);
					output(response, JsonUtil.buildFalseJson("0", "收藏成功"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("2", "对不起，收藏失败！"));
					log.info("用户" + userId + "点赞'收藏'" + descoveryId + "失败，失败原因" + e);
				}
			} else {
				if (collection.getIsCancel() == 0) {// 收藏过，未取消
					try {
						collection.setIsCancel(1);
						collection.setCreateTime(new Date());
						collectionService.cancelCollection(collection);
						descovery.setCollectCount(descovery.getCollectCount() - 1);
						decommonService.updateDescoveryCommonInfo(descovery);
						output(response, JsonUtil.buildFalseJson("1", "取消收藏成功"));
					} catch (Exception e) {
						output(response, JsonUtil.buildFalseJson("3", "对不起，取消收藏失败！"));
						log.info("用户" + userId + "取消收藏'发现'" + descoveryId + "失败，失败原因" + e);
					}
				} else {
					try {
						collection.setIsCancel(0);
						collection.setCreateTime(new Date());
						collectionService.cancelCollection(collection);
						descovery.setCollectCount(descovery.getCollectCount() + 1);
						decommonService.updateDescoveryCommonInfo(descovery);
						output(response, JsonUtil.buildFalseJson("0", "收藏成功"));
					} catch (Exception e) {
						output(response, JsonUtil.buildFalseJson("2", "对不起，收藏失败！"));
						log.info("用户" + userId + "收藏'发现'" + descoveryId + "失败，失败原因" + e);
					}
				}
			}
		}
	}

	// 查看我的收藏{分类两类，商品和‘发现’，先展示发现}
	@RequestMapping("/myCollection")
	public void showMyCollections(HttpServletResponse response, Integer userId, Integer pageNo, Integer pageSize,
			Integer loginPlat) {
		List<UserCollection> collist = collectionService.findMyDescoveryCollection((pageNo - 1) * pageSize, pageSize,
				loginPlat);
		output(response, JsonUtil.buildJson(collist));
	}
}
