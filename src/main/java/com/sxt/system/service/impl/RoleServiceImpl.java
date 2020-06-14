package com.sxt.system.service.impl;

import com.sxt.system.constant.Constant;
import com.sxt.system.domain.Role;
import com.sxt.system.mapper.RoleMapper;
import com.sxt.system.service.IRoleService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.RoleVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-30
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
	
	private Log log = LogFactory.getLog(RoleServiceImpl.class);
	
	@Autowired
	private RoleMapper roleMapper;
	
	
	@Transactional(readOnly = true)
	@Override
	public DataGridView loadAllRole(RoleVo roleVo) {
		QueryWrapper<Role> qw = new QueryWrapper<>();
		if(null != roleVo) {
			qw.like(StringUtils.isNoneBlank(roleVo.getName()), "name", roleVo.getName());
			qw.like(StringUtils.isNoneBlank(roleVo.getRemark()), "remark", roleVo.getRemark());
		}else {
			log.info("roleVo为空");
		}
		
		Page<Role> page = new Page<>(roleVo.getPage(),roleVo.getLimit());
		this.roleMapper.selectPage(page, qw);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	@Override
	public Role addRole(Role role) {
		this.roleMapper.insert(role);
		return role;
	}

	@Override
	public Role updateRole(Role role) {
		this.roleMapper.updateById(role);
		return role;
	}
	
		@Override
		public boolean removeById(Serializable id) {
			//根据角色ID删除角色和权限中间表的数据
			this.getBaseMapper().deleteRolePermissionByRid(id);
			this.getBaseMapper().deleteRoleUserByRoleId(id);
			return super.removeById(id);//删除角色
		}
	
		@Override
		public boolean removeByIds(Collection<? extends Serializable> idList) {
			//根据角色ID删除角色和权限中间表的数据
			if(idList.size() > 0) {
				for (Serializable id : idList) {
					this.getBaseMapper().deleteRolePermissionByRid(id);
					this.getBaseMapper().deleteRoleUserByRoleId(id);
				}
			}
			return super.removeByIds(idList);
		}	

	@Override
	public void saveRolePermission(Integer roleId, Integer[] pids) {
		//根据roleID删除sys_role_permission里面的数据
		this.getBaseMapper().deleteRolePermissionByRid(roleId);
		//保存关系
		if(null != pids&&pids.length > 0) {
			for (Integer pid : pids) {
				this.getBaseMapper().saveRolePermission(roleId, pid);
			}
		}
	}

	
	@Override
	public DataGridView queryRoleByUserId(Integer userId) {
		QueryWrapper<Role> qw= new QueryWrapper<>();
		qw.eq("available", Constant.AVAILABLE_TRUE);
		//查询所有可用的角色
		List<Role> allRoles = this.getBaseMapper().selectList(qw);
		
		//查询当前用户拥有的角色ID
		List<Integer> roleIds = this.getBaseMapper().selectRoleIdsByUserId(userId);
		
		List<Role> currentUserRoles = new ArrayList<Role>();
		if(roleIds != null && roleIds.size() != 0) {
			qw.in("id", roleIds);
			currentUserRoles = this.getBaseMapper().selectList(qw);
		}
		
		List<Map<String,Object>> res = new ArrayList<>();
		for (Role r1 : allRoles) {
			Boolean LAY_CHECKED = false;
			for (Role r2 : currentUserRoles) {
				if(r1.getId() == r2.getId()) {
					LAY_CHECKED = true;
					break;
				}
			}
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("id", r1.getId());
			map.put("name", r1.getName());
			map.put("remark", r1.getRemark());
			map.put("LAY_CHECKED", LAY_CHECKED);
			res.add(map);
		}
		return new DataGridView(res);
	}

	@Override
	public List<String> queryRoleNameByUserId(Integer id) {
		//查询当用户拥有的角色ID
		List<Integer> roleIds = this.getBaseMapper().selectRoleIdsByUserId(id);
		QueryWrapper<Role> qw = new QueryWrapper<>();
		qw.eq("available",Constant.AVAILABLE_TRUE );
		List<Role> currentUserRoles = new ArrayList<>();
		if(roleIds != null && roleIds.size() != 0) {
			qw.in("id", roleIds);
			currentUserRoles = this.getBaseMapper().selectList(qw);
		}
		List<String> roles = new ArrayList<>();
		for (Role role : currentUserRoles) {
			roles.add(role.getName());
		}
		return roles;
	}
}
