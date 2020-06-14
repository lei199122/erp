package com.sxt.system.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sxt.system.constant.Constant;
import com.sxt.system.domain.Dept;
import com.sxt.system.service.IDeptService;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.utils.ResultObj;
import com.sxt.system.utils.TreeNode;
import com.sxt.system.vo.DeptVo;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-29
 */
@RestController
@RequestMapping("dept")
public class DeptController {
	
	private Log log = LogFactory.getLog(DeptController.class);
	
	@Autowired
	private IDeptService deptService;
	
	
	/**
	 * 全查询
	 * @param deptVo
	 * @return
	 */
	@RequestMapping("loadAllDept")
	public DataGridView loadAllDept(DeptVo deptVo) {
		return this.deptService.loadAllDept(deptVo);
	}
	
	/**
	 * 生成json树
	 * @param deptVo
	 * @return
	 */
	@RequestMapping("loadAllDeptTreeJson")
	public DataGridView loadAllDeptTreeJson(DeptVo deptVo) {
		deptVo.setAvailable(Constant.AVAILABLE_TRUE);//只查可用的
		List<Dept> allDept = this.deptService.queryAllDeptForList(deptVo);
		List<TreeNode> treeNode = new ArrayList<>();
		for (Dept dept : allDept) {
			Integer id = dept.getId();
			Integer pid = dept.getPid();
			String title = dept.getTitle();
			Boolean spread = dept.getOpen() == 1?true:false;
			treeNode.add(new TreeNode(id, pid, title, spread));
					
		}
		return new DataGridView(treeNode);
	}
	
	/**
	 * 加载部门最大排序码
	 * @return
	 */
	@RequestMapping("loadDeptMaxOrderNum")
	public DataGridView loadDeptMaxOrderNum() {
		Integer maxOrderNum = this.deptService.queryDeptMaxOrdeNum();
		return new DataGridView(maxOrderNum + 1);
	}
	
	/**
	 * 添加
	 * @param dept
	 * @param session
	 * @return
	 */
	@RequestMapping("addDept")
	public ResultObj addDept(Dept dept,HttpSession session) {
		dept.setCreatetime(new Date());//设置时间
		try {
			deptService.addDept(dept);
			return ResultObj.ADD_SUCCESS;
		} catch (Exception e) {
			log.error("添加失败");
			return ResultObj.ADD_ERROR;
		}
			
	}
	
	/**
	 * 修改
	 * @return
	 */
	@RequestMapping("updateDept")
	public ResultObj updateDept(Dept dept) {
		
		try {
			deptService.updateDept(dept);
			return ResultObj.UPDATE_SUCCESS;
		} catch (Exception e) {
			log.error("修改失败");
			return ResultObj.UPDATE_ERROR;
		}
		
	}
	
	@RequestMapping("deleteDept")
	public ResultObj deleteDept(Integer id) {
		try {
			deptService.removeById(id);
			return ResultObj.DELETE_SUCCESS;
		} catch (Exception e) {
            log.error("删除失败");
            return ResultObj.DELETE_SUCCESS;
		}
	}
	@RequestMapping("checkCurrentDeptHasChild")
	public DataGridView checkCurrentDeptHasChild(Integer id) {
		Integer count = this.deptService.queryDeptCountByPid(id);
		return new DataGridView(count);
		
	}
}

