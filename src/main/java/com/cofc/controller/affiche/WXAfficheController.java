package com.cofc.controller.affiche;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.AfficheCommon;
import com.cofc.pojo.AfficheLeaveMessage;
import com.cofc.service.AfficheCommonService;
import com.cofc.service.AfficheLeaveMessageService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/affiche")
public class WXAfficheController extends BaseUtil {
	@Resource
	private AfficheCommonService affService;
	@Resource
	private AfficheLeaveMessageService almService;

	public static Logger log = Logger.getLogger("WXActiveController");

	// 前端发布公告
	@RequestMapping("/publishActive")
	public void publishActive(HttpServletResponse response, AfficheCommon acc) {
		if (acc.getAfficheTitle() == null || acc.getAfficheTitle().equals("")) {
			output(response, JsonUtil.buildFalseJson("1", "请输入活动信息"));
		} else {
			acc.setCreateTime(new Date());
			acc.setIsRemove(0);
			acc.setReadCount(0);
			acc.setPraiseCount(0);
			affService.publishCommonAffiche(acc);
			AfficheCommon returnAc = affService.getafficheById(acc.getAfficheId());
			List<AfficheCommon> acList = new ArrayList<AfficheCommon>();
			acList.add(returnAc);
			output(response, JsonUtil.buildJson(acList));
		}
	}

	// 前端展示公告列表
	@RequestMapping("/appActiveList")
	public void appShowActiveList(HttpServletResponse response, AfficheCommon ac, Integer loginPlat, Integer pageNo,
			Integer pageSize) {
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		if (ac == null) {
			ac = new AfficheCommon();
		}
		List<AfficheCommon> activeList = affService.findafficheList(ac, null, null, null, null, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(activeList));
	}

	// 小程序进入活动详情
	@RequestMapping("/appActiveDetails")
	public void appShowActiveDetails(HttpServletResponse response, AfficheCommon ac) {

	}
	
	// 公告详情
	@RequestMapping("/afficheDetails")
	public void afficheDetails(HttpServletResponse response, Integer afficheId) {
		if(afficheId==null||afficheId<=0){
			output(response, JsonUtil.buildFalseJson("1", "afficheId非法!"));
			return;
		}
		AfficheCommon aff = affService.getafficheById(afficheId);
		if(aff==null){
			output(response, JsonUtil.buildFalseJson("1", "公告不存在!"));
			return;
		}
		output(response, JsonUtil.objectToJson(aff));
	}

	// 小程序修改公告信息
	@RequestMapping("/updateActive")
	public void appUpdateActive(HttpServletResponse response, AfficheCommon acc) {
		AfficheCommon returnAc = affService.getafficheById(acc.getAfficheId());
		if (returnAc != null) {
//			if (acc.getPublisherId() != returnAc.getPublisherId()) {
//				output(response, JsonUtil.buildFalseJson("2", "您不是该活动的发布者，不能修改!"));
//			} else {
				affService.updateAfficheInfo(acc);
				List<AfficheCommon> acList = new ArrayList<AfficheCommon>();
				acList.add(returnAc);
				output(response, JsonUtil.buildJson(acList));
//			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "该活动不存在!"));
		}
	}

	// 公告留言
	@RequestMapping("/appLeaveAcMsg")
	public void appLeaveActiveMessage(HttpServletResponse response, AfficheLeaveMessage alm) {

		AfficheCommon returnAc = affService.getafficheById(alm.getAfficheId());
		if (returnAc == null) {
			output(response, JsonUtil.buildFalseJson("1", "该活动不存在!"));
		} else {
			alm.setCreateTime(new Date());
			almService.addNewMessage(alm);
			output(response, JsonUtil.buildFalseJson("0", "留言成功!"));
		}
	}

	// 公告留言列表
	@RequestMapping("/appAlmList")
	public void appLeaveActiveMessageList(HttpServletResponse response,Integer afficheId ,Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<AfficheLeaveMessage> almList = almService.findLeaveActiveMessage(afficheId,(pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(almList));
	}
}
