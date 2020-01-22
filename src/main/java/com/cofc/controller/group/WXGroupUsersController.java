package com.cofc.controller.group;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.GroupUsers;
import com.cofc.service.GroupUsersService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/groupUsers")
public class WXGroupUsersController extends BaseUtil {
	@Resource
	private GroupUsersService groupUService;
	public static Logger log = Logger.getLogger("GroupUsersController");

	@RequestMapping("/userList")
	public void groupUserList(HttpServletResponse response, Integer loginPlat,Integer isRenzheng,Integer isExamined, Integer pageNo,
			Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		List<GroupUsers> userList = groupUService.groupUsersList(loginPlat, isExamined,isRenzheng,(pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(userList));
	}
	
	//前端审核会员
	@RequestMapping("/examinUser")
	public void groupCreaterExaminUser(HttpServletResponse response, Integer loginPlat,Integer createrId,Integer joinerId) {
//		GroupUsers creater = groupUService.comfirmIsJoinThisGroup(createrId, loginPlat);
//		if(creater==null||creater.getIsCreater()!=1){
//			output(response, JsonUtil.buildFalseJson("2", "您不是该应用的管理员，不能审核！"));
//		}else{
			GroupUsers joiner = groupUService.comfirmIsJoinThisGroup(joinerId, loginPlat);
			if(joiner==null){
				output(response, JsonUtil.buildFalseJson("3", "该用户不是此应用的成员!"));
			} else {
				try{
					joiner.setIsExamined(1);
					groupUService.changeUserExaminedStatus(joiner);
					output(response, JsonUtil.buildFalseJson("0", "审核成功!"));
					log.info("应用"+loginPlat+"创建者"+createrId+"审核用户"+joinerId+"成功!");
				}catch(Exception e){
					log.info("应用"+loginPlat+"创建者"+createrId+"审核用户"+joinerId+"失败，失败原因"+e);
					output(response, JsonUtil.buildFalseJson("1", "审核失败"));
				}
			}
//		}
	}
	
	//前端审核会员
	@RequestMapping("/removeUserFromGroup")
	public void removeUserFromGroup(HttpServletResponse response, Integer loginPlat,Integer createrId,Integer joinerId) {
//		GroupUsers creater = groupUService.comfirmIsJoinThisGroup(createrId, loginPlat);
//		if(creater==null||creater.getIsCreater()!=1){
//			output(response, JsonUtil.buildFalseJson("2", "您不是该应用的管理员，不能审核！"));
//		}else{
			GroupUsers joiner = groupUService.comfirmIsJoinThisGroup(joinerId, loginPlat);
			if(joiner==null){
				output(response, JsonUtil.buildFalseJson("3", "该用户不是此应用的成员!"));
			}else{
				try{
					joiner.setIsExamined(0);
					groupUService.changeUserExaminedStatus(joiner);
					output(response, JsonUtil.buildFalseJson("0", "审核成功!"));
					log.info("应用"+loginPlat+"创建者"+createrId+"审核用户"+joinerId+"成功!");
				}catch(Exception e){
					log.info("应用"+loginPlat+"创建者"+createrId+"审核用户"+joinerId+"失败，失败原因"+e);
					output(response, JsonUtil.buildFalseJson("1", "审核失败"));
				}
			}
//		}
	}
}
