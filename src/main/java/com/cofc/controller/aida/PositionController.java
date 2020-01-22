package com.cofc.controller.aida;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.cofc.pojo.aida.Position;
import com.cofc.service.aida.PositionService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/position")
public class PositionController extends BaseUtil{
	@Resource
	private PositionService positionService;
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv,Position position){
		mv.addObject("position", position);
		mv.setViewName("position/index");
		return mv;
	}
	@RequestMapping("/getPositionList")
	public void getPositionList(HttpServletResponse response,Position position,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		int totalCount = positionService.getPositionCount(position);
		List<Position> lists = positionService.getPositionList(position,(page-1) * limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	@RequestMapping("/addPosition")
	public ModelAndView addPosition(HttpServletRequest request,ModelAndView mv){
		mv.setViewName("position/add");
		return mv;
	}
	@RequestMapping("/doAddPosition")
	public void doAddPosition(HttpServletResponse response,Position position){
		if(position.getAppId() == null){
			output(response,JsonUtil.buildFalseJson("1","添加失败"));
		}else{
			position.setIsShow(1);
			position.setCreateTime(new Date());
			positionService.addPosition(position);
			output(response,JsonUtil.buildFalseJson("0","添加成功"));
		}
	}
	@RequestMapping("/editPosition")
	public ModelAndView editPosition(HttpServletRequest request,ModelAndView mv,Integer positionId){
		Position position = positionService.getPositionInfo(positionId);
		mv.addObject("position", position);
		mv.setViewName("position/edit");
		return mv;
	}
	@RequestMapping("/doEditPosition")
	public void doEditPosition(HttpServletResponse response,Position position){
		positionService.updatePosition(position);
		output(response,JsonUtil.buildFalseJson("0","编辑成功"));
	}
	@RequestMapping("/delPosition")
	public void delPosition(HttpServletResponse response,Integer positionId){
		positionService.delPosition(positionId);
		output(response,JsonUtil.buildFalseJson("0","删除成功"));
	}
}
