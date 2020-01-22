package com.cofc.controller.aida;

import java.util.Comparator;

import com.cofc.pojo.aida.ActionUser;
import com.cofc.pojo.aida.SalesPerson;

class AIComparator implements Comparator<ActionUser> {
	// 这里的o1和o2就是list里任意的两个对象，然后按需求把这个方法填完整就行了
	@Override
	public int compare(ActionUser o1, ActionUser o2) {
		// 比较规则
		if (o1.getTimes() > o2.getTimes()) {
			return -1;
		} else if (o1.getTimes() < o2.getTimes()) {
			return 1;
		} else {
			return 0;
		}
	}
}

class AISpVisitedCountComparator implements Comparator<SalesPerson> {
	// 这里的o1和o2就是list里任意的两个对象，然后按需求把这个方法填完整就行了
	@Override
	public int compare(SalesPerson o1, SalesPerson o2) {
		// 比较规则
		if (o1.geteVisitedCount() > o2.geteVisitedCount()) {
			return -1;
		} else if (o1.geteVisitedCount() < o2.geteVisitedCount()) {
			return 1;
		} else {
			return 0;
		}
	}
}

class SalesAbilityComparator implements Comparator<SalesPerson> {
	// 这里的o1和o2就是list里任意的两个对象，然后按需求把这个方法填完整就行了
	@Override
	public int compare(SalesPerson o1, SalesPerson o2) {
		// 比较规则
		if (o1.getSalesAbility().getSumSorce() > o2.getSalesAbility().getSumSorce()) {
			return -1;
		} else if (o1.getSalesAbility().getSumSorce() < o2.getSalesAbility().getSumSorce()) {
			return 1;
		} else {
			return 0;
		}
	}
}