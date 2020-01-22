package com.cofc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cofc.pojo.TestDescovery;

@Service
public interface TestService {
	List<TestDescovery> getAll(int count);
}
