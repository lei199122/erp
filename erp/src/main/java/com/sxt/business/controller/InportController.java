package com.sxt.business.controller;


import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.business.domain.Inport;
import com.sxt.business.service.IInportService;
import com.sxt.business.vo.InportVo;
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
@RequestMapping("inport")
public class InportController {
	
	@Autowired
	private IInportService inportService;
	
	/**
	 * 全查询
	 * @param inportVo
	 * @return
	 */
	@RequestMapping("loadAllInport")
	public DataGridView loadAllInport(InportVo inportVo) {
		return this.inportService.queryAllInport(inportVo);
		
	}
	
	/**
	 * 添加
	 * @param inport
	 * @param session
	 * @return
	 */
	@RequestMapping("addInport")
	public ResultObj addInport(Inport inport , HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			inport.setOperateperson(user.getName());
			inport.setInporttime(new Date());
			this.inportService.addInport(inport);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
		
	}
	/**
	 * 修改
	 * @param inport
	 * @return
	 */
	@RequestMapping("updateInport")
	public ResultObj updateInport(Inport inport) {
		try {
			this.inportService.updateInport(inport);
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
	@RequestMapping("deleteInport")
	public ResultObj deleteInport(Inport id) {
		try {
			this.inportService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
}

