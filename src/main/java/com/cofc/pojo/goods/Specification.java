package com.cofc.pojo.goods;

import java.util.HashSet;

public class Specification {
	private String name;
	private HashSet<String> set = new HashSet<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashSet<String> getSet() {
		return set;
	}

	public void setSet(HashSet<String> set) {
		this.set = set;
	}

}
