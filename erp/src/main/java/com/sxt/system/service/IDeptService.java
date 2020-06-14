package com.sxt.system.service;

import com.sxt.system.domain.Dept;
import com.sxt.system.utils.DataGridView;
import com.sxt.system.vo.DeptVo;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LeiWei
 * @since 2020-03-29
 */
public interface IDeptService extends IService<Dept> {
	/**
	 * 全查询
	 * @param deptVo
	 * @return
	 */
	public DataGridView loadAllDept(DeptVo deptVo);
	
	/**
	 * 全查询返回集合
	 * @param deptVo
	 * @return
	 */
	public List<Dept> queryAllDeptForList(DeptVo deptVo);
	
	public Dept addDept(Dept dept);
	
	public Dept updateDept(Dept dept);
	
	/**
	 * 加载部门最大的排序码
	 * @return
	 */
	public Integer queryDeptMaxOrdeNum();
	
	/**
	 * 根据部门ID查询当前部门有多少子节点
	 * @param id
	 * @return
	 */
	public Integer queryDeptCountByPid(Integer id);
	
}
