package com.cofc.controller.video;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cofc.pojo.aida.FaceGame;
import com.cofc.pojo.aida.FaceGameBehavior;
import com.cofc.pojo.video.Asset;
import com.cofc.pojo.video.Column;
import com.cofc.pojo.video.UserBean;
import com.cofc.pojo.video.VodBean;
import com.cofc.pojo.video.VodBeanWithBLOBs;
import com.cofc.service.video.AssetService;
import com.cofc.service.video.ColumnService;
import com.cofc.service.video.UserBeanMapper;
import com.cofc.service.video.VodBeanMapper;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/video")
public class ClientController extends BaseUtil {

	@Resource
	private AssetService assetService;
	@Resource
	private ColumnService columnService;

	@Resource
	private UserBeanMapper userService;

	@Resource
	private VodBeanMapper vodService;

	public static Logger log = Logger.getLogger("ClientController");

	// 终端：

	// 1.开机连接,登陆，验证
	@RequestMapping("/startup")
	public void startup(HttpServletRequest request, HttpServletResponse response, FaceGameBehavior fgb)
			throws IOException {
		// fgb.setIp(getIpAddr(request));
//		log.info(fgb.getId() + "(" + fgb.getName() + ")" + "上报:" + fgb.getType() + "|"
//				+ fgb.getContent() + "|" + fgb.getTime());
//		
//		fgb.setCreateTime(new Date());
//		faceGameBehaviorService.addFaceGameBehavior(fgb);
//		output(response, JsonUtil.buildSuccessJson("0", "succes"));
	}

	// 2.获得portal门户和栏目信息
	@RequestMapping("/getColumnList")
	public void getColumnList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Column> columnList = columnService.getColumnList(0);
		output(response, JsonUtil.buildCustomJson("0", "succes", columnList));
	}

	// 2.1 音乐栏目
	@RequestMapping("/getSongColumnList")
	public void getSongSColumnList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Column> columnList = columnService.getColumnList(1);
		output(response, JsonUtil.buildCustomJson("0", "succes", columnList));
	}

	// 2.2 获得电影
	@RequestMapping("/getMoives")
	public void getMoives(HttpServletRequest request, HttpServletResponse response, Integer columnId)
			throws IOException {
		List<Asset> assetList = assetService.getAssetListByColumnId(columnId, 1, 10);
		output(response, JsonUtil.buildCustomJson("0", "succes", assetList));
	}

	// 2.3 获得歌曲，
	@RequestMapping("/getSongs")
	public void getSongs(HttpServletRequest request, HttpServletResponse response, Integer columnId)
			throws IOException {
		List<Asset> assetList = assetService.getAssetListByColumnId(columnId, 1, 10);
		output(response, JsonUtil.buildCustomJson("0", "succes", assetList));
	}

	// 4.校验信息有效性,被VS调用
	@RequestMapping("/authToken")
	public void authToken(HttpServletRequest request, HttpServletResponse response, FaceGame fg) throws IOException {

		output(response, JsonUtil.bulidObjectJson(fg));
	}

	// 5.终端心跳，人机交互安全验证，更新有效期
	@RequestMapping("/heartbeat")
	public void heartbeat(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// output(response, JsonUtil.bulidObjectJson(fg));
	}

	// 6.账号生成，通过设备信息生成
	@RequestMapping("/addAccount")
	public void addAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// output(response, JsonUtil.bulidObjectJson(fg));
	}

	// 7.账号增删改查
	@RequestMapping("/getAccount")
	public void getAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// output(response, JsonUtil.bulidObjectJson(fg));
	}

	// 8.观看历史
	@RequestMapping("/getHistoryMoives")
	public void getHistoryMoives(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// output(response, JsonUtil.bulidObjectJson(fg));
	}

	// 8.收藏
	@RequestMapping("/getFavoriteMoives")
	public void getFavoriteMoives(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// output(response, JsonUtil.bulidObjectJson(fg));
	}

	// 9.用户
	@RequestMapping("/getUser")
	public void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserBean ub = userService.selectByPrimaryKey(0);
		output(response, JsonUtil.bulidObjectJson(ub));
	}

	// 10.用户
	@RequestMapping("/getVod")
	public void getVod(HttpServletRequest request, HttpServletResponse response) throws IOException {
		VodBean ub = vodService.selectByPrimaryKey(39026);
		output(response, JsonUtil.bulidObjectJson(ub));
	}

	// 10.用户
	@RequestMapping("/getVodsBycolumnId")
	public void getVodsBycolumnId(HttpServletRequest request, HttpServletResponse response, Integer typeId,
			Integer pageNo, Integer pageSize) throws IOException {
		List<VodBeanWithBLOBs> vbs = vodService.getVodListBycolumnId(typeId, pageNo, pageSize);
		output(response, JsonUtil.buildJson(vbs));
	}

}
