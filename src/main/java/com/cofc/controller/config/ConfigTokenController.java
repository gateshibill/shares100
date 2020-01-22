package com.cofc.controller.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.ConfigToken;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ConfigTokenService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/token")
public class ConfigTokenController extends BaseUtil{
	@Resource
	private ConfigTokenService tokenService;
	@Resource
	private ApplicationCommonService applicationService;
	@RequestMapping("/goToken")
	public ModelAndView goToken(HttpServletRequest request,ModelAndView mv){
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
			mv.setViewName("config/tokenList");
		}
		return mv;
	}
	@RequestMapping("/getToken")
	public void getToken(HttpServletRequest request,HttpServletResponse response,
			ConfigToken token,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ConfigToken> lists = new ArrayList<ConfigToken>();
		int totalCount = 0;
		if(token.getLoginPlat() != null){
			 totalCount = tokenService.getTokenCount(token, null);
			 lists = tokenService.getTokenList(token, null, (page-1)*limit, limit);
		}else{
			 if(bu.getLoginPlat() == null){
				 totalCount = tokenService.getTokenCount(null, null);
				 lists = tokenService.getTokenList(null, null, (page-1)*limit, limit);
			 }else{
				 String[] idStrings = bu.getLoginPlat().split(",");
				 List<String> loginPlatList = Arrays.asList(idStrings);
				 totalCount = tokenService.getTokenCount(null, loginPlatList);
				 lists = tokenService.getTokenList(null, loginPlatList, (page-1) * limit, limit);
			 }
		}
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	@RequestMapping("/goAddToken")
	public ModelAndView goAddToken(HttpServletRequest request,ModelAndView mv,Integer loginPlat){
		mv.addObject("loginPlat", loginPlat);
		mv.setViewName("config/addToken");
		return mv;
	}
	@RequestMapping("/addToken")
	public void addToken(HttpServletResponse response,ConfigToken token){
		if(token.getLoginPlat() == null){
			output(response,JsonUtil.buildFalseJson("1","非法应用"));
		}else{
			ConfigToken info = tokenService.getTokenByLogin(token.getLoginPlat());
			if(info == null){//执行添加
				tokenService.addToken(token);
				output(response,JsonUtil.buildFalseJson("0","添加成功"));
			}else{
				output(response,JsonUtil.buildFalseJson("1","该应用已存在配置"));
			}
		}
	}
	@RequestMapping("/goUpdateToken")
	public ModelAndView goUpdateToken(HttpServletRequest request,ModelAndView mv,Integer tokenId){
		ConfigToken token = tokenService.getTokenById(tokenId);
		mv.addObject("token", token);
		mv.setViewName("config/updateToken");
		return mv;
	}
	@RequestMapping("/updateToken")
	public void updateToken(HttpServletResponse response,ConfigToken token){
		if(token.getTokenId() == null){
			output(response, JsonUtil.buildFalseJson("1","非法数据"));
		}else{
			try {
				tokenService.updateToken(token);
				output(response,JsonUtil.buildFalseJson("0","编辑成功"));
			} catch (Exception e) {
				output(response,JsonUtil.buildFalseJson("1","编辑失败"));
			}
		}
	}
	@RequestMapping("/delToken")
	public void delToken(HttpServletResponse response,Integer tokenId){
		if(tokenId == null){
			output(response,JsonUtil.buildFalseJson("1","非法数据"));
		}else{
			tokenService.delToken(tokenId);
			output(response,JsonUtil.buildFalseJson("0","删除成功"));
		}
	}
}
