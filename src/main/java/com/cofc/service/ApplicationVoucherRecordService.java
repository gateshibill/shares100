package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationVoucherRecord;

public interface ApplicationVoucherRecordService {

	public List<ApplicationVoucherRecord> findUserAppRecordList(@Param("record")ApplicationVoucherRecord record,@Param("pageNo") int i,
			@Param("pageSize")Integer pageSize);

	public void addApplicationVoucher(ApplicationVoucherRecord record);
}
