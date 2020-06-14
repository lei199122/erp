package com.sxt.system.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sxt.system.utils.ActiverUser;
import com.sxt.system.utils.ResultObj;
import com.sxt.system.utils.TreeNode;
import com.sxt.system.utils.TreeNodeBuilder;
import com.sxt.system.vo.PermissionVo;
import com.sxt.system.constant.Constant;
import com.sxt.system.domain.Loginfo;
import com.sxt.system.domain.Permission;
import com.sxt.system.domain.User;
import com.sxt.system.service.ILoginfoService;
import com.sxt.system.service.IPermissionService;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	private Log log = LogFactory.getLog(LoginController.class);
	
	@Autowired
	private IPermissionService permissionService;
	
	@Autowired
	private ILoginfoService loginfoservice;
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @param session
	 * @return
	 */
	@RequestMapping("login")
	public ResultObj login(String username,String password,HttpSession session , HttpServletRequest request) {
		try {
			Subject subject = SecurityUtils.getSubject();//得到主体
			AuthenticationToken token=new UsernamePasswordToken(username, password);
			subject.login(token);
			ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
			User user = activerUser.getUser();
			session.setAttribute("user", user);
			//写入登陆日志
			Loginfo entity = new Loginfo();
			entity.setLoginname(user.getName()+ "-" +user.getLoginname());
			//如果使用localhostip 为0.0.0.0
			entity.setLoginip(request.getRemoteAddr());
			entity.setLogintime(new Date());
			loginfoservice.save(entity);
			//说明登陆成功
			return ResultObj.LOGIN_SUCCESS;
		} catch (Exception e) {
			log.error("登陆失败，用户名或密码不正确");
			return ResultObj.LOGIN_ERROR;
		}
		
	}
	
	@RequestMapping("loadIndexLeftMenuTreeJson")
	public List<TreeNode> loadIndexLeftMenuTreeJson(PermissionVo permissionVo ,HttpSession session){
		//1查询出所有可用的菜单
		permissionVo.setType(Constant.TYPE_MENU);
		permissionVo.setAvailable(Constant.AVAILABLE_TRUE);
		User user = (User) session.getAttribute("user");
		List<Permission> permissions = null;
		if(user.getType() == Constant.USER_TYPE_SUPER) {
			permissions = this.permissionService.queryAllPermission(permissionVo);
		}else {
			permissions = this.permissionService.queryPermissionsByUserIdForList(permissionVo, user.getId());
		}
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for(Permission p : permissions) {
			Integer id = p.getId();
			Integer pid = p.getPid();
			String  title = p.getTitle();
			String href = p.getHref();
			String icon = p.getIcon();
			Boolean spread = p.getOpen() == 1?true:false;
			treeNodes.add(new TreeNode(id, pid, title, href, icon, spread));
		}
		return TreeNodeBuilder.build(treeNodes, 1);
		
	}
}
