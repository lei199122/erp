package com.sxt.system.service.impl;

import com.sxt.system.domain.Dept;
import com.sxt.system.mapper.DeptMapper;
import com.sxt.system.service.IDeptService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.DeptVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-29
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {

	@Override
	public DataGridView loadAllDept(DeptVo deptVo) {
		DeptMapper mapper = this.getBaseMapper();
		Page<Dept> page = new Page<>(deptVo.getPage(),deptVo.getLimit());
		QueryWrapper<Dept> qw = new QueryWrapper<>();
		qw.like(StringUtils.isNoneBlank(deptVo.getTitle()), "title", deptVo.getTitle());
		qw.like(StringUtils.isNoneBlank(deptVo.getAddress()), "address", deptVo.getAddress());
		qw.like(StringUtils.isNoneBlank(deptVo.getRemark()), "remark", deptVo.getRemark());
		qw.eq(deptVo.getId() != null, "id", deptVo.getId()).or().eq(deptVo.getId() != null, "pid", deptVo.getId());
		qw.orderByAsc("ordernum");
		mapper.selectPage(page, qw);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	@Override
	public List<Dept> queryAllDeptForList(DeptVo deptVo) {
		QueryWrapper<Dept> qw = new QueryWrapper<>();
		qw.eq(deptVo.getAvailable() != null, "available", deptVo.getAvailable());
		return this.getBaseMapper().selectList(qw);
	}

	@Override
	public Dept addDept(Dept dept) {
		this.getBaseMapper().insert(dept);
		return dept;
	}

	@Override
	public Dept updateDept(Dept dept) {
		this.getBaseMapper().updateById(dept);
		return dept;
	}

	@Override
	public Integer queryDeptMaxOrdeNum() {
		
		return this.getBaseMapper().queryMaxOrderNum();
	}

	@Override
	public Integer queryDeptCountByPid(Integer id) {
		
		return this.getBaseMapper().queryDeptCountByPid(id);
	}
	
	
	
}
