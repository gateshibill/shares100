package com.cofc.controller.video;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cofc.pojo.video.Source;
import com.cofc.service.video.SourceService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;


@Controller
@RequestMapping("/video")
public class SourceController extends BaseUtil {
	@Resource
	private SourceService sourceService;
	public static Logger log = Logger.getLogger(SourceController.class);

	// 1.添加节点资源
	@RequestMapping("/addSource")
	public void addSource(HttpServletRequest request, HttpServletResponse response, Source source) throws IOException {
		sourceService.addSource(source);
		output(response, JsonUtil.buildSuccessJson("0", "success"));
	}

	// 2.更新节点资源
	@RequestMapping("/updateSource")
	public void updateSource(HttpServletRequest request, HttpServletResponse response, Source source)
			throws IOException {
		Source s = sourceService.getSourceById(source.getId());
		s.setLastTime(new Date());
		s.setStatus(0);
		sourceService.updateSource(s);
		output(response, JsonUtil.bulidObjectJson(s));
	}

	// 3.获取可用节点资源
	@RequestMapping("/getAvailabeSourceList")
	public void getAvailabeSourceList(HttpServletRequest request, HttpServletResponse response, Source source)
			throws IOException {
		List<Source> list = sourceService.getSourceListByVodIdAndSubId(source.getVodId(), source.getSubId(), 0, 10);
		output(response, JsonUtil.buildCustomJson("0", "success", list));
	}
}
