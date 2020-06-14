package com.sxt.system.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.system.service.ILoginfoService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;
import com.sxt.system.vo.LoginfoVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-28
 */
@RestController
@RequestMapping("loginfo")
public class LoginfoController {

	
	private  Log log = LogFactory.getLog(LoginfoController.class);
	
	@Autowired
	private ILoginfoService loginfoService;
	
	/**
	 * 全查询
	 * @param loginfoVo
	 * @return
	 */
	@RequestMapping("loadAllLoginfo")
	public DataGridView loadAllLoginfo(LoginfoVo loginfoVo) {
		return this.loginfoService.loadAllLoginfo(loginfoVo);
		
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteLoginfo")
	public ResultObj deleteLoginfo(Integer id) {
		try {
			loginfoService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
			return ResultObj.DELETE_ERROR;
		}
	}
	
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("batchDeleteLoginfo")
	public ResultObj batchDeleteLoginfo(Integer[] ids) {
		try {
			if(ids == null || ids.length == 0) {
				log.error("参数不能为空");
				return ResultObj.DELETE_ERROR;
			}
			Collection<Serializable> idList = new ArrayList<Serializable>();
			for (Integer integer : ids) {
				idList.add(integer);
			}
			loginfoService.removeByIds(idList);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
			return ResultObj.DELETE_ERROR;
		}

	}
}

