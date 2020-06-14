package com.sxt.business.controller;


import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.business.domain.Outport;
import com.sxt.business.service.IOutportService;
import com.sxt.business.vo.OutportVo;
import com.sxt.system.domain.User;
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
@RequestMapping("outport")
public class OutportController {
	
	@Autowired
	private IOutportService outportService;
	
	/**
	 * 保存退货信息
	 * @param outport
	 * @param session
	 * @return
	 */
	@RequestMapping("addOutprort")
	public ResultObj addOutport(Outport outport ,HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			outport.setOperateperson(user.getName());
			outport.setOutporttime(new Date());
			this.outportService.addOutport(outport);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 根据进货单ID查询退货单数量
	 * @param inportid
	 * @return
	 */
	@RequestMapping("loadOutportByInportId")
	public DataGridView loadOutportByInportId(Integer inportid) {
		Integer count = this.outportService.queryOutportCountByInportId(inportid);
		return new DataGridView(count);
		
	}
	/**
	 * 全查询退货信息
	 * @param outportVo
	 * @return
	 */
	@RequestMapping("loadAllOutport")
	public DataGridView loadAllOutport(OutportVo outportVo) {
		return this.outportService.queryAllOutport(outportVo);
	}
}

