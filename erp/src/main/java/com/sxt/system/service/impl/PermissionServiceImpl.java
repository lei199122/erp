package com.sxt.system.service.impl;

import com.sxt.system.constant.Constant;
import com.sxt.system.domain.Permission;
import com.sxt.system.mapper.PermissionMapper;
import com.sxt.system.mapper.RoleMapper;
import com.sxt.system.service.IPermissionService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.TreeNode;
import com.sxt.system.vo.PermissionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
 * @since 2020-03-27
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {
	
	private Log log = LogFactory.getLog(PermissionServiceImpl.class);
	
	@Override
	public List<Permission> queryAllPermission(PermissionVo permissionVo) {
		PermissionMapper permissionMapper = this.getBaseMapper();
		QueryWrapper<Permission> qw = new QueryWrapper<>();
		if(null != permissionVo) {
			qw.eq(StringUtils.isNotBlank(permissionVo.getType()), "type", permissionVo.getType());
			qw.eq(permissionVo.getAvailable() != null, "available", permissionVo.getAvailable());
			qw.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
		}else {
			log.info("permissionVo的参数为空");
		}
		qw.orderByAsc("ordernum");
		List<Permission> list = permissionMapper.selectList(qw);
		return list;
	}

	@Override
	public DataGridView loadAllPermission(PermissionVo permissionVo) {
		Page<Permission> page = new Page<>(permissionVo.getPage() ,permissionVo.getLimit());
		QueryWrapper<Permission> qw = new QueryWrapper<>();
		qw.eq(StringUtils.isNoneBlank(permissionVo.getType()), "type", permissionVo.getType());
		qw.eq(permissionVo.getAvailable() != null, "available", permissionVo.getAvailable());
		qw.like(StringUtils.isNoneBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
		qw.like(StringUtils.isNoneBlank(permissionVo.getPercode()), "percode", permissionVo.getPercode());
		
		qw.and(permissionVo.getId() != null, new Consumer<QueryWrapper<Permission>>() {
			
			@Override
			public void accept(QueryWrapper<Permission> t) {
				t.eq(permissionVo.getId() != null, "id" , permissionVo.getId())
				.or().
				eq(permissionVo.getId() != null, "pid", permissionVo.getId());
			}
		});
		qw.orderByAsc("ordernum");
		this.getBaseMapper().selectPage(page, qw);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	@Override
	public Integer queryPermissionMaxOrderNum() {
		
		return this.getBaseMapper().queryPermissionMaxOrderNum();
	}

	@Override
	public Permission addPermission(Permission permission) {
		this.baseMapper.insert(permission);
		return permission;
	}

	@Override
	public Permission updatePermission(Permission permission) {
		this.baseMapper.updateById(permission);
		return permission;
	}

	@Override
	public Integer queryPermissionCountByPid(Integer id) {
		
		return this.baseMapper.queryPermissionCountByPid(id);
	}
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public boolean removeById(Serializable id) {
		//根据菜单或权限ID删除sys_role_permission里面的数据
		this.roleMapper.deleteRolePermissionByPid(id);
		return super.removeById(id);
	}

	@Override
	public DataGridView queryRolePermissionByRoleId(Integer roleId) {
		QueryWrapper<Permission> qw = new QueryWrapper<>();
		qw.eq("available", Constant.AVAILABLE_TRUE);
		//1查询所有可用的权限和菜单
		List<Permission> allPermission = this.baseMapper.selectList(qw);
		//2根据角色ID查询当前角色拥有的权限ID集合
		List<Integer> permissionIds = this.baseMapper.queryPermissionIdsByRoleId(roleId);
		List<Permission> currRolePermission = null;
		//3,根据角色ID查询当前角色拥有的权限集合
		if(permissionIds == null || permissionIds.size()==0) {
			currRolePermission = new ArrayList<>();
		}else {
			qw.in("id", permissionIds);
			currRolePermission = this.baseMapper.selectList(qw);
		}
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		for (Permission p1 : allPermission) {
			String checkArr = "0";
			for (Permission p2 : currRolePermission) {
				if(p1.getId()== p2.getId()) {
				   checkArr = "1";
					break;
				}
			}
			Boolean spread=p1.getOpen()==null?false:p1.getOpen()==1?true:false;
			nodes.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), spread, checkArr));
		}
		return new DataGridView(nodes);
	}

	@Override
	public List<String> queryPermissionByUserId(Integer id) {
		//根据用户ID查询角色ID
		List<Integer> roleIds = this.roleMapper.selectRoleIdsByUserId(id);
		if(null == roleIds || roleIds.size() == 0) {
			return null;
		}else {
			//根据角色ID集合查询权限ID
			List<Integer> permissionIds = this.getBaseMapper().queryPermissionIdsByRoleIds(roleIds);
			if(null == permissionIds || permissionIds.size() == 0) {
				return null;
			}else {
				QueryWrapper<Permission> qw = new QueryWrapper<>();
				qw.eq("available", Constant.AVAILABLE_TRUE);
				qw.eq("type", Constant.TYPE_PERMISSION);
				qw.in("id", permissionIds);
				List<Permission> permissionObjs = this.getBaseMapper().selectList(qw);
				//根据用户ID查询权限
				List<String> permissions =new ArrayList<>();
				for (Permission permission : permissionObjs) {
					permissions.add(permission.getPercode());
				}
				return permissions;
			}
		}
	}

	@Override
	public List<Permission> queryPermissionsByUserIdForList(PermissionVo permissionVo ,Integer id) {
		//根据用户ID查询角色ID
		List<Integer> roleIds = this.roleMapper.selectRoleIdsByUserId(id);
		if (null == roleIds || roleIds.size() == 0) {
			return null;
		}else {
			//根据角色ID集合查询权限ID
			List<Integer> permissionIds = this.getBaseMapper().queryPermissionIdsByRoleIds(roleIds);
			if(null == permissionIds || permissionIds.size() == 0) {
				return null;
			}else {
				QueryWrapper<Permission> qw = new QueryWrapper<>();
				qw.eq("available", Constant.AVAILABLE_TRUE);
				qw.eq("type", Constant.TYPE_MENU);
				qw.in("id", permissionIds);
				List<Permission> permissionObjs = this.getBaseMapper().selectList(qw);
				return permissionObjs;
			}
		}
	}
}
