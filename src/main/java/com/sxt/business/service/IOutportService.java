package com.sxt.business.service;

import com.sxt.business.domain.Outport;
import com.sxt.business.vo.OutportVo;
import com.sxt.system.utils.DataGridView;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LeiWei
 * @since 2020-04-08
 */
public interface IOutportService extends IService<Outport> {
	
	void addOutport(Outport outport);
	
	Integer queryOutportCountByInportId(Integer inportid);
	
	/**
	 * 查询退货信息
	 * @param outportVo
	 * @return
	 */
	DataGridView queryAllOutport(OutportVo outportVo);
}
