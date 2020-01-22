package com.cofc.service;

import java.util.List;

import com.cofc.pojo.DescoveryRecommend;


public interface DescoveryRecommendService {

	public void addDescoveryRecommend(DescoveryRecommend descoveryRecommend);
	public List<Integer> findAllDescoveryID();
	public List<Integer> findAllApplicationID();
	public List<String> findAllDescoveryRecommend();
	public void updateDescoveryRecommend(DescoveryRecommend descoveryRecommend);
}
