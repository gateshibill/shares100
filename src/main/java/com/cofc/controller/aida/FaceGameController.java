package com.cofc.controller.aida;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.UserBackuserRelation;
import com.cofc.pojo.UserRole;
import com.cofc.pojo.aida.FaceGame;
import com.cofc.pojo.aida.FaceGameBehavior;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserRoleService;
import com.cofc.service.aida.FaceGameBehaviorService;
import com.cofc.service.aida.FaceGameService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/aida")
public class FaceGameController extends BaseUtil {
	@Resource
	private FaceGameBehaviorService faceGameBehaviorService;
	@Resource
	private FaceGameService faceGameService;
	@Resource
	private UserRoleService userroleService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private UserBackuserRelationService uburelaService;
	
	public static Logger log = Logger.getLogger("ActionReportController");

	// 1.aiclient上报信息
	@RequestMapping("/reportFaceGameBehavior")
	public void reportFaceGameBehavior(HttpServletRequest request,HttpServletResponse response, FaceGameBehavior fgb) throws IOException {       
		fgb.setIp(getIpAddr(request));
		log.info(fgb.getId() + "(" + fgb.getName() + ")" + "上报:" + fgb.getType() + "|"
				+ fgb.getContent() + "|" + fgb.getTime());
		
		fgb.setCreateTime(new Date());
		faceGameBehaviorService.addFaceGameBehavior(fgb);
		output(response, JsonUtil.buildSuccessJson("0", "succes"));
	}
	
	// 2.客户端信息
	@RequestMapping("/reportFaceGame")
	public void reportFaceGame(HttpServletRequest request,HttpServletResponse response, FaceGame fg) throws IOException {
		fg.setIp(getIpAddr(request));
		
		log.info(fg.getIp() + "(" + fg.getName() + ")" + "上报:" + fg.getHostname() + "|"
				+ fg.getOsuser()+ "|" + fg.getOsversion());
		
		FaceGame faceGame=faceGameService.getFaceGameByEncrypt(fg.getEncrypt());
		if(faceGame!=null)
		{
			fg.setId(faceGame.getId());
			fg.setName(faceGame.getName());
			fg.setExpire(faceGame.getExpire());
			fg.setNotice(faceGame.getNotice());
			fg.setCreateTime(faceGame.getCreateTime());
			fg.setLastTime(new Date());		
			faceGameService.updateFaceGame(fg);
		}else
		{
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, 30);
			Date date = cal.getTime();
			fg.setLastTime(new Date());
			fg.setCreateTime(new Date());
			fg.setExpire(date);
			faceGameService.addFaceGame(fg);
		}
		output(response, JsonUtil.bulidObjectJson(fg));
	}
	
	 //3.ai后台管理
	@RequestMapping("/aiClientList")
	public ModelAndView aiClientList(ModelAndView modelAndView,HttpServletRequest request){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		//bu.setUserId(1);
		UserRole userRole = userroleService.getUserRoleById(bu.getUserId());
		String[] rolesarr = userRole.getRoleId().split(",");
		boolean isSuperm = false;
		for(String role:rolesarr){
			while("1".equals(role)){
				isSuperm = true;
				break;
			}
		}
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (isSuperm) {
			appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,null, null, null);
		} else {
			List<UserBackuserRelation> userbackList = uburelaService.getUserBackuserList(bu.getUserId());
			if (!userbackList.isEmpty()) {
				for (UserBackuserRelation user:userbackList) {
					appList = applicationService.findApplicationsByCriteria(null, null, null, user.getUserId(), null, null, null, null,null, null, null);
				}
			}
		}
		modelAndView.addObject("appList", appList);
		modelAndView.setViewName("/aiclient/index");
		return modelAndView;
	}
	
	@RequestMapping("/showAiClientList")
	public void showCarList(HttpServletResponse response,String dateRange ,Integer page, Integer limit, String  name) throws Exception{
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
//		String startTime = null;
//		String endTime = null;
//		if (dateRange != null && !dateRange.equals("")) {
//			String[] myData = dateRange.split(" -| ");
//			startTime = myData[0];
//			endTime = myData[2];
//		}
//		if (startTime != null) {
//			startTime = startSdf.format(sdf.parse(startTime));
//		}
//		if (endTime != null) {
//			endTime = endSdf.format(sdf.parse(endTime));
//		}
		List<FaceGame> carList = faceGameService.getFaceGameList((page-1)*limit,limit);
		int count = faceGameService.getFaceGameCount();
		output(response, JsonUtil.buildJsonByTotalCount(carList, count));
	}
	
	@RequestMapping("/deleteAiClent")
	public void deleteOrder(HttpServletResponse response,Integer id){
		try {
			faceGameService.deleteFaceGame(id);
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "删除失败!"));
		}
	}
	
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for"); 
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
            System.out.println("Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
            System.out.println("X-Real-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            System.out.println("getRemoteAddr ip: " + ip);
        } 
        System.out.println("获取客户端ip: " + ip);
        return ip;  
    }
}
