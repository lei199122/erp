package com.sxt.business.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.business.domain.Customer;
import com.sxt.business.service.ICustomerService;
import com.sxt.business.vo.CustomerVo;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-08
 */
@RestController
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired
	private ICustomerService customerService;
	
	/**
	 * 查询
	 * @param customerVo
	 * @return
	 */
	@RequestMapping("loadAllCustomer")
	public DataGridView loadAllCustomer(CustomerVo customerVo) {
		return this.customerService.queryAllCustomer(customerVo);
		
	}
	
	/**
	 * 添加
	 * @param customer
	 * @return
	 */
	@RequestMapping("addCustomer")
	public ResultObj addCustomer(Customer customer) {
		try {
			this.customerService.addCustomer(customer);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 修改
	 * @param customer
	 * @return
	 */
	@RequestMapping("updateCustomer")
	public ResultObj updateCustomer(Customer customer) {
		try {
			this.customerService.updateCustomer(customer);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.UPDATE_ERROR;
		}
		
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteCustomer")
	public ResultObj deleteCustomer(Integer id) {
		try {
			this.customerService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("batchDeleteCustomer")
	public ResultObj batchDeleteCustomer(Integer[] ids) {
		try {
			 for (Integer id : ids) {
				 this.customerService.removeById(id);
			}
			 return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}

