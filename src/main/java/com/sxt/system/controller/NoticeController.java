package com.sxt.system.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

 
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.system.domain.Notice;
import com.sxt.system.domain.User;
import com.sxt.system.service.INoticeService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;
import com.sxt.system.vo.NoticeVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-28
 */
@RestController
@RequestMapping("notice")
public class NoticeController {
	
	private Log log = LogFactory.getLog(NoticeController.class);
	
	@Autowired
	private INoticeService noticeService;
					 
	@RequestMapping("loadAllNotice")
	public DataGridView loadAllNotice(NoticeVo noticeVo) {
		return this.noticeService.loadAllNotice(noticeVo);
	}
	
	/**
	 * 添加
	 * @param notice
	 * @return
	 */
	@RequestMapping("addNotice")
	public ResultObj addNotice(Notice notice ,HttpSession session) {
		User user = (User) session.getAttribute("user");
		notice.setOpername(user.getName());//设置发布时间
		notice.setCreatetime(new Date());//设置时间
		try {
			noticeService.addNotice(notice);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			log.error("添加失败");
			return ResultObj.ADD_ERROR;
		}
	}
	
	/**
	 * 更新
	 * @param notice
	 * @return
	 */
	@RequestMapping("updateNotice")
	public ResultObj updateNotice(Notice notice) {
		
		try {
			noticeService.updateNotice(notice);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			log.error("更新失败");
			return ResultObj.UPDATE_ERROR;
		}
		
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteNotice")
	public ResultObj deleteNotice(Integer id) {
		try {
			noticeService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
			log.error("删除失败");
			return ResultObj.DELETE_ERROR;
		}
	}
	
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("batchDeleteNotice")
	public ResultObj batchDeleteNotice(Integer[] ids) {
		try {
			if(ids == null || ids.length == 0) {
				log.error("参数不能为空");
				return ResultObj.DELETE_ERROR;
			}
			Collection<Serializable> idList = new ArrayList<Serializable>();
			for (Integer integer : ids) {
				idList.add(integer);
			}
			noticeService.removeByIds(idList);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
		log.error("删除失败");
		return ResultObj.DELETE_ERROR;
		}

	}
	
	@RequestMapping("loadNoticeById")
	public DataGridView loadNoticeById(Integer id) {
		return new DataGridView(this.noticeService.getById(id));
		
	}
}

