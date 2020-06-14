package com.sxt.business.service.impl;

import com.sxt.business.domain.Customer;
import com.sxt.business.mapper.CustomerMapper;
import com.sxt.business.service.ICustomerService;
import com.sxt.business.vo.CustomerVo;
import com.sxt.system.utils.DataGridView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;

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
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

	@Override
	public boolean updateById(Customer entity) {
		return super.updateById(entity);
	}
	
	@Override
		public boolean removeById(Serializable id) {
			return super.removeById(id);
		}
	
	@Override
	public Customer getById(Serializable id) {
		return super.getById(id);
	}
	
	@Override
	public Customer addCustomer(Customer customer) {
		this.getBaseMapper().insert(customer);
		return customer;
	}

	@Override
	public DataGridView queryAllCustomer(CustomerVo customerVo) {
		IPage<Customer> page = new Page<>(customerVo.getPage(),customerVo.getLimit());
		QueryWrapper<Customer> qw = new QueryWrapper<>();
		qw.like(StringUtils.isNoneBlank(customerVo.getCustomername()), "customername", customerVo.getCustomername());
		qw.like(StringUtils.isNoneBlank(customerVo.getPhone()), "phone", customerVo.getPage());
		qw.like(StringUtils.isNoneBlank(customerVo.getConnectionperson()), "connectionperson", customerVo.getConnectionperson());
		this.baseMapper.selectPage(page, qw);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	@Override
	public Customer updateCustomer(Customer customer) {
		this.getBaseMapper().updateById(customer);
		return customer;
	}

}
