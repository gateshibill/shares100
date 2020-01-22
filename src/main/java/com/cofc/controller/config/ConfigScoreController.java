package com.cofc.controller.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.ConfigScore;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ConfigScoreService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/config")
public class ConfigScoreController extends BaseUtil{
	@Resource
	private ConfigScoreService configScoreService;
	@Resource
	private ApplicationCommonService applicationService;
	@RequestMapping("/goConfig")
	public ModelAndView goConfig(HttpServletRequest request,ModelAndView mv){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(bu == null){
			mv.setViewName("../../login");
		}else{
			List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
			if(bu.getLoginPlat() == null || bu.getLoginPlat() == ""){
				appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,
						null, null, null);
			}else{
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				appList = applicationService.getApplicationByLoginPlat(loginPlatList);
			}
			mv.addObject("appList", appList);
			mv.setViewName("config/scoreList");
		}
		return mv;
	}
	@RequestMapping("/getConfig")
	public void getConfig(HttpServletRequest request,HttpServletResponse response,
			ConfigScore score,Integer page,Integer limit){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ConfigScore> list = new ArrayList<ConfigScore>();
		int totalCount = 0;
		if(score.getLoginPlat() != null){
			 totalCount = configScoreService.getConfigCount(score,null);
			 list = configScoreService.getConfigList(score, null, (page-1) * limit, limit);
		}else{
			if(bu.getLoginPlat() == null || bu.getLoginPlat() == ""){
				 totalCount = configScoreService.getConfigCount(null,null);
				 list = configScoreService.getConfigList(null, null, (page-1) * limit, limit);
			}else{
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				totalCount = configScoreService.getConfigCount(null, loginPlatList);
				list = configScoreService.getConfigList(null, loginPlatList,(page-1) * limit, limit);
			}
		}
		output(response,JsonUtil.buildJsonByTotalCount(list, totalCount));
		
	}
	@RequestMapping("/goAddConfig")
	public ModelAndView goAddConfig(HttpServletRequest request,ModelAndView mv,Integer loginPlat){
		mv.addObject("loginPlat", loginPlat);
		mv.setViewName("config/addScore");
		return mv;
	}
	@RequestMapping("/addConfig")
	public void addConfig(HttpServletRequest request,HttpServletResponse response,ConfigScore score){
		if(score.getLoginPlat() == null){
			output(response,JsonUtil.buildFalseJson("1","添加失败"));
		}else{
			ConfigScore check = configScoreService.getConfigScoreByLoginPlat(score.getLoginPlat());
			if(check != null){
				output(response,JsonUtil.buildFalseJson("1", "该应用已存在数据,请执行修改"));
			}else{
				score.setCreateTime(new Date());
				configScoreService.addConfigScore(score);
				output(response,JsonUtil.buildFalseJson("0","添加成功"));
			}
		}
	}
	@RequestMapping("/goUpdateConfig")
	public ModelAndView goUpdateConfig(HttpServletResponse response,Integer id,ModelAndView mv){
		ConfigScore score = configScoreService.getConfigScoreById(id);
		mv.addObject("score", score);
		mv.setViewName("config/updateScore");
		return mv;
	}
	@RequestMapping("/updateConfig")
	public void updateConfig(HttpServletResponse response,ConfigScore score){
		score.setUpdateTime(new Date());
		configScoreService.updateConfigScore(score);
		output(response,JsonUtil.buildFalseJson("0","更新数据成功"));
	}
	@RequestMapping("/delConfig")
	public void delConfig(HttpServletResponse response,Integer id){
		if(id == null){
			output(response,JsonUtil.buildFalseJson("1","数据不存在"));
		}else{
			configScoreService.delConfig(id);
			output(response,JsonUtil.buildFalseJson("0","删除成功"));
		}
	}
}
