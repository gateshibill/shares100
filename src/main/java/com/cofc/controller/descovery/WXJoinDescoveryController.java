package com.cofc.controller.descovery;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.util.BaseUtil;

/**
 * 用户加入项目
 * 
 * @author chen
 *
 */
@Controller
@RequestMapping("/wx/descovery")
public class WXJoinDescoveryController extends BaseUtil {
	public static Logger log = Logger.getLogger("WXJoinDescoveryController");

	@RequestMapping("/joinDescovery")
	public void userJoinProject(HttpServletResponse response, Integer userId, Integer projectId) {
//		try {
//			ProjectJoin join = new ProjectJoin();
//			join.setJoinedUserId(userId);
//			join.setJoinedProjectId(projectId);
//			join.setJoinTime(new Date());
//			pjoinService.userJoinProject(join);
//			ProjectCommon thisPP = ppService.getProjectById(projectId);
//			thisPP.setJoinedCount(thisPP.getJoinedCount() + 1);
//			ppService.updateProjectInfo(thisPP);
//			output(response, JsonUtil.buildFalseJson("0", "加入成功!"));
//		} catch (Exception e) {
//			output(response, JsonUtil.buildFalseJson("1", "对不起，加入失败！"));
//			log.info("用户" + userId + "加入项目" + projectId + "失败，失败原因" + e);
//		}
	}
}
