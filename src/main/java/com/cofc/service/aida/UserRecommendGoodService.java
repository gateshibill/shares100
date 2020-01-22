package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.UserRecommendGood;

public interface UserRecommendGoodService {
	public List<UserRecommendGood> getRecommendList(@Param("re")UserRecommendGood re,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
