package com.cofc.pojo;

import java.util.Date;
import java.util.List;

public class GoodsEvaluation {
	private Integer evaluationId;
	private Integer evaluationUser;
	private Integer evaluationGoods;
	private Date evaluationTime;
	private Integer parentId;
	private UserCommon evaluUser;
	private List<GoodsEvaluation> addedEvaluation;

	public Integer getEvaluationId() {
		return evaluationId;
	}

	public void setEvaluationId(Integer evaluationId) {
		this.evaluationId = evaluationId;
	}

	public Integer getEvaluationUser() {
		return evaluationUser;
	}

	public void setEvaluationUser(Integer evaluationUser) {
		this.evaluationUser = evaluationUser;
	}

	public Integer getEvaluationGoods() {
		return evaluationGoods;
	}

	public void setEvaluationGoods(Integer evaluationGoods) {
		this.evaluationGoods = evaluationGoods;
	}

	public Date getEvaluationTime() {
		return evaluationTime;
	}

	public void setEvaluationTime(Date evaluationTime) {
		this.evaluationTime = evaluationTime;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public UserCommon getEvaluUser() {
		return evaluUser;
	}

	public void setEvaluUser(UserCommon evaluUser) {
		this.evaluUser = evaluUser;
	}

	public List<GoodsEvaluation> getAddedEvaluation() {
		return addedEvaluation;
	}

	public void setAddedEvaluation(List<GoodsEvaluation> addedEvaluation) {
		this.addedEvaluation = addedEvaluation;
	}

	@Override
	public String toString() {
		return "GoodsEvaluation [evaluationId=" + evaluationId + ", evaluationUser=" + evaluationUser
				+ ", evaluationGoods=" + evaluationGoods + ", evaluationTime=" + evaluationTime + ", parentId="
				+ parentId + ", evaluUser=" + evaluUser + ", addedEvaluation=" + addedEvaluation + "]";
	}

}
