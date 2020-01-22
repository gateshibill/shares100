package com.cofc.controller.descovery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.DescoveryCommon;
import com.cofc.pojo.DescoveryType;
import com.cofc.pojo.DescoveryloginPlatType;
import com.cofc.pojo.GroupTypes;
import com.cofc.pojo.ProductTags;
import com.cofc.pojo.ProjectTags;
import com.cofc.pojo.RecommendCommon;
import com.cofc.pojo.SkillTags;
import com.cofc.pojo.TagCommon;
import com.cofc.pojo.WantedTags;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.DescoveryCommonService;
import com.cofc.service.DescoveryTypeService;
import com.cofc.service.DescoveryloginPlatTypeService;
import com.cofc.service.GroupTypesService;
import com.cofc.service.ProductTagsService;
import com.cofc.service.ProjectTagsService;
import com.cofc.service.RecommendCommonService;
import com.cofc.service.RecommendService;
import com.cofc.service.SkillTagsService;
import com.cofc.service.TagCommonService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserRoleService;
import com.cofc.service.WantedTagsService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/descovery")
public class DescoveryController extends BaseUtil {
	@Resource
	private DescoveryCommonService descoveryService;
	@Resource
	private DescoveryTypeService descovTypeService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private TagCommonService tagCommonService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private UserBackuserRelationService userBackService;
	@Resource
	private ProjectTagsService projectTagsService;
	@Resource
	private ProductTagsService productTagsService;
	@Resource
	private SkillTagsService skillTagsService;
	@Resource
	private WantedTagsService wantedTagsService;
	@Resource
	private GroupTypesService groupTypesService;
	// @Resource
	@Autowired
	private RecommendCommonService recommendService;
	@Autowired
	private RecommendService recommendService2;
	@Resource
	private DescoveryloginPlatTypeService loginPlatTypeService;

	public static Logger log = Logger.getLogger("DescoveryController");

