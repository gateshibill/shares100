package com.cofc.pojo.video;

import java.util.Date;

public class Asset {
   private int id;
   private int appId;
   private String name;
   private String posterUrl;//海报，多个用‘;’隔开
   private String desc;
   private String playUrl;
   private String lyric;
   private String director;
   private String actor;
   private String sitcom;//连续剧集数，电影为0
   private String duration;//时长
   private String lableIds;//标签集
   private int type;//儿童、武大、记录
   private float score;//评分，
   private int views;// 观看次数
   private String area;//区域
   private String language;//语种
   private String source;//来源
   private String format;//格式
   private Date publishTime;
   private Date createTime;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getAppId() {
	return appId;
}
public void setAppId(int appId) {
	this.appId = appId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPosterUrl() {
	return posterUrl;
}
public void setPosterUrl(String posterUrl) {
	this.posterUrl = posterUrl;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public String getPlayUur() {
	return playUrl;
}
public void setPlayUur(String playUrl) {
	this.playUrl = playUrl;
}

public String getPlayUrl() {
	return playUrl;
}
public void setPlayUrl(String playUrl) {
	this.playUrl = playUrl;
}
public String getLyric() {
	return lyric;
}
public void setLyric(String lyric) {
	this.lyric = lyric;
}
public void setActor(String actor) {
	this.actor = actor;
}
public String getDirector() {
	return director;
}
public void setDirector(String director) {
	this.director = director;
}
public String getActor() {
	return actor;
}
public void setActors(String actor) {
	this.actor = actor;
}
public String getSitcom() {
	return sitcom;
}
public void setSitcom(String sitcom) {
	this.sitcom = sitcom;
}
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}
public String getLableIds() {
	return lableIds;
}
public void setLableIds(String lableIds) {
	this.lableIds = lableIds;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public float getScore() {
	return score;
}
public void setScore(float score) {
	this.score = score;
}
public int getViews() {
	return views;
}
public void setViews(int views) {
	this.views = views;
}
public String getArea() {
	return area;
}
public void setArea(String area) {
	this.area = area;
}
public String getLanguage() {
	return language;
}
public void setLanguage(String language) {
	this.language = language;
}
public String getSource() {
	return source;
}
public void setSource(String source) {
	this.source = source;
}
public String getFormat() {
	return format;
}
public void setFormat(String format) {
	this.format = format;
}
public Date getPublishTime() {
	return publishTime;
}
public void setPublishTime(Date publishTime) {
	this.publishTime = publishTime;
}
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}

	
}
