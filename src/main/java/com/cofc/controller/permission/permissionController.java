package com.cofc.controller.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.BackRole;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.MenuTree;
import com.cofc.pojo.OperateLog;
import com.cofc.pojo.UserRole;
import com.cofc.service.BackRoleService;
import com.cofc.service.BackUserService;
import com.cofc.service.MenuTreeService;
import com.cofc.service.OperateLogService;
import com.cofc.service.UserRoleService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;

@Controller
@RequestMapping("/permission")
public class permissionController extends BaseUtil {

	@Resource
	private BackRoleService backRoleService;
	@Resource
	private BackUserService BackUserService;
	@Resource
	private MenuTreeService menuTreeService;
	@Resource
	private UserRoleService UserRoleService;
	@Resource
	private OperateLogService logService;
    
	// 管理员列表
	@RequestMapping("/goManagerList")
	public ModelAndView goManagerListJsp(ModelAndView modelView) {
		modelView.setViewName("permissionManage/managerList");
		return modelView;
	}

	// 后台角色列表
	@RequestMapping("/goRoleList")
	public ModelAndView goRoleListJsp(ModelAndView modelView) {
		List<BackRole> brList = backRoleService.findBackRoleList();
		modelView.addObject("brList", brList);
		modelView.setViewName("permissionManage/roleList");
		return modelView;
	}

