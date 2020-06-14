package com.sxt.system.mapper;

import com.sxt.system.domain.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-29
 */
public interface DeptMapper extends BaseMapper<Dept> {
	
	Integer queryMaxOrderNum();
	
	Integer queryDeptCountByPid(Integer id);
}
