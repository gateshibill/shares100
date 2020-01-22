package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.CarouselPicture;

public interface CarouselPictureService {

	public void addNewCarouselPic(CarouselPicture carousel);

	public List<CarouselPicture> findPictureByCriteria(@Param("pictureId") Integer pictureId,
			@Param("picName") String picName, @Param("isUsing") Integer isUsing, @Param("userId") Integer userId,
			@Param("userKeyWords") String userKeyWords, @Param("startTime") String startTime,
			@Param("endTime") String endTime, @Param("loginPlat") Integer loginPlat,
			@Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize);

	public CarouselPicture getPictureById(Integer pictureId);

	public void changePicInfo(CarouselPicture currPic);

	public int getCountPictures(@Param("pictureId") Integer pictureId, @Param("picName") String picName,
			@Param("isUsing") Integer isUsing, @Param("userId") Integer userId, @Param("keyWords") String keyWords,
			@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("loginPlat") Integer loginPlat);

	public void insertPictureBatch(List<CarouselPicture> insertList);

	public List<CarouselPicture> findCurrAppCarousels(@Param("loginPlat")Integer loginPlat,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);
	
	//区分应用
	public int getCountPicturesByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("pictureId") Integer pictureId, @Param("picName") String picName,
			@Param("isUsing") Integer isUsing, @Param("userId") Integer userId, @Param("keyWords") String keyWords,
			@Param("startTime") String startTime, @Param("endTime") String endTime);
	public List<CarouselPicture>findPictureByCriteriaByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("pictureId") Integer pictureId,
			@Param("picName") String picName, @Param("isUsing") Integer isUsing, @Param("userId") Integer userId,
			@Param("userKeyWords") String userKeyWords, @Param("startTime") String startTime,
			@Param("endTime") String endTime,
			@Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize);
	public void delPictureById(@Param("pictureId")Integer pictureId);
}
