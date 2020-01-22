package com.cofc.service;

import com.cofc.pojo.AutoWithdrawSet;

public interface AutoWithdrawSetService {
	public AutoWithdrawSet getCurrentApplicationSet(Integer applicationId);
	public void setAutoWithdrawInfo(AutoWithdrawSet aws);
}
