package com.sxt.system.mapper;

import com.sxt.system.domain.Role;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-30
 */
public interface RoleMapper extends BaseMapper<Role> {
	
	/**
	 * 根据角色ID删除角色和权限中间表的数据
	 * @param id
	 */
	void deleteRolePermissionByRid(Serializable id);
	
	/**
	 * 根据菜单或权限ID删除sys_role_permission里面的数据
	 * @param id
	 */
	void deleteRolePermissionByPid(Serializable id);
	
	/**
	 * 保存角色和菜单的关系
	 * @param roleId
	 * @param pid
	 */
	void saveRolePermission(@Param("rid")Integer roleId , @Param("pid")Integer pid);
	/**
	 * 根据用户ID删除用户和角色关系
	 * @param id
	 */
	void deleteRoleUserByUserId(Serializable id);
	/**
	 * 根据角色ID删除用户和角色的关系
	 * @param id
	 */
	void deleteRoleUserByRoleId(Serializable id);
	/**
	 * 查询当前用户拥有的角色ID
	 * @param userId
	 * @return
	 */
	List<Integer> selectRoleIdsByUserId(Integer userId);
}
