package com.sxt.system.service;

import com.sxt.system.domain.Loginfo;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.LoginfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-28
 */
public interface ILoginfoService extends IService<Loginfo> {
	
	DataGridView loadAllLoginfo(LoginfoVo loginfoVo);
}
