package com.sxt.system.controller;


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
import com.sxt.system.vo.PermissionVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-27
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

	private Log log = LogFactory.getLog(PermissionController.class);
	
	@Autowired
	private IPermissionService permissionService;
	
	/**
	 * 全查询
	 * @param permissionVo
	 * @return
	 */
	@RequestMapping("loadAllPermission")
	public DataGridView loadAllPermission(PermissionVo permissionVo) {
		permissionVo.setType(Constant.TYPE_PERMISSION);
		return this.permissionService.loadAllPermission(permissionVo);
		
	}
	
	
	/**
	 * 加载菜单最大的排序码
	 * @return
	 */
	@RequestMapping("loadPermissionMaxOrderNum")
	public DataGridView loadPermissionMaxOrderNum() {
		Integer maxOrderNum = this.permissionService.queryPermissionMaxOrderNum();
		return new DataGridView(maxOrderNum+1);		
	}
	
	/**
	 * 添加
	 * @param permission
	 * @return
	 */
	@RequestMapping("addPermission")
	public ResultObj addPermission(Permission permission) {
		permission.setType(Constant.TYPE_PERMISSION);
		permission.setOpen(0);
		try {
			permissionService.addPermission(permission);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			log.error("添加失败");
			return ResultObj.ADD_ERROR;
		}
	}
	@RequestMapping("updatePermission")
	public ResultObj updatePermission(Permission permission) {
		try {
			permissionService.updatePermission(permission);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			log.error("修改失败");
			return ResultObj.UPDATE_ERROR;
		}
	}
	/**
	 * 根据菜单ID查询当前菜单有多少子节点
	 * @param id
	 * @return
	 */
	@RequestMapping("checkCurrentPermissionHasChild")
	public DataGridView checkCurrentPermissionHasChild(Integer id) {
		Integer count = permissionService.queryPermissionCountByPid(id);
		return new DataGridView(count);
	}
	
	@RequestMapping("deletePermission")
	public ResultObj deletePermission(Integer id) {
		try {
			permissionService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
			return ResultObj.DELETE_ERROR;
		}
	}
}

