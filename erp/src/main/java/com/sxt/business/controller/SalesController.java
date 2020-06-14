package com.sxt.business.controller;


import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.business.domain.Sales;
import com.sxt.business.service.ISalesService;
import com.sxt.business.vo.SalesVo;
import com.sxt.system.domain.User;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-19
 */
@RestController
@RequestMapping("sales")
public class SalesController {
	
	@Autowired
	private ISalesService salesService;
	
	/**
	 * 全查询
	 * @param salesVo
	 * @return
	 */
	@RequestMapping("loadAllSales")
	public DataGridView loadAllSales(SalesVo salesVo) {
		return this.salesService.queryAllSales(salesVo);
		
	}
	
	/**
	 * 添加
	 * @param sales
	 * @param session
	 * @return
	 */
	@RequestMapping("addSales")
	public ResultObj addSales(Sales sales ,HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			sales.setOperateperson(user.getName());
			sales.setSalestime(new Date());
			this.salesService.addSales(sales);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 修改
	 * @param sales
	 * @return
	 */
	@RequestMapping("updateSales")
	public ResultObj updateSales(Sales sales) {
		try {
			this.salesService.updateSales(sales);
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
	@RequestMapping("deleteSales")
	public ResultObj deleteSales(Integer id) {
		try {
			this.salesService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}

