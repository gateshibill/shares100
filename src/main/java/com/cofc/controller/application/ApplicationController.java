package com.cofc.controller.application;

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

import com.cofc.pojo.ApplicationCode;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.ApplicationLeaveMessage;
import com.cofc.pojo.ApplicationManage;
import com.cofc.pojo.ApplicationModel;
import com.cofc.pojo.ApplicationSubtype;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.CompanyAbstract;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.OperateLog;
import com.cofc.pojo.UserCommon;
import com.cofc.service.ApplicationModelService;
import com.cofc.service.ApplicationSubtypeService;
import com.cofc.service.CompanyAbstractService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.OperateLogService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserRoleService;
import com.cofc.service.ApplicationCodeService;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ApplicationLeaveMessageService;
import com.cofc.service.ApplicationManageService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/application")
public class ApplicationController extends BaseUtil {
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private ApplicationModelService appmodelService;
	@Resource
	private UserRoleService userroleService;
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private ApplicationManageService appmanageService;
	@Resource
	private UserCommonService userService;
	@Resource
	private CompanyAbstractService abstractService;
	@Resource
	private ApplicationSubtypeService subtypeService;
	@Resource
	private ApplicationCodeService codeService;
	@Resource
	private ApplicationLeaveMessageService messageService;
	@Resource
	private OperateLogService logService;
 
	public static Logger log = Logger.getLogger("ApplicationController");

