package com.cofc.controller.user;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserFeedBack;
import com.cofc.service.UserFeedBackService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 前端用户反馈相关接口
 * @author chenhui
 *
 */
@Controller
@RequestMapping("/wx/feedBack")
public class WXUserFeedBackController extends BaseUtil {
	@Resource
	private UserFeedBackService ufbService;
	public static Logger log = Logger.getLogger("WXUserFeedBackController");

	@RequestMapping("/addFeed")
	public void UserAddFeedBack(HttpServletResponse response, UserFeedBack ufb) {
		try {
			ufb.setFeedTime(new Date());
			ufbService.addNewFeedBack(ufb);
			output(response, JsonUtil.buildFalseJson("0", "提交成功,感谢您的反馈!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "提交失败!"));
			log.info("用户" + ufb.getFeedUser() + "反馈" + ufb.getFeedDetail() + "失败，失败原因" + e);
		}

	}
}
