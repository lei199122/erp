package com.sxt.business.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.business.domain.Provider;
import com.sxt.business.service.IProviderService;
import com.sxt.business.vo.ProviderVo;
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
@RequestMapping("provider")
public class ProviderController {
	
	@Autowired
	private IProviderService providerService;
		
	/**
	 * 查询
	 * @param providerVo
	 * @return
	 */
	@RequestMapping("loadAllProvider")
	public DataGridView loadAllProvider(ProviderVo providerVo) {
		return this.providerService.queryAllProvider(providerVo);
		
	}
	
	@RequestMapping("addProvider")
	public ResultObj addProvider(Provider provider) {
		try {
			this.providerService.addProvider(provider);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 修改
	 * @param provider
	 * @return
	 */
	@RequestMapping("updateProvider")
	public ResultObj updateProvider(Provider provider) {
		try {
			this.providerService.updateProvider(provider);
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
	@RequestMapping("deleteProvider")
	public ResultObj deleteProvider(Integer id) {
		try {
			this.providerService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	@RequestMapping("batchDeleteProvider")
	public ResultObj batchDeleteProvider(Integer[] ids) {
		try {
			for (Integer id : ids) {
				this.providerService.removeById(id);
			}
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	/**
	 * 加载可用的供应商不分页
	 * @return
	 */
	@RequestMapping("loadAllAvailableProvider")
	public DataGridView loadAllAvailableProvide() {
		return this.providerService.queryAllAvailableProvider();
	}
}

