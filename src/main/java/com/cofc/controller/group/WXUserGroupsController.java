package com.cofc.controller.group;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.AfficheCommon;
import com.cofc.pojo.ApplicationColumn;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.ApplicationManage;
import com.cofc.pojo.ApplicationModelSubtype;
import com.cofc.pojo.CarouselPicture;
import com.cofc.pojo.CompanyAbstract;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.GroupUsers;
import com.cofc.pojo.UserCommon;
import com.cofc.service.AfficheCommonService;
import com.cofc.service.ApplicationColumnService;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ApplicationManageService;
import com.cofc.service.ApplicationModelSubtypeService;
import com.cofc.service.ApplicationTypeService;
import com.cofc.service.CarouselPictureService;
import com.cofc.service.CompanyAbstractService;
import com.cofc.service.DescoveryCommonService;
import com.cofc.service.DescoveryTypeService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.GroupTypesService;
import com.cofc.service.GroupUsersService;
import com.cofc.service.TagCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/group")
public class WXUserGroupsController extends BaseUtil {
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private DescoveryCommonService descoveryService;
	@Resource
	private GroupUsersService guserService;
	@Resource
	private CarouselPictureService cpicService;
	@Resource
	private GroupTypesService gtypeService;
	@Resource
	private AfficheCommonService activeServce;
	@Resource
	private UserCommonService ucService;
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private DescoveryTypeService dtService;
	@Resource
	private TagCommonService tagcService;
	@Resource
	private CompanyAbstractService comabsService;
	@Resource
	private ApplicationTypeService applicationTypeService;
	@Resource
	private ApplicationManageService applicationManageService;
	@Resource
	private ApplicationColumnService appcolumnService;
	@Resource
	private ApplicationModelSubtypeService subtypeService;
	@Resource
	private ApplicationColumnService applicationColumnService;

	public static Logger log = Logger.getLogger("WXUserGroupsController");

