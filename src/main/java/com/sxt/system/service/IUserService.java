package com.sxt.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sxt.system.domain.User;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 雷卫
 * @since 2020-03-26
 */
public interface IUserService extends IService<User> {
	
	/**
	 * 根据登录名查询用户对象
	 * @param username
	 * @return
	 */
	public User queryByUserName(String username);
	
	public DataGridView loadAllUser(UserVo userVo);
	
	/**
	 * 根据部门ID查询员工集合
	 * @param deptid
	 * @return
	 */
	public List<User> queryUserByDeptId(Integer deptid);
	/**
	 * 加载最大排序码
	 * @return
	 */
	public Integer loadUserMaxOrderNum();
	
	public User addUser(User user);
	
	public User updateUser(User user);
	
	/**
	 * 重置密码
	 * @param id
	 */
	public void resetUserPwd(Integer id);
	
	public void saveUserRole(Integer userId, Integer[] rids);
	/**
	 * 修改个人信息
	 * @param user
	 */
	public void changeUser(User user);
}
