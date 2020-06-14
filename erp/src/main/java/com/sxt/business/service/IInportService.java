package com.sxt.business.service;

import com.sxt.business.domain.Inport;
import com.sxt.business.vo.InportVo;
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
public interface IInportService extends IService<Inport> {
	
	
	/**
	 * 全查询进货信息 
	 */
	public DataGridView queryAllInport(InportVo inportVo);
	
	/**
	 * 添加
	 * @param inport
	 * @return
	 */
	public Inport addInport(Inport inport);
	
	/**
	 * 修改
	 * @param inport
	 * @return
	 */
	public Inport updateInport(Inport inport);
}
