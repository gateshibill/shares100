package com.cofc.controller.affiche;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.AfficheCommon;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.service.AfficheCommonService;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserRoleService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

//公告
@Controller
@RequestMapping("/affiche")
public class AfficheController extends BaseUtil {
	@Resource
	private AfficheCommonService affcService;
	@Resource
	private UserRoleService userroleService;
	@Resource
	private UserBackuserRelationService uburService;
	@Resource
	private ApplicationCommonService appService;
	public static Logger log = Logger.getLogger("ActiveController");

	@RequestMapping("/goAfficheList")
	public ModelAndView showActiveList(HttpServletRequest request, HttpServletResponse response, ModelAndView modelView)
			throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
//		UserRole userRole = userroleService.getUserRoleById(currUser.getUserId());
//		List<UserBackuserRelation> idlist = uburService.getUserBackuserList(currUser.getUserId());
//		String[] rolesarr = userRole.getRoleId().split(",");
//		boolean isSuperm = false;
//		for (String role : rolesarr) {
//			while ("1".equals(role)) {
//				isSuperm = true;
//				break;
//			}
//		}
//		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
//		if (isSuperm) {
//			appList = appService.findApplicationCommon(null);
//		} else {
//			List<String> idsl = new ArrayList<String>();
//			for (UserBackuserRelation cuid : idlist) {
//				idsl.add(cuid.getUserId().toString());
//			}
//			if (idsl != null && !idsl.isEmpty()) {
//				appList = appService.findMyCreatedAllAppications(idsl);
//			} 
//		}
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat() == ""){
			appList = appService.getNewApplicationList(null);
		}else{
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = appService.getApplicationByLoginPlat(loginPlatList);
		}
		modelView.addObject("appList", appList);
		modelView.setViewName("afficheManage/afficheList");
		return modelView;
	}

	// 后台活动列表
	@RequestMapping("/afficheList")
	public void showActiveList(HttpServletRequest request,HttpServletResponse response, Integer loginPlat1, AfficheCommon affc,
			String userKeyWords, String dateRange, Integer page, Integer limit) throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myDate = dateRange.split(" -| ");
			startTime = startSdf.format(sdf.parse(myDate[0]));
			endTime = endSdf.format(sdf.parse(myDate[2]));
		}
		int rowsCount = 0;
		List<AfficheCommon> activeList = new ArrayList<AfficheCommon>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){ //百享园
			rowsCount = affcService.getCountAffiche(affc, loginPlat1,userKeyWords, startTime, endTime);
			activeList = affcService.findafficheList(affc, loginPlat1,userKeyWords, startTime, endTime,
					(page - 1) * limit, limit);
		}else{
			if(affc.getLoginPlat() == null){ //应用
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				rowsCount = affcService.getCountAfficheByLoginPlat(loginPlatList, affc, loginPlat1, userKeyWords, startTime, endTime);
				activeList = affcService.findafficheListByLoginPlat(loginPlatList, affc, loginPlat1, userKeyWords, startTime, endTime,
						(page-1) * limit, limit);
			}else{ //搜索进来的
				rowsCount = affcService.getCountAffiche(affc, loginPlat1,userKeyWords, startTime, endTime);
				activeList = affcService.findafficheList(affc, loginPlat1,userKeyWords, startTime, endTime,
						(page - 1) * limit, limit);
			}
		}
		
		output(response, JsonUtil.buildJsonByTotalCount(activeList, rowsCount));
	}

	@RequestMapping("/goAddaffiche")
	public ModelAndView toAddActive(ModelAndView modelView,Integer loginPlat,Integer afficheType) {
		ApplicationCommon currApp = appService.getApplicationById(loginPlat);
		modelView.addObject("afficheType", afficheType);
		modelView.addObject("loginPlat",loginPlat);
		modelView.addObject("currApp",currApp);
		modelView.setViewName("afficheManage/addAffiche");
		return modelView;
	}

	@RequestMapping("/addActive")
	public String addActive(HttpServletResponse response, AfficheCommon active) {
		active.setCreateTime(new Date());
		affcService.publishCommonAffiche(active);
		return "redirect:./activeList.do";
	}
    /**
     * 后台发布公告【区分公告和活动的概念】
     * @param response
     * @param affc
     */
	@RequestMapping("/addaffiche")
	public void addAffiche(HttpServletResponse response,AfficheCommon affc){
		affc.setCreateTime(new Date());
		affc.setReadCount(0);
		affc.setJoinCount(0);
		affc.setIsRemove(0);
		affcService.publishCommonAffiche(affc);
		try {
			log.info("添加公告成功");
			output(response,JsonUtil.buildFalseJson("0","添加公告成功"));
		} catch (Exception e) {
			log.info("添加公告失败,失败原因："+e);
			output(response,JsonUtil.buildFalseJson("1","添加公告失败"));
		}
	}
	
	// 后台修改活动
	@RequestMapping("/updateActive")
	public String updateGoodsInfo(HttpServletResponse response, AfficheCommon ac, Integer pageNo) {
		affcService.updateAfficheInfo(ac);
		return "redirect:./activeList.do?pageNo=" + pageNo;
	}
    
	/**
	 * 编辑公告
	 * @param response
	 * @param affc
	 */
	@RequestMapping("/updateaffiche")
	public void updateAffiche(HttpServletResponse response,AfficheCommon affc){
		affcService.updateAfficheInfo(affc);
		try {
			log.info("编辑公告成功");
			output(response,JsonUtil.buildFalseJson("0","编辑公告成功"));
		} catch (Exception e) {
			log.info("编辑公告失败，失败应用:"+e);
			output(response,JsonUtil.buildFalseJson("1","编辑公告失败"));
		}
	}
	// 后台删除活动
	@RequestMapping("/deleteActive")
	public String deleteGoods(HttpServletResponse response, String goodsIds) {
		String[] ids = goodsIds.split(",");
		List idList = Arrays.asList(ids);
		affcService.afficheUndercarriage(idList);
		return "redirect:./goodsList.do";
	}

	// 后台活动详情
	@RequestMapping("/afficheDetails")
	public ModelAndView showActiveDetails(HttpServletResponse response, ModelAndView modelView, AfficheCommon ac,
			Integer pageNo) {
		AfficheCommon active = affcService.getafficheById(ac.getAfficheId());
		modelView.setViewName("afficheManage/afficheDetails");
		modelView.addObject("affc", active);
		return modelView;
	}
	//删除公告
	@RequestMapping("/deleteAffiche")
	public void deleteAffiche(HttpServletResponse response,Integer afficheId){
		try {
			affcService.deleteAffiche(afficheId);
			output(response, JsonUtil.buildFalseJson("0", "删除公告成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "删除公告失败!"));
		}
	}
}
