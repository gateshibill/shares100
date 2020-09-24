package com.cofc.controller.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cofc.service.CommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

//import com.ylb.service.OfPropertyService;
//import com.ylb.util.JsonUtil;

@Controller
@RequestMapping("/upload")
public class UploadFile  extends BaseUtil {

	private final static String FILE_SERVER_DIR = "file_server_dir";
	private final static String FILE_SERVER_URL = "file_server_url";
	//private final static String BASE_URL = "http://file.freejoymusic.com/";
	//private final static String DIR = "tmp";// 其他上传文件

	@Autowired
	private CommonService commonService;

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(HttpServletResponse response, @RequestParam(value = "file", required = false) MultipartFile file, InputStream inputStream,
			HttpServletRequest request) {
		System.out.println("upload:" + file.getName() + "/" + file.getSize());
		output(response,  uploadFileToServer(file, request, "", ""));
	}

	private String uploadFileToServer(MultipartFile file, HttpServletRequest request, String uploadDir, String subffix) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		int code = 0;
		String msg = "上传失败";
		String url = "";// 文件上传后访问url

		if (file == null) {
			code = 1;
			return JsonUtil.newBuildFalseJson(code+"", "file is null", url);
		}
		if (file.getSize() > 50 * 1024 * 1024) { // 50M
			code = 2;
			return JsonUtil.newBuildFalseJson(code+"", "file is size 超过50M", url);
		}

		Date now = new Date();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		try {
			// 本身带了扩展名，用文件自带的
			String subffix1 = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			if (null != subffix1) {
				subffix = subffix1;
			}
		} catch (Exception e) {
			System.out.println("exception:get file subffix ");
		}

		String filename = format.format(now) + random + subffix;// 文件名
		String dir=commonService.getValue(FILE_SERVER_DIR).getValue();
		String baseUrl=commonService.getValue(FILE_SERVER_URL).getValue();
		//确保路径存在
		checkDirExist(dir);
		checkDirExist(dir + uploadDir);	
		String path = dir + uploadDir + "/" + filename;// 文件所在盘路径
		url = baseUrl + uploadDir + "/" + filename;
		
		System.out.println("uploadFileToServer() "+path+"/"+url);
		
		File oldFile = new File(path);
		try {
			file.transferTo(oldFile);
			msg = "success";
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return JsonUtil.newBuildFalseJson("3", e.getMessage(), url);
		} catch (IOException e) {
			e.printStackTrace();
			return JsonUtil.newBuildFalseJson("4", e.getMessage(), url);
		}
		System.out.println("url:" + url);
		return JsonUtil.newBuildFalseJson(code+"", msg, url);
	}

	public static void checkDirExist(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("dir exists:"+filePath);
			} else {
				System.out.println("fail to create dir:" + file.getAbsolutePath());
			}
		} else {
			System.out.println("dir not exists, create:" + file.getAbsolutePath());
			file.mkdir();
		}
	}
}
