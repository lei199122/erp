package com.sxt.system.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sxt.system.domain.Role;
import com.sxt.system.service.IPermissionService;
import com.sxt.system.service.IRoleService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;
import com.sxt.system.vo.RoleVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-30
 */
@RestController
@RequestMapping("role")
public class RoleController {
	
	private Log log = LogFactory.getLog(RoleController.class);
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPermissionService permissionService;
	
	/**
	 * 全查询
	 * @param roleVo
	 * @return
	 */
	@RequestMapping("loadAllRole")
	public DataGridView loadAllRole(RoleVo roleVo) {
		
		return this.roleService.loadAllRole(roleVo);
		
	}
	
	/**
	 * 添加
	 * @param role
	 * @return
	 */
	@RequestMapping("addRole")
	public ResultObj addRole(Role role) {
		role.setCreatetime(new Date());//设置时间
		try {
			roleService.addRole(role);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			log.error("添加失败");
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 修改
	 * @param role
	 * @return
	 */
	@RequestMapping("updateRole")
	public ResultObj updateRole(Role role) {
		try {
			roleService.updateRole(role);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			log.error("修改失败");
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteRole")
	public ResultObj deleteRole(Integer id) {
		try {
			roleService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
  			return ResultObj.DELETE_ERROR;
		}
		
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("batchDeleteRole")
	public ResultObj batchDeleteRole(Integer[] ids) {
		try {
			if(ids == null || ids.length == 0) {
				log.error("参数不能为空");
				return ResultObj.DELETE_ERROR;
			}
			
			Collection<Serializable> idList = new ArrayList<Serializable>();
			for (Integer integer : ids) {
				idList.add(integer);
			}
			roleService.removeByIds(idList);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
		}
		return ResultObj.DELETE_ERROR;
	}
	/**
	 * 根据角色ID加载并选中权限和菜单
	 * @param roleId
	 * @return
	 */
	@RequestMapping("loadRolePermission")
	public DataGridView loadRolePermission(@RequestParam("id")Integer roleId) {
		return this.permissionService.queryRolePermissionByRoleId(roleId);
	}
	
	@RequestMapping("saveRolePermission")
	public ResultObj saveRolePermission(Integer roleId,Integer[] pids) {
		try {
			this.roleService.saveRolePermission(roleId, pids);
			return ResultObj.DISPATCH_SUCCESS;
		} catch (Exception e) {
			log.error("分配失败");
			return ResultObj.DISPATCH_ERROR;
		}		
	}
	
	/**
	 * 根据用户ID加载用户管理页面的分配角色弹出层里面的数据 并选中之前拥有的角色
	 * @param userId
	 * @return
	 */
	@RequestMapping("loadRolesByUserId")
	public DataGridView loadRolesByUserId(@RequestParam("userId")Integer userId) {
		return this.roleService.queryRoleByUserId(userId);
		
	}
}

