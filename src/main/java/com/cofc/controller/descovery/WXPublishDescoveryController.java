package com.cofc.controller.descovery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.DescoveryCommon;
import com.cofc.pojo.DescoveryLeaveMessage;
import com.cofc.pojo.DescoveryParise;
import com.cofc.pojo.DescoveryReader;
import com.cofc.pojo.ProductTags;
import com.cofc.pojo.ProjectTags;
import com.cofc.pojo.ShareContract;
import com.cofc.pojo.SkillTags;
import com.cofc.pojo.TagCommon;
import com.cofc.pojo.TaskCommon;
import com.cofc.pojo.UserCollection;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.WantedTags;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.DescoveryCommonService;
import com.cofc.service.DescoveryLeaveMessageService;
import com.cofc.service.DescoveryPariseService;
import com.cofc.service.DescoveryReaderService;
import com.cofc.service.ProductTagsService;
import com.cofc.service.ProjectTagsService;
import com.cofc.service.RecommendService;
import com.cofc.service.ShareContractService;
import com.cofc.service.SkillTagsService;
import com.cofc.service.TagCommonService;
import com.cofc.service.TaskCommonService;
import com.cofc.service.UserCollectionService;
import com.cofc.service.UserCommonService;
import com.cofc.service.WantedTagsService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/descovery")
public class WXPublishDescoveryController extends BaseUtil {
	

	@Resource
	private UserCommonService userService;
	@Resource
	private TagCommonService tagcService;
	@Resource
	private DescoveryCommonService dcService;
	@Resource
	private ProjectTagsService pjtagService;
	@Resource
	private ProductTagsService pdtagService;
	@Resource
	private SkillTagsService skilltagService;
	@Resource
	private WantedTagsService wantedtagService;
	@Resource
	private DescoveryReaderService dreaderService;
	@Resource
	private UserCollectionService collectionService;
	@Resource
	private DescoveryPariseService dpariseService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private TaskCommonService taskService;
	@Resource
	private ShareContractService shareContractService;
	@Resource
	private DescoveryLeaveMessageService dleavemsgService;
	// @Resource
	// private SqlSessionFactory sessionFactory;
	public static Logger log = Logger.getLogger("WXPariseDescoveryController");
	
	@Autowired
	private RecommendService recommendService;

