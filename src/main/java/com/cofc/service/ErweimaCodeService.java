package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ErweimaCode;

public interface ErweimaCodeService {
	 //百享园
     public List<ErweimaCode> getAllList(@Param("code")ErweimaCode code,@Param("page")Integer page,@Param("limit")Integer limit);
     public int getAllCount(@Param("code")ErweimaCode code);
     public void addCode(ErweimaCode code);
     public void updateCode(ErweimaCode code); // 删除和修改 ，删除更改isEffect字段
     public ErweimaCode getCodeInfo(@Param("loginPlat")Integer loginPlat,@Param("deskId")Integer deskId);
     public ErweimaCode getCodeInfoByCodeId(@Param("codeId")Integer codeId);
     //分应用
     public List<ErweimaCode> getAllListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
    		 @Param("code")ErweimaCode code,@Param("page")Integer page,@Param("limit")Integer limit);
     public int getAllCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
    		 @Param("code")ErweimaCode code);
}
