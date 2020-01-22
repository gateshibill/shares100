package com.cofc.controller.carousel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.CarouselPicture;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.CarouselPictureService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/carousel")
public class CarouselPictureController extends BaseUtil {
	@Resource
	private CarouselPictureService carouselService;
	@Resource
	private ApplicationCommonService applicationService;

	private HttpSession session;

	public static Logger log = Logger.getLogger("CarouselPictureController");

	@RequestMapping("/goCarouselList")
	public ModelAndView goCarousePictureList(HttpServletRequest request,ModelAndView modelView) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();

        if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
        	appList = applicationService.getAppList(null,null);
        }else{
        	String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
        }
		log.info("进入图片列表的jsp页面");
		modelView.addObject("appList", appList);
		modelView.setViewName("/carousel/pictureList");
		return modelView;
	}

	@RequestMapping("/carouselList")
	public void CarousePictureList(HttpServletRequest request, HttpServletResponse response, String picName,
			Integer pictureId, Integer isUsing, String userKeyWords, Integer userId, String searchBeginDate,
			String searchEndDate, Integer loginPlat, Integer page, Integer limit)
			throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null || page == 0) {
			page = 1;
		}
		if (limit == null || limit == 0) {
			limit = 15;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (searchBeginDate != null && searchBeginDate != "") {
			startTime = startSdf.format(sdf.parse(searchBeginDate));
		}
		if (searchEndDate != null && searchEndDate != "") {
			endTime = endSdf.format(sdf.parse(searchEndDate));
		}
		int rowCount = 0;
		List<CarouselPicture> pictureList = new ArrayList<CarouselPicture>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			rowCount = carouselService.getCountPictures(pictureId, picName, isUsing, userId, userKeyWords, startTime,
					endTime,loginPlat);
			pictureList = carouselService.findPictureByCriteria(pictureId, picName, isUsing, userId,
					userKeyWords, startTime, endTime, loginPlat, (page - 1) * limit, limit);
		}else{
			if(loginPlat == null){ //应用
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				rowCount = carouselService.getCountPicturesByLoginPlat(loginPlatList, pictureId, picName, isUsing, userId,userKeyWords, startTime, endTime);
			    pictureList = carouselService.findPictureByCriteriaByLoginPlat(loginPlatList, pictureId, picName, isUsing, userId, userKeyWords, startTime, endTime,(page-1) * limit , page); 
			}else{ 
				rowCount = carouselService.getCountPictures(pictureId, picName, isUsing, userId, userKeyWords, startTime,
						endTime,loginPlat);
				pictureList = carouselService.findPictureByCriteria(pictureId, picName, isUsing, userId,
						userKeyWords, startTime, endTime, loginPlat, (page - 1) * limit, limit);
			}
		}
		
		//output(response, JsonUtil.buildBackJson(pictureList, rowCount));
		output(response,JsonUtil.buildJsonByTotalCount(pictureList, rowCount));
		
	}

	@RequestMapping("/sxjCarousel")
	public String shangxiajiaCarouselPicture(HttpServletRequest request, HttpServletResponse response,
			Integer pictureId, Integer isUsing, Integer orderId) {
		CarouselPicture picture = carouselService.getPictureById(pictureId);
		ApplicationCommon currapp = applicationService.getApplicationById(picture.getLoginPlat());
		picture.setIsUsing(isUsing);
		picture.setOrderId(orderId);
		picture.setSxjUser(currapp.getApplicationOwner());
		try {
			carouselService.changePicInfo(picture);
			return "redirect:./carouselList.do?";
		} catch (Exception e) {
			log.info("用户" + currapp.getApplicationOwner() + (isUsing == 1 ? "上架" : "下架") + "图片" + pictureId + "失败，失败原因" + e);
			return null;
		}
	}

	@RequestMapping("/showDetails")
	public ModelAndView showPictureDetails(ModelAndView modelView, Integer pictureId) {
		CarouselPicture currPicture = carouselService.getPictureById(pictureId);
		modelView.setViewName("/carousel/carouselDetails");
		modelView.addObject("picture", currPicture);
		return modelView;
	}

	@RequestMapping("/goAddCarousel")
	public ModelAndView goAddCarousel(ModelAndView modelView, Integer id) {
		modelView.addObject("id", id);
		modelView.setViewName("carousel/addCarousel");
		return modelView;
	}

	/**
	 * 新增轮播图
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addNewCarouselPic")
	public void addNewCarouselPic(HttpServletRequest request, HttpServletResponse response,CarouselPicture car) {
		session = request.getSession();
		BackUser user = (BackUser) session.getAttribute("loginer");
		if(user == null){
			output(response,JsonUtil.buildFalseJson("1","请先登录"));
			return;
		}
		car.setUploadTime(new Date());
		if (user != null) {
			car.setUploadUser(user.getUserId());
		}
		carouselService.addNewCarouselPic(car);

		CarouselPicture car2 = carouselService.getPictureById(car.getPictureId());

		if (car2 == null) {
			output(response, JsonUtil.buildFalseJson("1", "新增失败"));
		} else {
			output(response, JsonUtil.buildFalseJson("0", "新增成功"));
		}
	}
	
	@RequestMapping("/updateCarousel")
	public void updateCarousel(HttpServletRequest request, HttpServletResponse response,
			CarouselPicture picture) {
		carouselService.changePicInfo(picture);
		output(response,JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	/**
	 * 真删除轮播图
	 * @param request
	 * @param response
	 * @param pictureId
	 */
	@RequestMapping("/delCarousel")
	public void delCarousel(HttpServletRequest request,HttpServletResponse response,Integer pictureId){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(bu == null){
			output(response,JsonUtil.buildFalseJson("1", "你无权限操作"));
		}else{
			if(pictureId == null){
				output(response,JsonUtil.buildFalseJson("1", "参数有误"));
			}else{
				carouselService.delPictureById(pictureId);
				output(response,JsonUtil.buildFalseJson("0", "删除轮播图成功"));
			}
			
		}
	}
}