	/**
	 * 前端创建社区
	 * 
	 * @param response
	 * @param applic
	 */
	@RequestMapping("/createGroup")
	public synchronized void userCreateGroup(HttpServletRequest request, HttpServletResponse response, ApplicationCommon applic) {
		applic.setApplicationCreator(applic.getApplicationOwner());
//		 int createdCount =applicationService.countMyCreatedGoups(applic.getApplicationOwner(),applic.getApplicationType(), applic.getParentId());
		 ApplicationModelSubtype subtype = subtypeService.getModelSubtypeModelId(applic.getGroupType());
		String myImage[] = null;
		if (subtype.getModel().getModelCarousel() != null) {
			myImage = subtype.getModel().getModelCarousel().split(",");
		}
//		 if (createdCount < 3) {
		ApplicationCommon aplica = applicationService.comfirmIsCreated(null,applic.getApplicationName().trim(), applic.getApplicationOwner());
		String contextPath = request.getContextPath();// 项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
		if (aplica != null) {
			output(response, JsonUtil.buildFalseJson("3", "该应用名称已存在，请重新换一个名称试试!"));
		} else {
			if (myImage == null) {
				applic.setGroupCard(weburl + "/carousel/defaultGroupCarousel2.jpg");
			} else {
				applic.setGroupCard(myImage[0]);
			}
			applic.setApplicationStatus(1);
			applic.setIsRecommend(0);
			applic.setSaleStatus("售前");
			applic.setAppCreateTime(new Date());
			applic.setApplicationOwner(applic.getApplicationCreator());//应用归属者
			try {
				applicationService.createNewApplication(applic);
				//创建完之后默认加入管理
				ApplicationManage manage = new ApplicationManage();
				manage.setCreateTime(new Date());
				manage.setManageStatus(1);
				manage.setLoginPlat(applic.getApplicationId());
				manage.setManageUser(applic.getApplicationCreator());
				applicationManageService.addApplicationManage(manage);
				// 创建完成之后自动加入该社区
				GroupUsers guser = new GroupUsers();
				guser.setGroupId(applic.getApplicationId());
				guser.setIsCreater(1);
				guser.setJoinTime(new Date());
				guser.setUserId(applic.getApplicationCreator());
				guser.setIsExamined(1);
				guserService.UserJoinGroup(guser);
				List<CarouselPicture> insertList = new ArrayList<CarouselPicture>();
				
				String seccondP = weburl + "/carousel/defaultGroupCarousel1.jpg";
				String thirdP = weburl + "/carousel/defaultGroupCarousel2.jpg";
				
				//根据typeId找到对应的模板
				if (myImage != null) {
					for (int i = 0; i < myImage.length; i++) {
						CarouselPicture cpic = new CarouselPicture();
						cpic.setIsUsing(1);
						cpic.setLoginPlat(applic.getApplicationId());
						cpic.setOrderId(i);
						cpic.setPictureName("系统设置默认轮播图" + i);
						if (i == 0) {
							cpic.setPictureUrl(myImage[i]);
						}
						if (i == 1) {
							cpic.setPictureUrl(myImage[i]);
						}
						if (i == 2) {
							cpic.setPictureUrl(myImage[i]);
						}
						cpic.setSxjUser(0);
						cpic.setUploadTime(new Date());
						cpic.setUploadUser(0);
						insertList.add(cpic);
					}
				} else {
				    for (int i = 1; i <= 3; i++) {
						CarouselPicture cpic = new CarouselPicture();
						cpic.setIsUsing(1);
						cpic.setLoginPlat(applic.getApplicationId());
						cpic.setOrderId(i);
						cpic.setPictureName("系统设置默认轮播图" + i);
						if (i == 1) {
							cpic.setPictureUrl(thirdP);
						}
						if (i == 2) {
							cpic.setPictureUrl(seccondP);
						}
						if (i == 3) {
							cpic.setPictureUrl(thirdP);
						}
						cpic.setSxjUser(0);
						cpic.setUploadTime(new Date());
						cpic.setUploadUser(0);
						insertList.add(cpic);
					}
				}
				cpicService.insertPictureBatch(insertList);
				log.info("社区" + applic.getApplicationId() + "设置默认轮播图成功");
				UserCommon curUser = ucService.getUserByUserId(applic.getApplicationCreator());
				if (applic.getApplicationType() == 2) {
					AfficheCommon acc = new AfficheCommon();
					if (myImage != null) {
						acc.setAfficheImage(myImage[0]);
					} else {
						acc.setAfficheImage(weburl + "/carousel/defaultActiveCarousel.jpg");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					acc.setAfficheDetails("欢迎进入" + applic.getApplicationName() + "社区，该社区由" + curUser.getNickName() + "于"
							+ sdf.format(applic.getAppCreateTime()) + "创建，欢迎各位小伙伴的加入。");
					acc.setAfficheTitle("社区公告");
					acc.setCreateTime(new Date());
					acc.setLoginPlat(applic.getApplicationId());
					acc.setIsRemove(0);
					acc.setAfficheType(1);
					acc.setPraiseCount(0);
					acc.setPublisherId(applic.getApplicationCreator());
					acc.setReadCount(0);
					acc.setJoinCount(0);
					activeServce.publishCommonAffiche(acc);
					log.info("社区" + applic.getApplicationId() + "设置默认公告成功");
				} else {
					CompanyAbstract comabs = new CompanyAbstract();
					comabs.setAbstractTitle(applic.getApplicationName() + "简介");
					if (subtype.getModel().getModelSynopsis() != null) {
						comabs.setAbstractContent(subtype.getModel().getModelSynopsis());
					} else {
						comabs.setAbstractContent("XXXX成立于2015年位于中国城市，注册资金1000万，公司成立以来一直致力于XXX产品研发和生成。"
								+ "公司获得过高科技企业荣誉，凭借技术实力，已经成为XXX领域提供商和运营商。产品远销海内外。" + "\n企业愿景：让科技产品给每个人都能带来快乐。"
								+ "\n企业精神：持续创新、卓越高效、快乐分享。" + "\n服务宗旨：快速响应 专业优质服务。" + "\n公司网站：www.shares100.com");
					}
					comabs.setCreateTime(new Date());
					comabs.setCompanyAddress("XXX省XXX市XX区...");
					comabs.setCompanyPhone("020-88888888");
					comabs.setLoginPlat(applic.getApplicationId());
					if (myImage != null) {
						comabs.setAbstractPictures(myImage[0]);
						comabs.setTitleImage(myImage[0]);
					} else {
						comabs.setAbstractPictures(seccondP);
						comabs.setTitleImage(seccondP);
					}
					comabsService.addAbstractForCompany(comabs);
					log.info("社区" + applic.getApplicationId() + "设置默认简介成功");
				}
				List<GoodsCommon> goodsBatch = new ArrayList<GoodsCommon>();
				for (int i = 0; i < 3; i++) {
					GoodsCommon defaultGoods = new GoodsCommon();
					defaultGoods.setCreateTime(new Date());
					defaultGoods.setFirstCost(0.1);
					if (i == 0) {
						defaultGoods.setGoodsDetails("火星旅行票");
						defaultGoods.setGoodsImage(weburl + "/goodsImage/hxlxp.jpg");
						defaultGoods.setGoodsName("火星旅行票");
						defaultGoods.setSmallImage(weburl + "/goodsImage/shxlxp.jpg");
					}
					if (i == 1) {
						defaultGoods.setGoodsDetails("购买VIP，享受至尊会员待遇");
						defaultGoods.setGoodsImage(weburl + "/goodsImage/vip.jpg");
						defaultGoods.setGoodsName("VIP");
						defaultGoods.setSmallImage(weburl + "/goodsImage/vip1.jpg");
					}
					if (i == 2) {
						defaultGoods.setGoodsDetails("买我一份签名");
						defaultGoods.setGoodsImage(weburl + "/goodsImage/qm.jpg");
						defaultGoods.setGoodsName("签名");
						defaultGoods.setSmallImage(weburl + "/goodsImage/qmx.jpg");
					}
					defaultGoods.setGoodsStock(9999);
					defaultGoods.setGoodsType(4);
					defaultGoods.setLoginPlat(applic.getApplicationId());
					defaultGoods.setIsHot(1);
					defaultGoods.setIsPassSell(1);
					defaultGoods.setIsSelled(1);
					defaultGoods.setIsRecommend(1);
					defaultGoods.setPublisherId(applic.getApplicationCreator());
					defaultGoods.setSelledCount(6666);
					defaultGoods.setSellOrRent(1);
					defaultGoods.setSellPrice("0.2");
					goodsBatch.add(defaultGoods);
				}
				goodsService.addNewBatchGoods(goodsBatch);
				log.info("社区" + applic.getApplicationId() + "设置默认商品成功");
				List<ApplicationCommon> returnList = new ArrayList<ApplicationCommon>();
				returnList.add(applic);
				output(response, JsonUtil.buildJson(returnList));
			} catch (Exception e) {
				log.info("用户" + applic.getApplicationCreator() + "创建社区-类型:" + applic.getGroupType() + ",名称:"
						+ applic.getApplicationName() + "失败，失败原因" + e);
				output(response, JsonUtil.buildFalseJson("1", "创建失败。"));
			}
		}
//		 } else {
//		 output(response, JsonUtil.buildFalseJson("2", "您已创建3个社区，不能再创建。"));
//		 }
	}

	@RequestMapping("/groupList") // 社区列表，分为我创建的社区、我加入的社区和其他人创建的社区
	public void showGroupList(HttpServletResponse response, Integer createrId, Integer applicationType, Integer userId,
			Integer others, Integer loginPlat, Integer pageNo, Integer pageSize) {
		if (createrId != null) {// 我创建的社区
			List<ApplicationCommon> groupsList = applicationService.findMyCreatedGroups(loginPlat, createrId,
					applicationType, (pageNo - 1) * pageSize, pageSize, null);
			output(response, JsonUtil.buildJson(groupsList));
		} else if (others != null && others == 1) {// 全部社区
			List<ApplicationCommon> groupsList = applicationService.findAllApplicationTypeis2(applicationType,
					loginPlat, (pageNo - 1) * pageSize, pageSize, null);
			// List<ApplicationCommon> groupsList =
			// applicationService.findOthersCreatedByCriteria(loginPlat,
			// applicationType, userId, (pageNo - 1) * pageSize, pageSize);
			output(response, JsonUtil.buildJson(groupsList));
		} else if (userId != null) {// 我参加的社区，不包含我创建的
			List<ApplicationCommon> myjoined = applicationService.findMyJoinedGroups(userId, applicationType, loginPlat,
					(pageNo - 1) * pageSize, pageSize, null);
			output(response, JsonUtil.buildJson(myjoined));
		}
	}

	// 前端拿到类型列表(分类)
	@RequestMapping("/applictionTypeList")
	public void applictionTypeList(HttpServletResponse response) {
		List<ApplicationColumn> typeList = appcolumnService.getApplicationTypeList();
		output(response, JsonUtil.buildJson(typeList));
	}

	/**
	 * 子应用列表，分为我创建的子应用、 我加入的子应用和其他人创建的子应用
	 * 
	 */
	//根据typeId走不同实现不同的效果
	@RequestMapping("/childAppList")
	public void childAppList(HttpServletResponse response, Integer loginPlat, Integer typeId, Integer userId,
			Integer pageNo, Integer pageSize, String appName) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		List<ApplicationCommon> groupsList = new ArrayList<ApplicationCommon>();
		if (typeId == 1) {// 推荐
			groupsList = applicationService.getRecommendList((pageNo - 1) * pageSize, pageSize, loginPlat, appName);
		} else if (typeId == 2) {// 社区
			groupsList = applicationService.findAllApplicationTypeis2(2, loginPlat, (pageNo - 1) * pageSize, pageSize,
					appName);
		} else if (typeId == 3) {// 应用(企业)
			groupsList = applicationService.findAllApplicationTypeis2(3, loginPlat, (pageNo - 1) * pageSize, pageSize,
					appName);
		} else if (typeId == 4) {// 我创建的（包括社区和应用）
			if (loginPlat != null) {
				groupsList = applicationService.findMyCreatedGroups(loginPlat, userId, loginPlat,
						(pageNo - 1) * pageSize, pageSize, appName);
			} else {
				groupsList = applicationService.findMyCreatedChildApplication(userId, null, null,
						(pageNo - 1) * pageSize, pageSize, appName);
			}
		} else if (typeId == 5) {// 我关注的（包括社区和应用）
			if (loginPlat != null) {
				groupsList = applicationService.findMyJoinedGroups(userId, null, loginPlat, (pageNo - 1) * pageSize,
						pageSize, appName);
			} else {
				groupsList = applicationService.findMyJoinedChildApplication(userId, null, null,
						(pageNo - 1) * pageSize, pageSize, appName);
			}
		} else {// 所有对应的应用
			ApplicationColumn column = applicationColumnService.getApplicationColumnAppTypeById(typeId);
			groupsList = applicationService.getAppClassifyList(column.getAppType(), loginPlat, (pageNo - 1) * pageSize, pageSize,appName);
		}
		output(response, JsonUtil.buildJson(groupsList));
	}
	
