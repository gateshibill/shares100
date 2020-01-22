package com.cofc.controller.tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.AllParentTag;
import com.cofc.pojo.ChildTags;
import com.cofc.pojo.DescoveryType;
import com.cofc.pojo.ProductTags;
import com.cofc.pojo.ProjectTags;
import com.cofc.pojo.SkillTags;
import com.cofc.pojo.TagCommon;
import com.cofc.pojo.WantedTags;
import com.cofc.service.ProductTagsService;
import com.cofc.service.ProjectTagsService;
import com.cofc.service.SkillTagsService;
import com.cofc.service.TagCommonService;
import com.cofc.service.WantedTagsService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/tag")
public class WXTagController extends BaseUtil {
	@Resource
	private TagCommonService tcService;
	@Resource
	private ProjectTagsService pjtService;
	@Resource
	private ProductTagsService pdtService;
	@Resource
	private SkillTagsService sktService;
	@Resource
	private WantedTagsService wttService;

	public static Logger log = Logger.getLogger("WXTagController");

	@RequestMapping("/showTag")
	public void findTagList(HttpServletResponse response, Integer tagType, Integer isChildTag, Integer pageNo,
			Integer pageSize) {
		if (tagType == null) {
			List<TagCommon> tagType1List = tcService.showTagsByqualification(1,null,(pageNo - 1) * pageSize,
					pageSize);
			List<TagCommon> tagType2List = tcService.showTagsByqualification(2,null,(pageNo - 1) * pageSize,
					pageSize);
			List<TagCommon> tagType3List = tcService.showTagsByqualification(3,null,(pageNo - 1) * pageSize,
					pageSize);
			List<TagCommon> tagType4List = tcService.showTagsByqualification(4,null,(pageNo - 1) * pageSize,
					pageSize);
			List<TagCommon> tcList1 = new ArrayList<TagCommon>();
			List<TagCommon> tcList2 = new ArrayList<TagCommon>();
			List<TagCommon> tcList3 = new ArrayList<TagCommon>();
			List<TagCommon> tcList4 = new ArrayList<TagCommon>();
			AllParentTag aptag1 = new AllParentTag();
			AllParentTag aptag2 = new AllParentTag();
			AllParentTag aptag3 = new AllParentTag();
			AllParentTag aptag4 = new AllParentTag();
			List<AllParentTag> apTagList = new ArrayList<AllParentTag>();
			aptag1.setTagType(1);
			tcList1.addAll(tagType1List);
			aptag1.setTagList(tcList1);
			
			aptag2.setTagType(2);
			tcList2.addAll(tagType2List);
			aptag2.setTagList(tcList2);
			
			aptag3.setTagType(3);
			tcList3.addAll(tagType3List);
			aptag3.setTagList(tcList3);
			
			aptag4.setTagType(4);
			tcList4.addAll(tagType4List);
			aptag4.setTagList(tcList4);
			
			apTagList.add(aptag1);
			apTagList.add(aptag2);
			apTagList.add(aptag3);
			apTagList.add(aptag4);
			output(response, JsonUtil.buildJson(apTagList));
		} else {
			List<TagCommon> tcommonList = tcService.showTagsByqualification(tagType,null,
					(pageNo - 1) * pageSize, pageSize);
			output(response, JsonUtil.buildJson(tcommonList));
		}
	}

	@RequestMapping("/addTag")
	public void findTagList(HttpServletResponse response, TagCommon tc) {
		TagCommon tCommon = tcService.findTagIsExist(tc);
		if (tCommon != null) {
			output(response, JsonUtil.buildFalseJson("1", "该标签已存在!"));
		} else {
			try {
				tc.setCreateTime(new Date());
				tcService.addNewTag(tc);
				output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
			} catch (Exception e) {
				output(response, JsonUtil.buildFalseJson("2", "添加失败!"));
				log.info("用户" + tc.getCreateUser() + "添加标签" + tc.getTagName() + "失败,失败原因" + e);
			}
		}

	}

