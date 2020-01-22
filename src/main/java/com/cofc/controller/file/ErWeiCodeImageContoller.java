package com.cofc.controller.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import com.alibaba.fastjson.JSON;


/**
 * 生成微信二维码
 * @author admin
 *
 */
public class ErWeiCodeImageContoller{
	
	public static Logger log = Logger.getLogger("ErWeiCodeImageContoller");
	
	
	public static String upload(String access_token, String path,HttpServletRequest request) throws Exception {
		log.info("进入调用方法开始");
		
		String URL = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode";
		log.info("获取url="+URL);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		String filename = format.format(now) + random;// 随机生成的二维码图片名
		log.info("随机生成的二维码图片名为："+filename);
		
		String contextPath = request.getContextPath();// 项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String url1 = request.getScheme() + "://" + request.getServerName() + port + contextPath + "/erweiCode/";
		log.info("保存全部路径为："+url1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("path", path);
		map.put("width", 430);
		String json = null;
		try {
			json = JSON.toJSONString(map,true);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("json"+json);
		httpPostWithJSON(URL + "?access_token=" + access_token, json.toString(), filename,"erweiCode",request);
//		String downloadUrl = url1 + jumpUrl + "/";
		String downloadUrl = url1;
		// 返回给前端的后台服务器文件下载路径
		String downloadfileUrl = downloadUrl + filename + ".jpg";
		log.info("最终生成的图片路径==="+downloadfileUrl);
		return downloadfileUrl;
		
	}
    
	/**
	 * 获取圆形的二维码
	 * @param scensStr
	 * @param accessToken
	 * @return
	 * @throws Exception 
	 */
	public static String getMiniqrQR(String path,Integer width,String scensStr,String accessToken,HttpServletRequest request) throws Exception{
		log.info("获取圆形二维码");
		String apiUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		String filename = format.format(now) + random;// 随机生成的二维码图片名
		log.info("随机生成的二维码图片名为："+filename);
		
		String contextPath = request.getContextPath();// 项目名
		//String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String port = "";
		if(request.getServerPort() != 80 && request.getServerPort() != 443){
			port = ":" + request.getServerPort();
		}
		String header = request.getScheme();
		log.info("协议前--"+header);
		String newheader = "";
		if(!header.equals("https")){
			newheader =  header.replace("http", "https");
		}else{
			newheader = header;
		}
		log.info("协议后--"+newheader);
		String url1 = newheader + "://" + request.getServerName() + port + contextPath + "/erweiCode/";
		log.info("保存全部路径为："+url1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", path);
		map.put("width", width);
		map.put("scene",scensStr);
		map.put("is_hyaline", false);//是否透明
		map.put("auto_color", false);
		 Map<String,Object> line_color = new HashMap<>();
         line_color.put("r", 0);
         line_color.put("g", 0);
         line_color.put("b", 0);
         map.put("line_color", line_color);
		String json = null;
		try {
			json = JSON.toJSONString(map,true);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("json"+json);
		log.info("json"+json);
		httpPostWithJSON(apiUrl, json.toString(), filename,"erweiCode",request);
		String downloadUrl = url1;
		// 返回给前端的后台服务器文件下载路径
		String downloadfileUrl = downloadUrl + filename + ".jpg";
		log.info("最终生成的图片路径==="+downloadfileUrl);
		return downloadfileUrl;		
	}
	
	/**
	 * 生成商品分享二维码
	 * @param path
	 * @param width
	 * @param scensStr
	 * @param accessToken
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String getGoodShareQR(String path,Integer width,String scensStr,String accessToken,HttpServletRequest request) throws Exception{
		log.info("获取圆形二维码");
		String apiUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		String filename = format.format(now) + random;// 随机生成的二维码图片名
		log.info("随机生成的二维码图片名为："+filename);
		
		String contextPath = request.getContextPath();// 项目名
		//String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String port = "";
		log.info("端口号---"+request.getServerPort());
		if(request.getServerPort() != 80 && request.getServerPort() != 443){
			port = ":" + request.getServerPort();
		}
		String header = request.getScheme();
		log.info("协议前--"+header);
		String newheader = "";
		if(!header.equals("https")){
			newheader =  header.replace("http", "https");
		}else{
			newheader = header;
		}
		log.info("协议后--"+newheader);
		String url1 = newheader + "://" + request.getServerName() + port + contextPath + "/goodShareCode/";
		log.info("保存全部路径为："+url1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", path);
		map.put("width", width);
		map.put("scene",scensStr);
		map.put("is_hyaline", false);//是否透明
		map.put("auto_color", false);
		 Map<String,Object> line_color = new HashMap<>();
         line_color.put("r", 0);
         line_color.put("g", 0);
         line_color.put("b", 0);
         map.put("line_color", line_color);
		String json = null;
		try {
			json = JSON.toJSONString(map,true);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("json"+json);
		log.info("json"+json);
		httpPostWithJSON(apiUrl, json.toString(), filename,"goodShareCode",request);
		String downloadUrl = url1;
		// 返回给前端的后台服务器文件下载路径
		String downloadfileUrl = downloadUrl + filename + ".jpg";
		log.info("最终生成的图片路径==="+downloadfileUrl);
		return downloadfileUrl;		
	}
	
	/**
	 * 小程序登陆二维码
	 * @param path
	 * @param width
	 * @param scensStr
	 * @param accessToken
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String vijLoginQR(String path,Integer width,String scensStr,String accessToken,HttpServletRequest request) throws Exception{
		log.info("获取圆形二维码");
		String apiUrl = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		String filename = format.format(now) + random;// 随机生成的二维码图片名
		log.info("随机生成的二维码图片名为："+filename);
		
		String contextPath = request.getContextPath();// 项目名
		//String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String port = "";
		log.info("端口号---"+request.getServerPort());
		if(request.getServerPort() != 80 && request.getServerPort() != 443){
			port = ":" + request.getServerPort();
		}
		String header = request.getScheme();
//		log.info("协议前--"+header);
//		String newheader = "";
//		if(!header.equals("https")){
//			newheader =  header.replace("http", "https");
//		}else{
//			newheader = header;
//		}
		//log.info("协议后--"+newheader);
		//String url1 = newheader + "://" + request.getServerName() + port + contextPath + "/vijUserLoginCode/";
		String url1 = header + "://" + request.getServerName() + port + contextPath + "/vijUserLoginCode/";
		log.info("保存全部路径为："+url1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", path);
		map.put("width", width);
		map.put("scene",scensStr);
		map.put("is_hyaline", false);//是否透明
		map.put("auto_color", false);
		 Map<String,Object> line_color = new HashMap<>();
         line_color.put("r", 0);
         line_color.put("g", 0);
         line_color.put("b", 0);
         map.put("line_color", line_color);
		String json = null;
		try {
			json = JSON.toJSONString(map,true);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.info("json"+json);
		log.info("json"+json);
		httpPostWithJSON(apiUrl, json.toString(), filename,"vijUserLoginCode",request);
		String downloadUrl = url1;
		// 返回给前端的后台服务器文件下载路径
		String downloadfileUrl = downloadUrl + filename + ".jpg";
		log.info("最终生成的图片路径==="+downloadfileUrl);
		return downloadfileUrl;		
	}
	
	public static String httpPostWithJSON(String url, String json, String id,String fileName,HttpServletRequest request) throws Exception {
		log.info("拿到token="+url);
		String result = null;
		// 将JSON进行UTF-8编码,以便传输中文
		String contextPath = request.getContextPath();// 项目名
		String path = request.getServletContext().getRealPath("/") + fileName + "/";// 文件所在盘路径
		log.info("项目盘路径为"+path);
		//String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String port = "";
		if(request.getServerPort() != 80 && request.getServerPort() != 443){
			port = ":" + request.getServerPort();
		}
		String url1 = request.getScheme() + "://" + request.getServerName() + port + contextPath + "/"+fileName+"/";
		String minUrl = url1;// 缩略图覆盖原图、
		
		//String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);
		log.info("拿到项目的路径="+url1);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		StringEntity se = new StringEntity(json);
		log.info("应用application/json="+se);
		se.setContentType("application/json");
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "UTF-8"));
		httpPost.setEntity(se);
		// httpClient.execute(httpPost);
		log.info("重点看是否报错"+httpPost);
		HttpResponse response = httpClient.execute(httpPost);
		log.info("是否localhost="+response);
		if (response != null) {
			HttpEntity resEntity = response.getEntity();
			log.info("resEntity："+resEntity.toString());
			if (resEntity != null) {
				InputStream instreams = resEntity.getContent();
				log.info("日志流"+instreams);
				// String uploadSysUrl = "D:\\upload" + "/";
				File saveFile = new File(url1 + id + ".jpg");
				// 判断这个文件（saveFile）是否存在
				if (!saveFile.getParentFile().exists()) {
					// 如果不存在就创建这个文件夹
					saveFile.getParentFile().mkdirs();
				}
				log.info("文件是否存在"+saveFile);
				saveToImgByInputStream(instreams, path, id + ".jpg");
			}
		}
		 result = minUrl;
		log.info("生成图片成功"+result);
		return result;
	}

	/*
	 * @param instreams 二进制流
	 * 
	 * @param imgPath 图片的保存路径
	 * 
	 * @param imgName 图片的名称
	 * 
	 * @return 1：保存正常 0：保存失败
	 */
	public static int saveToImgByInputStream(InputStream instreams, String imgPath, String imgName)
			throws FileNotFoundException {
		int stateInt = 1;
		File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
		log.error("图片路径连接"+file);
		FileOutputStream fos = new FileOutputStream(file);
		if (instreams != null) {
			try {
				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = instreams.read(b)) != -1) {
					fos.write(b, 0, nRead);
				}

			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
				log.error(e.getMessage()+"容易报错"+stateInt);
			} finally {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.error(e.getMessage()+"报错2.0");
				}
			}
		}
		return stateInt;
	}
	public static void main(String[] args) throws FileNotFoundException {
//		File saveFile = new File("C:\\Users\\wangzf\\Desktop\\images\\0.jsp");
		FileInputStream bis =new  FileInputStream("C:\\Users\\wangzf\\Desktop\\images\\0.jpg");
		ErWeiCodeImageContoller.saveToImgByInputStream(bis, "C:\\Users\\wangzf\\Desktop\\images", "1.jpg");
	}
}
