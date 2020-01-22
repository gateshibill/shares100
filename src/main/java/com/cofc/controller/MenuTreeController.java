package com.cofc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.BackRole;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.MenuTree;
import com.cofc.pojo.UserRole;
import com.cofc.service.BackRoleService;
import com.cofc.service.MenuTreeService;
import com.cofc.service.UserRoleService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/menuTree")
public class MenuTreeController extends BaseUtil{
	@Resource
	private MenuTreeService menuTreeService;
	@Resource
	private UserRoleService UserRoleService;
	@Resource
	private BackRoleService BackRoleService;
	
	@RequestMapping("/menuTree")
	public void showAllParentMenu(HttpServletRequest request,HttpServletResponse response) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole userRole = UserRoleService.getUserRoleById(bu.getUserId());
		//防止一个用户有多个角色的情况
		String[] myRole = userRole.getRoleId().split(",");
		List roleIds = Arrays.asList(myRole);
		//循环拿得到当前用户所有的菜单权限
		List<BackRole> roleList = BackRoleService.getBackRoleRoleIds(roleIds);
		/**
		 * 以下是分值一个用户有多个角色的情况，
		 * 然后根据角色拿到对应的菜单权限，过滤重复的菜单权限
		 */
		//拿到角色对应的权限做累加
		String roles = "";
		for (BackRole role : roleList) {
			roles += BackRoleService.getRoleMenuIds(role.getRoleId()).getRolePermission();
		}
	    //过滤重复的菜单权限
		Set<String> mLinkedSet = new LinkedHashSet<String>();
		String[] idStrings = roles.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < idStrings.length; i++) {
			if (!mLinkedSet.contains(idStrings[i])) {
				mLinkedSet.add(idStrings[i]);
				sb.append(idStrings[i] + ",");
			}
		}
		/***
		 * 以上想同
		 * 
		 */
		List<MenuTree> mmmmList = new ArrayList<MenuTree>();
		List<MenuTree> mtList = null;
		List<MenuTree> mTreeList = null;
		List tmtList = Arrays.asList(idStrings);
		mtList = menuTreeService.getParentMenu(tmtList);
		mTreeList = menuList(mtList);
		for (MenuTree mTree : mTreeList) {
			MenuTree mt = new MenuTree();
			mt.setMenuId(mTree.getMenuId());
			mt.setOrderId(mTree.getOrderId());
			mt.setMenuLevel(mTree.getMenuLevel());
			mt.setIcon(mTree.getIcon());
		    mt.setTitle(mTree.getTitle());
		    mt.setChildren(mTree.getChildren());
			mmmmList.add(mt);
		}
		output(response, JsonUtil.buildJson(mmmmList));
	}

	/**
	 * 父菜单
	 * 
	 * @param menu
	 * @return
	 */
	public List<MenuTree> menuList(List<MenuTree> menuList) {
		List<MenuTree> treeList = new ArrayList<MenuTree>();
		for (MenuTree x : menuList) {
			MenuTree tree = new MenuTree();
			if (x.getParentId()==null||x.getParentId() == 0) {
				tree.setMenuId(x.getMenuId());
				tree.setMenuLevel(x.getMenuLevel());
				tree.setIcon(x.getIcon());
				tree.setTitle(x.getTitle());
				tree.setHref(x.getHref());
				tree.setOrderId(x.getOrderId());
				tree.setChildren(menuChild(x.getMenuId(), menuList));
				treeList.add(tree);
			}
		}
		return treeList;
	}

	/**
	 * 子菜单
	 * 
	 * @param id
	 * @return
	 */
	public List<MenuTree> menuChild(int id, List<MenuTree> menuList) {
		List<MenuTree> treeList = new ArrayList<MenuTree>();
		for (MenuTree a : menuList) {
			MenuTree tree = new MenuTree();
			if (a.getParentId()!=null&&a.getParentId() == id) {
				tree.setMenuId(a.getMenuId());
				tree.setMenuLevel(a.getMenuLevel());
				tree.setIcon(a.getIcon());
				tree.setTitle(a.getTitle());
				tree.setHref(a.getHref());
				tree.setOrderId(a.getOrderId());
				tree.setParentId(a.getParentId());
				tree.setChildren(menuChild(a.getMenuId(), menuList));
				treeList.add(tree);
			}
		}
		return treeList;
	}
}
