package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TravelComment;

public interface TravelCommentService {
    public void addCommentInfo(TravelComment comment);
    public List<TravelComment> getCommentList(@Param("travelId")Integer travelId,@Param("pageNo")Integer pageNo,
    		@Param("pageSize")Integer pageSize);
    public TravelComment getOneComment(@Param("commentId")Integer commentId);
    public void delComment(@Param("commentId")Integer commentId);
    public int getCommentCountByTravelId(@Param("travelId")Integer travelId); //行程总评论数
    public int getCommentCountByUserId(@Param("travelId")Integer travelId,@Param("userId")Integer userId);//我的评论总数
    public int getCommentImageCountByTravelId(@Param("travelId")Integer travelId);
    public int getCommentImageCountByUserId(@Param("travelId")Integer travelId,@Param("userId")Integer userId);
    public List<TravelComment> getCommentListByBxy(@Param("comment")TravelComment comment,
    		@Param("travelId")Integer travelId,@Param("page")Integer page,
    		@Param("limit")Integer limit);
}
