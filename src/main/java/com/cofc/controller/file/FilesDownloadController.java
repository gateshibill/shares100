package com.cofc.controller.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FilesDownloadController {

	@RequestMapping("/download")
	public HttpServletResponse downloadFile(String files, String nickName,String realName,HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String realPath = request.getServletContext().getRealPath("/userImage");
		List<File> filesList=new ArrayList<File>();
		String[] fileArray = files.split(",");
		for(String fileUrl:fileArray){
			File currFile = new File(realPath+"/"+fileUrl.substring(fileUrl.lastIndexOf("/")+2,fileUrl.length()));
			filesList.add(currFile);
		}
		/**
		 * 创建一个临时压缩文件， 我们会把文件流全部注入到这个文件中 这里的文件你可以自定义是.rar还是.zip
		 */
		File file = new File(realPath + "/"+("null".equals(realName)?nickName:realName)+".rar");
		if (!file.exists()) {
			// file.createNewFile();
			file = new File(realPath + "/"+("null".equals(realName)?nickName:realName)+".rar");
		}
		response.reset();
		// 创建文件输出流
		FileOutputStream fous = new FileOutputStream(file);
		/**
		 * 打包的方法我们会用到ZipOutputStream这样一个输出流, 所以这里我们把输出流转换一下
		 */
		ZipOutputStream zipOut = new ZipOutputStream(fous);
		/**
		 * 这个方法接受的就是一个所要打包文件的集合， 还有一个ZipOutputStream
		 */
		zipFile(filesList, zipOut);
		zipOut.close();
		fous.close();
		return downloadZip(file, response);
	}

	/**
	 * 把接受的全部文件打成压缩包
	 * 
	 * @param List<File>;
	 * @param org.apache.tools.zip.ZipOutputStream
	 */
	public static void zipFile(List files, ZipOutputStream outputStream) {
		int size = files.size();
		for (int i = 0; i < size; i++) {
			File file = (File) files.get(i);
			zipFile(file, outputStream);
		}
	}

	/**
	 * 根据输入的文件与输出流对文件进行打包
	 * 
	 * @param File
	 * @param org.apache.tools.zip.ZipOutputStream
	 */
	public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
		try {
			if (inputFile.exists()) {
				/**
				 * 如果是目录的话这里是不采取操作的， 至于目录的打包正在研究中
				 */
				if (inputFile.isFile()) {
					FileInputStream IN = new FileInputStream(inputFile);
					BufferedInputStream bins = new BufferedInputStream(IN, 512);
					// org.apache.tools.zip.ZipEntry
					ZipEntry entry = new ZipEntry(inputFile.getName());
					ouputStream.putNextEntry(entry);
					// 向压缩文件中输出数据
					int nNumber;
					byte[] buffer = new byte[512];
					while ((nNumber = bins.read(buffer)) != -1) {
						ouputStream.write(buffer, 0, nNumber);
					}
					// 关闭创建的流对象
					bins.close();
					IN.close();
				} else {
					try {
						File[] files = inputFile.listFiles();
						for (int i = 0; i < files.length; i++) {
							zipFile(files[i], ouputStream);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
		try {
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();

			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");

			// 如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				File f = new File(file.getPath());
				f.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
