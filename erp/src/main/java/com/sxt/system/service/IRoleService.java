package com.sxt.system.service;

import com.sxt.system.domain.Role;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.RoleVo;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-30
 */
public interface IRoleService extends IService<Role> {
	
	public DataGridView loadAllRole(RoleVo roleVo);
	
	public Role addRole(Role role);
	
	public Role updateRole(Role role);
	
	public DataGridView queryRoleByUserId(Integer userId);
	
	/**
	 * 保存角色和菜单之间的关系
	 * @param roleId
	 * @param pids
	 */
	public void saveRolePermission(Integer roleId, Integer[] pids);
	
	/**
	 * 根据用户ID查询用户拥有的角色名称
	 * @param id
	 * @return
	 */
	public List<String> queryRoleNameByUserId(Integer id);
}