	@RequestMapping("/goAppList")
	public ModelAndView goApplicationList(HttpServletRequest request,ModelAndView modelview) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		modelview.addObject("loginPlat",bu.getLoginPlat());
		modelview.setViewName("applicationManage/applicationList");
		return modelview;
	}

	// 查看模板(选择应用)
	@RequestMapping("/goChooseModel")
	public ModelAndView goChooseApplicationModel(ModelAndView modelview) {
		modelview.setViewName("applicationManage/chooseModel");
		return modelview;
	}

	// 查看应用
	@RequestMapping("/goapplicationList")
	public ModelAndView goapplicationList(ModelAndView mView, ApplicationCommon app, String userKeyWords) {
		mView.setViewName("applicationManage/appList");
		mView.addObject("app", app);
		List<ApplicationSubtype> typeList = subtypeService.findApplicationSubtypeByPlat(1, 3, 0, 100);
		mView.addObject("userKeyWords", userKeyWords);
		mView.addObject("typeList", typeList);
		return mView;

	}

	// ajax拿应用信息
	@RequestMapping("/showAppList")
	public void showAllApplications(HttpServletRequest request,HttpServletResponse response, String modelName, String appName, Integer type,
			Integer applicationStatus, Integer userId, String userKeyWords, Integer applicationType, Integer page,
			Integer limit, Integer isRecommend, Integer groupType) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		int count = 0;
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			appList = appService.findApplicationsByCriteria(modelName, appName, applicationStatus,
					userId, userKeyWords, applicationType, (page - 1) * limit, limit, type, isRecommend, groupType);
			count = appService.findApplicationsByCount(modelName, appName, applicationStatus, userId, userKeyWords,
					applicationType, type, isRecommend, groupType);
		}else{
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = appService.findApplicationsByCriteriaByLoginPlat(loginPlatList,modelName, appName, applicationStatus,
					userId, userKeyWords, applicationType, (page - 1) * limit, limit, type, isRecommend, groupType);
		    count = appService.findApplicationsByCountByLoginPlat(loginPlatList,modelName, appName, applicationStatus, userId, userKeyWords,
					applicationType, type, isRecommend, groupType);
		}
	
		output(response, JsonUtil.buildJsonByTotalCount(appList, count));
	}

	// 修改应用及社区
	@RequestMapping("/updateAppliction")
	public void updateAppliction(HttpServletResponse response, HttpServletRequest request, ApplicationCommon app,
			CompanyAbstract abstract1) {
//		ApplicationCommon appli = appService.getApplicationsByCriteria(app.getApplicationId());
//		BackUser currLoginer = (BackUser) request.getSession().getAttribute("loginer");
//		UserRole userRole = userroleService.getUserRoleById(currLoginer.getUserId());
//		String[] rolesarr = userRole.getRoleId().split(",");
//		boolean isSuperm = false;
//		for (String role : rolesarr) {
//			while ("1".equals(role)) {
//				isSuperm = true;
//				break;
//			}
//		}
		// if (!currLoginer.getUserId().equals(appli.getApplicationOwner()) &&
		// !isSuperm) {
		// output(response, JsonUtil.buildFalseJson("1", "系统检测到您没有该应用或社区的权限!"));
		// } else {
		CompanyAbstract abstract2 = abstractService.getAbstractByLoginPlat(app.getApplicationId());
		if (abstract2 != null) {
			abstract1.setUpdateTime(new Date());
			abstractService.updateAbstractContent(abstract1);
		}
		app.setAppUpdateTime(new Date());
		if (abstract1.getTitleImage() != null) {
			app.setGroupCard(abstract1.getTitleImage());
		}
		appService.updateApplicationStatus(app);
		output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
		// }
	}

	// 查看 应用详情
	@RequestMapping("/appDetails")
	public ModelAndView showAppDetails(ModelAndView modelView, Integer appId) {
		ApplicationCommon app = appService.getApplicationsByCriteria(appId);
		CompanyAbstract abstract1 = abstractService.getAbstractByLoginPlat(appId);
		if (abstract1 != null) {
			modelView.addObject("abstract1", abstract1);
			if (abstract1.getAbstractPictures() != null) {
				String myImage[] = abstract1.getAbstractPictures().split(",");
				modelView.addObject("myImage", myImage);
				modelView.addObject("length", myImage.length);
			} else {
				modelView.addObject("myImage", null);
				modelView.addObject("length", 0);
			}
		} else {
			modelView.addObject("abstract1", null);
		}
		ApplicationCode code = codeService.findLoginPlat(appId, app.getParentId());
		List<ApplicationSubtype> typeList = subtypeService.findApplicationSubtypeByPlat(app.getParentId(), 3, 0, 100);
		List<ApplicationModel> midelList = appmodelService.findAPPModels();
		modelView.setViewName("applicationManage/applicationDetails");
		modelView.addObject("app", app);
		modelView.addObject("code", code);
		modelView.addObject("typeList", typeList);
		modelView.addObject("midelList", midelList);
		return modelView;
	}

	// 应用管理者列表
	@RequestMapping("/appManageList")
	public void appManageList(HttpServletResponse response, Integer loginPlat, Integer page, Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		List<ApplicationManage> manageList = appmanageService.getManageLoginPlatList(loginPlat, (page - 1) * limit,
				limit);
		int count = appmanageService.getManageLoginPlatCount(loginPlat);
		output(response, JsonUtil.buildJsonByTotalCount(manageList, count));
	}

	// 添加应用页面
	@RequestMapping("/goaddapp")
	public ModelAndView goAddApplicationJSP(ModelAndView modelView) {
		List<ApplicationModel> midelList = appmodelService.findAPPModels();
		modelView.setViewName("applicationManage/addApplication");
		modelView.addObject("midelList", midelList);
		return modelView;
	}

	// 后台添加小程序
	@RequestMapping("/addApplication")
	public void addApplication(HttpServletRequest request, HttpServletResponse response, ApplicationCommon newapp) {
		BackUser buser = (BackUser) request.getSession().getAttribute("loginer");
		ApplicationCommon app = appService.getApplicationName(newapp.getApplicationName());
		if (app == null) {
			newapp.setAppCreateTime(new Date());
			newapp.setApplicationOwner(buser.getUserId());
			newapp.setApplicationStatus(1);// 默认试用期
			newapp.setAppUpdateTime(new Date());
			newapp.setApplicationType(1);// 小程序
			
			newapp.setAppId(newapp.getAppId().trim());
			newapp.setAppSecret(newapp.getAppSecret().trim());
			newapp.setApiKey(newapp.getApiKey().trim());
			newapp.setMchid(newapp.getMchid().trim());
			newapp.setApplicationName(newapp.getApplicationName().trim());
			
			appService.createNewApplication(newapp);
			output(response, JsonUtil.buildFalseJson("0", "新建成功!"));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "小程序名称已存在!"));
		}
	}

	// ajax查看模板
	@RequestMapping("/showModelList")
	public void showModelList(HttpServletResponse response, String modelName, Integer page, Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		List<ApplicationModel> modelList = appmodelService.getApplicationModelList(modelName, (page - 1) * limit,
				limit);
		int count = appmodelService.getApplicationModelCount(modelName);
		output(response, JsonUtil.buildJsonByTotalCount(modelList, count));
	}

	// 修改模板页面
	@RequestMapping("/modityModel")
	public ModelAndView modityModel(ModelAndView mView, Integer modelId) {
		ApplicationModel model = appmodelService.getModelById(modelId);
		if (model.getModelCarousel() != null) {
			String myImage[] = model.getModelCarousel().split(",");
			mView.addObject("myImage", myImage);
			mView.addObject("length", myImage.length);
		} else {
			mView.addObject("myImage", null);
			mView.addObject("length", 0);
		}
		mView.addObject("model", model);
		mView.setViewName("applicationManage/updateModel");
		return mView;
	}

	// 修改模板
	@RequestMapping("/updateModel")
	public void updateModel(HttpServletResponse response, ApplicationModel model) {
		ApplicationModel model2 = appmodelService.getModelById(model.getModelId());
		ApplicationModel name = appmodelService.getModelName(model.getModelName());
		if (model2 == null) {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		} else if (name != null && !model2.getModelName().equals(model.getModelName())) {
			output(response, JsonUtil.buildFalseJson("2", "该模板名称已存在!"));
		} else {
			appmodelService.updateModelInfo(model);
			output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
		}
	}

	// 添加模板页面
	@RequestMapping("/addModel")
	public ModelAndView addModel(ModelAndView mAndView) {
		mAndView.setViewName("applicationManage/addModel");
		return mAndView;
	}

	@RequestMapping("/addappModel")
	public void addappModel(HttpServletResponse response, ApplicationModel model) {
		ApplicationModel name = appmodelService.getModelName(model.getModelName());
		if (name != null) {
			output(response, JsonUtil.buildFalseJson("1", "该模板名称已存在!"));
		} else if (model.getModelCarousel() == null || model.getModelCarousel().equals("")) {
			output(response, JsonUtil.buildFalseJson("2", "模板图片不能为空"));
		} else {
			model.setModelCreateTime(new Date());
			appmodelService.addApplicationModel(model);
			output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
		}
	}

	@RequestMapping("/deleteModel")
	public void deleteModel(HttpServletResponse response, Integer modelId, HttpServletRequest request) {
		ApplicationModel model = appmodelService.getModelById(modelId);
		BackUser currLoginer = (BackUser) request.getSession().getAttribute("loginer");
		OperateLog log = new OperateLog();
		log.setOperateTime(new Date());
		log.setOperateUser(currLoginer.getUserName());
		if (model.getModelUsedCount() >= 0) {
			// 该模板已被使用，暂不能删除
			log.setOperateResult(2);
			log.setOperateName("【" + currLoginer.getUserName() + "】删除模板，该模板为" + model.getModelName());
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("1", "暂不支持删除模板!"));
		} else {
			appmodelService.deleteApplicationModel(model);
			log.setOperateResult(1);
			log.setOperateName("【" + currLoginer.getUserName() + "】删除模板，该模板为" + model.getModelName());
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		}
	}

	// 删除社区和应用
	@RequestMapping("/deleteAppId")
	public void deleteAppId(HttpServletResponse response, Integer applicationId, GoodsCommon goods,
			HttpServletRequest request) {
		ApplicationCommon app = appService.getApplicationById(applicationId);
		goods.setLoginPlat(applicationId);
		List<GoodsCommon> goodsList = goodsService.getMyGoodsList(goods, 0, 100);
		BackUser currLoginer = (BackUser) request.getSession().getAttribute("loginer");
//		UserRole userRole = userroleService.getUserRoleById(currLoginer.getUserId());
//		String[] rolesarr = userRole.getRoleId().split(",");
//		boolean isSuperm = false;
//		for (String role : rolesarr) {
//			while ("1".equals(role)) {
//				isSuperm = true;
//				break;
//			}
//		}
		OperateLog log = new OperateLog();
		log.setOperateTime(new Date());
		log.setOperateUser(currLoginer.getUserName());
//		if (!app.getApplicationOwner().equals(currLoginer.getUserId()) && !isSuperm) {
//			log.setOperateResult(2);
//			log.setOperateName("【" + currLoginer.getUserName() + "】删除应用，该应用为" + app.getApplicationName());
//			logService.addOperateLog(log);
//			output(response, JsonUtil.buildFalseJson("1", "系统检测到您没有删除该应用的权限!"));
//		} else 
			if (goodsList != null && !goodsList.isEmpty()) {
			log.setOperateResult(2);
			log.setOperateName("【" + currLoginer.getUserName() + "】删除应用，该应用为" + app.getApplicationName());
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("2", "系统检测到该应用已被使用!"));
		} else {
			appService.deleteApplication(app);
			log.setOperateResult(1);
			log.setOperateName("【" + currLoginer.getUserName() + "】删除应用，该应用为" + app.getApplicationName());
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		}
	}

	@RequestMapping("/addManageUser")
	public void addManageUser(HttpServletRequest request,HttpServletResponse response, Integer page, Integer limit, UserCommon user) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer"); // 获取当前登陆用户
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 10;
		}
		List<UserCommon> userList = new ArrayList<UserCommon>();
		int totalCount = 0;
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) { // 百享园
		  userList = userService.findUserList(user, null, null, (page - 1) * limit, limit, null);
		  totalCount = userService.getCountUser(user, null, null, null, null);
		}else{
			if(user.getLoginPlat() == null){
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				totalCount = userService.getUserCountByLoginPlat(loginPlatList, user, null, null, null);
				userList = userService.getUserListByLoginPlat(loginPlatList, user, null, null, null,
						(page - 1) * limit, limit);
			}else{
				userList = userService.findUserList(user, null, null, (page - 1) * limit, limit, null);
				totalCount = userService.getCountUser(user, null, null, null, null);
			}
		}
		if (userList != null && !userList.isEmpty()) {
			output(response, JsonUtil.buildJsonByTotalCount(userList, totalCount));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 添加应用的管理者
	@RequestMapping("/addApplicationManage")
	public void addApplicationManage(HttpServletResponse response, ApplicationManage manage,
			HttpServletRequest request) {
		ApplicationManage app = appmanageService.getIsManageUser(manage.getManageUser(), manage.getLoginPlat(), null);
		OperateLog log = new OperateLog();
		BackUser currLoginer = (BackUser) request.getSession().getAttribute("loginer");
		UserCommon user = userService.getUserByUserId(manage.getManageUser());
		ApplicationCommon app1 = appService.getApplicationById(manage.getLoginPlat());
		log.setOperateTime(new Date());
		log.setOperateUser(currLoginer.getUserName());
		if (app == null) {
			manage.setCreateTime(new Date());
			manage.setManageStatus(1);
			appmanageService.addApplicationManage(manage);
			log.setOperateResult(1);
			log.setOperateName("【" + currLoginer.getUserName() + "】添加应用管理者，管理者为【" + user.getNickName() + "】，该应用为"
					+ app1.getApplicationName());
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
		} else {
			if (app.getManageStatus() == 1) {
				log.setOperateResult(2);
				log.setOperateName("【" + currLoginer.getUserName() + "】添加应用管理者，管理者为【" + user.getNickName() + "】，该应用为"
						+ app1.getApplicationName());
				logService.addOperateLog(log);
				output(response, JsonUtil.buildFalseJson("1", "您已添加该用户请勿重复添加！"));
			} else {
				app.setUpdateTime(new Date());
				app.setManageStatus(1);
				appmanageService.updateManageStatus(app);
				log.setOperateResult(1);
				log.setOperateName("【" + currLoginer.getUserName() + "】添加应用管理者，管理者为【" + user.getNickName() + "】，该应用为"
						+ app1.getApplicationName());
				logService.addOperateLog(log);
				output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
			}
		}
	}

	/**
	 * 
	 * type为1 删除用户管理应用的权限
	 * 
	 * type为2 设置用户为该应用的所有者
	 */
	@RequestMapping("/deleteManage")
	public void deleteManage(HttpServletResponse response, Integer manageId, Integer manageStatus, Integer type,
			HttpServletRequest request) {
		ApplicationManage manage = appmanageService.getManageById(manageId);
		OperateLog log = new OperateLog();
		BackUser currLoginer = (BackUser) request.getSession().getAttribute("loginer");
		if (manage == null) {
			output(response, JsonUtil.buildFalseJson("1", "该用户不存在!"));
		} else {
			UserCommon user = userService.getUserByUserId(manage.getManageUser());
			ApplicationCommon app = appService.getApplicationById(manage.getLoginPlat());
			log.setOperateTime(new Date());
			log.setOperateUser(currLoginer.getUserName());
			if (type == 1) {
				if (manage.getManageStatus() == 3) {
					// 取消应用所有者成功之后把应用表的所有者更新
					app.setAppUpdateTime(new Date());
					app.setApplicationOwner(null);
					appService.updateApplicationOwner(app);
				}
				manage.setUpdateTime(new Date());
				manage.setManageStatus(manageStatus);
				appmanageService.updateApplicationManage(manage);
				log.setOperateResult(1);
				log.setOperateName("【" + currLoginer.getUserName() + "】取消【" + user.getNickName() + "】的管理权限，该应用为"
						+ app.getApplicationName());
				logService.addOperateLog(log);
				output(response, JsonUtil.buildFalseJson("0", "删除用户成功!"));
			} else {
				boolean isAdmin = false;
				List<ApplicationManage> mnageList = appmanageService.getManageLoginPlatList(manage.getLoginPlat(), 0,
						10);
				for (ApplicationManage m : mnageList) {
					while (m.getManageStatus().equals(3)) {
						isAdmin = true;
						break;
					}
				}
				if (isAdmin) {
					log.setOperateResult(2);
					log.setOperateName("【" + currLoginer.getUserName() + "】设置归属者【" + user.getNickName() + "】的管理权限，该应用为"
							+ app.getApplicationName());
					logService.addOperateLog(log);
					output(response, JsonUtil.buildFalseJson("1", "应用所有者只能有一个!"));
				} else {
					manage.setUpdateTime(new Date());
					manage.setManageStatus(manageStatus);
					appmanageService.updateApplicationManage(manage);

					// 设置应用所有者成功之后把应用表的所有者更新
					app.setAppUpdateTime(new Date());
					app.setApplicationOwner(manage.getManageUser());
					appService.updateApplicationStatus(app);

					log.setOperateResult(1);
					log.setOperateName("【" + currLoginer.getUserName() + "】取消【" + user.getNickName() + "】的管理权限，该应用为"
							+ app.getApplicationName());
					logService.addOperateLog(log);
					output(response, JsonUtil.buildFalseJson("0", "设置应用所有者成功!"));
				}
			}
		}
	}

	// 删除图片
	@RequestMapping("/updateImage")
	public void updateImage(HttpServletResponse response, Integer abstractId, int index) {
		CompanyAbstract abstract1 = abstractService.getAbstractById(abstractId);
		String[] myImage = abstract1.getAbstractPictures().split(",");
		myImage[index - 1] = myImage[myImage.length - 1];
		// 数组缩容
		myImage = Arrays.copyOf(myImage, myImage.length - 1);
		String my = "";
		for (int i = 0; i < myImage.length; i++) {
			my += myImage[i] + ",";
		}
		if (myImage.length > 0) {
			my.subSequence(0, my.length() - 1);
		}
		abstract1.setUpdateTime(new Date());
		abstract1.setAbstractPictures(my);
		abstractService.updateAbstractImage(abstract1);
		output(response, JsonUtil.buildFalseJson("0", "删除图片成功!"));
	}

	// 删除模板图片
	@RequestMapping("/updateModelImage")
	public void updateModelImage(HttpServletResponse response, Integer modelId, int index) {
		ApplicationModel model = appmodelService.getModelById(modelId);
		String[] myImage = model.getModelCarousel().split(",");
		myImage[index - 1] = myImage[myImage.length - 1];
		// 数组缩容
		myImage = Arrays.copyOf(myImage, myImage.length - 1);
		String my = "";
		for (int i = 0; i < myImage.length; i++) {
			my += myImage[i] + ",";
		}
		if (myImage.length > 0) {
			my.substring(0, my.length() - 1);
		}
		model.setModelCarousel(my);
		appmodelService.updateModelInfo(model);
		output(response, JsonUtil.buildFalseJson("0", "删除图片成功"));
	}
	
	@RequestMapping("/gomessage")
	public ModelAndView goMessagePage(HttpServletRequest request,ModelAndView mv){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = appService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = appService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("appList", appList);
		mv.setViewName("applicationManage/messageList");
		return mv;
	}
	/**
	 * 获取咨询列表
	 * @param request
	 * @param response
	 * @param loginPlat
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getmessagelist")
	public void getMessageList(HttpServletRequest request,HttpServletResponse response,Integer loginPlat,Integer page,Integer limit){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
        if(page == null){
        	page = 1;
        }
        if(limit == null){
        	limit = 15;
        }
        int rowsCount = 0;
        List<ApplicationLeaveMessage> lists = new ArrayList<ApplicationLeaveMessage>();
        if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
        	rowsCount = messageService.getMessageCount(loginPlat);
        	lists = messageService.getMessageList(loginPlat, (page-1) * limit, limit);
        }else{
        	if(loginPlat == null){
        		String[] idStrings = bu.getLoginPlat().split(",");
    			List<String> loginPlatList = Arrays.asList(idStrings);
    			rowsCount = messageService.getMessageCountByLoginPlat(loginPlatList);
    			lists = messageService.getMessageListByLoginPlat(loginPlatList, (page-1) * limit, limit);
        	}else{
        		rowsCount = messageService.getMessageCount(loginPlat);
            	lists = messageService.getMessageList(loginPlat, (page-1) * limit, limit);
        	}
        }
		output(response, JsonUtil.buildJsonByTotalCount(lists, rowsCount));
	}
}
