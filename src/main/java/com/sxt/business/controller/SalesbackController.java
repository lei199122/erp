package com.sxt.business.controller;


import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.business.domain.Salesback;
import com.sxt.business.service.ISalesbackService;
import com.sxt.business.vo.SalesbackVo;
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
@RequestMapping("salesback")
public class SalesbackController {
	
	@Autowired
	public ISalesbackService salesbackService;
	
	/**
	 * 根据进货单id查询退货单的数量
	 * @param salesId
	 * @return
	 */
	@RequestMapping("loadOutportBySalesId")
	public DataGridView loadOutportBySalesId(Integer salesId) {
		Integer countBySalesId = this.salesbackService.queryOutportCountBySalesId(salesId);
		return new DataGridView(countBySalesId);
	}
	
	/**
	 * 退货保存信息
	 * @param salesback
	 * @param session
	 * @return
	 */
	@RequestMapping("addSalesback")
	public ResultObj addSalesback(Salesback salesback , HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			salesback.setOperateperson(user.getName());
			salesback.setSalesbacktime(new Date());
			this.salesbackService.addSales(salesback);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 全查询退货信息
	 * @param salesbackVo
	 * @return
	 */
	@RequestMapping("loadAllSalesback")
	public DataGridView loadAllSalesback(SalesbackVo salesbackVo) {
		return this.salesbackService.queryAllSalesback(salesbackVo);
		
	}
}