	@RequestMapping("/publishDescovery")
	public void userPublishNewProject(HttpServletResponse response, DescoveryCommon dc, Integer days) {
		// SqlSession sqlsession = sessionFactory.openSession();
		// ProjectCommonService pjService =
		// sqlsession.getMapper(ProjectCommonService.class);
		// ProjectCommon pc1 = pjService.getProjectById(1);
		// System.out.println(pc1.getProjectTitle());
		// sqlsession.close();
		try {
			dc.setJoinedCount(0);
			dc.setReadCount(0);
			dc.setPariseCount(0);
			dc.setPublishTime(new Date());
			dc.setIsPass(0);
			dc.setIsShangJia(1);
			dc.setCollectCount(0);
			if (days != null) {
				Calendar cal = Calendar.getInstance();
				dc.setStartTime(cal.getTime());
				cal.add(Calendar.DAY_OF_YEAR, days);
				dc.setEndTime(cal.getTime());
			}
			dcService.addNewDesCommon(dc);
			
			//更新推荐表
			recommendService.updateDescoveryRecommendByDescoveryID(dc);
			
			output(response, JsonUtil.buildFalseJson("0", dc.getDescoveryId().toString()));
			log.info("用户" + dc.getPublisherId() + "发布'发现'" + dc.getDescoveryTitle() + "成功!");
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "发布失败!"));
			log.info("用户" + dc.getPublisherId() + "发布" + dc.getDescoveryTitle() + "失败，失败原因" + e);
		}
	}

	//查看发布(根据type不同)
	@RequestMapping("/descoveryList")
	public void showPublishedProjectList(HttpServletResponse response, Integer publisherId, Integer type,
			Integer isPass, Integer loginPlat, Integer pageNo, Integer pageSize,Integer descoveryId,Integer userId) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		ApplicationCommon app = appService.getApplicationCommonById(loginPlat);
		List<DescoveryCommon> dcList = dcService.publishedDescoveryList(publisherId, type, isPass, loginPlat,
				(pageNo - 1) * pageSize, pageSize, app.getGroupType(),descoveryId);
		for (DescoveryCommon d : dcList) {
			List<DescoveryLeaveMessage> leaveMsgList = dleavemsgService.findLeavemsgByDescoveryId(d.getDescoveryId(),
					(pageNo - 1) * pageSize, pageSize);
			d.setLeaveList(leaveMsgList);
			if(leaveMsgList.size() > 0){
				d.setCommentCount(leaveMsgList.size());
			}
			DescoveryParise parise = dpariseService.comfirmUserParisedDescovery(userId, d.getDescoveryId(),null);
			if(parise != null){
				d.setIsParised(1);
			}else{
				d.setIsParised(0);
			}

		}
		output(response, JsonUtil.buildJson(dcList));
	}
	
	// 发布列表，根据typeId
	@RequestMapping("/descloginPlatList")
	public void descoveryList(HttpServletResponse response, Integer pageNo, Integer pageSize, Integer typeId,
			Integer loginPlat) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		if (typeId != 6 && typeId != 7) {
			List<DescoveryCommon> dcList = new ArrayList<DescoveryCommon>();
			if (typeId == 1) {// 项目
				dcList = dcService.getLoginPlatTypeIdList(loginPlat, 1, (pageNo - 1) * pageSize, pageSize);
			} else if (typeId == 2) {// 产品
				dcList = dcService.getLoginPlatTypeIdList(loginPlat, 2, (pageNo - 1) * pageSize, pageSize);
			} else if (typeId == 3) {// 技能
				dcList = dcService.getLoginPlatTypeIdList(loginPlat, 3, (pageNo - 1) * pageSize, pageSize);
			} else if (typeId == 4) {// 招聘
				dcList = dcService.getLoginPlatTypeIdList(loginPlat, 4, (pageNo - 1) * pageSize, pageSize);
			} else if (typeId == 5) {// 资讯
				dcList = dcService.getLoginPlatTypeIdList(loginPlat, 5, (pageNo - 1) * pageSize, pageSize);
			} else if (typeId == 8) {// 指南
				dcList = dcService.getLoginPlatTypeIdList(loginPlat, 8, (pageNo - 1) * pageSize, pageSize);
			} else if (typeId == 9) {// 问答
				dcList = dcService.getLoginPlatTypeIdList(loginPlat, 9, (pageNo - 1) * pageSize, pageSize);
			}
			output(response, JsonUtil.buildJson(dcList));
		} else {
			if (typeId == 6) {// 打赏
				List<ShareContract> allcontracts = shareContractService.getAllShareContract(loginPlat, (pageNo - 1) * pageSize,	pageSize);
				output(response, JsonUtil.buildJson(allcontracts));
			} else if (typeId == 7) {// 悬赏
				 List<TaskCommon> taskList = taskService.findTaskByLoginPlat(loginPlat, (pageNo - 1) * pageSize, pageSize);
				output(response, JsonUtil.buildJson(taskList));
			}
		}
	}
	
	//查看资讯
	@RequestMapping("/descoveryList2")
	public void showPublishedProjectList2(HttpServletResponse response, Integer loginPlat, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		ApplicationCommon app = appService.getApplicationCommonById(loginPlat);
		List<DescoveryCommon> dcList = dcService.getDescoveryListByPlat(app.getApplicationId(), (pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(dcList));
	}

	// 审核发现
	@RequestMapping("/passDescovery")
	public void doPassDescovery(HttpServletResponse response, Integer userId, Integer descoveryId, Integer loginPlat) {
		UserCommon currUser = userService.getUserByUserId(userId);
		ApplicationCommon currapp = appService.getApplicationById(loginPlat);
		if (currapp.getParentId() != null) {
			if (currUser == null || currUser.getUserId() != currapp.getApplicationOwner()) {
				output(response, JsonUtil.buildFalseJson("1", "您没有审核权限!"));
			} else {
				try {
					dcService.passThisDescovery(descoveryId);
					output(response, JsonUtil.buildFalseJson("0", "审核成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("2", "审核失败!"));
					log.info("用户" + userId + "审核'发现'" + descoveryId + "失败，失败原因" + e);
				}
			}
		} else {
			if (currUser == null || currUser.getIsManager() != 1) {
				output(response, JsonUtil.buildFalseJson("1", "您没有审核权限!"));
			} else {
				try {
					dcService.passThisDescovery(descoveryId);
					output(response, JsonUtil.buildFalseJson("0", "审核成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("2", "审核失败!"));
					log.info("用户" + userId + "审核'发现'" + descoveryId + "失败，失败原因" + e);
				}
			}
		}
	}

	// 发现上下架
	@RequestMapping("/sxjia")
	public void shangxiajiaDescovery(HttpServletResponse response, Integer descoveryId, Integer userId) {
		UserCommon currUser = userService.getUserByUserId(userId);
		DescoveryCommon currDescovery = dcService.getDescoveryById(descoveryId);
		ApplicationCommon currapp = appService.getApplicationById(currDescovery.getLoginPlat());
		if (currUser == null) {
			output(response, JsonUtil.buildFalseJson("1", "该用户没有找到!"));
		} else {
			if (currUser.getUserId() == currDescovery.getPublisherId()) {
				if (currDescovery.getIsShangJia() == 0) {
					currDescovery.setIsShangJia(1);
					currDescovery.setUpdateTime(new Date());
					dcService.updateDescoveryCommonInfo(currDescovery);
					output(response, JsonUtil.buildFalseJson("0", "上架成功!"));
					log.info("发现id" + descoveryId + "上架成功，是发布者" + userId + "主动上架!");
				} else {
					currDescovery.setIsShangJia(0);
					currDescovery.setUpdateTime(new Date());
					dcService.updateDescoveryCommonInfo(currDescovery);
					output(response, JsonUtil.buildFalseJson("0", "下架成功!"));
					log.info("发现id" + descoveryId + "下架成功，是发布者" + userId + "主动下架!");
				}
			} else if (currUser.getIsManager() == 1 || currUser.getUserId() == currapp.getApplicationOwner()) {
				if (currDescovery.getIsShangJia() == 0) {
					currDescovery.setIsShangJia(1);
					currDescovery.setUpdateTime(new Date());
					dcService.updateDescoveryCommonInfo(currDescovery);
					output(response, JsonUtil.buildFalseJson("0", "上架成功!"));
					log.info("发现id" + descoveryId + "上架成功，是管理员" + userId + "操作上架!");
				} else {
					currDescovery.setIsShangJia(0);
					currDescovery.setUpdateTime(new Date());
					dcService.updateDescoveryCommonInfo(currDescovery);
					output(response, JsonUtil.buildFalseJson("0", "下架成功!"));
					log.info("发现id" + descoveryId + "下架成功，是管理员" + userId + "操作下架!");
				}
			} else {
				output(response, JsonUtil.buildFalseJson("3", "您没有权限进行该操作!"));
			}
		}
	}

	// 发现详情,每次调用的时候都要把该‘发现’的访客+1
	@RequestMapping("/descoveryDetails")
	public void showDescoveryDetails(HttpServletResponse response, Integer descoveryId, Integer userId, Integer pageNo,
			Integer pageSize) {
		List<DescoveryCommon> returnList = new ArrayList<DescoveryCommon>();
		DescoveryCommon descovery = dcService.getDescoveryById(descoveryId);
		if (descovery == null) {
			output(response, JsonUtil.buildFalseJson("1", "该'发现'不存在"));
		}else if (userId == null) {// 用户登录的时候拒绝授权
			List<DescoveryReader> readers = dreaderService.findReaderList(descovery.getDescoveryId(),
					(pageNo - 1) * pageSize, pageSize);
			descovery.setReaderList(readers);
			if (descovery.getProjectType() != null) {
				// 找到该项目的父标签，返回中文
				TagCommon projectTag = tagcService.getCommonTagById(descovery.getProjectType());
				descovery.setProjectTypeName(projectTag.getTagName());
				String[] hasTag = descovery.getHasResourceTag().split(",");
				String[] needTag = descovery.getNeedResourceTag().split(",");
				String[] payTag = descovery.getProjectPay().split(",");
				// 找到拥有标签，并拼接中文字返回
				List<ProjectTags> hasTags = pjtagService.findTagsByIds(Arrays.asList(hasTag));
				String hasTagName = "";
				for (ProjectTags has : hasTags) {
					hasTagName += has.getChildtagName() + ",";
				}
				descovery.setHasResourceName(hasTagName);
				// 找到需要标签，并拼接中文字返回
				List<ProjectTags> needTags = pjtagService.findTagsByIds(Arrays.asList(needTag));
				String needTagName = "";
				for (ProjectTags need : needTags) {
					needTagName += need.getChildtagName() + ",";
				}
				descovery.setNeedResourceName(needTagName);
				// 找到项目回报标签，并拼接中文字返回
				List<ProjectTags> payTags = pjtagService.findTagsByIds(Arrays.asList(payTag));
				String payTagName = "";
				for (ProjectTags pay : payTags) {
					payTagName += pay.getChildtagName() + ",";
				}
				descovery.setProjectPayName(payTagName);

			} else if (descovery.getProductType() != null) {
				// 找到该产品资源的父标签，返回中文
				TagCommon productTag = tagcService.getCommonTagById(descovery.getProductType());
				descovery.setProductTypeName(productTag.getTagName());
				// 找到该产品资源的领用方式标签，返回标签名称
				ProductTags pdtag = pdtagService.getTagById(descovery.getBidWay());
				descovery.setBidWayName(pdtag.getChildtagName());
			} else if (descovery.getSkillType() != null) {
				// 找到该项目的父标签，返回中文
				TagCommon skillTag = tagcService.getCommonTagById(descovery.getSkillType());
				descovery.setSkillTypeName(skillTag.getTagName());
				String[] serviceTag = descovery.getExplanatoryService().split(",");
				String[] serviceWayTag = descovery.getServiceWay().split(",");
				// 找到额外服务标签，并拼接中文字返回
				List<SkillTags> serviceTags = skilltagService.findTagsByIds(Arrays.asList(serviceTag));
				String serviceName = "";
				for (SkillTags service : serviceTags) {
					serviceName += service.getChildtagName() + ",";
				}
				descovery.setExplanatoryServiceName(serviceName);
				// 找到服务方式标签，并拼接中文字返回
				List<SkillTags> serviceWayTags = skilltagService.findTagsByIds(Arrays.asList(serviceWayTag));
				String serviceWayName = "";
				for (SkillTags serviceWay : serviceWayTags) {
					serviceWayName += serviceWay.getChildtagName() + ",";
				}
				descovery.setServiceWayName(serviceWayName);
			} else if (descovery.getWantedType() != null) {
				// 找到该项目的父标签，返回中文
				TagCommon wantedtag = tagcService.getCommonTagById(descovery.getWantedType());
				descovery.setWantedTypeName(wantedtag.getTagName());
				String[] welfareTags = descovery.getWelfare().split("");
				List<WantedTags> wantedTagList = wantedtagService.findWantedTagsByIds(Arrays.asList(welfareTags));
				String wantedWelfareNames = "";
				for (WantedTags wtag : wantedTagList) {
					wantedWelfareNames += wtag.getChildtagName() + ",";
				}
				descovery.setWelfareName(wantedWelfareNames);
			}
		} else {
			UserCollection collection = collectionService.comfirmUserCollected(descoveryId, null, userId, null);
			DescoveryParise parise = dpariseService.comfirmUserParisedDescovery(userId, descoveryId, 0);
			if (!userId.equals(descovery.getPublisherId())) {// 不是发布者
				DescoveryReader dreader = new DescoveryReader();
				dreader.setReaderId(userId);
				dreader.setDescoveryId(descoveryId);
				DescoveryReader result = dreaderService.comfirmIsRead(dreader);
				if (result == null) {// 没有阅读
					dreader.setReadTime(new Date());
					dreaderService.addNewReader(dreader);
					descovery.setReadCount(descovery.getReadCount() + 1);
					dcService.updateDescoveryCommonInfo(descovery);// 访客+1
				} else {
					result.setReadTime(new Date());
					dreaderService.reflashReadTime(dreader);
					//先不限制
					descovery.setReadCount(descovery.getReadCount() + 1);
					dcService.updateDescoveryCommonInfo(descovery);// 访客+1
				}
			}
			List<DescoveryReader> readers = dreaderService.findReaderList(descovery.getDescoveryId(),
					(pageNo - 1) * pageSize, pageSize);
			descovery.setReaderList(readers);
			if (collection != null) {
				descovery.setIsCollected(1);
			} else {
				descovery.setIsCollected(0);
			}
			if (parise != null) {
				descovery.setIsParised(1);
			} else {
				descovery.setIsParised(0);
			}
			if (descovery.getProjectType() != null) {
				// 找到该项目的父标签，返回中文
				TagCommon projectTag = tagcService.getCommonTagById(descovery.getProjectType());
				descovery.setProjectTypeName(projectTag.getTagName());
				String[] hasTag = descovery.getHasResourceTag().split(",");
				String[] needTag = descovery.getNeedResourceTag().split(",");
				String[] payTag = descovery.getProjectPay().split(",");
				// 找到拥有标签，并拼接中文字返回
				List<ProjectTags> hasTags = pjtagService.findTagsByIds(Arrays.asList(hasTag));
				String hasTagName = "";
				for (ProjectTags has : hasTags) {
					hasTagName += has.getChildtagName() + ",";
				}
				descovery.setHasResourceName(hasTagName);
				// 找到需要标签，并拼接中文字返回
				List<ProjectTags> needTags = pjtagService.findTagsByIds(Arrays.asList(needTag));
				String needTagName = "";
				for (ProjectTags need : needTags) {
					needTagName += need.getChildtagName() + ",";
				}
				descovery.setNeedResourceName(needTagName);
				// 找到项目回报标签，并拼接中文字返回
				List<ProjectTags> payTags = pjtagService.findTagsByIds(Arrays.asList(payTag));
				String payTagName = "";
				for (ProjectTags pay : payTags) {
					payTagName += pay.getChildtagName() + ",";
				}
				descovery.setProjectPayName(payTagName);

			} else if (descovery.getProductType() != null) {
				// 找到该产品资源的父标签，返回中文
				TagCommon productTag = tagcService.getCommonTagById(descovery.getProductType());
				descovery.setProductTypeName(productTag.getTagName());
				// 找到该产品资源的领用方式标签，返回标签名称
				ProductTags pdtag = pdtagService.getTagById(descovery.getBidWay());
				descovery.setBidWayName(pdtag.getChildtagName());
			} else if (descovery.getSkillType() != null) {
				// 找到该项目的父标签，返回中文
				TagCommon skillTag = tagcService.getCommonTagById(descovery.getSkillType());
				descovery.setSkillTypeName(skillTag.getTagName());
				String[] serviceTag = descovery.getExplanatoryService().split(",");
				String[] serviceWayTag = descovery.getServiceWay().split(",");
				// 找到额外服务标签，并拼接中文字返回
				List<SkillTags> serviceTags = skilltagService.findTagsByIds(Arrays.asList(serviceTag));
				String serviceName = "";
				for (SkillTags service : serviceTags) {
					serviceName += service.getChildtagName() + ",";
				}
				descovery.setExplanatoryServiceName(serviceName);
				// 找到服务方式标签，并拼接中文字返回
				List<SkillTags> serviceWayTags = skilltagService.findTagsByIds(Arrays.asList(serviceWayTag));
				String serviceWayName = "";
				for (SkillTags serviceWay : serviceWayTags) {
					serviceWayName += serviceWay.getChildtagName() + ",";
				}
				descovery.setServiceWayName(serviceWayName);
			} else if (descovery.getWantedType() != null) {
				// 找到该项目的父标签，返回中文
				TagCommon wantedtag = tagcService.getCommonTagById(descovery.getWantedType());
				descovery.setWantedTypeName(wantedtag.getTagName());
				String[] welfareTags = descovery.getWelfare().split("");
				List<WantedTags> wantedTagList = wantedtagService.findWantedTagsByIds(Arrays.asList(welfareTags));
				String wantedWelfareNames = "";
				for (WantedTags wtag : wantedTagList) {
					wantedWelfareNames += wtag.getChildtagName() + ",";
				}
				descovery.setWelfareName(wantedWelfareNames);
			}
		}
		UserCommon user = userService.getUserByUserId(descovery.getPublisherId());
		if (user != null) {
			descovery.setPublisherName(user.getNickName());
		}
		returnList.add(descovery);
		output(response, JsonUtil.buildJson(returnList));
	}
	/**
	 * 获取评论
	 * @param response
	 * @param pageNo
	 * @param pageSize
	 * @param descoveryId
	 */
	@RequestMapping("/getLeaveMessage")
	public void getLeaveMessage(HttpServletResponse response,Integer pageNo,Integer pageSize,Integer descoveryId){
		List<DescoveryLeaveMessage> leaveMsgList = dleavemsgService.findLeavemsgByDescoveryId(descoveryId,
				(pageNo - 1) * pageSize, pageSize);
		output(response,JsonUtil.buildJson(leaveMsgList));
	}
}
