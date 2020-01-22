package com.cofc.controller.carousel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.CarouselPicture;
import com.cofc.service.CarouselPictureService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/carousel")
public class WXCarouselPictureController extends BaseUtil {
	@Resource
	private CarouselPictureService carouselService;

	public static Logger log = Logger.getLogger("CarouselPictureController");

	@RequestMapping("/addPicture")
	// 传upload_user,orderId,pictureUrl和pictureName
	public void addNewCarouselPicture(HttpServletResponse response, Integer userId, String pictures,
			Integer loginPlat) {
		if (pictures == null) {
			output(response, JsonUtil.buildFalseJson("2", "请上传图片"));
		} else {
			List<CarouselPicture> bachList = new ArrayList<CarouselPicture>();
			// List<CarouselPicture> existsList =
			// carouselService.findCurrAppCarousels(loginPlat);
			String[] picarr = pictures.split(",");
			for (String pic : picarr) {
				CarouselPicture carousel = new CarouselPicture();
				carousel.setIsUsing(1);
				carousel.setSxjUser(userId);
				carousel.setUploadTime(new Date());
				carousel.setLoginPlat(loginPlat);
				carousel.setPictureUrl(pic);
				carousel.setUploadUser(userId);
				bachList.add(carousel);
			}
			try {
				carouselService.insertPictureBatch(bachList);
				output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
			} catch (Exception e) {
				output(response, JsonUtil.buildFalseJson("1", "添加失败!"));
				log.info("用户" + userId + "添加轮播图失败，失败原因" + e);
			}
		}
	}

	/**
	 * 
	 * @param response
	 * @param pageNo
	 *            第几页
	 * @param pageSize
	 *            每页多少条数据
	 */
	@RequestMapping("/pictureList")
	public void findPictures(HttpServletResponse response, Integer loginPlat, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 5;
		}
		List<CarouselPicture> cpList = carouselService.findCurrAppCarousels(loginPlat, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(cpList));
	}

	/**
	 */
	@RequestMapping("/changePicture")
	public void findPictures(HttpServletResponse response, Integer loginPlat, Integer userId, String pictures) {

		if (pictures == null) {
			output(response, JsonUtil.buildFalseJson("2", "请上传图片 "));
		} else {
			List<CarouselPicture> cpList = carouselService.findCurrAppCarousels(loginPlat, 0, 5);
			String[] picarr = pictures.split(",");
			for (int i = 0; i < picarr.length; i++) {// 循环去上传的图片地址和已存在的图片地址比较
				if (cpList.size() - 1 >= i) {// 需要已存在的图片张数大于等于新上传的张数，否则需要新增
					if (!picarr[i].equals(cpList.get(i).getPictureUrl())) {
						cpList.get(i).setPictureUrl(picarr[i]);
						try {
							carouselService.changePicInfo(cpList.get(i));
							output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
							log.info("用户" + userId + "修改轮播图" + cpList.get(i).getPictureId() + "成功");
						} catch (Exception e) {
							output(response, JsonUtil.buildFalseJson("1", "修改失败!"));
							log.info("用户" + userId + "修改轮播图" + cpList.get(i).getPictureId() + "失败，失败原因" + e);
						}
					}
				} else {// 修改的图片张数大于原图片张数，需要新增
					CarouselPicture carousel = new CarouselPicture();
					carousel.setIsUsing(1);
					carousel.setSxjUser(userId);
					carousel.setUploadTime(new Date());
					carousel.setLoginPlat(loginPlat);
					carousel.setPictureUrl(picarr[i]);
					carousel.setUploadUser(userId);
					try {
						carouselService.addNewCarouselPic(carousel);
						output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
						log.info("用户" + userId + "修改时添加轮播图" + picarr[i] + "成功");
					} catch (Exception e) {
						output(response, JsonUtil.buildFalseJson("1", "修改失败!"));
						log.info("用户" + userId + "修改时添加轮播图" + picarr[i] + "失败，失败原因" + e);
					}
				}
			}
		}
	}
	/**
	 * 
	 * @param response
	 * @param pictureId
	 *            需要修改信息的图片id
	 * @param userId
	 *            修改该图片信息的用户id
	 * @param orderId
	 *            该图片上架时的轮播顺序，下架可不传
	 */
	// @RequestMapping("/sxjCarousel")
	// public void shangxiajiaCarousel(HttpServletResponse response, Integer
	// pictureId, Integer userId, Integer orderId) {
	// CarouselPicture currPic = carouselService.getPictureById(pictureId);
	// if (currPic == null) {
	// output(response, JsonUtil.buildFalseJson("1", "该轮播图不存在！"));
	// } else {
	// String jsonMsg = null;
	// if (orderId == null) {
	// if (currPic.getIsUsing() == 0) {
	// currPic.setIsUsing(1);
	// jsonMsg = "该图片加入轮播成功!";
	// } else {
	// currPic.setIsUsing(0);
	// jsonMsg = "该图片成功取出轮播!";
	// }
	// }
	// currPic.setSxjUser(userId);
	// currPic.setOrderId(orderId);
	// try {
	// carouselService.changePicInfo(currPic);
	// output(response, JsonUtil.buildFalseJson("0", jsonMsg));
	// } catch (Exception e) {
	// output(response, JsonUtil.buildFalseJson("2", "处理失败!"));
	// log.info("用户" + userId + "修改轮播图" + pictureId + "上下架失败，失败原因" + e);
	// }
	// }
	// }
	@RequestMapping("/updatecarouselbyId")
	public void updateCarouselById(HttpServletResponse response,Integer pictureId){
		if(pictureId == null){
			output(response,JsonUtil.buildFalseJson("1","传递参数非法"));
			return;
		}
		CarouselPicture picture = new CarouselPicture();
		picture.setIsUsing(0);
		picture.setPictureId(pictureId);
		carouselService.changePicInfo(picture);
		try {
			output(response,JsonUtil.buildFalseJson("0","删除成功"));
		} catch (Exception e) {
			output(response,JsonUtil.buildFalseJson("1","删除失败"));
		}
		
	}
}
