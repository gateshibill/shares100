package com.cofc.controller.system;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.GoodsType;
import com.cofc.pojo.ImageSet;
import com.cofc.pojo.SystemSet;
import com.cofc.service.GoodsTypeService;
import com.cofc.service.ImageSetService;
import com.cofc.service.SystemSetService;

@Controller
@RequestMapping("/systemSet")
public class SystemSetController {
	@Resource
	private SystemSetService ssService;
	@Resource
	private GoodsTypeService gtypeService;
	@Resource
	private ImageSetService isetService;
	public static Logger log = Logger.getLogger("SystemSetController");

	@RequestMapping("/updateSet")
	private String updateSystemSet(HttpServletRequest request, SystemSet ss) {
		ssService.updateSystemSet(ss);
		return "redirect:findSet.do";
	}

	@RequestMapping("/findSet")
	private ModelAndView findSystemSet(HttpServletRequest request, ModelAndView modelView) {
		SystemSet ss = ssService.findSystemSet();
		modelView.setViewName("systemSet/isNeedApply");
		modelView.addObject("sysSet", ss);
		return modelView;
	}

	// 后台商品类型列表
	@RequestMapping("/goodsTypeList")
	public ModelAndView findGoodsTypeList(ModelAndView modelView) {
		List<GoodsType> gtList = gtypeService.findAllGoodsType(null,null,null,null,null,null,null);
		modelView.setViewName("systemSet/goodsTypeSet");
		modelView.addObject("gtList", gtList);
		return modelView;
	}

	// 后台添加商品类型
	@RequestMapping("/addGoodsType")
	public String addGoodsType(HttpServletRequest request, HttpServletResponse response, GoodsType gt) {
		// request.getSession().getAttribute("loginer");
		gt.setCreateTime(new Date());
		gtypeService.addNewGoodsType(gt);
		return "redirect:./goodsTypeList.do";
	}

	// 后台查看图片的压缩大小
	@RequestMapping("/showImageSet")
	public ModelAndView showImageSet(ModelAndView modelView) {
		ImageSet imageset = isetService.getImageSetInfo();
		modelView.setViewName("systemSet/imageSet");
		modelView.addObject("imageset", imageset);
		return modelView;
	}

	// 后台修改图片的压缩大小
	@RequestMapping("/updateImageSet")
	public String updateImageSet(HttpServletRequest request, HttpServletResponse response, ImageSet iset) {
		isetService.updateImageSet(iset);
		return "redirect:./showImageSet.do";
	}
}
