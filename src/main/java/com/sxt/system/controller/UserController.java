package com.sxt.system.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.system.constant.Constant;
import com.sxt.system.domain.User;
import com.sxt.system.service.IUserService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;
import com.sxt.system.vo.UserVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 雷卫
 * @since 2020-03-26
 */
@RestController
@RequestMapping("user")
public class UserController {
	
	private Log log = LogFactory.getLog(UserController.class);
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 全查询
	 * @param user
	 * @return
	 */
	@RequestMapping("loadAllUser")
	public DataGridView loadAllUser(UserVo userVo) {
		return this.userService.loadAllUser(userVo);
		
	}
	
	/**
	 * 根据部门ID查询员工
	 * @param deptid
	 * @return
	 */
	@RequestMapping("queryUserByDeptId")
	public DataGridView queryUserByDeptId(Integer deptid) {
		return new DataGridView(this.userService.queryUserByDeptId(deptid));
		
	}
	
	/**
	 * 加载最大排序码
	 * @return
	 */
	@RequestMapping("loadUserMaxOrderNum")
	public DataGridView loadUserMaxOrderNum() {
		Integer max = this.userService.loadUserMaxOrderNum();
		return new DataGridView(max+1);
	}
	
		
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@RequestMapping("addUser")
	public ResultObj addUser(User user) {
		user.setHiredate(new Date());
		user.setType(Constant.USER_TYPE_NORMAL);
		user.setImgpath(Constant.USER_DEFAULT_IMAGE);
		user.setSalt(UUID.randomUUID().toString().replace("-", "").toUpperCase());//生成盐
		user.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, user.getSalt(), 2).toString());//加密
		try {
			userService.addUser(user);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			log.error("添加失败");
			return ResultObj.ADD_ERROR;
		}	
	}
	
	/**
	 * 根据用户ID查询用户对象
	 * @param userId
	 * @return
	 */
	@RequestMapping("loadUserByUserId")
	public DataGridView loadUserByUserId(Integer userId) {
		User user = this.userService.getById(userId);
		return new DataGridView(user);
	}
	
	/**
	 * 修改
	 * @param user
	 * @return
	 */
	@RequestMapping("updateUser")
	public ResultObj updateUser(User user) {
		try {
			userService.updateUser(user);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			log.error("修改失败");
			return ResultObj.UPDATE_ERROR;
		}
	}
	
	/**
	 * 保存角色和用户之间的关系 操作sys_role_user
	 * @param userId
	 * @param rids
	 * @return
	 */
	@RequestMapping("saveUserRole")
	public ResultObj saveUserRole(Integer userId,Integer[] rids) {
		try {
			this.userService.saveUserRole(userId, rids);
			return ResultObj.DISPATCH_SUCCESS;
		} catch (Exception e) {
			log.error("分配失败");
			return ResultObj.DISPATCH_ERROR;
		}
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteUser")
	public ResultObj deleteUser(Integer id) {
		try {
			userService.removeById(id);
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
	@RequestMapping("batchDeleteUser")
	public ResultObj batchDeleteUser(Integer[] ids) {
		try {
			if(ids == null || ids.length == 0) {
				log.error("参数为空");
				return ResultObj.DELETE_ERROR;
			}
		Collection<Serializable> idList = new ArrayList<>();
		for (Integer integer : ids) {
			idList.add(integer);
		}
		userService.removeByIds(idList);
		return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
			return ResultObj.DELETE_ERROR;
		}
	}
	
	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	@RequestMapping("resetUserPwd")
	public ResultObj resetUserPwd(Integer id) {
		try {
			userService.resetUserPwd(id);
			return ResultObj.RESET_SUCCESS;
		} catch (Exception e) {
			log.error("重置失败");
			return ResultObj.RESET_ERROR;
		}
		
	}
	
	/**
	 * 修改个人信息
	 * @param user
	 * @return
	 */
	@RequestMapping("changeUser")
	public ResultObj changeUser(User user) {
		try {
			this.userService.changeUser(user);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			log.error("修改失败");
			return ResultObj.UPDATE_ERROR;
		}
	}
}

