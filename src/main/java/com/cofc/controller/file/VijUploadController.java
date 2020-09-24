package com.cofc.controller.file;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/vij/upload")
public class VijUploadController extends BaseUtil{
	@RequestMapping(value = "/upload")
	public void upload(@RequestParam(value = "file") CommonsMultipartFile file, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String respJson = null;
		if (file == null) {
			respJson = JsonUtil.buildFalseJson("1", "上传文件为空!");
			output(response, respJson);
			return;
		}
		if (file.getSize() > 1000000) { // 1M
			respJson = JsonUtil.buildFalseJson("2", "文件大小限制在1M以内!");
			output(response, respJson);
			return;
		}
		Date now = new Date();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		String filehouzui = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
		String filename = format.format(now) + random + filehouzui;// 文件名
		String path = request.getServletContext().getRealPath("/") + "vijimages" + "/" + filename;// 文件所在盘路径
		String contextPath = request.getContextPath();// 项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String url = request.getScheme() + "://" + request.getServerName() + port + contextPath + "/vijimages/"
				+ filename;
		String minUrl = url;// 缩略图覆盖原图、
		File oldFile = new File(path);
		// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		file.transferTo(oldFile);
		respJson = JsonUtil.buildFalseJson("0", minUrl);
		output(response, respJson);
	}
}
