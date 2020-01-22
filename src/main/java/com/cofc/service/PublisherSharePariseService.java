package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.PublisherShareParise;

public interface PublisherSharePariseService {

	public PublisherShareParise getUserIsParise(@Param("userId")Integer userId, @Param("shareId")Integer shareId);

	public void addPublisherShareParise(PublisherShareParise parise);

	public void updatePublisherShareParise(PublisherShareParise shareParise);

}
