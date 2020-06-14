package com.sxt.business.service;

import com.sxt.business.domain.Salesback;
import com.sxt.business.vo.SalesbackVo;
import com.sxt.system.utils.DataGridView;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-19
 */
public interface ISalesbackService extends IService<Salesback> {
	
	Integer queryOutportCountBySalesId(Integer salesId);
	
	void addSales(Salesback salesback);
	
	DataGridView queryAllSalesback(SalesbackVo salesbackVo);
}
