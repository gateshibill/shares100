package com.cofc.controller.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.GoodsEvaluation;
import com.cofc.service.GoodsEvaluationService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/evaluation")
public class WXGoodsEvaluationController extends BaseUtil {
	@Resource
	private GoodsEvaluationService gevaService;
	public static Logger log = Logger.getLogger("WXGoodsEvaluationController");

	/**
	 * 商品的评价列表
	 * 
	 * @param response
	 * @param goodsId
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/goodsEvaList")
	public void findGoodsEvaluations(HttpServletResponse response, Integer goodsId, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 5;
		}
		List<GoodsEvaluation> evaluationList = gevaService.findAllParentEvaluation(goodsId, (pageNo - 1) * pageSize,
				pageSize);
		List<Integer> idsList = new ArrayList<Integer>();
		for (GoodsEvaluation ge : evaluationList) {
			idsList.add(ge.getEvaluationId());
		}
		List<GoodsEvaluation> addedEvaList = gevaService.findAddedEvaluation(idsList);
		for (GoodsEvaluation evaluation : evaluationList) {
			for (GoodsEvaluation addedEva : addedEvaList) {
				if (evaluation.getEvaluationId() == addedEva.getParentId()) {
					evaluation.setAddedEvaluation(addedEvaList);
				}
			}
		}
		output(response, JsonUtil.buildJson(evaluationList));
	}

	/**
	 * 评价商品
	 * 
	 * @param response
	 * @param evaluation
	 */
	@RequestMapping("/evaGoods")
	public void userEvaluationGoods(HttpServletResponse response, GoodsEvaluation evaluation) {
		if (evaluation.getParentId() != null) {
			GoodsEvaluation parentEvaluation = gevaService.findEvaluationById(evaluation.getParentId());
			if (parentEvaluation != null && parentEvaluation.getEvaluationUser() == evaluation.getEvaluationUser()) {// 追加评论的主评论存在
				evaluation.setEvaluationTime(new Date());
				try {
					gevaService.addNewEvaluation(evaluation);
					output(response, JsonUtil.buildFalseJson("0", "评论成功!"));
				} catch (Exception e) {
					log.info("用户" + evaluation.getEvaluationUser() + "追加评论商品" + evaluation.getEvaluationGoods()
							+ "失败，失败原因" + e);
					output(response, JsonUtil.buildFalseJson("1", "评论失败!"));
				}

			} else {// 追加评论的主评论不存在
				evaluation.setParentId(null);// 主评论赋空值
				evaluation.setEvaluationTime(new Date());
				try {
					gevaService.addNewEvaluation(evaluation);
					output(response, JsonUtil.buildFalseJson("0", "评论成功!"));
				} catch (Exception e) {
					log.info("用户" + evaluation.getEvaluationUser() + "评论商品" + evaluation.getEvaluationGoods()
							+ "失败，失败原因" + e);
					output(response, JsonUtil.buildFalseJson("1", "评论失败!"));
				}
			}
		} else {
			evaluation.setEvaluationTime(new Date());
			try {
				gevaService.addNewEvaluation(evaluation);
				output(response, JsonUtil.buildFalseJson("0", "评论成功!"));
			} catch (Exception e) {
				log.info("用户" + evaluation.getEvaluationUser() + "评论商品" + evaluation.getEvaluationGoods() + "失败，失败原因"
						+ e);
				output(response, JsonUtil.buildFalseJson("1", "评论失败!"));
			}
		}
	}
}
