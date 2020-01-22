package com.cofc.controller.erweima;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.controller.file.ErWeiCodeImageContoller;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.ErweimaCode;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ErweimaCodeService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.WeiXinSessionUtils;

@Controller
@RequestMapping("/erweimacode")
public class ErweimaCodeController extends BaseUtil {
	@Resource
	private ErweimaCodeService codeService;
	@Resource
	private ApplicationCommonService applicationService;
	public static Logger log = Logger.getLogger("ErweimaCodeController");

	/**
	 * 进入二维码列表页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goerweimalistpage")
	public ModelAndView goErweimaListPage(HttpServletRequest request, ModelAndView mv) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat() == ""){
			appList = applicationService.getNewApplicationList(null);
		}else{
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("appList", appList);
		mv.setViewName("erweimaManage/erweimalist");
		return mv;
	}

	/**
	 * 获取二维码列表
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/geterweimalist")
	public void getErweimaList(HttpServletRequest request, HttpServletResponse response, ErweimaCode code, Integer page,
			Integer limit) {
		  BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
	      if(page == null){
	    	  page = 1;
	      }
	      if(limit == null){
	    	  limit =15;
	      }
	      int rowsCount = 0;
	      List<ErweimaCode> lists = new ArrayList<ErweimaCode>();
	      if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
	    	  rowsCount = codeService.getAllCount(code);
	    	  lists = codeService.getAllList(code,(page-1) * limit, limit);
	      }else{
	    	  if(code.getLoginPlat() == null || code.getLoginPlat().equals("")){
	    		  String[] idStrings = bu.getLoginPlat().split(",");
	  			  List<String> loginPlatList = Arrays.asList(idStrings);
	  			  rowsCount = codeService.getAllCountByLoginPlat(loginPlatList,code);
	  			  lists = codeService.getAllListByLoginPlat(loginPlatList, code,(page-1) * limit, limit);
	    	  }else{
	    		  rowsCount = codeService.getAllCount(code);
		    	  lists = codeService.getAllList(code,(page-1) * limit, limit);
	    	  }
	      }
	      output(response,JsonUtil.buildJsonByTotalCount(lists, rowsCount));
	}

	/**
	 * 进入添加生成二维码页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goadderweimapage")
	public ModelAndView goAddErweimaPage(HttpServletRequest request, ModelAndView mv,Integer loginPlat) {
		ApplicationCommon appinfo = applicationService.getApplicationById(loginPlat);
		mv.addObject("appinfo",appinfo);
		mv.setViewName("erweimaManage/addcode");
		return mv;
	}

	/**
	 * 执行添加二维码
	 * 
	 * @param response
	 * @param code
	 * @throws Exception 
	 */
	@RequestMapping("/adderweima")
	public void addErweima(HttpServletRequest request,HttpServletResponse response, ErweimaCode code) throws Exception {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(code.getLoginPlat() == null || code.getLoginPlat().equals("")){
        	output(response,JsonUtil.buildFalseJson("1","应用id为空"));
        }else{
        	 if(code.getDeskId() == null || code.getDeskId().equals("")){
             	output(response,JsonUtil.buildFalseJson("1","桌子号为空"));
        	 }else{
        		 ErweimaCode erweimaCode = codeService.getCodeInfo(code.getLoginPlat(),code.getDeskId());
        		 if(erweimaCode == null){ 
        			 String appId = "wx474aed7d8ee915d9";
        			 String appSecret = "9b4dcb4fd032ae6cef2c25aae0aa1129";
        			 ApplicationCommon appinfo = applicationService.getApplicationById(code.getLoginPlat());
        			 if(appinfo.getAppId() != null && appinfo.getAppSecret() != null){
        				 appId = appinfo.getAppId();
        				 appSecret = appinfo.getAppSecret();
        			 }
        			 //获取token
        			 String token = WeiXinSessionUtils.getAccessToken(appId,appSecret);
        			 String path = "/pages/application/application_index_page?tabId=2&applicationId="+code.getLoginPlat()+"&deskId="+code.getDeskId();
        			 if(token.equals("") || token == null){
        				 output(response,JsonUtil.buildFalseJson("1","获取token失败"));
        			 }else{
        				 String codePath = ErWeiCodeImageContoller.upload(token, path, request);
        				 if(codePath.equals("")){
        					  output(response,JsonUtil.buildFalseJson("1","生成二维码失败"));
        				 }else{
        					  code.setPath(path);
        					  code.setCreateUserId(bu.getUserId());
        					  code.setCodePath(codePath);
        					  code.setIsEffect(1);
        					  code.setUseCount(0);
        					  code.setDownloadCount(0);
        					  code.setCreateTime(new Date());
        					  codeService.addCode(code);
	        					try {
								  output(response,JsonUtil.buildFalseJson("0","生成二维码成功"));
								} catch (Exception e) {
	                              log.info("生成二维码失败，失败原因："+e);
	                              output(response,JsonUtil.buildFalseJson("1","生成二维码失败"));
								}
        				 }
        			 }
        		 }else{
        	        output(response,JsonUtil.buildFalseJson("1","该应用的该桌子已经存在有效的二维码"));
        		 }
        	 }
        }
	}

	/**
	 * 进入修改二维码页面
	 * 
	 * @param request
	 * @param response
	 * @param codeId
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goupdateerweima")
	public ModelAndView goUpdateErweima(HttpServletRequest request, HttpServletResponse response, Integer codeId,
			ModelAndView mv) {
		ErweimaCode code = codeService.getCodeInfoByCodeId(codeId);
		mv.addObject("code",code);
		mv.setViewName("erweimaManage/updatecode");
		return mv;
	}

	/**
	 * 执行更新二维码操作
	 * 
	 * @param response
	 * @param code
	 */
	@RequestMapping("/updateerweima")
	public void updateErweima(HttpServletResponse response, ErweimaCode code) {
       if(code == null){
    	   output(response,JsonUtil.buildFalseJson("1","没有数据更新"));
       }else{
    	   if(code.getCodeId() == null){
        	   output(response,JsonUtil.buildFalseJson("1","编辑失败，传递参数有误"));
    	   }else{
    		   codeService.updateCode(code);
    		    try {
    		      log.info("编辑成功");
				  output(response,JsonUtil.buildFalseJson("0","编辑成功"));
				} catch (Exception e) {
				  log.info("编辑失败，失败原因:"+e);
				  output(response,JsonUtil.buildFalseJson("1","编辑失败"));
				}
    	   }
       }
	}

	
}
