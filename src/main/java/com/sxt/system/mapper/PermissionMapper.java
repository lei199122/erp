package com.sxt.system.mapper;

import com.sxt.system.domain.Permission;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-27
 */
public interface PermissionMapper extends BaseMapper<Permission> {
	
	Integer queryPermissionMaxOrderNum();
	
	Integer queryPermissionCountByPid(Integer id);
	/**
	 * 根据角色ID查询当前角色拥有的权限ID集合
	 * @param roleId
	 * @return
	 */
	List<Integer> queryPermissionIdsByRoleId(@Param("roleId")Integer roleId);
	
	/**
	 * 根据角色ID集合查询菜单和权限ID
	 * @param roleId
	 * @return
	 */
	List<Integer> queryPermissionIdsByRoleIds(@Param("roleIds")List<Integer> roleIds);
}
