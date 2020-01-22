package com.cofc.controller.wallet;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserWalletDiary;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 用户钱包日志记录相关接口
 * @author wang
 *
 */
@Controller
@RequestMapping("/wx/userWallet")
public class WXUserWalletController extends BaseUtil{
	@Resource
	private UserWalletDiaryService uwdService;
	
	@RequestMapping("/appWalletDiary")
	public void showMyWalletDiary(HttpServletResponse response,Integer userId,Integer pageNo,Integer pageSize){
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		List<UserWalletDiary> uwdList = uwdService.findMyWalletDiary(userId,(pageNo-1)*pageSize,pageSize);
		output(response, JsonUtil.buildJson(uwdList));
	}
}
