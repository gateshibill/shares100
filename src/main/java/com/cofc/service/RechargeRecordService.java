package com.cofc.service;

import com.cofc.pojo.RechargeRecord;

public interface RechargeRecordService {
	public int userWantToRecharge(RechargeRecord apayRecord);
	public void updatePayRecordInfo(RechargeRecord apayRecord);
	public RechargeRecord getPayRecordByPayId(int prePayId);
}
