package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserShoppingCar;
import com.cofc.pojo.aida.FaceGame;

public interface FaceGameService {

	public void addFaceGame(FaceGame faceGame);
	public FaceGame getFaceGameByEncrypt(@Param("encrypt") String encrypt);
	public void updateFaceGame(FaceGame faceGame);
	public void deleteFaceGame(@Param("id")Integer id);
	//public List<FaceGame> getFaceGameList(FaceGame faceGame);
	public List<FaceGame> getFaceGameList(@Param("page")int i,
			 @Param("limit")Integer limit);
	
	public int getFaceGameCount();

}