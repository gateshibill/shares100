package com.cofc.pojo.aida;

import java.util.ArrayList;
import java.util.List;

//销售漏斗
public class SalesFunnel {
	List<column> list = new ArrayList<column>();

	public List<column> getList() {
		return list;
	}

	public void setList(List<column> list) {
		this.list = list;
	}

	public void addColume(String name, int sum, int change) {
		list.add(new column(name, sum, change));
	}

	public class column {
		String name;
		int sum = 899;
		int change = 99;

		public column(String name, int sum, int change) {
			this.name = name;
			this.sum = sum;
			this.change = change;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
