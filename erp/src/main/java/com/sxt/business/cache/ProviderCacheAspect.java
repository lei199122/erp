package com.sxt.business.cache;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.sxt.business.domain.Provider;
import com.sxt.system.cache.data.DataProvider;


@Component
@Aspect
public class ProviderCacheAspect {
private Log log = LogFactory.getLog(ProviderCacheAspect.class);
	
	//切入点
	private static final String PONITCUT_PROVIDER_ADD="execution(* com.sxt.business.service.impl.ProviderServiceImpl.addProvider(..))";
	private static final String PONITCUT_PROVIDER_UPDATE="execution(* com.sxt.business.service.impl.ProviderServiceImpl.updateProvider(..))";
	private static final String PONITCUT_PROVIDER_GETONE="execution(* com.sxt.business.service.impl.ProviderServiceImpl.getById(..))";
	private static final String PONITCUT_PROVIDER_DELETE="execution(* com.sxt.business.service.impl.ProviderServiceImpl.removeById(..))";
	
	private static final String KEY_PROFIX="provider:";
	
	/**
	 * 添加的缓存切面
	 * @param joinPoint
	 * @return
	 */
	@Around(value = PONITCUT_PROVIDER_ADD)
	public Object cacheAddProvider(ProceedingJoinPoint joinPoint) {
		try {
			//放到去做添加
			Provider provider = (Provider) joinPoint.proceed();
			//把添加完成的部门对象放到缓存里面
			DataProvider.DATA_CACHE.put(KEY_PROFIX+provider.getId(),provider);
			log.info("部门对象缓存更新成功："+ KEY_PROFIX+provider.getId());
			return provider;
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
	@Around(value = PONITCUT_PROVIDER_UPDATE)
	public Object cacheUpdateProvider(ProceedingJoinPoint joinPoint) {
		try {
			Provider provider = (Provider) joinPoint.proceed();
			DataProvider.DATA_CACHE.put(KEY_PROFIX+provider.getId(), provider);
			log.info("部门对象缓存更新成功："+KEY_PROFIX+provider.getId());
			return provider;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("部门对象缓存更新异常："+e.getMessage());
		}
		
		return null;
		
	}
	
	@Around(value = PONITCUT_PROVIDER_GETONE)
	public Object cacheGetOneProvider(ProceedingJoinPoint joinPoint) {
		try {
			//根据ID去查询缓存
			Serializable id = (Serializable) joinPoint.getArgs()[0];
			Provider cacheProvider = (Provider) DataProvider.DATA_CACHE.get(KEY_PROFIX+id);
			if(null == cacheProvider) {
				Provider provider = (Provider) joinPoint.proceed();	
				log.info("缓存里面没有部门对象，去数据库查放入缓存："+KEY_PROFIX+provider.getId());
				DataProvider.DATA_CACHE.put(KEY_PROFIX+provider.getId(), provider);
				return provider;
				}else {
					log.info("缓存里面找到部门对象："+KEY_PROFIX+cacheProvider.getId());
					return cacheProvider;
				}
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("部门对象缓存更新异常："+e.getMessage());
		}
		
		return null;
		
	}
	
	@Around(value = PONITCUT_PROVIDER_DELETE)
	public Object cacheDeleteProvider(ProceedingJoinPoint joinPoint) {
		try {
			Serializable id = (Serializable) joinPoint.proceed();
			log.info("删除缓存："+KEY_PROFIX+id);
			//放到去做数据库删除
			DataProvider.DATA_CACHE.remove(KEY_PROFIX+id);
			Object obj = joinPoint.proceed();
			return obj;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("部门对象删除及缓存删除异常："+e.getMessage());
		}
		return false;
		
	}
}
