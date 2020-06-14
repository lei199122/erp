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
import com.sxt.system.domain.Dept;

/**
 * 部门缓冲处理
 * @author LW
 *
 */
@Component
@Aspect
@EnableAspectJAutoProxy //默认开启的 不加也可以
public class DeptCacheAspect {
	
	private Log log = LogFactory.getLog(DeptCacheAspect.class);
	
	//切入点
	private static final String PONITCUT_DEPT_ADD="execution(* com.sxt.system.service.impl.DeptServiceImpl.addDept(..))";
	private static final String PONITCUT_DEPT_UPDATE="execution(* com.sxt.system.service.impl.DeptServiceImpl.updateDept(..))";
	private static final String PONITCUT_GETONE="execution(* com.sxt.system.service.impl.DeptServiceImpl.getById(..))";
	private static final String PONITCUT_DELETE="execution(* com.sxt.system.service.impl.DeptServiceImpl.removeById(..))";
	
	private static final String KEY_PROFIX="dept:";
	
	/**
	 * 添加的缓存切面
	 * @param joinPoint
	 * @return
	 */
	@Around(value = PONITCUT_DEPT_ADD)
	public Object cacheAddDept(ProceedingJoinPoint joinPoint) {
		try {
			//放到去做添加
			Dept dept = (Dept) joinPoint.proceed();
			//把添加完成的部门对象放到缓存里面
			DataProvider.DATA_CACHE.put(KEY_PROFIX+dept.getId(),dept);
			log.info("部门对象缓存更新成功："+ KEY_PROFIX+dept.getId());
			return dept;
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
	@Around(value = PONITCUT_DEPT_UPDATE)
	public Object cacheUpdateDept(ProceedingJoinPoint joinPoint) {
		try {
			Dept dept = (Dept) joinPoint.proceed();
			DataProvider.DATA_CACHE.put(KEY_PROFIX+dept.getId(), dept);
			log.info("部门对象缓存更新成功："+KEY_PROFIX+dept.getId());
			return dept;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("部门对象缓存更新异常："+e.getMessage());
		}
		
		return null;
		
	}
	
	@Around(value = PONITCUT_GETONE)
	public Object cacheGetOneDept(ProceedingJoinPoint joinPoint) {
		try {
			//根据ID去查询缓存
			Serializable id = (Serializable) joinPoint.getArgs()[0];
			Dept cacheDept = (Dept) DataProvider.DATA_CACHE.get(KEY_PROFIX+id);
			if(null == cacheDept) {
				Dept dept = (Dept) joinPoint.proceed();	
				log.info("缓存里面没有部门对象，去数据库查放入缓存："+KEY_PROFIX+dept.getId());
				DataProvider.DATA_CACHE.put(KEY_PROFIX+dept.getId(), dept);
				return dept;
				}else {
					log.info("缓存里面找到部门对象："+KEY_PROFIX+cacheDept.getId());
					return cacheDept;
				}
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("部门对象缓存更新异常："+e.getMessage());
		}
		
		return null;
		
	}
	
	@Around(value = PONITCUT_DELETE)
	public Object cacheDeleteDept(ProceedingJoinPoint joinPoint) {
		try {
			Serializable id = (Serializable) joinPoint.proceed();
			log.info("删除缓存："+KEY_PROFIX+id);
			//放到去做数据库删除
			Object obj = joinPoint.proceed();
			return obj;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("部门对象删除及缓存删除异常："+e.getMessage());
		}
		return false;
		
	}
}
