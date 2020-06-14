package com.sxt.business.cache;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.sxt.business.domain.Goods;
import com.sxt.system.cache.data.DataProvider;

@Component
@Aspect
public class GoodsCacheAspect {
	
	private Log log = LogFactory.getLog(GoodsCacheAspect.class);
	
	//声明切入点
	private static final String PONITCUT_GOODS_ADD = "execution(* com.sxt.business.service.impl.GoodsServiceImpl.addGoods(..))";
	private static final String PONITCUT_GOODS_UPDATE = "execution(* com.sxt.business.service.impl.GoodsServiceImpl.updateGoods(..))";
	private static final String PONITCUT_GOODS_GETONE = "execution(* com.sxt.business.service.impl.GoodsServiceImpl.getById(..))";
	private static final String PONITCUT_GOODS_DELETE = "execution(* com.sxt.business.service.impl.GoodsServiceImpl.removeById(..))";
	
	private static final String KEY_PROFIX = "goods:";
	
	/**
	 * 添加的缓存切面
	 * @param joinPoint
	 * @return
	 */
	@Around(value = PONITCUT_GOODS_ADD)
	public Object cacheAddGoods(ProceedingJoinPoint joinPoint) {
		try {
			Goods provider = (Goods) joinPoint.proceed();//放到去做添加
			//把添加完成的商品对象放到缓存里面
			DataProvider.DATA_CACHE.put(KEY_PROFIX + provider.getId(), provider);
			log.info("商品对象缓存成功：" + KEY_PROFIX + provider.getId());
			return provider;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("缓存在处理异常：" + e.getMessage());
		}
		return null;
		
	}
	
	/**
	 * 修改的切面缓存
	 * @param joinPoint
	 * @return
	 */
	@Around(value =  PONITCUT_GOODS_UPDATE)
	public Object cacheUpdateGoods(ProceedingJoinPoint joinPoint) {
		try {
			//放到去做添加
			Goods proider = (Goods) joinPoint.proceed();
			//把添加完成的商品对象放到缓存里面
			DataProvider.DATA_CACHE.put(KEY_PROFIX + proider.getId(), proider);
			log.info("商品对象缓存更新成功：" + KEY_PROFIX+proider.getId());
			return proider;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("商品对象缓存更新异常：" + e.getMessage());
		}
		return null;
	}
	
	/**
	 * 查询一个缓存切面
	 * @param joinPoint
	 * @return
	 */
	@Around(value = PONITCUT_GOODS_GETONE)
	public Object cacheGetoneGoods(ProceedingJoinPoint joinPoint) {
		try {
			//先根据ID去查询缓存
			Serializable id = (Serializable) joinPoint.proceed();
			
			Goods cacheGoods = (Goods) DataProvider.DATA_CACHE.get(KEY_PROFIX + id); 
			if(null == cacheGoods) {
				Goods provider = (Goods) joinPoint.proceed();
				log.info("缓存里面没有商品对象，去数据库查放入缓存：" + KEY_PROFIX + provider.getId());
				DataProvider.DATA_CACHE.put(KEY_PROFIX + provider.getId(), provider);
				return provider;
			}else {
				log.info("缓存里面找到商品对象：" + KEY_PROFIX + cacheGoods.getId());
				return cacheGoods;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("商品对象缓存更新异常" + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * 删除缓存
	 * @param joinPoint
	 * @return
	 */
	@Around(value = PONITCUT_GOODS_DELETE)
	public Object cacheDeleteGoods(ProceedingJoinPoint joinPoint) {
		try {
			//先根据ID去查询缓存
			Serializable id = (Serializable) joinPoint.proceed();
			log.info("删除缓存" + KEY_PROFIX + id);
			DataProvider.DATA_CACHE.remove(KEY_PROFIX + id);
			Object obj= joinPoint.proceed();
			return obj;
		} catch (Throwable e) {
			e.printStackTrace();
			log.error("商品对象删除及缓存删除异常" + e.getMessage());
		}
		return false;
	}
}
