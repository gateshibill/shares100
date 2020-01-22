package com.cofc.controller.affiche;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.AfficheCommon;
import com.cofc.pojo.AfficheParise;
import com.cofc.service.AfficheCommonService;
import com.cofc.service.AffichePariseService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/wx/active")
public class WXPariseAfficheController extends BaseUtil{
	@Resource
	private AfficheCommonService accService;
	@Resource
	private AffichePariseService apService;
	public static Logger log = Logger.getLogger("WXPariseActiveController");
	// 活动点赞
		@RequestMapping("/pariseActive")
		public void appPariseActive(HttpServletResponse response, AfficheParise ap) {
			AfficheCommon returnAc = accService.getafficheById(ap.getAfficheId());
			if (returnAc != null) {
				try {
					ap.setPariseTime(new Date());
					apService.addParisedActive(ap);
					returnAc.setPraiseCount(returnAc.getPraiseCount() == null ? 0 : returnAc.getPraiseCount() + 1);
					accService.updateAfficheInfo(returnAc);
					output(response, JsonUtil.buildFalseJson("0", "点赞成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("2", "点赞失败"));
					log.info("用户" + ap.getUserId() + "点赞活动" + ap.getAfficheId() + "失败，失败原因" + e);
				}
			} else {
				output(response, JsonUtil.buildFalseJson("1", "点赞失败,该活动不存在!"));
			}
		}

		// 获取我点赞的活动
		@RequestMapping("/myParise")
		public void appShowMyPariseActiveList(HttpServletResponse response, AfficheParise ap, Integer pageNo,
				Integer pageSize) {
			List<AfficheParise> apList = apService.findMyPariseList(ap, (pageNo - 1) * pageSize, pageSize);
			output(response, JsonUtil.buildJson(apList));
		}

}
