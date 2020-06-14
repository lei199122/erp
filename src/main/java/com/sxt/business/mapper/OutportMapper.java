package com.sxt.business.mapper;

import com.sxt.business.domain.Outport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-08
 */
public interface OutportMapper extends BaseMapper<Outport> {
	
	/**
	 * 
	 * @param inport
	 * @return
	 */
	Integer queryOutportCountByInportId(Integer inportid );
}
