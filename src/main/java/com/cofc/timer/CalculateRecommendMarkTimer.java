package com.cofc.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cofc.service.RecommendService;


public class CalculateRecommendMarkTimer {

	@Autowired
	private RecommendService recommendService;
	
	public void calculate(){
		recommendService.updateAllDescoveryRecommend();
	}
}
