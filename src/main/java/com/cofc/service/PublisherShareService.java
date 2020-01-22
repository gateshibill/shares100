package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.PublisherShare;

public interface PublisherShareService {

	public void addPublisherShare(PublisherShare share);

	public List<PublisherShare> getMyPublisherShareList(@Param("loginPlat")Integer loginPlat, @Param("pageNo")int pageNo, @Param("pageSize")Integer pageSize);

	public void updatePublisherShare(PublisherShare share);

	public List<PublisherShare> findPublisherShareList(@Param("shareUserId")Integer shareUserId, @Param("page")int page, @Param("limit")Integer limit);

	public int getPublisherShareCount(@Param("shareUserId")Integer shareUserId);

	public void deletePublisherShare(@Param("shareId")Integer shareId);

	public PublisherShare getShareById(Integer shareId);

	public PublisherShare getPublisherShareLoginPlatById(@Param("loginPlat")Integer loginPlat, @Param("shareId")Integer shareId);

}
