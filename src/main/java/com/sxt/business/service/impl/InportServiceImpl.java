package com.sxt.business.service.impl;

import com.sxt.business.domain.Goods;
import com.sxt.business.domain.Inport;
import com.sxt.business.mapper.InportMapper;
import com.sxt.business.service.IGoodsService;
import com.sxt.business.service.IInportService;
import com.sxt.business.service.IProviderService;
import com.sxt.business.vo.InportVo;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.WebAppUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-08
 */
@Service
@Transactional
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements IInportService {
	
	
	
	
	
	@Override
	public DataGridView queryAllInport(InportVo inportVo) {
		Page<Inport> page = new Page<>(inportVo.getPage(),inportVo.getLimit());
		
		QueryWrapper<Inport> qw = new QueryWrapper<>();
		qw.eq(inportVo.getProviderid() != null , "providerid", inportVo.getProviderid());
		qw.eq(inportVo.getGoodsid() != null, "goodsid", inportVo.getGoodsid());
		qw.ge(null != inportVo.getStartTime(), "inporttime", inportVo.getStartTime());
		qw.le(null != inportVo.getEndTime(), "inporttime", inportVo.getEndTime());
		qw.orderByDesc("inporttime");
		this.getBaseMapper().selectPage(page, qw);
		List<Inport> list = page.getRecords();
		for (Inport inport : list) {
			Integer providerid = inport.getProviderid();
			Integer goodsid = inport.getGoodsid();
			if(null != providerid) {
				inport.setProvidername(WebAppUtils.getContext().getBean(IProviderService.class).getById(providerid).getProvidername());	
			}
			if(null != goodsid) {
				Goods goods = WebAppUtils.getContext().getBean(IGoodsService.class).getById(goodsid);
				inport.setGoodsname(goods.getGoodsname());
				inport.setSize(goods.getSize());
			}
		}
		return new DataGridView(page.getTotal(), list);
	}

	

	@Override
	public Inport addInport(Inport inport) {
		//添加进货单
		this.baseMapper.insert(inport);
		//增加商品库存
		IGoodsService iGoodsService = WebAppUtils.getContext().getBean(IGoodsService.class);
		Goods goods = iGoodsService.getById(inport.getGoodsid());
		goods.setNumber(goods.getNumber()+inport.getNumber());
		iGoodsService.updateById(goods);
		return inport;
	}

	@Override
	public Inport updateInport(Inport inport) {
		//老进货单位      //修改之后的进货单      //商品原来的库存0
		//100                             100X
		//100Y				20            20Z  Z=100-100+20
		Inport oldInport = this.getBaseMapper().selectById(inport.getId());
		IGoodsService iGoodsService =WebAppUtils.getContext().getBean(IGoodsService.class);
		Goods goods = iGoodsService.getById(inport.getGoodsid());
		//总库存-之前添加的库存 +新修改之后的数量 
		goods.setNumber(goods.getNumber()-oldInport.getNumber()+inport.getNumber());
		iGoodsService.updateById(goods);
		this.getBaseMapper().updateById(inport);
		return inport;
	}
	
	@Override
	public boolean removeById(Serializable id) {
		Inport inport = this.getBaseMapper().selectById(id);
		IGoodsService iGoodsService = WebAppUtils.getContext().getBean(IGoodsService.class);
		Goods goods = iGoodsService.getById(inport.getGoodsid());
		goods.setNumber(goods.getNumber()-inport.getNumber());
		iGoodsService.updateById(goods);
		return super.removeById(id);
	}
}
