package com.sxt.business.service;

import com.sxt.business.domain.Customer;
import com.sxt.business.vo.CustomerVo;
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
public interface ICustomerService extends IService<Customer> {
	
	
	DataGridView queryAllCustomer(CustomerVo customerVo);
	
	Customer addCustomer(Customer customer);
	
	Customer updateCustomer(Customer customer);
}
