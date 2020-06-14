package com.sxt.system.service.impl;

import com.sxt.system.domain.Loginfo;
import com.sxt.system.mapper.LoginfoMapper;
import com.sxt.system.service.ILoginfoService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.LoginfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-28
 */
@Service
public class LoginfoServiceImpl extends ServiceImpl<LoginfoMapper, Loginfo> implements ILoginfoService {
	
	private Log log = LogFactory.getLog(LoginfoServiceImpl.class);
	
	
	@Override
	public DataGridView loadAllLoginfo(LoginfoVo loginfoVo) {
		QueryWrapper<Loginfo> qw = new QueryWrapper<>();
		if(null != loginfoVo) {
			qw.like(StringUtils.isNoneBlank(loginfoVo.getLoginname()), "loginname", loginfoVo.getLoginname());
			qw.ge(loginfoVo.getStartTime() != null, "logintime", loginfoVo.getStartTime());
			qw.le(loginfoVo.getEndTime() != null, "logintime", loginfoVo.getEndTime());
		}else {
			log.info("loginfoVo为空");
		}
		qw.orderByDesc("logintime");
		Page<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
		this.baseMapper.selectPage(page, qw);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

}
