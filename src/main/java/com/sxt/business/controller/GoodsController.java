package com.sxt.business.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.business.domain.Goods;
import com.sxt.business.service.IGoodsService;
import com.sxt.business.vo.GoodsVo;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-17
 */
@RestController
@RequestMapping("goods")
public class GoodsController {
	
	@Autowired
	private IGoodsService goodsService;
	
	/**
	 * 查询
	 * @param goodsVo
	 * @return
	 */
	@RequestMapping("loadAllGoods")
	public DataGridView loadAllGoods(GoodsVo goodsVo) {
		return this.goodsService.queryAllGoods(goodsVo);
	}
	
	/**
	 * 添加
	 * @param goods
	 * @return
	 */
	@RequestMapping("addGoods")
	public ResultObj addGoods(Goods goods) {
		try {
			this.goodsService.addGoods(goods);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.ADD_ERROR;
		}
	}
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	@RequestMapping("updateGoods")
	public ResultObj updateGoods(Goods goods) {
		try {
			this.goodsService.updateGoods(goods);
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
	@RequestMapping("deleteGoods")
	public ResultObj deleteGoods(Integer id) {
		try {
			this.goodsService.removeById(id);
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
	@RequestMapping("batchDeleteGoods")
	public ResultObj batchDeleteGoods(Integer[] ids) {
		try {
			for (Integer id : ids) {
				this.goodsService.removeById(id);
			}
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultObj.DELETE_ERROR;
		}
	}
	
	/**
	 * 加载可用的商品不分页
	 * @return
	 */
	@RequestMapping("loadAllAvailableGoods")
	public DataGridView loadAllAvailableGoods() {
		return this.goodsService.queryAllAvailableGoods();
	}
	
	/**
	 * 根据供应商ID查询商品
	 * @param providerid
	 * @return
	 */
	@RequestMapping("loadGoodsByProviderid")
	public DataGridView loadGoodsByProviderid(Integer providerid) {
		return this.goodsService.queryGoodsByProviderid(providerid);
	}
	
	/**
	 * 根据客户ID查询商品
	 * @param customerid
	 * @return
	 */
	@RequestMapping("loadGoodsByCustomerid")
	public DataGridView loadGoodsByCustomerid(Integer customerid) {
		return this.goodsService.loadGoodsByCustomerid(customerid);
	}
}

