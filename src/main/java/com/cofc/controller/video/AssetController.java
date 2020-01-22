package com.cofc.controller.video;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.video.Asset;
import com.cofc.service.video.AssetService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/asset")
public class AssetController extends BaseUtil{
	@Resource
	private AssetService assetService;
	public static Logger log = Logger.getLogger(AssetController.class);
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView assetIndex(HttpServletRequest request,ModelAndView mv,Asset asset){
		mv.addObject("asset", asset);
		mv.setViewName("vedio/asset/index");
		return mv;
	}
	@RequestMapping(value="/getAssetList",method=RequestMethod.POST)
	public void getAssetList(HttpServletResponse response,Asset asset,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 10;
		}
		int totalCount = assetService.getAssetCount(asset);
		List<Asset> lists = assetService.getAssetList(asset,(page-1)*limit,limit);
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView addAsset(HttpServletRequest request,ModelAndView mv){
		mv.setViewName("vedio/asset/add");
		return mv;
	}
	@RequestMapping(value="/doAddAsset",method=RequestMethod.POST)
	public void doAddAsset(HttpServletResponse response,Asset asset){
		assetService.addAsset(asset);
		if(asset.getId() == 0){
			output(response,JsonUtil.buildFalseJson("1", "添加失败"));
		}else{
			output(response,JsonUtil.buildFalseJson("0", "添加成功"));
		}
		
	}
	@RequestMapping(value="/editAsset",method=RequestMethod.GET)
	public ModelAndView editAsset(HttpServletRequest request,ModelAndView mv,Integer id){
		Asset asset = assetService.getAssetById(id);
		mv.addObject("asset", asset);
		mv.setViewName("");
		return mv;
	}
	@RequestMapping(value="/doEditAsset",method= RequestMethod.POST)
	public void doEditAsset(HttpServletResponse response,Asset asset){
		assetService.updateAsset(asset);
		output(response,JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	@RequestMapping(value="/delAsset",method=RequestMethod.POST)
	public void delAsset(HttpServletResponse response,Integer id){
		if(id == null){
			output(response,JsonUtil.buildFalseJson("1", "删除失败"));
		}else{
			assetService.delAsset(id);
			output(response,JsonUtil.buildFalseJson("0", "删除成功"));
		}
	}
}
