package com.cofc.controller.tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.BackUser;
import com.cofc.pojo.TagCommon;
import com.cofc.pojo.UserCommon;
import com.cofc.service.TagCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/tag")
public class TagConroller extends BaseUtil{
	@Resource
	private TagCommonService tagService;
	public static Logger log = Logger.getLogger("GoodsController");
	
	@RequestMapping("/toTagList")
	public ModelAndView goTagListJsp(ModelAndView modelView){
//		List<TagCommon> tagList = tagService.showAllTag();
		modelView.setViewName("tagManage/tagList");
		return modelView;
	}
	@RequestMapping("/showTagList")
	public void showTagDataLis(HttpServletRequest request,HttpServletResponse response,TagCommon tag,UserCommon tagUser,String dataRange,Integer pageNo,Integer pageSize) throws ParseException{
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		request.getSession().setAttribute("tagPageNo",pageNo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dataRange != null && !dataRange.equals("")) {
			String[] myData = dataRange.split(" -| ");
			startTime = myData[0];
			endTime = myData[2];
		}
		if (startTime != null) {
			startTime = startSdf.format(sdf.parse(startTime));
		}
		if (endTime != null) {
			endTime = endSdf.format(sdf.parse(endTime));
		}
		int tagsCount = tagService.getTagCount(tag, tagUser, startTime, endTime);
		List<TagCommon> tagList = tagService.showAllTag(tag,tagUser,startTime,endTime,(pageNo-1)*pageSize,pageSize);
//		output(response, JsonUtil.buildBackJson(tagList, tagsCount));
		output(response, JsonUtil.buildJsonByTotalCount(tagList, tagsCount));
	}
	@RequestMapping("updateTagInfo")
	public String updateTagCommonInfo(HttpServletRequest request,HttpServletResponse response,TagCommon tag){
		int currPage = (int) request.getSession().getAttribute("tagPageNo");
		tagService.updateTagInfo(tag);
		return "redirect:./showTagList.do?pageNo="+currPage;
	}
	
	//添加标签页面
	@RequestMapping("/addTage")
	public ModelAndView addTage(ModelAndView modelAndView){
		modelAndView.setViewName("tagManage/addTage");
		return modelAndView;
	}
	
	//添加标签
	@RequestMapping("/doaddTag")
	public void doaddTag(HttpServletResponse response,TagCommon tag,HttpServletRequest request){
		BackUser buser = (BackUser) request.getSession().getAttribute("loginer");
		TagCommon tag1 = tagService.getTagCommonName(tag.getTagName(), tag.getTagType());
		if (tag1 != null) {
			output(response, JsonUtil.buildFalseJson("1", "标签已存在!"));
		} else {
			tag.setCreateTime(new Date());
			tag.setCreateUser(buser.getUserId());
			tagService.addNewTag(tag);
			output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
		}
	}
	//删除标签
	@RequestMapping("/deleteTag")
	public void deleteTag(HttpServletResponse response,Integer tagId){
		try {
			tagService.deleteTag(tagId);
			output(response, JsonUtil.buildFalseJson("1", "删除成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "删除失败!"));
		}
	}
}
