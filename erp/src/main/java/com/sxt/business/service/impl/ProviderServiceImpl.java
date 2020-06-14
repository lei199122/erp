package com.sxt.business.service.impl;

import com.sxt.business.domain.Provider;
import com.sxt.business.mapper.ProviderMapper;
import com.sxt.business.service.IProviderService;
import com.sxt.business.vo.ProviderVo;
import com.sxt.system.constant.Constant;
import com.sxt.system.utils.DataGridView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
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
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements IProviderService {
		
		@Override
		public boolean removeById(Serializable id) {
			return super.removeById(id);
		}
		
		@Override
		public Provider getById(Serializable id) {
		return super.getById(id);
		}
		
		@Override
		public boolean removeByIds(Collection<? extends Serializable> idList) {
				return super.removeByIds(idList);
			}
		
	@Override
	public DataGridView queryAllAvailableProvider() {
		QueryWrapper<Provider> qw = new QueryWrapper<>();
		qw.eq("available", Constant.AVAILABLE_TRUE);
		return new DataGridView(this.getBaseMapper().selectList(qw));
	}

	@Override
	public DataGridView queryAllProvider(ProviderVo providerVo) {
		IPage<Provider> page = new Page<>(providerVo.getPage(),providerVo.getLimit());
		QueryWrapper<Provider> qw = new QueryWrapper<>();
		qw.like(StringUtils.isNoneBlank(providerVo.getProvidername()),"providername", providerVo.getProvidername());
		qw.like(StringUtils.isNoneBlank(providerVo.getPhone()),"phone", providerVo.getPhone());
		qw.like(StringUtils.isNoneBlank(providerVo.getConnectionperson()),"connectionperson", providerVo.getConnectionperson());
		this.getBaseMapper().selectPage(page, qw);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	@Override
	public Provider addProvider(Provider provider) {
		this.getBaseMapper().insert(provider);
		return provider;
	}

	@Override
	public Provider updateProvider(Provider provider) {
		this.getBaseMapper().updateById(provider);
		return provider;
	}

}
