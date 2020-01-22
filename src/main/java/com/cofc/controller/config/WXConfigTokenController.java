package com.cofc.controller.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ConfigToken;
import com.cofc.service.ConfigTokenService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/wx/token")
public class WXConfigTokenController extends BaseUtil{
	@Resource
	private ConfigTokenService tokenService;
	@RequestMapping("/getToken")
	public void getToken(HttpServletResponse response,Integer loginPlat){
		if(loginPlat == null){
			output(response,JsonUtil.buildFalseJson("1","非法应用"));
		}else{
			ConfigToken token = tokenService.getTokenByLogin(loginPlat);
			if(token == null){
				output(response,JsonUtil.buildFalseJson("1","暂无数据"));
			}else{
				List<ConfigToken> lists = new ArrayList<ConfigToken>();
				lists.add(token);
				output(response,JsonUtil.buildJson(lists));
			}
		}
	}
}
