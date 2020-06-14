package com.sxt.system.service;

import com.sxt.system.domain.Notice;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.NoticeVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-28
 */
public interface INoticeService extends IService<Notice> {
	
	public DataGridView loadAllNotice(NoticeVo noticeVo);
	
	public void addNotice(Notice notice);
	
	public void updateNotice(Notice notice);
}