	//查找全部应用
	@RequestMapping("/lookupAppname")
	public void lookupAppname(HttpServletResponse response,Integer pageNo,Integer pageSize,ApplicationCommon app,String appName,Integer loginPlat){
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		app.setApplicationName(appName);
		app.setParentId(loginPlat);
		List<ApplicationCommon> groupsList = applicationService.findGroupsByCriteria(app, (pageNo - 1) * pageSize,pageSize);
		output(response, JsonUtil.buildJson(groupsList));
	}

	@RequestMapping("/allGroups") // 所有的应用
	public void showAllGroupList(HttpServletResponse response, ApplicationCommon applic, Integer pageNo,
			Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		List<ApplicationCommon> groupsList = applicationService.findGroupsByCriteria(applic, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(groupsList));
	}

	// 用于微信端用户进入社区
	@RequestMapping("/loginGroup")
	public synchronized void userLoginGroup(HttpServletResponse response, Integer userId, Integer loginPlat) {
		// ApplicationCommon group = applicationService.getApplicationById(groupId);
		GroupUsers guser = guserService.comfirmIsJoinThisGroup(userId, loginPlat);
		List<GroupUsers> returnList = new ArrayList<GroupUsers>();
		if (guser != null) {
			returnList.add(guser);
			output(response, JsonUtil.buildJson(returnList));
		} else {
			guser = new GroupUsers();
			guser.setUserId(userId);
			guser.setGroupId(loginPlat);
			guser.setIsCreater(0);
			guser.setJoinTime(new Date());
			guser.setIsExamined(0);
			try {
				guserService.UserJoinGroup(guser);
				returnList.add(guser);
				output(response, JsonUtil.buildJson(returnList));
			} catch (Exception e) {
				log.info("用户" + userId + "登录社区" + loginPlat + "失败!");
				output(response, JsonUtil.buildFalseJson("1", "登录社区失败!"));
			}
		}
	}

	// 获取社区的logo图片
	@RequestMapping("/getCommunity")
	public void getCommunity(HttpServletResponse response, Integer loginPlat) {
		ApplicationCommon app = applicationService.getApplicationById(loginPlat);
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		appList.add(app);
		output(response, JsonUtil.buildJson(appList));
	}

	// 修改社区logo图
	@RequestMapping("/updateCommunity")
	public void updateCommunity(HttpServletResponse response, Integer applicationId,String groupCard) {
		ApplicationCommon app = applicationService.getApplicationById(applicationId);
		app.setAppUpdateTime(new Date());
		app.setGroupCard(groupCard);
		applicationService.updateApplicationStatus(app);
		output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	}
}
