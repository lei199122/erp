package com.sxt.business.mapper;

import com.sxt.business.domain.Salesback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-19
 */
public interface SalesbackMapper extends BaseMapper<Salesback> {
	
	Integer queryOutportCountByIsalesId(Integer salesId);
	
}
