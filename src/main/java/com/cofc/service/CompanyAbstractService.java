package com.cofc.service;

import com.cofc.pojo.CompanyAbstract;

public interface CompanyAbstractService {
	
	public int addAbstractForCompany(CompanyAbstract abs);
	
	public void updateAbstractContent(CompanyAbstract abs);
	
	public CompanyAbstract getAbstractById(Integer abstractId);
	
	public CompanyAbstract getAbstractByLoginPlat(Integer loginPlat);
	
	public void updateAbstractImage(CompanyAbstract abstract1);
}
