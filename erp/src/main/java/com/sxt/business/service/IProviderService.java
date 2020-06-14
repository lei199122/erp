package com.sxt.business.service;

import com.sxt.business.domain.Provider;
import com.sxt.business.vo.ProviderVo;
import com.sxt.system.utils.DataGridView;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-08
 */
public interface IProviderService extends IService<Provider> {
	
	DataGridView queryAllProvider(ProviderVo providerVo);
	
	Provider addProvider(Provider provider);
	
	Provider updateProvider(Provider provider);
	
	/**
	 * 查询所有可用的供应商
	 * @return
	 */
	DataGridView queryAllAvailableProvider();
}