	// ajax查询角色列表
	@RequestMapping("/showBackRoleList")
	public void showBackRoleList(HttpServletResponse response, Integer pageNo, Integer limit, Integer roleId,
			String roleName) {
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		if (limit == null || limit == 0) {
			limit = 20;
		}
		int rowsCount = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (pageNo - 1) * limit);
		map.put("limit", limit);
		map.put("roleId", roleId);
		map.put("roleName", roleName);
		List<BackRole> brList = backRoleService.showBackRoleList(map);
		rowsCount = backRoleService.getBackRoleCount(map);
		output(response, JsonUtil.buildJsonByTotalCount(brList, rowsCount));
	}

	// ajax查询管理员列表
	@RequestMapping("/showBackUserList")
	public void showBackUserList(HttpServletResponse response, Integer page, Integer limit, Integer userId,
			String roleName) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (limit == null || limit == 0) {
			limit = 20;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page - 1) * limit);
		map.put("limit", limit);
		map.put("userId", userId);
		map.put("roleName", roleName);
		List<BackUser> buList = BackUserService.showBackUserList(map);
		int rowsCount = BackUserService.showBackUserCount(map);
		
		output(response, JsonUtil.buildJsonByTotalCount(buList, rowsCount));
	}

	// 管理员详情页面
	@RequestMapping("/backUserDetails")
	public ModelAndView backUserDetails(ModelAndView modelView, Integer userId, HttpServletRequest request) {
		BackUser user = BackUserService.getBackUserById(userId);
		UserRole urole = UserRoleService.getUserRoleById(userId);
		List<BackRole> roleList = backRoleService.findBackRoleList();

		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole userRole = UserRoleService.getUserRoleById(bu.getUserId());
		modelView.addObject("user", user);
		modelView.addObject("urole", urole);
		modelView.addObject("roleId", userRole.getRoleId());
		modelView.addObject("roleList", roleList);
		modelView.addObject("loginPlat",bu.getLoginPlat());
		modelView.setViewName("permissionManage/backUserDetails");
		return modelView;
	}

	// 删除角色
	@RequestMapping("/deleteRoleId")
	public void deleteRoleId(Integer roleId, HttpServletResponse response, HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole role = UserRoleService.getUserRoleById(bu.getUserId());
		boolean isAdmin = false;
		String[] user = role.getRoleId().split(",");
		for (String u : user) {
			while ("1".equals(u)) {
				isAdmin = true;
				break;
			}
		}
		BackRole backRole = backRoleService.getRoleStringById(String.valueOf(roleId));
		if (!isAdmin) {
			OperateLog log = new OperateLog();
			log.setOperateTime(new Date());
			log.setOperateUser(bu.getUserName());
			log.setOperateResult(2);
			log.setOperateName("【" + bu.getUserName() + "】删除角色【" + backRole.getRoleName() + "】失败");
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("1", "系统检测到您没有删除角色的权限!"));
		} else {
			try {
				backRoleService.deleteRoleId(roleId);
				OperateLog log = new OperateLog();
				log.setOperateTime(new Date());
				log.setOperateUser(bu.getUserName());
				log.setOperateResult(1);
				log.setOperateName("【" + bu.getUserName() + "】删除角色【" + backRole.getRoleName() + "】成功");
				logService.addOperateLog(log);
				output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
			} catch (Exception e) {
				output(response, JsonUtil.buildFalseJson("1", "删除失败!"));
			}
		}
	}

	// 删除管理
	@RequestMapping("/deleteUserId")
	public void deleteUserId(HttpServletResponse response, Integer userId, HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole userrole = UserRoleService.getUserRoleById(bu.getUserId());
		boolean isAdmin = false;
		String[] user = userrole.getRoleId().split(",");
		for (String u : user) {
			while ("1".equals(u)) {
				isAdmin = true;
				break;
			}
		}
		BackUser user2 = BackUserService.getBackUserById(userId);
		if (!isAdmin) {
			OperateLog log = new OperateLog();
			log.setOperateTime(new Date());
			log.setOperateUser(bu.getUserName());
			log.setOperateResult(2);
			log.setOperateName("【" + bu.getUserName() + "】删除管理【" + user2.getUserName() + "】失败");
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("1", "系统检测到您没有删除角色的权限!"));
		} else {
			try {
				BackUserService.deleteUserId(userId);
				UserRole role = UserRoleService.getUserRoleById(userId);
				if (role != null) {
					UserRoleService.deleteUserRole(role);
				}
				OperateLog log = new OperateLog();
				log.setOperateTime(new Date());
				log.setOperateUser(bu.getUserName());
				log.setOperateResult(1);
				log.setOperateName("【" + bu.getUserName() + "】删除管理【" + user2.getUserName() + "】成功");
				logService.addOperateLog(log);
				output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
			} catch (Exception e) {
				output(response, JsonUtil.buildFalseJson("1", "删除失败!"));
			}
		}
	}

	// 角色详情页面(编辑)
	@RequestMapping("/backRoleDetails")
	public ModelAndView backRoleDetails(Integer roleId, ModelAndView modelView) {
		BackRole role = backRoleService.getRoleMenuIds(roleId);
		modelView.addObject("role", role);
		modelView.setViewName("permissionManage/backRoleDetails");
		return modelView;
	}

	// 修改角色权限
	@RequestMapping("/showHisRole")
	public void showHisRole(HttpServletResponse response, Integer roleId, HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		BackRole roleName = backRoleService.getRoleMenuIds(roleId);
		UserRole userRole = UserRoleService.getUserRoleById(bu.getUserId());
		BackRole role = backRoleService.getRoleStringById(userRole.getRoleId());
		String[] idStrings = role.getRolePermission().split(",");
		List tmtList = Arrays.asList(idStrings);// 拿到当前登录用户的权限
		if (roleName != null) {
			String[] idStrings1 = roleName.getRolePermission().split(",");
			List tmtList1 = Arrays.asList(idStrings1);// 拿到添加角色的权限
			List<MenuTree> mtList1 = menuTreeService.getParentMenu(tmtList);// 登录用户的权限
			List<MenuTree> mtList2 = menuTreeService.getParentMenu(tmtList1);// 角色的权限
			List<MenuTree> mTreeList = menuList(mtList1, mtList2);
			output(response, JsonUtil.buildJson(mTreeList));
		} else {
			List<MenuTree> mtList = menuTreeService.getParentMenu(tmtList);
			List<MenuTree> mTreeList = menuListName(mtList);
			output(response, JsonUtil.buildJson(mTreeList));
		}
	}

	/**
	 * 父菜单
	 * 
	 * @param menu
	 * @return
	 */
	public List<MenuTree> menuListName(List<MenuTree> menuList) {
		List<MenuTree> treeList = new ArrayList<MenuTree>();
		for (MenuTree x : menuList) {
			MenuTree tree = new MenuTree();
			if (x.getParentId() == null) {
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
			if (a.getParentId() != null) {
				if (a.getParentId() == id) {
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
		}
		return treeList;
	}

	private List<MenuTree> menuList(List<MenuTree> mtList1, List<MenuTree> mtList2) {
		List<MenuTree> treeList = new ArrayList<MenuTree>();
		for (MenuTree x : mtList1) {
			MenuTree tree = new MenuTree();
			List<MenuTree> childTreeList = new ArrayList<MenuTree>();
			if (x.getMenuLevel() == 1) {
				tree.setMenuId(x.getMenuId());
				tree.setMenuLevel(x.getMenuLevel());
				tree.setIcon(x.getIcon());
				tree.setTitle(x.getTitle());
				tree.setHref(x.getHref());
				for (MenuTree mt : mtList2) {
					if (mt.getMenuId() == x.getMenuId()) {
						tree.setHasAuthority(1);
						break;
					} else {
						tree.setHasAuthority(0);
					}
				}
				tree.setChildren(childTreeList);
				for (MenuTree child1 : mtList1) {
					MenuTree tree1 = new MenuTree();
					if (child1.getMenuLevel() == 2 && child1.getParentId() == x.getMenuId()) {
						tree1.setMenuId(child1.getMenuId());
						tree1.setMenuLevel(child1.getMenuLevel());
						tree1.setIcon(child1.getIcon());
						tree1.setTitle(child1.getTitle());
						tree.setHref(child1.getHref());
						for (MenuTree child2 : mtList2) {
							if (child2.getMenuId() == child1.getMenuId()) {
								tree1.setHasAuthority(1);
								break;
							} else {
								tree1.setHasAuthority(0);
							}
						}
						childTreeList.add(tree1);
					}
				}
				treeList.add(tree);
			}
		}
		return treeList;
	}

	// 保存修改的角色权限
	@RequestMapping("/updateBackUserRole")
	public void updateBackUserRole(HttpServletResponse response, Integer roleId, String roleName, String menuIds,
			HttpServletRequest request) {
		BackRole backRole = backRoleService.getRoleMenuIds(roleId);
		BackRole backName = backRoleService.getBackRoleName(roleName);
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole role = UserRoleService.getUserRoleById(bu.getUserId());
		boolean isAdmin = false;
		String[] user = role.getRoleId().split(",");
		for (String u : user) {
			while ("1".equals(u)) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			OperateLog log = new OperateLog();
			log.setOperateTime(new Date());
			log.setOperateUser(bu.getUserName());
			log.setOperateResult(2);
			log.setOperateName("【" + bu.getUserName() + "】修改角色权限【" + roleName + "】失败");
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("2", "系统检测到您没有修改角色的权限!"));
		} else {
			if (backName != null && !backRole.getRoleName().equals(roleName)) {
				output(response, JsonUtil.buildFalseJson("1", "该角色名称已存在!"));
			} else {
				if (menuIds != null) {
					backRole.setRolePermission(menuIds);
				}
				backRole.setRoleName(roleName);
				backRoleService.updateBackRole(backRole);
				OperateLog log = new OperateLog();
				log.setOperateTime(new Date());
				log.setOperateUser(bu.getUserName());
				log.setOperateResult(1);
				log.setOperateName("【" + bu.getUserName() + "】修改角色权限【" + roleName + "】成功");
				logService.addOperateLog(log);
				output(response, JsonUtil.buildFalseJson("0", "修改角色成功!"));
			}
		}
	}

	// 添加角色
	@RequestMapping("/addBackUserRole")
	public void addBackUserRole(HttpServletResponse response, String roleName, String menuIds,
			HttpServletRequest request) {
		BackRole backName = backRoleService.getBackRoleName(roleName);
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole role1 = UserRoleService.getUserRoleById(bu.getUserId());
		boolean isAdmin = false;
		String[] user = role1.getRoleId().split(",");
		for (String u : user) {
			while ("1".equals(u)) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			output(response, JsonUtil.buildFalseJson("4", "系统检测到您没有添加角色的权限!"));
		} else {
			if (backName != null) {
				output(response, JsonUtil.buildFalseJson("1", "该角色名称已存在!"));
			} else if (roleName == null) {
				output(response, JsonUtil.buildFalseJson("2", "请输入角色名称!"));
			} else if (menuIds == null) {
				output(response, JsonUtil.buildFalseJson("3", "请选择角色权限!"));
			} else {
				BackRole role = new BackRole();
				role.setRoleName(roleName);
				role.setRolePermission(menuIds);
				role.setCreateTime(new Date());
				role.setCreateUser(bu.getUserId());
				// 往操作日志写数据
				OperateLog log = new OperateLog();
				log.setOperateTime(new Date());
				log.setOperateUser(bu.getUserName());
				log.setOperateName("【" + bu.getUserName() + "】添加管理角色为【" + roleName + "】成功");
				try {
					log.setOperateResult(1);
					backRoleService.addBackRole(role);
					logService.addOperateLog(log);
					output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("4", "添加失败!"));
				}
			}
		}
	}

	// 编辑管理员
	@RequestMapping("/updateBackUser")
	public void updateBackUser(HttpServletResponse response, BackUser user, HttpServletRequest request, String roleId) {
		BackUser backUser = BackUserService.getUserByUserName(user.getUserName());
		BackUser user2 = BackUserService.getBackUserById(user.getUserId());
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole role1 = UserRoleService.getUserRoleById(bu.getUserId());
		UserRole role2 = UserRoleService.getUserRoleById(user.getUserId());
		boolean isAdmin = false;
		String[] userRole = role1.getRoleId().split(",");
		for (String u : userRole) {
			while ("1".equals(u)) {
				isAdmin = true;
				break;
			}
		}
		if (!user2.getUserId().equals(bu.getUserId()) && !isAdmin) {
			output(response, JsonUtil.buildFalseJson("4", "系统检测到您没有修改用户的权限!"));
		} else {
			if (backUser != null && !user2.getUserName().equals(user.getUserName())) {
				output(response, JsonUtil.buildFalseJson("1", "账号已存在，请换一个试试!"));
			} else {
				BackUserService.updateBackUser(user);
				if (!role2.getRoleId().equals(roleId) && roleId != null) {

					role2.setRoleId(roleId);
					UserRoleService.updateUserRole(role2);
				}
				output(response, JsonUtil.buildFalseJson("0", "修改成功!!"));
			}
		}
	}

	// 添加角色
	@RequestMapping("/addRole")
	public ModelAndView addRole(ModelAndView mView) {
		mView.setViewName("permissionManage/newRole");
		return mView;
	}

	// 添加管理
	@RequestMapping("/addUser")
	public ModelAndView addUser(ModelAndView mView) {
		List<BackRole> roleList = backRoleService.findBackRoleList();
		//获取所有应用
		
		mView.addObject("roleList", roleList);
		mView.setViewName("permissionManage/newBackUser");
		return mView;
	}

	// 保存新增管理
	@RequestMapping("/addbackUser")
	public void addbackUser(HttpServletResponse response, BackUser user, String roleId, HttpServletRequest request) {
		BackUser userName = BackUserService.getUserByUserName(user.getUserName());
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole role1 = UserRoleService.getUserRoleById(bu.getUserId());
		boolean isAdmin = false;
		String[] userRole = role1.getRoleId().split(",");
		for (String u : userRole) {
			while ("1".equals(u)) {
				isAdmin = true;
				break;
			}
		}
		if (!isAdmin) {
			output(response, JsonUtil.buildFalseJson("2", "系统检测到您没有添加管理的权限!"));
		} else {
			if (userName != null) {
				output(response, JsonUtil.buildFalseJson("1", "系统检测到该账号已存在!"));
			} else {
				user.setCreateTime(new Date());
				user.setPassword(MD5Util.MD5Encode(user.getPassword(), "utf-8"));
				BackUserService.addBackUser(user);// 返回userId
				UserRole role = new UserRole();
				role.setRoleId(roleId);
				role.setUserId(user.getUserId());
				UserRoleService.addUserRole(role);
				// 往操作日志写数据
				OperateLog log = new OperateLog();
				log.setOperateTime(new Date());
				log.setOperateUser(bu.getUserName());
				log.setOperateName("【" + bu.getUserName() + "】添加管理用户为【" + user.getUserName() + "】成功");
				log.setOperateResult(1);
				logService.addOperateLog(log);
				output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
			}
		}
	}
}
