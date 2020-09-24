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

/**
 * 商品图片 上传
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/testImage")
public class TestImage extends BaseUtil {

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
		String filehouzui = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),
				file.getOriginalFilename().length());
		String filename = format.format(now) + random + filehouzui;// 上传后的文件名
		String oldpath = request.getServletContext().getRealPath("/") + "goodsImage" + "/" + filename;// 文件所在盘路径
		String minpath = request.getServletContext().getRealPath("/") + "goodsImage" + "/";// 文件所在盘路径
		String contextPath = request.getContextPath();// 项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String url = request.getScheme() + "://" + request.getServerName() + port + contextPath + "/goodsImage/"
				+ filename;
		String minUrl = url;// 缩略图覆盖原图、
		File oldFile = new File(oldpath);
		// 通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		file.transferTo(oldFile);

		saveMinPhoto(oldpath, minpath, filename, filehouzui);
		respJson = JsonUtil.buildFalseJson("0", minUrl);
		output(response, respJson);
	}

	// 等比例压缩
	public void saveMinPhoto(String srcURL, String deskURL, String filename, String filehouzui) throws Exception {

		//ImageUtil imageDeal = new ImageUtil(srcURL, deskURL, filename, filehouzui);
		// 制定宽压缩
		// if (srcWidth > Width) {
		//imageDeal.zoom(720, 0);
		// }
		// 制定高压缩
		// if (srcHeight > Height) {
		// imageDeal.zoom(0, Height);
		// }
		// // 制定宽和高压缩
		// if (srcWidth > Width && srcHeight > Height) {
		// imageDeal.zoom(Width, Height);
		// }
		// 测试旋转
		// imageDeal.spin(200);
		// 测试马赛克
		// imageDeal.mosaic(10);

		// File srcFile = new File(srcURL);
		// Image src = ImageIO.read(srcFile);
		// int srcHeight = src.getHeight(null);
		// int srcWidth = src.getWidth(null);
		// int deskHeight = 0;// 缩略图高
		// int deskWidth = 0;// 缩略图宽
		// try {
		// Image src = ImageIO.read(srcFile);
		// int srcHeight = src.getHeight(null);
		// int srcWidth = src.getWidth(null);
		//
		// double srcScale = (double) srcHeight / srcWidth;
		// /** 缩略图宽高算法 */
		// if (srcWidth > Width && srcHeight > Height) {
		// if (srcWidth / Width > srcHeight / Height) {
		// deskWidth = Width;
		// deskHeight = srcHeight * deskWidth / srcWidth;
		// } else {
		// deskHeight = Height;
		// deskWidth = srcWidth * deskHeight / srcHeight;
		// }
		// } else {
		// deskHeight = srcHeight;
		// deskWidth = srcWidth;
		// }
		// BufferedImage tag = new BufferedImage(deskWidth, deskHeight,
		// BufferedImage.TYPE_3BYTE_BGR);
		// tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null);
		// // 绘制缩小后的图
		// FileOutputStream deskImage = new FileOutputStream(deskURL); // 输出到文件流
		// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
		// encoder.encode(tag); // 近JPEG编码
		// deskImage.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}
}
