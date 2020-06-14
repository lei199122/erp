package com.sxt.system.service;

import com.sxt.system.domain.Permission;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.PermissionVo;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-27
 */
public interface IPermissionService extends IService<Permission> {
	
	/**
	 * 查询菜单或权限
	 * @param permissionVo
	 * @return
	 */
	List<Permission> queryAllPermission(PermissionVo permissionVo);
	
	DataGridView loadAllPermission(PermissionVo permissionVo);
	
	Integer queryPermissionMaxOrderNum();
	
	Permission addPermission(Permission permission);
	
	Permission updatePermission(Permission permission);
	
	Integer queryPermissionCountByPid(Integer id);
	
	DataGridView queryRolePermissionByRoleId(Integer roleId);
	/**
	 * 根据用户ID查询用户关联的权限编码
	 * @param id
	 * @return
	 */
	List<String> queryPermissionByUserId(Integer id);
	
	/**
	 * 根据用户ID查询用户拥有的菜单
	 * @param permissionVo
	 * @return
	 */
	List<Permission> queryPermissionsByUserIdForList(PermissionVo permissionVo, Integer id);
	

} 
