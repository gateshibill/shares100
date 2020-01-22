package com.cofc.controller.weiaijia;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.vij.GoodComment;
import com.cofc.pojo.vij.GoodCommentPraise;
import com.cofc.service.vij.GoodCommentPraiseService;
import com.cofc.service.vij.GoodCommentService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/vij/comment")
public class VijGoodCommentController extends BaseUtil{
	@Resource
	private GoodCommentService commentService;//商品评论
	@Resource
	private GoodCommentPraiseService praiseService;//评论点赞
	public static Logger log = Logger.getLogger("VijGoodCommentController");
	/**
	 * 评论
	 * @param response
	 * @param comment
	 */
	@RequestMapping("/addComment")
	public void addComment(HttpServletResponse response,GoodComment comment){
		comment.setCreateTime(new Date());
		commentService.addComment(comment);
		output(response,JsonUtil.buildFalseJson("0", "评论商品成功"));
	}
	/**
	 * 获取评论列表
	 * @param response
	 * @param comment
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getCommentList")
	public void getCommentList(HttpServletResponse response,GoodComment comment,Integer page,Integer limit,Integer status){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 20;
		}
		comment.setLevel(1);
		int count = commentService.getCommentCount(comment, status);
		List<GoodComment> lists = commentService.getCommentList(comment,status,(page-1), limit);
		for (GoodComment goodComment : lists) {
			List<GoodComment> list = commentService.getChildList(goodComment.getCid());
			goodComment.setChildList(list);
		}
		output(response,JsonUtil.buildJsonByTotalCount(lists, count));
	}
	/**
	 * 更新评论点赞
	 * @param response
	 * @param praise
	 */
	@RequestMapping("/updateCommentPraise")
	public void updateCommentPraise(HttpServletResponse response,GoodCommentPraise praise){
		
	}
}
