package com.cofc.controller.weiaijia.admin;

import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.jivesoftware.smackx.workgroup.packet.UserID;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.vij.FeedBack;
import com.cofc.service.vij.FeedBackService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/feedBack")
public class FeedBackController extends BaseUtil {
	
	@Resource
	private FeedBackService fBackService;	//反馈意见
	public static Logger log = Logger.getLogger("FeedBackController"); 
	
	
	/***
	 * 查看用户反馈信息
	 * @param response
	 * @param mv
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getFeedBack")
	public ModelAndView getFeedBack(HttpServletResponse response,ModelAndView mv) {
		mv.setViewName("weiaijia/project/feedBack");
		return mv;
	}
	/***
	 * 确认用户反馈信息
	 * @param response
	 * @param fBack
	 */
	@RequestMapping("/doFeedBack")
	public void doFeedBack(HttpServletResponse response,FeedBack back,Integer page,Integer limit) {
		if (page == null) {
				page = 1;
			} 
		if (limit == null) {
				limit = 20;
			}
		int count = fBackService.getFeedBackCount(back);
		List<FeedBack> list = fBackService.getFeedBackList(back,(page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	
	/***
	 * 意见反馈是否处理
	 * @param response
	 * @param userId
	 * @return
	 */
	@RequestMapping("/uploadFeedBack")
	public ModelAndView uploadFeedBack(HttpServletResponse response,ModelAndView mv,Integer id) {
		FeedBack fBack = fBackService.getInfoById(id);
		mv.addObject("fBack", fBack);
		mv.setViewName("weiaijia/project/editFeedBack");
		return mv;
	}
	
	/***
	 * 确定处理意见
	 * @param response
	 * @param fBack
	 */
	@RequestMapping("/doUploadFeedBack")
	public void doUploadFeedBack(HttpServletResponse response,FeedBack fBack) {
		if (fBack.getId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			fBackService.updateFeedBack(fBack);
			output(response, JsonUtil.buildFalseJson("0", "修改成功"));
		}
	}
	/***
	 * 删除意见
	 * @param response
	 * @param id
	 */
	@RequestMapping("/delFeedBack")
	public void delFeedBack(HttpServletResponse response,Integer id) {
		if (id ==null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			fBackService.delFeebBack(id);
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		}
	}
}
