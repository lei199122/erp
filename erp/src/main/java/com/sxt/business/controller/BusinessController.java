package com.sxt.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 业务管理路由器
 * @author LW
 *
 */
@Controller
@RequestMapping("/business")
public class BusinessController {
	
	/**
	 * 跳转到客户管理
	 * @return
	 */
	@RequestMapping("toCustomerManager")
	public String  toCustomerManager() {
		return "business/customer/customerManager";
	}
	/**
	 * 跳转到供应商管理
	 * @return
	 */
	@RequestMapping("toProviderManager")
	public String toProviderManager() {
		return "business/provider/providermanager";
	}
	
	/**
	 * 跳转到商品管理
	 * @return
	 */
	@RequestMapping("toGoodsManager")
	public String toGoodsManager() {
		return "business/goods/goodsManager";
		
	}
	
	/**
	 * 跳转到进货管理
	 * @return
	 */
	@RequestMapping("toInportManager")
	public String toInportManager() {
		return "business/inport/inportManager";
		
	}
	
	/**
	 * 跳转到退货查询管理
	 * @return
	 */
	@RequestMapping("toOutportManager")
	public String toOutportManager() {
		return "business/outport/outportManager";
		
	}
	
	/**
	 * 跳转到进货管理
	 * @return
	 */
	@RequestMapping("toSalesManager")
	public String toSalesManager() {
		return "business/sales/salesManager";
	}
	
	/**
	 * 跳转到进货管理
	 * @return
	 */
	@RequestMapping("toSalesBackManager")
	public String toSalesbackManager() {
		return "business/salesback/salesbackManager";
		
	}
}
