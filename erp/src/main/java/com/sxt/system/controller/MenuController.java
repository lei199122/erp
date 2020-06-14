package com.sxt.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sxt.system.constant.Constant;
import com.sxt.system.domain.Permission;
import com.sxt.system.service.IPermissionService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;
import com.sxt.system.utils.TreeNode;
import com.sxt.system.vo.PermissionVo;

/**
 * 菜单管理前控制器
 * @author LW
 *
 */

@RestController
@RequestMapping("menu")
public class MenuController {
	
	private Log log = LogFactory.getLog(MenuController.class);
	
	@Autowired
	private IPermissionService permissionService;
	
	/**
	 * 全查询
	 */
	@RequestMapping("loadAllMenu")
	public DataGridView loadAllMenu(PermissionVo permissionVo) {
		permissionVo.setType(Constant.TYPE_MENU); //只查菜单
		return this.permissionService.loadAllPermission(permissionVo);
		
	}
	
	/**
	 * 生成菜单json树
	 * @param permissionVo
	 * @return
	 */
	@RequestMapping("loadAllMenuTreeJson")
	public DataGridView loadAllMenuTreeJson(PermissionVo permissionVo) {
		permissionVo.setAvailable(Constant.AVAILABLE_TRUE);
		permissionVo.setType(Constant.TYPE_MENU);
		List<Permission> allMenu = this.permissionService.queryAllPermission(permissionVo);
		
		List<TreeNode> treeNode = new ArrayList<>();
		for (Permission p : allMenu) {
			Integer id = p.getId();
			Integer pid = p.getPid();
			String title = p.getTitle();
			Boolean spread = p.getOpen()==1?true:false;
			treeNode.add(new TreeNode(id, pid, title, spread));
		}
		return new DataGridView(treeNode);
		
	}
	
	/**
	 * 加大菜单最大的排序码
	 * @return
	 */
	@RequestMapping("loadPermissionMaxOrderNum")
	public  DataGridView loadPermissionMaxOrderNum() {
		Integer maxOrderNum = this.permissionService.queryPermissionMaxOrderNum();
		return new DataGridView(maxOrderNum + 1);
	}
	
	/**
	 * 添加
	 * @param permission
	 * @return
	 */
	@RequestMapping("addMenu")
	public ResultObj addMenu(Permission permission) {
		permission.setType(Constant.TYPE_MENU);
		try {
			permissionService.addPermission(permission);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			log.error("添加失败");
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 修改
	 * @param permission
	 * @return
	 */
	@RequestMapping("updateMenu")
	public ResultObj updateMenu(Permission permission) {
		try {
			permissionService.updatePermission(permission);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			log.error("修改失败");
			return ResultObj.UPDATE_ERROR;
		}

	}
	
	/**
	 * 根据菜单ID查询当前菜单有多少节点
	 * @param id
	 * @return
	 */
	@RequestMapping("checkCurrentMenuHasChild")
	public DataGridView checkCurrentMenuHasChild(Integer id) {
		Integer count = this.permissionService.queryPermissionCountByPid(id);
		return new DataGridView(count);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteMenu")
	public ResultObj deleteMenu(Integer id) {
		try {
			permissionService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
			return ResultObj.DELETE_ERROR;
		}
	}
}
