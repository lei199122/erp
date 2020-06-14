package com.sxt.business.service;

import com.sxt.business.domain.Goods;
import com.sxt.business.vo.GoodsVo;
import com.sxt.system.utils.DataGridView;



import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-17
 */
public interface IGoodsService extends IService<Goods> {
	
	/**
	 * 查询商品
	 */
	public DataGridView queryAllGoods(GoodsVo goodsVo);
	/**
	 * 添加
	 * @param goods
	 * @return
	 */
	public Goods addGoods(Goods goods);
	/**
	 * 修改
	 * @param goods
	 * @return
	 */
	public Goods updateGoods(Goods goods);
	/**
	 * 加载可用的商品
	 * @return
	 */
	public DataGridView queryAllAvailableGoods();
	/**
	 * 根据供应商ID查询商品
	 * @param providerid
	 * @return
	 */
	public DataGridView queryGoodsByProviderid(Integer providerid);
	
	public DataGridView loadGoodsByCustomerid(Integer customerid);
}
