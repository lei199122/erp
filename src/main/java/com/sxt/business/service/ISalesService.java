package com.sxt.business.service;

import com.sxt.business.domain.Sales;
import com.sxt.business.vo.SalesVo;
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
public interface ISalesService extends IService<Sales> {
	
	DataGridView queryAllSales(SalesVo salesVo);
	
	Sales addSales(Sales sales);
	
	Sales updateSales(Sales sales);
}
