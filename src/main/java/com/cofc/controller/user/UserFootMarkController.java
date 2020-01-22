package com.cofc.controller.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserFootMark;
import com.cofc.service.UserFootMarkService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 用户足迹相关接口,记录用户行为，为了推荐系统
 * @author chenhui
 *
 */
@Controller
@RequestMapping("/wx/footMark")
public class UserFootMarkController extends BaseUtil {
	@Resource
	private UserFootMarkService ufmService;

	private static Logger log = Logger.getLogger("UserFootMarkController");

	@RequestMapping("/myMarked")
	public void findMyFootMarked(HttpServletResponse response, Integer userId, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<UserFootMark> ufmList = ufmService.findMyfootMark(userId, (pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(ufmList));
	}

	@RequestMapping("/addMark")
	public void addMyFootMark(HttpServletResponse response, UserFootMark ufm) {
		String markedName = "";
		UserFootMark marked = ufmService.getMyMakedRecored(ufm);
		if (ufm.getMarkedGoodsId() == null && ufm.getMarkedUserId() != null) {
			markedName = "对用户" + ufm.getMarkedUserId();
			if (marked == null) {
				try {
					ufm.setMarkedTime(new Date());
					ufmService.addNewFootMark(ufm);
					output(response, JsonUtil.buildFalseJson("0", "记录成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("1", "记录失败!"));
					log.info("用户" + ufm.getUserId() + "增加" + markedName + "的记录失败，失败原因" + e);
				}
			} else {
				try {
					marked.setMarkedTime(new Date());
					ufmService.reMarkMyfoot(marked);
					output(response, JsonUtil.buildFalseJson("0", "记录成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("1", "记录失败!"));
					log.info("用户" + ufm.getUserId() + "增加" + markedName + "的记录失败，失败原因" + e);
				}
			}
		} else {
			markedName = "对商品" + ufm.getMarkedGoodsId();
			if (marked == null) {
				try {
					ufm.setMarkedTime(new Date());
					ufmService.addNewFootMark(ufm);
					output(response, JsonUtil.buildFalseJson("0", "纪录成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("1", "纪录失败!"));
					log.info("用户" + ufm.getUserId() + "增加" + markedName + "的记录失败，失败原因" + e);
				}
			} else {
				try {
					marked.setMarkedTime(new Date());
					ufmService.reMarkMyfoot(marked);
					output(response, JsonUtil.buildFalseJson("0", "记录成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("1", "记录失败!"));
					log.info("用户" + ufm.getUserId() + "增加" + markedName + "的记录失败，失败原因" + e);
				}
			}
		}
	}
}