	@RequestMapping("/goList")
	private ModelAndView goDescoveryListJsp(ModelAndView modelView, HttpServletRequest request, DescoveryCommon desc) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");	
		List<ApplicationCommon> appList = null;
		List<DescoveryType> typeList = null;
		String allloginPlat = "all";
		String[] typeLoginPlat = allloginPlat.split(",");
		List<String> typeLoginPlatList = Arrays.asList(typeLoginPlat);
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
			typeList = descovTypeService.getTypeByLoginPlat(typeLoginPlatList);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			typeList = descovTypeService.getTypeByLoginPlat(loginPlatList);
			if(typeList.size() <= 0){
				typeList = descovTypeService.getTypeByLoginPlat(typeLoginPlatList);
			}
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		log.info("进入发现列表的jsp页面");
		modelView.addObject("appList", appList);
		modelView.addObject("typeList", typeList);
		modelView.addObject("desc", desc);
		modelView.setViewName("/descoveryManage/descoveryList");
		return modelView;
	}

	/**
	 * 拿到相对应的应用Id(发布页面)
	 * 
	 * @param modelView
	 * @param id
	 * @return
	 */
	@RequestMapping("/goAddDescovery")
	public ModelAndView goAddDescovery(ModelAndView modelView, Integer id, Integer descoveryType) {
		ApplicationCommon common = applicationService.getApplicationById(id);
		List<TagCommon> tagList = null;
		if (descoveryType == 1) {
			List<ProjectTags> projectTags = projectTagsService.findProjectTagsByOrder(null, null, null);
			tagList = tagCommonService.showTagsByqualification(1, null, null, null);
			modelView.setViewName("descoveryManage/addProject");
			modelView.addObject("projectTags", projectTags);
		} else if (descoveryType == 2) {
			List<ProductTags> productTags = productTagsService.findProductTagsByOrder(null, null, null);
			tagList = tagCommonService.showTagsByqualification(2, null, null, null);
			modelView.setViewName("descoveryManage/addProduct");
			modelView.addObject("productTags", productTags);
		} else if (descoveryType == 3) {
			List<SkillTags> skillTags = skillTagsService.findSkillTagsByOrder(null, null, null);
			tagList = tagCommonService.showTagsByqualification(3, null, null, null);
			modelView.setViewName("descoveryManage/addSkill");
			modelView.addObject("skillTags", skillTags);
		} else if (descoveryType == 4) {
			List<WantedTags> wantedTags = wantedTagsService.findSkillTagsByOrder(null, null, null);
			tagList = tagCommonService.showTagsByqualification(4, null, null, null);
			modelView.addObject("wantedTags", wantedTags);
			modelView.setViewName("descoveryManage/addRecruit");
		} else if (descoveryType == 5 || descoveryType == 8) {
			List<GroupTypes> groupTypes = groupTypesService.findGroupTypesByplat(null, null, null, null, null);
			tagList = tagCommonService.showTagsByqualification(5, null, null, null);
			modelView.setViewName("descoveryManage/addDescovery");
			modelView.addObject("groupTypes", groupTypes);
		}else{
			List<GroupTypes> groupTypes = groupTypesService.findGroupTypesByplat(null, null, null, null, null);
			tagList = tagCommonService.showTagsByqualification(5, null, null, null);
			modelView.setViewName("descoveryManage/addDescovery");
			modelView.addObject("groupTypes", groupTypes);
		}
		modelView.addObject("id", id);
		modelView.addObject("descoveryType", descoveryType);
		modelView.addObject("common", common);
		modelView.addObject("tagList", tagList);
		return modelView;
	}

	/**
	 * 发现列表
	 * 
	 * @param request
	 * @param response
	 * @param loginPlat
	 * @param descovery
	 * @param userKeyWords
	 * @param searchBeginDate
	 * @param searchEndDate
	 * @param pageNo
	 * @param pageSize
	 * @throws ParseException
	 */
	@RequestMapping("/showDataList")
	public void showDescoveryList(HttpServletRequest request, HttpServletResponse response, Integer loginPlat1,
			DescoveryCommon descovery, String userKeyWords, String dataRange, Integer page, Integer limit)
					throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		request.getSession().setAttribute("currdescoPage", page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dataRange != null && !dataRange.equals("")) {
			String[] myData = dataRange.split(" -| ");
			startTime = myData[0];
			endTime = myData[2];
		}
		if (startTime != null) {
			startTime = startSdf.format(sdf.parse(startTime));
		}
		if (endTime != null) {
			endTime = endSdf.format(sdf.parse(endTime));
		}
		int count = 0;
		List<DescoveryCommon> dcList = new ArrayList<DescoveryCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat() == "") { // 百享园
			count = descoveryService.getDescoveryCount(descovery, userKeyWords, startTime, endTime);
			dcList = descoveryService.findDescveryCommonInfo(descovery, userKeyWords, startTime, endTime,
					(page - 1) * limit, limit);
		} else {
			if (descovery.getLoginPlat() == null) { // 应用
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				count = descoveryService.getDescoveryCountByLoginPlat(loginPlatList, descovery, userKeyWords, startTime,
						endTime);
				dcList = descoveryService.findDescveryCommonInfoByLoginPlat(loginPlatList, descovery, userKeyWords,
						startTime, endTime, (page - 1) * limit, limit);
			} else {
				count = descoveryService.getDescoveryCount(descovery, userKeyWords, startTime, endTime);
				dcList = descoveryService.findDescveryCommonInfo(descovery, userKeyWords, startTime, endTime,
						(page - 1) * limit, limit);
			}
		}

		output(response, JsonUtil.buildJsonByTotalCount(dcList, count));
	}

	@RequestMapping("/sxjDescovery")
	public String shangxiajiaDescovery(DescoveryCommon descov) {
		descoveryService.updateDescoveryCommonInfo(descov);
		return "redirect:./showDataList.do";
	}

	/**
	 * 发布详情
	 * 
	 * @param modelView
	 * @param descoveryId
	 * @return
	 */
	@RequestMapping("/descoveryDetails")
	public ModelAndView showDescoveryDetails(ModelAndView modelView, Integer descoveryId) {
		DescoveryCommon dccommon = descoveryService.getDescoveryById(descoveryId);
		DescoveryCommon common = null;
		if (dccommon.getDescoveryType() == 1) {// 跳往项目模板(项目详情)
			List<ProjectTags> projectTags = projectTagsService.findProjectTagsByOrder(null, null, null);
			common = descoveryService.getPublisherDetails(descoveryId, dccommon.getDescoveryType());
			modelView.setViewName("/descoveryManage/projectDetails");
			modelView.addObject("projectTags", projectTags);
		} else if (dccommon.getDescoveryType() == 2) {// 跳往产品模板(产品详情)
			common = descoveryService.getPublisherDetails(descoveryId, dccommon.getDescoveryType());
			modelView.setViewName("/descoveryManage/productDetails");
			List<ProductTags> productTags = productTagsService.findProductTagsByOrder(null, null, null);
			modelView.addObject("productTags", productTags);
		} else if (dccommon.getDescoveryType() == 3) {// 跳往技能模板(技能详情)
			List<SkillTags> skillTags = skillTagsService.findSkillTagsByOrder(null, null, null);
			common = descoveryService.getPublisherDetails(descoveryId, dccommon.getDescoveryType());
			modelView.setViewName("/descoveryManage/skillDetails");
			modelView.addObject("skillTags", skillTags);
		} else if (dccommon.getDescoveryType() == 4) {// 跳往招聘模板(招聘详情)
			common = descoveryService.getPublisherDetails(descoveryId, dccommon.getDescoveryType());
			List<WantedTags> wantedTags = wantedTagsService.findSkillTagsByOrder(null, null, null);
			modelView.setViewName("/descoveryManage/wantedDetails");
			modelView.addObject("wantedTags", wantedTags);
		} else if (dccommon.getDescoveryType() == 5 || dccommon.getDescoveryType() == 8) {// 跳往咨询模板(咨询详情)
			List<GroupTypes> groupTypes = groupTypesService.findGroupTypesByplat(null, null, null, null, null);
			common = descoveryService.getPublisherDetails(descoveryId, dccommon.getDescoveryType());
			modelView.setViewName("/descoveryManage/informationDetails");
			modelView.addObject("groupTypes", groupTypes);
		} else if (dccommon.getDescoveryType() == 9) {
			common = descoveryService.getPublisherDetails(descoveryId, 9);
			if (common.getDescoveryImage() != null) {
				String[] myImage = common.getDescoveryImage().split(",");
				modelView.addObject("myImage", myImage);
				modelView.addObject("length", myImage.length);
			} else {
				modelView.addObject("length", 0);
				modelView.addObject("myImage", null);
			}
			modelView.setViewName("/descoveryManage/askanswerDetails");
		}else{
			List<GroupTypes> groupTypes = groupTypesService.findGroupTypesByplat(null, null, null, null, null);
			common = descoveryService.getPublisherDetails(descoveryId, dccommon.getDescoveryType());
			modelView.setViewName("/descoveryManage/informationDetails");
			modelView.addObject("groupTypes", groupTypes);
		}
		modelView.addObject("common", common);
		return modelView;
	}

	/**
	 * 发布文章
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addNewDescovery", method = RequestMethod.POST)
	@ResponseBody
	public void addNewDescovery(HttpServletRequest request, HttpServletResponse response, DescoveryCommon dc) {
		ApplicationCommon currapp = applicationService.getApplicationById(dc.getLoginPlat());
		String descoveryTitle = request.getParameter("descoveryTitle");
		if (descoveryTitle != null && descoveryTitle != "") {
			dc.setDescoveryTitle(descoveryTitle);
		}
		dc.setDescoveryType(dc.getDescoveryType());
		dc.setIsPass(1);
		dc.setIsShangJia(1);
		dc.setPublishTime(new Date());
		dc.setPublisherId(currapp.getApplicationOwner());
		if (dc.getLoginPlat() != null) {
			dc.setLoginPlat(dc.getLoginPlat());
		}
		if (dc.getDescoveryDetails() != null && dc.getDescoveryDetails() != "") {
			dc.setDescoveryDetails(dc.getDescoveryDetails());
		}
		dc.setPariseCount(0);
		dc.setReadCount(0);
		dc.setJoinedCount(0);
		dc.setCollectCount(0);
		// dc.setIsTop(1);//默认置顶
		if (dc.getIsRecommend() == 1) {
			dc.setRecommendWay(dc.getRecommendWay());
		}
		if (dc.getExpiryDate() == null) {
			dc.setExpiryDate(String.valueOf(1));
		}
		try {
			descoveryService.addNewDesCommon(dc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 更新推荐表
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				recommendService2.updateDescoveryRecommendByDescoveryID(dc);
			}
		});
		thread.start();

		DescoveryCommon dc2 = descoveryService.getDescoveryById(dc.getDescoveryId());
		if (dc2 == null) {
			output(response, JsonUtil.buildFalseJson("1", "新增失败"));
		} else {
			output(response, JsonUtil.buildFalseJson("0", "新增成功"));
		}
	}

	// 删除发布
	@RequestMapping("/dealDescovery")
	public void dealDescovery(HttpServletResponse response, Integer descoveryId, HttpServletRequest request) {
		DescoveryCommon descovery = descoveryService.getDescoveryById(descoveryId);
		// BackUser bu = (BackUser)
		// request.getSession().getAttribute("loginer");
		// ApplicationCommon currapp =
		// applicationService.getApplicationById(descovery.getLoginPlat());
		// UserRole userRole = userRoleService.getUserRoleById(bu.getUserId());
		// String[] rolesarr = userRole.getRoleId().split(",");
		// boolean isSuperm = false;
		// for (String role : rolesarr) {
		// while ("1".equals(role)) {
		// isSuperm = true;
		// break;
		// }
		// }
		// if (!currapp.getApplicationOwner().equals(bu.getUserId()) &&
		// !isSuperm) {
		// output(response, JsonUtil.buildFalseJson("2", "系统检测到你没有删除发布的权限!"));
		// } else {
		try {
			descoveryService.deleteDescoveryCommon(descovery);
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "删除失败!"));
		}
		// }
	}

	/**
	 * 修改发布文章
	 * 
	 * @param response
	 * @param dc
	 */
	@RequestMapping("/updateDescovery")
	public void updateDescovery(HttpServletResponse response, DescoveryCommon dc, HttpServletRequest request) {
		DescoveryCommon common = descoveryService.getDescoveryById(dc.getDescoveryId());

		// BackUser bu = (BackUser)
		// request.getSession().getAttribute("loginer");
		// ApplicationCommon currapp =
		// applicationService.getApplicationById(common.getLoginPlat());
		// UserRole userRole = userRoleService.getUserRoleById(bu.getUserId());
		// String[] rolesarr = userRole.getRoleId().split(",");
		// boolean isSuperm = false;
		// for (String role : rolesarr) {
		// while ("1".equals(role)) {
		// isSuperm = true;
		// break;
		// }
		// }
		// if (!currapp.getApplicationOwner().equals(bu.getUserId()) &&
		// !isSuperm) {
		// output(response, JsonUtil.buildFalseJson("1", "系统检测到您没有修改发布的权限!"));
		// } else {
		if (common != null) {
			dc.setUpdateTime(new Date());
			if (dc.getIsShangJia() != null) {
				dc.setIsShangJia(dc.getIsShangJia());
			}
			descoveryService.updateDescoveryCommonInfo(dc);
			output(response, JsonUtil.buildFalseJson("0", "修改成功"));
		}
		// }
	}

	@RequestMapping("/tjtoSame")
	public void tuijianToTheSameGroups(HttpServletResponse response, Integer descoveryId) {
		DescoveryCommon currdes = descoveryService.getDescoveryById(descoveryId);
		currdes.setRecommendWay(2);
		currdes.setIsRecommend(1);
		descoveryService.updateDescoveryCommonInfo(currdes);
		ApplicationCommon currapp = applicationService.getApplicationById(currdes.getLoginPlat());
		List<ApplicationCommon> sameApps = applicationService.findCurrTypeApplication(currapp.getApplicationType(),
				currdes.getLoginPlat());
		List<RecommendCommon> listbatch = new ArrayList<RecommendCommon>();
		for (ApplicationCommon thisapp : sameApps) {
			RecommendCommon recomRecord = recommendService.confirmIsRecommendtoThis(thisapp.getApplicationId(),
					descoveryId);
			if (recomRecord == null) {
				RecommendCommon newrecom = new RecommendCommon();
				newrecom.setCreateTime(new Date());
				newrecom.setDescoveryId(descoveryId);
				newrecom.setIsEndRecommend(0);
				newrecom.setLoginPlat(thisapp.getApplicationId());
				listbatch.add(newrecom);
			}
		}
		try {
			recommendService.insertBatchRecommend(listbatch);
			output(response, JsonUtil.buildFalseJson("0", "推荐成功"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "推荐失败，请稍后重试或联系管理员"));
		}
	}

	public static String guoHtml(String string) {
		// 替换replaceAll
		String content = string.replace("<\\s*img\\s+([^>]*)\\s*>", " style='max-width:100%;width:100%' ");
		return content;
	}

	// 删除图片
	@RequestMapping("/updateImage")
	public void updateImage(HttpServletResponse response, Integer descoveryId, int index) {
		DescoveryCommon desc = descoveryService.getDescoveryById(descoveryId);
		String[] myImage = desc.getDescoveryImage().split(",");
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
		desc.setUpdateTime(new Date());
		desc.setDescoveryImage(my);
		descoveryService.updateDescoveryCommonInfo(desc);
		output(response, JsonUtil.buildFalseJson("0", "上传图片成功"));
	}

	// 发布类型
	@RequestMapping("/godescoveryType")
	public ModelAndView godescoveryType(HttpServletRequest request, ModelAndView mAndView) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mAndView.addObject("appList", appList);
		mAndView.setViewName("/descoveryType/typeList");
		return mAndView;
	}

	@RequestMapping("/descoveryTypeList")
	public void descoveryTypeList(HttpServletRequest request, HttpServletResponse response, Integer page, Integer limit,
			DescoveryloginPlatType type) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		List<DescoveryloginPlatType> typeList = new ArrayList<DescoveryloginPlatType>();
		int count = 0;
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			typeList = loginPlatTypeService.findLoginPplatList(type, (page - 1) * limit, limit);
			count = loginPlatTypeService.findLoginPplatCount(type);
		} else {
			if (type.getLoginPlat() == null) { // 应用
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				count = loginPlatTypeService.findLoginPplatCountByLoginPlat(loginPlatList, type);
				typeList = loginPlatTypeService.findLoginPplatListByLoginPlat(loginPlatList, type, (page - 1) * limit,
						limit);
			} else {
				typeList = loginPlatTypeService.findLoginPplatList(type, (page - 1) * limit, limit);
				count = loginPlatTypeService.findLoginPplatCount(type);
			}
		}

		output(response, JsonUtil.buildJsonByTotalCount(typeList, count));
	}

	@RequestMapping("/addtype")
	public ModelAndView addtype(ModelAndView mAndView) {
		List<ApplicationCommon> appList = applicationService.getNewApplicationList(null);
		mAndView.addObject("appList", appList);
		mAndView.setViewName("/descoveryType/addType");
		return mAndView;
	}

	// 修改排序顺序
	@RequestMapping("/updateTypeOrder")
	public void updateTypeOrder(HttpServletResponse response, DescoveryloginPlatType type) {
		if (type.getOrderNo() == null || type.getOrderNo() < 0) {
			output(response, JsonUtil.buildFalseJson("1", "请输入正确的排列数字!"));
		} else {
			type.setUpdateTime(new Date());
			loginPlatTypeService.updateLoginPlatType(type);
			output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
		}
	}
}
