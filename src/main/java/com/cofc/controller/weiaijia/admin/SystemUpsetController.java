package com.cofc.controller.weiaijia.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cofc.pojo.vij.SystemUpset;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.vij.AdvService;
import com.cofc.service.vij.SystemUpsetService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/***
 * PC官网设置
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/system")
public class SystemUpsetController extends BaseUtil{

	@Resource 
	private AdvService advService;
	@Resource
	private ApplicationCommonService aCommonService;
	@Resource
	private SystemUpsetService systemUpsetService;
	
	/***
	 * 进入编辑页面
	 * @param response
	 * @param adv
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goAddSystemUpset")
	public ModelAndView addAdv(HttpServletResponse response, HttpServletRequest request,SystemUpset systemUpset,ModelAndView mv){		
		/*if(systemUpset.getAppId()==null){
			systemUpset.setAppId(709);
		}*/
		SystemUpset systemUpset_1 = systemUpsetService.getInfoById(systemUpset);
		if(systemUpset_1 == null){
			mv.addObject("systemUpset", systemUpset);
		}else{
			mv.addObject("systemUpset", systemUpset_1);
		}
		mv.setViewName("weiaijia/system/systemUpset");
		return mv;
	}
	
	/***
	 * 执行编辑
	 * @param response
	 * @param adv
	 */
	@RequestMapping("/addSystemUpset")
	public void doaddAdv(HttpServletResponse response,SystemUpset systemUpset) {
		if(systemUpset.getId() == null){
			systemUpsetService.addSystemUpset(systemUpset);
		}else{
			systemUpsetService.updateSystemUpset(systemUpset);
		}
		output(response, JsonUtil.buildFalseJson("0", "设置成功"));
	}
}
