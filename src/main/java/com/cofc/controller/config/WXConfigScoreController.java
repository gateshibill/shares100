package com.cofc.controller.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ConfigScore;
import com.cofc.service.ConfigScoreService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 前端获取积分配置信息
 * @author 46678
 *
 */
@Controller
@RequestMapping("/wx/config")
public class WXConfigScoreController extends BaseUtil{
	@Resource
	private ConfigScoreService configScoreService;
	@RequestMapping("/getConfigScore")
	public void getConfigScore(HttpServletResponse response,Integer loginPlat){
		List<ConfigScore> lists = new ArrayList<ConfigScore>();
		ConfigScore score = configScoreService.getConfigScoreByLoginPlat(loginPlat);
		if(score != null){
			lists.add(score);
		}
		output(response,JsonUtil.buildJson(lists));
	}
}
