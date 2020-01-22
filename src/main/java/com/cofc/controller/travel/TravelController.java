package com.cofc.controller.travel;

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
import com.cofc.pojo.TravelComment;
import com.cofc.pojo.TravelCommon;
import com.cofc.pojo.TravelJoin;
import com.cofc.pojo.TravelView;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.TravelCommentService;
import com.cofc.service.TravelCommonService;
import com.cofc.service.TravelJoinService;
import com.cofc.service.TravelViewService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 后台旅游相关接口文件
 * 
 * @author 46678
 *
 */
@Controller
@RequestMapping("/travel")
public class TravelController extends BaseUtil {
	@Resource
	private TravelCommonService travelService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private TravelJoinService joinService;
	@Resource
	private TravelCommentService commentService;
	@Resource
	private TravelViewService viewService;
	public static Logger log = Logger.getLogger("DescoveryController");

	@RequestMapping("/gotravelpage")
	public ModelAndView goTravelPage(ModelAndView mv, HttpServletRequest request,String travelTitle) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
        if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
        	appList = applicationService.getNewApplicationList(null);
        }else{
        	String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
        }
        log.info("进入行程列表页面");
		mv.addObject("appList", appList);
		mv.addObject("travelTitle",travelTitle);
		mv.setViewName("travel/travelList");
		return mv;
	}

	/**
	 * 获取行程列表
	 * 
	 * @param request
	 * @param response
	 * @param loginPlat
	 * @param travel
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/gettravellist")
	public void getTravelList(HttpServletRequest request, HttpServletResponse response, Integer loginPlat,
			TravelCommon travel, Integer page, Integer limit) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 15;
		}
		int rowsCount = 0;
		List<TravelCommon> travellist = new ArrayList<TravelCommon>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			rowsCount = travelService.getTravelCountByBxy(travel);
			travellist = travelService.getTravelListByBxy(travel,(page-1) * limit, limit);
		}else{
			if(travel.getLoginPlat() == null){
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				rowsCount = travelService.getTravelCountByLoginPlat(loginPlatList, travel);
				travellist = travelService.getTravelListByLoginPlat(loginPlatList, travel,(page-1) * limit, limit);
			}else{
				rowsCount = travelService.getTravelCountByBxy(travel);
				travellist = travelService.getTravelListByBxy(travel,(page-1) * limit, limit);
			}
		}
		output(response,JsonUtil.buildJsonByTotalCount(travellist, rowsCount));
	}

	/**
	 * 进入添加行程页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goaddtravel")
	public ModelAndView goAddTravel(HttpServletRequest request, ModelAndView mv) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
        if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
        	appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,
					null, null, null);
        }else{
        	String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
        }
        log.info("进入行程列表页面");
		mv.addObject("appList", appList);
		mv.setViewName("travel/addTravel");
		return mv;
	}

	/**
	 * 执行添加行程
	 * 
	 * @param request
	 * @param response
	 * @param travelCommon
	 */
	@RequestMapping("/doaddtravel")
	public void doAddTravel(HttpServletRequest request, HttpServletResponse response, TravelCommon travel) {
        travel.setState(1);
        travel.setIsEffect(1);
        travel.setCreateTime(new Date());
		travelService.addTravelInfo(travel);
		try {
			output(response, JsonUtil.buildFalseJson("0","添加行程成功"));
		} catch (Exception e) {
			log.info("添加成功失败,失败原因:"+e);
			output(response,JsonUtil.buildFalseJson("1","添加行程失败"));
		}
	}

	/**
	 * 进入更新行程页面
	 * 
	 * @param mv
	 * @param travelId
	 * @param request
	 * @return
	 */
	@RequestMapping("/goupdatetravel")
	public ModelAndView goUpdateTravel(ModelAndView mv, Integer travelId, HttpServletRequest request) {
		mv.setViewName("travel/updateTravel");
		return mv;
	}

	/**
	 * 执行更新行程操作
	 * 
	 * @param response
	 * @param travel
	 */
	@RequestMapping("/doupdatetravel")
	public void doUpdateTravel(HttpServletResponse response, TravelCommon travel) {

	}

	/**
	 * 执行删除行程操作
	 * 
	 * @param response
	 * @param travelId
	 */
	@RequestMapping("/dodeteletravel")
	public void dodeteleTravel(HttpServletResponse response, Integer travelId) {
		if (travelId == null) {
			log.info("删除行程失败,失败原因未传递travelId");
			output(response, JsonUtil.buildFalseJson("1", "删除行程失败：传递参数非法"));
			return;
		}
		TravelCommon travel = travelService.getTravelDetaiInfo(travelId);
		if (travel.getState() == 2) {
			log.info("该行程正在进行中,不允许删除");
			output(response, JsonUtil.buildFalseJson("1", "该行程正在进行中,不允许删除"));
			return;
		}
		if (travel.getTravelJoinPeople() > 0 && travel.getState() == 1) {
			log.info("该行程已有旅客加入,不允许删除");
			output(response, JsonUtil.buildFalseJson("1", "该行程已有旅客加入,不允许删除"));
			return;
		}
		travelService.deleteTravelInfo(travelId,null);
		try {
			log.info("删除行程成功");
			output(response, JsonUtil.buildFalseJson("0", "删除行程成功"));
		} catch (Exception e) {
			log.info("删除行程失败,失败原因：" + e);
			output(response, JsonUtil.buildFalseJson("1", "删除行程失败"));
		}
	}

	/**
	 * 
	 * @param response
	 * @param travelId
	 */
	@RequestMapping("/gojointravel")
	public ModelAndView goJoinTravel(HttpServletResponse response, Integer travelId, ModelAndView mv) {
		mv.addObject("travelId",travelId);
		mv.setViewName("travel/joinTravelList");
		return mv;
	}

	/**
	 * 获取行程加入人数列表
	 * 
	 * @param response
	 * @param travelId
	 */
	@RequestMapping("/getjointravel")
	public void getJoinTravel(HttpServletResponse response, Integer travelId, Integer page, Integer limit) {
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 15;
		}
		int rowCount = joinService.getJoinPeopleCountByTravelId(travelId);
		List<TravelJoin> joinlist = joinService.getTravelJoinListInfo(travelId,(page-1) * page,limit);
		output(response,JsonUtil.buildJsonByTotalCount(joinlist, rowCount));
	}

	/**
	 * 进入行程景点列表
	 * 
	 * @param mv
	 * @param response
	 * @param travelId
	 * @return
	 */
	@RequestMapping("/gotravelview")
	public ModelAndView goTravelView(ModelAndView mv, HttpServletResponse response, Integer travelId) {
		mv.addObject("travelId",travelId);
		mv.setViewName("travel/travelViewList");
		return mv;
	}

	/**
	 * 获取景点列表
	 * 
	 * @param response
	 * @param travelId
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/gettravelviewlist")
	public void getTravelViewList(HttpServletResponse response, Integer travelId, Integer page, Integer limit) {
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 15;
		}
		int rowCount = viewService.getViewCount(travelId);
		List<TravelView> viewlist = viewService.getViewList(travelId,(page - 1) * limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(viewlist, rowCount));
		
	}
	/**
	 * 进入行程留言页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/gotravelcomment")
	public ModelAndView goTravelComment(HttpServletRequest request,ModelAndView mv,Integer travelId){
		mv.addObject("travelId",travelId);
		mv.setViewName("travel/travelCommentList");
		return mv;
	}
	/**
	 * 获取行程留言
	 * @param response
	 * @param travelId
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/gettravelcomment")
	public void getTravelComment(HttpServletResponse response,Integer travelId,Integer page,Integer limit,TravelComment comment){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 15;
		}
		int rowCount = commentService.getCommentCountByTravelId(travelId);
		List<TravelComment> commentlist = commentService.getCommentListByBxy(comment, travelId,(page-1) * limit, limit);
	    output(response,JsonUtil.buildJsonByTotalCount(commentlist, rowCount));
	}
}
