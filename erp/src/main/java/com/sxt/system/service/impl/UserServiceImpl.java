package com.sxt.system.service.impl;

import com.sxt.system.mapper.RoleMapper;
import com.sxt.system.mapper.UserMapper;
import com.sxt.system.service.IDeptService;
import com.sxt.system.service.IUserService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.FileUploadAndDownUtil;
import com.sxt.system.vo.UserVo;
import com.sxt.system.constant.Constant;
import com.sxt.system.domain.Dept;
import com.sxt.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 雷卫
 * @since 2020-03-26
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService, ApplicationContextAware{
	
	/**
	 * 声明日志输入对象
	 */
	private Log  log = LogFactory.getLog(UserServiceImpl.class);
	
	@Autowired
	@Lazy
	private IDeptService deptService;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public User queryByUserName(String username) {
		
		UserMapper userMapper = this.getBaseMapper();
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		if(null == username) {
			log.error("用户名不能为空");
			throw new RuntimeException("用户登录名不能为空");
		}
		queryWrapper.eq("loginname", username);
		User user = userMapper.selectOne(queryWrapper);
		return user;
	}
	
	
		@Override
		public User getById(Serializable id) {
			return super.getById(id);
		}


		@Override
		public DataGridView loadAllUser(UserVo userVo) {
			Page<User> page = new Page<>(userVo.getPage(),userVo.getLimit());
			QueryWrapper<User> qw = new QueryWrapper<>();
			qw.eq("type", Constant.USER_TYPE_NORMAL);
			this.baseMapper.selectPage(page, qw);
			List<User> list = page.getRecords();
			for (User user : list) {
				Integer deptid = user.getDeptid();
				Integer mgr = user.getMgr();
				//根据ID去查询部门
				Dept dept = deptService.getById(deptid);
				user.setDeptname(dept.getTitle());
				
				//根据领导ID去查询领导名称
				if(null != mgr) {
					IUserService userService = content.getBean(IUserService.class);
					User user2 = userService.getById(mgr);
					user.setLeadername(user2.getName());
				}
			}
			return new DataGridView(page.getTotal(),list);
		}
		
		
		private ApplicationContext content;
		
		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.content=applicationContext;
		}


		@Override
		public List<User> queryUserByDeptId(Integer deptid) {
			if(null == deptid) {
				return null;
			}else {
				QueryWrapper<User> qw = new QueryWrapper<>();
				qw.eq("type", Constant.USER_TYPE_NORMAL);
				qw.eq(deptid != null, "deptid", deptid);
				return this.getBaseMapper().selectList(qw);
			}
		}


		@Override
		public Integer loadUserMaxOrderNum() {
			
			return this.getBaseMapper().queryUserMaxOrderNum();
		}


		@Override
		public User addUser(User user) {
			this.getBaseMapper().insert(user);
			return user;
		}


		@Override
		public User updateUser(User user) {
			this.getBaseMapper().updateById(user);
			return user;
		}
		
	@Override
	public boolean removeById(Serializable id) {
		//根据用户ID删除用户和角色的关系
		roleMapper.deleteRoleUserByUserId(id);
		return super.removeById(id);
	}	
	
	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		for (Serializable id : idList) {
			this.roleMapper.deleteRoleUserByUserId(id);
		}
		return super.removeByIds(idList);
	}


	@Override
	public void resetUserPwd(Integer id) {
	User user = new User();
	user.setId(id);
	user.setSalt(UUID.randomUUID().toString().replace("-", "").toUpperCase());
	user.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, user.getSalt(), 2).toString());
	this.getBaseMapper().updateById(user);
	}


	@Override
	public void saveUserRole(Integer userId, Integer[] rids) {
		//根据用户ID删除sys_role_user里面的数据
		this.roleMapper.deleteRoleUserByUserId(userId);
		if(rids != null && rids.length > 0) {
			for (Integer rid : rids) {
				this.getBaseMapper().saveUserRole(userId, rid);
			}
		}
	}
	@Value("${upload.upload-root-path}")
	private String uploadRootPath="E:/upload";
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void changeUser(User user) {
		//1处理图片
		String imgpath = user.getImgpath();
		if(!imgpath.equals(Constant.USER_DEFAULT_IMAGE)) {
			if(imgpath.endsWith("_temp")) {
				String path = FileUploadAndDownUtil.changeFileName(uploadRootPath, imgpath);
				user.setImgpath(path);
				//删除原来的图片
				User sessionUser = (User) request.getSession().getAttribute("user");
				FileUploadAndDownUtil.deleteFile(uploadRootPath, sessionUser.getImgpath());
			}
		}
		this.getBaseMapper().updateById(user);
	}
	
	
}
