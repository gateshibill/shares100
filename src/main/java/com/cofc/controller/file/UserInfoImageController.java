package com.cofc.controller.file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cofc.pojo.ImageSet;
import com.cofc.service.ImageSetService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@Controller
@RequestMapping("/userImageUpload")
public class UserInfoImageController extends BaseUtil{
	@Resource
	private ImageSetService isService;
	
	@RequestMapping(value = "/upload")
	public void upload(@RequestParam(value = "file")CommonsMultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String respJson = null;
		if (file == null) {
			respJson=JsonUtil.buildFalseJson("1", "上传文件为空!");
			output(response, respJson);
			return;
		}
		if (file.getSize() > 1000000) { // 1M
			respJson=JsonUtil.buildFalseJson("2", "文件大小限制在1M以内!");
			output(response, respJson);
			return;
		}
		Date now = new Date();
		String random = UUID.randomUUID().toString().replace("-", "").substring(0, 5);
		String filehouzui = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."),file.getOriginalFilename().length());
		String filename = format.format(now) + random + filehouzui;//上传后的文件名
		String oldpath = request.getServletContext().getRealPath("/") + "userImage" + "/" + filename;//文件所在盘路径
		String minpath = request.getServletContext().getRealPath("/") + "userImage" + "/" + 1+filename;//文件所在盘路径
		String contextPath = request.getContextPath();//项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
//		String url = request.getScheme() + "://" + request.getServerName() + port + contextPath + "/userImage/"
//				+ filename;//原图盘路径
		String minUrl = request.getScheme() + "://" + request.getServerName() + port + contextPath + "/userImage/"
				+ 1+filename;;//缩略访问路径
		File oldFile=new File(oldpath);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(oldFile);
        ImageSet imageS = isService.getImageSetInfo();
		saveMinPhoto(oldpath, minpath, imageS.getUimageWidth(), imageS.getUimageHeight());
		respJson = JsonUtil.buildFalseJson("0", minUrl);
		output(response, respJson);
	}

	// 等比例压缩
	public void saveMinPhoto(String srcURL, String deskURL, int Width, int Height) throws Exception {
		File srcFile = new File(srcURL);
		try {
			Image src = ImageIO.read(srcFile);
			int srcHeight = src.getHeight(null);
			int srcWidth = src.getWidth(null);
			int deskHeight = 0;// 缩略图高
			int deskWidth = 0;// 缩略图宽
			double srcScale = (double) srcHeight / srcWidth;
			/** 缩略图宽高算法 */
			if (srcWidth > Width && srcHeight > Height) {
				if (srcWidth / Width > srcHeight / Height) {
					deskWidth = Width;
					deskHeight = srcHeight * deskWidth / srcWidth;
				} else {
					deskHeight = Height;
					deskWidth = srcWidth * deskHeight / srcHeight;
				}
			} else {
				deskHeight = srcHeight;
				deskWidth = srcWidth;
			}
			BufferedImage tag = new BufferedImage(deskWidth, deskHeight, BufferedImage.TYPE_3BYTE_BGR);
			tag.getGraphics().drawImage(src, 0, 0, deskWidth, deskHeight, null); // 绘制缩小后的图
			FileOutputStream deskImage = new FileOutputStream(deskURL); // 输出到文件流
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(deskImage);
			encoder.encode(tag); // 近JPEG编码
			deskImage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
