package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.PublisherShareCollection;

public interface PublisherShareCollectionService {

	public PublisherShareCollection getUserisCollection(@Param("userId")Integer userId, @Param("shareId")Integer shareId);

	public void addPublisherShareCollection(PublisherShareCollection collection);

	public void updateIsCollection(PublisherShareCollection collection2);

}
