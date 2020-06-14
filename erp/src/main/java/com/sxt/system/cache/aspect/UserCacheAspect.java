package com.sxt.system.cache.aspect;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.sxt.system.cache.data.DataProvider;
import com.sxt.system.domain.User;

/**
 * 用户缓存处理
 * @author LW
 *
 */
@Component
@Aspect
@EnableAspectJAutoProxy //默认开启的，不加也可以
public class UserCacheAspect {
	
	private Log log = LogFactory.getLog(UserCacheAspect.class);
	
	//声明切入点
	private static final String PONITCUT_USER_ADD="execution(* com.sxt.system.service.impl.UserServiceImpl.addUser(..))";
	private static final String PONITCUT_USER_UPDATE="execution(* com.sxt.system.service.impl.UserServiceImpl.updateUser(..))";
	private static final String PONITCUT_USER_GETONE="execution(* com.sxt.system.service.impl.UserServiceImpl.getById(..))";
	private static final String PONITCUT_USER_DELETE="execution(* com.sxt.system.service.impl.UserServiceImpl.removeById(..))";
	
	private static final String KEY_PROFIX="user";
	
	/**
	 * 查询一个的缓存切面
	 * @param joinPoint
	 * @return
	 */
	@Around(value = PONITCUT_USER_GETONE)
	public Object cacheGetOneUser(ProceedingJoinPoint joinPoint) {
		try {
			//先根据ID去查询缓存
			Serializable id = (Serializable) joinPoint.getArgs()[0];
			User cacheUser = (User) DataProvider.DATA_CACHE.get(KEY_PROFIX+id);
			if(null == cacheUser) {
				User user = (User) joinPoint.proceed();//放到去做数据库查询
				log.info("缓存里面没有用户对象，去数据库查放入缓存"+KEY_PROFIX+user.getId());
				DataProvider.DATA_CACHE.put(KEY_PROFIX+user.getId(), user);
				return user;
			}else {
				log.info("缓存里面找到用户对象："+KEY_PROFIX+cacheUser.getId());
				return cacheUser;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("用户对象缓存更新异常："+e.getMessage());
		}
		return null;
		
	}
	
	/**
	 * 添加的缓存切面
	 * @param joinPoint
	 * @return
	 */
	@Around(value = PONITCUT_USER_ADD)
	public Object cacheAddUser(ProceedingJoinPoint joinPoint) {
		try {
			User user = (User) joinPoint.proceed();
			//把添加完成的用户对象放到缓存里面
			DataProvider.DATA_CACHE.put(KEY_PROFIX+user.getId(), user);
			log.info("用户对象缓存成功：" + KEY_PROFIX+user.getId());
			return user;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("缓存在处理异常：" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 修改的缓存切面
	 * @param joinPoint
	 * @return
	 */
	@Around(value = PONITCUT_USER_UPDATE)
	public Object cacheUpdateUser(ProceedingJoinPoint joinPoint) {
		try {
			//放到去做添加
			User user = (User) joinPoint.proceed();
			//把添加完成的用户对象放到缓存里面
			DataProvider.DATA_CACHE.put(KEY_PROFIX+user.getId(), user);
			log.info("用户对象缓存更新成功："+KEY_PROFIX+user.getId());
			return user;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("用户对象缓存更新异常："+e.getMessage());
		}
		return null;
	}
	
	@Around(value = PONITCUT_USER_DELETE)
	public Object cacheDeleteUser(ProceedingJoinPoint joinPoint) {
		try {
			//根据ID去查询缓存
			Serializable id = (Serializable) joinPoint.getArgs()[0];
			log.info("删除缓存："+KEY_PROFIX+id);
			DataProvider.DATA_CACHE.remove(KEY_PROFIX+id);
			Object obj = joinPoint.proceed();//放到去做数据库删除
			return obj;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("用户对象删除及缓存删除异常："+e.getMessage());
		}
		return false;
	}
}
