package com.cofc.service.vij;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectOfferDetail;

public interface ProjectOfferDetailService {

	public void addOfferDetail(ProjectOfferDetail pDetail);
	public void updateOfferDetail(ProjectOfferDetail pDetail);
	public void delOfferDetail(@Param("offerId")Integer offerId);
	public ProjectOfferDetail getOfferDetailById(@Param("pofferId")Integer pofferId);
	public ProjectOfferDetail getOfferDetailByOfferId(@Param("offerId")Integer offerId);
}
