package com.cofc.service;

import com.cofc.pojo.VerificationCode;

public interface VerificationCodeService {

	public VerificationCode getCodeByPhone(String phone);

	public void addNewCode(VerificationCode vc);

	public void updateCode(VerificationCode vccc);

}
