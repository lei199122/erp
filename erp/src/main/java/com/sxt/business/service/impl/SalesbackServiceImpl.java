package com.sxt.business.service.impl;

import com.sxt.business.domain.Goods;
import com.sxt.business.domain.Sales;
import com.sxt.business.domain.Salesback;
import com.sxt.business.mapper.GoodsMapper;
import com.sxt.business.mapper.SalesMapper;
import com.sxt.business.mapper.SalesbackMapper;
import com.sxt.business.service.ICustomerService;
import com.sxt.business.service.IGoodsService;
import com.sxt.business.service.ISalesbackService;
import com.sxt.business.vo.SalesbackVo;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.WebAppUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-19
 */
@Service
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements ISalesbackService {
	
	@Autowired
	private SalesMapper salesMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	
	@Override
	public Integer queryOutportCountBySalesId(Integer salesId) {
		return this.getBaseMapper().queryOutportCountByIsalesId(salesId);
	}

	@Override
	public void addSales(Salesback salesback) {
		Integer salesid = salesback.getSalesid();
		//根据进货单ID查询进货信息
		Sales sales = salesMapper.selectById(salesid);
		//向outport里面封装数据
		salesback.setGoodsid(sales.getGoodsid());
		salesback.setSalebackprice(sales.getSaleprice());
		salesback.setPaytype(sales.getPaytype());
		salesback.setCustomerid(sales.getCustomerid());
		//保存退货数据
		this.getBaseMapper().insert(salesback);
		
		//更新进货单的数据
		sales.setNumber(sales.getNumber()-salesback.getNumber());
		salesMapper.updateById(sales);
		
		//更新库存
		Goods goods = goodsMapper.selectById(sales.getGoodsid());
		goods.setNumber(goods.getNumber()-salesback.getNumber());
		this.goodsMapper.updateById(goods);
		
	}

	@Override
	public DataGridView queryAllSalesback(SalesbackVo salesbackVo) {
		Page<Salesback> page = new Page<>(salesbackVo.getPage(),salesbackVo.getLimit());
		QueryWrapper<Salesback> qw = new QueryWrapper<>();
		qw.eq(salesbackVo.getCustomerid() != null, "customerid", salesbackVo.getCustomerid());
		qw.eq(salesbackVo.getGoodsid() != null, "goodsid", salesbackVo.getGoodsid());
		qw.ge(null != salesbackVo.getStartTime(), "salesbacktime", salesbackVo.getStartTime());
		qw.le(null != salesbackVo.getEndTime(), "salesbacktime", salesbackVo.getEndTime());
		qw.orderByDesc("salesbacktime");
		this.getBaseMapper().selectPage(page, qw);
		List<Salesback> list = page.getRecords();
		for (Salesback salesback : list) {
			Integer customerid = salesback.getCustomerid();
			Integer goodsid = salesback.getGoodsid();
			if(null != customerid) {
			salesback.setCustomername(WebAppUtils.getContext().getBean(ICustomerService.class).getById(customerid).getCustomername());	
			}
			if(null != goodsid) {
				Goods goods = WebAppUtils.getContext().getBean(IGoodsService.class).getById(goodsid);
				salesback.setCustomername(goods.getGoodsname());
				salesback.setSize(goods.getSize());
			}
		}
		return new DataGridView(page.getTotal(), list);
	}

}
