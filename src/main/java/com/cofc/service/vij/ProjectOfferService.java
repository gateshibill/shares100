package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectOffer;

public interface ProjectOfferService {

	public void addOffer(ProjectOffer pOffer);
	public void updateOffer(ProjectOffer pOffer);
	public void delOffer(@Param("pofferId")Integer pofferId);
	public List<ProjectOffer> getOfferByProjectId(@Param("projectId")Integer projectId);
	public int getOfferCountByProjectId(@Param("projectId")Integer projectId, @Param("offerTypeId")Integer offerTypeId);
	public ProjectOffer getOfferByProjectIdType(@Param("projectId")Integer projectId, @Param("offerTypeId")Integer offerTypeId);
	
}
