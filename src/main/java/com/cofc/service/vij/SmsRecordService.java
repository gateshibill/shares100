package com.cofc.service.vij;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.SmsRecord;

public interface SmsRecordService {
	public void addSms(SmsRecord record);
	public SmsRecord getSms(@Param("phone")String phone,@Param("type")Integer type,
			@Param("appId")Integer appId,@Param("code")String code);
}
