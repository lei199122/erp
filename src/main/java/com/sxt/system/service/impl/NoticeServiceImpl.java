package com.sxt.system.service.impl;

import com.sxt.system.domain.Notice;
import com.sxt.system.mapper.NoticeMapper;
import com.sxt.system.service.INoticeService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.NoticeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-28
 */
@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

	private Log log = LogFactory.getLog(NoticeServiceImpl.class);
	
	@Autowired
	private NoticeMapper noticeMapper;
	
	@Transactional(readOnly = true)
	@Override
	public DataGridView loadAllNotice(NoticeVo noticeVo) {
		QueryWrapper<Notice> qw = new QueryWrapper<>();
		if(null != noticeVo) {
			qw.like(StringUtils.isNoneBlank(noticeVo.getTitle()), "title", noticeVo.getTitle());
			qw.ge(noticeVo.getStartTime() != null, "createtime", noticeVo.getStartTime());
			qw.le(noticeVo.getEndtime() != null, "createtime", noticeVo.getEndtime());
		}else {
			log.info("noticeVo为空");
		}
		qw.orderByDesc("createtime");
		Page<Notice> page = new Page<>(noticeVo.getPage(), noticeVo.getLimit());
		this.noticeMapper.selectPage(page, qw);
		return new DataGridView(page.getTotal(), page.getRecords());
	}

	@Override
	public void addNotice(Notice notice) {
		this.noticeMapper.insert(notice);
	}

	@Override
	public void updateNotice(Notice notice) {
		this.noticeMapper.updateById(notice);
		
	}

}