	// 根据父标签类型找到子标签
	@RequestMapping("/showChildTag")
	public void findChildTagsByParentType(HttpServletResponse response, Integer tagType, Integer pageNo,
			Integer pageSize) {
		ChildTags child1 = new ChildTags();
		ChildTags child2 = new ChildTags();
		ChildTags child3 = new ChildTags();
		ChildTags child4 = new ChildTags();
		List<ChildTags> resultList = new ArrayList<ChildTags>();
		if (tagType == 1) {
			List<ProjectTags> pjtags1 = pjtService.findProjectTagsByOrder(1, (pageNo - 1) * pageSize, pageSize);
			List<ProjectTags> pjtags2 = pjtService.findProjectTagsByOrder(2, (pageNo - 1) * pageSize, pageSize);
			List<ProjectTags> pjtags3 = pjtService.findProjectTagsByOrder(3, (pageNo - 1) * pageSize, pageSize);
			child1.setOrderId(1);
			child1.setPjtList(pjtags1);
			child2.setOrderId(2);
			child2.setPjtList(pjtags2);
			child3.setOrderId(3);
			child3.setPjtList(pjtags3);
			resultList.add(child1);
			resultList.add(child2);
			resultList.add(child3);
			output(response, JsonUtil.buildJson(resultList));
		} else if (tagType == 2) {
			List<ProductTags> pjtags1 = pdtService.findProductTagsByOrder(1, (pageNo - 1) * pageSize, pageSize);
//			List<ProductTags> pjtags3 = pdtService.findProductTagsByOrder(3, (pageNo - 1) * pageSize, pageSize);
			child1.setOrderId(1);
			child1.setPdtList(pjtags1);
//			child3.setOrderId(3);
//			child3.setPdtList(pjtags3);
			resultList.add(child1);
//			resultList.add(child3);
			output(response, JsonUtil.buildJson(resultList));
		} else if (tagType == 3) {
			List<SkillTags> pjtags1 = sktService.findSkillTagsByOrder(1, (pageNo - 1) * pageSize, pageSize);
			List<SkillTags> pjtags2 = sktService.findSkillTagsByOrder(2, (pageNo - 1) * pageSize, pageSize);
			child1.setOrderId(1);
			child1.setSktList(pjtags1);
			child2.setOrderId(2);
			child2.setSktList(pjtags2);
			resultList.add(child1);
			resultList.add(child2);
			output(response, JsonUtil.buildJson(resultList));
		} else if (tagType == 4) {
			List<WantedTags> pjtags1 = wttService.findSkillTagsByOrder(1, (pageNo - 1) * pageSize, pageSize);
			child1.setOrderId(1);
			child1.setWttList(pjtags1);
			resultList.add(child1);
			output(response, JsonUtil.buildJson(resultList));
		}
	}

	@RequestMapping("/addChildTag")
	public void addChildTagByParentType(HttpServletResponse response, Integer tagType, Integer userId, String tagName,
			Integer tagOrder) {
		if (tagType == 1) {
			ProjectTags ptags = pjtService.getTagByName(tagName);
			if (ptags != null) {
				output(response, JsonUtil.buildFalseJson("1", "该标签/类型已存在!"));
			} else {
				ProjectTags pjt = new ProjectTags();
				pjt.setChildtagCreateTime(new Date());
				pjt.setChildtagCreateUser(userId);
				pjt.setChildtagName(tagName);
				pjt.setChildtagOrder(tagOrder);
				pjt.setIsUsing(1);
				pjtService.addNewProjectTag(pjt);
				output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
			}
		} else if (tagType == 2) {
			ProductTags ptags = pdtService.getTagByName(tagName);
			if (ptags != null) {
				output(response, JsonUtil.buildFalseJson("1", "该标签/类型已存在!"));
			} else {
				ProductTags pdt = new ProductTags();
				pdt.setChildtagCreateTime(new Date());
				pdt.setChildtagCreateUser(userId);
				pdt.setChildtagName(tagName);
				pdt.setChildtagOrder(tagOrder);
				pdt.setIsUsing(1);
				pdtService.addNewProductTags(pdt);
				output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
			}
		} else if (tagType == 3) {
			SkillTags ptags = sktService.getTagByName(tagName);
			if (ptags != null) {
				output(response, JsonUtil.buildFalseJson("1", "该标签/类型已存在!"));
			} else {
				SkillTags skt = new SkillTags();
				skt.setChildtagCreateTime(new Date());
				skt.setChildtagCreateUser(userId);
				skt.setChildtagName(tagName);
				skt.setChildtagOrder(tagOrder);
				skt.setIsUsing(1);
				sktService.addNewSkillTags(skt);
				output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
			}
		} else if (tagType == 4) {
			WantedTags ptags = wttService.getTagByName(tagName);
			if (ptags != null) {
				output(response, JsonUtil.buildFalseJson("1", "该标签/类型已存在!"));
			} else {
				WantedTags wtt = new WantedTags();
				wtt.setChildtagCreateTime(new Date());
				wtt.setChildtagCreateUser(userId);
				wtt.setChildtagName(tagName);
				wtt.setChildtagOrder(tagOrder);
				wtt.setIsUsing(1);
				wttService.addNewWantedTags(wtt);
				output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
			}
		}
	}
}
