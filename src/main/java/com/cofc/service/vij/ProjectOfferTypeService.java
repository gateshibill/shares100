package com.cofc.service.vij;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectOfferDetail;
import com.cofc.pojo.vij.ProjectOfferType;

public interface ProjectOfferTypeService {
	public void addOfferType(ProjectOfferType pOfferType);
	public void updateOfferType(ProjectOfferType pOfferType);
	public void delOfferType(@Param("offerTypeId")Integer offerTypeId);
	public ProjectOfferDetail getOfferTypeById(@Param("projectId")Integer projectId);
}
