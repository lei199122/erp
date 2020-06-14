package com.sxt.business.service.impl;

import com.sxt.business.domain.Goods;
import com.sxt.business.domain.Sales;
import com.sxt.business.mapper.SalesMapper;
import com.sxt.business.service.IGoodsService;
import com.sxt.business.service.IProviderService;
import com.sxt.business.service.ISalesService;
import com.sxt.business.vo.SalesVo;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.WebAppUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.List;

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
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements ISalesService {

	@Override
	public DataGridView queryAllSales(SalesVo salesVo) {
		Page<Sales> page = new Page<>(salesVo.getPage(),salesVo.getLimit());
		QueryWrapper<Sales> qw = new QueryWrapper<>();
		qw.eq(salesVo.getCustomerid() != null, "customerid", salesVo.getCustomerid());
		qw.eq(salesVo.getGoodsid() != null, "goodsid", salesVo.getGoodsid());
		qw.ge(null != salesVo.getStartTime(), "salestime", salesVo.getStartTime());
		qw.le(null != salesVo.getEndTime(), "salestime", salesVo.getEndTime());
		qw.orderByDesc("salestime");
		this.getBaseMapper().selectPage(page, qw);
		List<Sales> list = page.getRecords();
		for (Sales sales : list) {
			Integer customerid = sales.getCustomerid();
			Integer goodsid = sales.getGoodsid();
			if(null != customerid) {
				sales.setCustomername(WebAppUtils.getContext().getBean(IProviderService.class).getById(customerid).getProvidername());
			}
			if(null != goodsid) {
				Goods goods = WebAppUtils.getContext().getBean(IGoodsService.class).getById(goodsid);
				sales.setGoodsname(goods.getGoodsname());
				sales.setSize(goods.getSize());
			}
		}
		return new DataGridView(page.getTotal(), list);
	}

	@Override
	public Sales addSales(Sales sales) {
		//添加进货单
		this.baseMapper.insert(sales);
		//增加商品库存
		IGoodsService iGoodsService = WebAppUtils.getContext().getBean(IGoodsService.class);
		Goods goods = iGoodsService.getById(sales.getGoodsid());
		goods.setNumber(goods.getNumber()+sales.getNumber());
		iGoodsService.updateById(goods);
		return sales;
	}

	@Override
	public Sales updateSales(Sales sales) {
		Sales byId = this.getBaseMapper().selectById(sales);
		IGoodsService iGoodsService = WebAppUtils.getContext().getBean(IGoodsService.class);
		Goods goods = iGoodsService.getById(sales.getGoodsid());
		goods.setNumber(goods.getNumber()-byId.getNumber()+sales.getNumber());
		iGoodsService.updateById(goods);
		this.getBaseMapper().updateById(sales);
		return sales;
	}
	
	
	@Override
	public boolean removeById(Serializable id) {
		Sales sales = this.getBaseMapper().selectById(id);
		IGoodsService iGoodsService = WebAppUtils.getContext().getBean(IGoodsService.class);
		Goods goods = iGoodsService.getById(sales.getGoodsid());
		goods.setNumber(goods.getNumber()-sales.getNumber());
		iGoodsService.updateById(goods);
		return super.removeById(id);
	}
}
