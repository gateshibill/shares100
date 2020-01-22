package com.cofc.controller.weiaijia.admin;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cofc.pojo.vij.Adviser;
import com.cofc.service.vij.DesignerService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/***
 * 后台设计师管理页面接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/designer")
public class DesignerController extends BaseUtil{
	@Resource 
	private DesignerService vijdesignerService;	//总设计师
	public static Logger log = Logger.getLogger("DesignerController");
	
	/***
	 * 进入列表页面
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv, Adviser adviser) {
		mv.addObject("adviser",adviser);
		mv.setViewName("/weiaijia/designer/index");
		return mv;
	}
	/***
	 * 获取数据
	 * @param response
	 * @param request
	 * @param designer
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/queryDesigner")
	public void queryDesigner(HttpServletResponse response,HttpServletRequest request,Adviser adviser,Integer page, Integer limit) {
		if (page !=null) {
			page =1;
		}
		if(limit != null) {
			limit =20;
		}
		//获取设计师总数
		int count =vijdesignerService.getCountDesigner(adviser);
		List<Adviser> list = vijdesignerService.queryCountDesigner(adviser, (page-1)*limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(list, count));
	}
	
	/***
	 * 进入添加页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addDesigner")
	public ModelAndView addDesigner(HttpServletRequest request,ModelAndView mv) {
		mv.setViewName("/weiaijia/designer/add");
		return mv;
	}
	
	/***
	 * 编辑添加设计师
	 * @param request
	 * @param response
	 * @param vdesigner
	 */
	@RequestMapping("/vijAddDesigner")
	public void vijAddDesigner(HttpServletRequest request, HttpServletResponse response,Adviser adviser,Integer designerOrder) {
		if (adviser.getRealName() ==null && adviser.getRealName()=="") {
			output(response, JsonUtil.buildFalseJson("1", "设计师的名字不能为空"));
			return;
		}
		adviser.setCreateTime(new Date());
		vijdesignerService.addDesigner(adviser);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	/***
	 * 进入修改页面
	 * @param request
	 * @param mv
	 * @param designerId
	 * @return
	 */
	@RequestMapping("/updateDesigner")
	public ModelAndView updateDesigner(HttpServletRequest request,ModelAndView mv,Integer id) {
		Adviser vdesigner = vijdesignerService.getSingleDesigner(id);
		mv.addObject("vdesigner",vdesigner);
		mv.setViewName("/weiaijia/designer/edit");
		return mv;
	}
	/***
	 * 执行编辑设计师
	 * @param response
	 * @param vijdesigner
	 */
	@RequestMapping("/upDesigner")
	public void upDesigner(HttpServletResponse response,Adviser adviser) {
		if (adviser.getRealName()==null && adviser.getRealName() =="") {
			output(response, JsonUtil.buildFalseJson("1", "设计师的名字不能为空"));
			return;
		}
		adviser.setCreateTime(new Date());
		vijdesignerService.updateDesigner(adviser);
		output(response, JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	/***
	 * 删除设计师
	 * @param response
	 * @param vijdesigner
	 * @param designerId
	 */
	@RequestMapping("/delictDesigner")
	public void delictDesigner(HttpServletResponse response, Adviser adviser,Integer id) {
		vijdesignerService.delectDesigner(id);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
}
