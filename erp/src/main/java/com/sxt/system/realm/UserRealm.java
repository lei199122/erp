package com.sxt.system.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.sxt.system.service.IPermissionService;
import com.sxt.system.service.IRoleService;
import com.sxt.system.service.IUserService;
import com.sxt.system.utils.ActiverUser;
import com.sxt.system.constant.Constant;
import com.sxt.system.domain.User;

public class UserRealm  extends AuthorizingRealm{
	
	
	@Autowired
	@Lazy
	private IUserService userService;
	
	@Autowired
	@Lazy
	private IRoleService roleService;
	
	@Autowired
	@Lazy
	private IPermissionService permissionService;
	
	@Override
	public String getName() {
		
		return this.getClass().getSimpleName();
	}
	
	/**
	 *认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) 
			throws AuthenticationException {
		
		User user = this.userService.queryByUserName(token.getPrincipal().toString());
		if(null != user) {
			//创建ActiverUser
			ActiverUser activerUser = new ActiverUser();
			activerUser.setUser(user);
			if(user.getType() == Constant.USER_TYPE_NORMAL) {
				//说明是普通用户
				List<String> roles = roleService.queryRoleNameByUserId(user.getId());
				activerUser.setRoles(roles);
				//查询权限编码
				List<String> permissions = permissionService.queryPermissionByUserId(user.getId());
				activerUser.setPermissions(permissions);
			}
			ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
			//转化盐
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser, user.getPwd() ,credentialsSalt , getName());
			
			return info;
		}
		return null;
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		ActiverUser activerUser = (ActiverUser) principals.getPrimaryPrincipal();
		if(activerUser.getUser().getType() == Constant.USER_TYPE_SUPER) {
			info.addStringPermission("*:*");
		}else {
			List<String> roles = activerUser.getRoles();
			List<String> permission = activerUser.getPermissions();
			if(roles != null && roles.size() > 0) {
				info.addRoles(roles);
			}
			if(permission != null && permission.size() > 0) {
				info.addStringPermissions(permission);
			}
		}
		return info;
	}

	



}
