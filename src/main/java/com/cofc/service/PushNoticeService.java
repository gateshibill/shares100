package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.PushNotice;

public interface PushNoticeService {
    public void addNoticeInfo(PushNotice notice);
    public void updateNoticeInfo(PushNotice notice);
    public PushNotice getNoticeDetail(@Param("noticeId")Integer noticeId);
    public List<PushNotice> getSengMessage(@Param("loginPlat")Integer loginPlat);
    public void delelteNotice(@Param("noticeId")Integer noticeId);
    public int checkUserForNotice(@Param("loginPlat")Integer loginPlat,@Param("userId")Integer userId,
    		@Param("openId")String openId,@Param("noticeType") Integer noticeType);
    public int getNoticeCount(@Param("notice") PushNotice notice);
    public List<PushNotice> getNoticeList(@Param("notice") PushNotice notice,@Param("page")Integer page,@Param("limit")Integer limit);
    public int getNoticeCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
    		@Param("notice")PushNotice notice);
    public List<PushNotice> getNoticeListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
    		@Param("notice")PushNotice notice,@Param("page") Integer page,@Param("limit") Integer limit);
}
