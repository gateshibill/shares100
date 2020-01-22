package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cofc.pojo.GoodsType;

/**
 * 商品分类递归
 * @author 46678
 *
 */
public class TypeTreeUtil {
	public static List<GoodsType> typeTreeList(List<GoodsType> lists){
		List<GoodsType> treeList = new ArrayList<GoodsType>();
		for (GoodsType x : lists) {
			GoodsType tree = new GoodsType();
			if (x.getParentId()==null||x.getParentId() == 0) {
				tree.setTypeIcon(x.getTypeIcon());
				tree.setTypeId(x.getTypeId());
				tree.setTypeName(x.getTypeName());
				tree.setEnTypeName(x.getEnTypeName());
				tree.setChildList(typeTreechildList(x.getTypeId(), lists));
				treeList.add(tree);
			}
		}
		return treeList;
	}
	public static List<GoodsType> typeTreechildList(int id,List<GoodsType> lists){
		List<GoodsType> treeList = new ArrayList<GoodsType>();
		for (GoodsType a : lists) {
			GoodsType tree = new GoodsType();
			if (a.getParentId()!=null&&a.getParentId() == id) {
				tree.setTypeIcon(a.getTypeIcon());
				tree.setTypeId(a.getTypeId());
				tree.setEnTypeName(a.getEnTypeName());
				tree.setTypeName(a.getTypeName());
				tree.setParentId(a.getParentId());
				tree.setChildList(typeTreechildList(a.getTypeId(), lists));
				treeList.add(tree);
			}
		}
		return treeList;
	}
}
