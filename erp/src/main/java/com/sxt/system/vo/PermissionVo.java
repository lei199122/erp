package com.sxt.system.vo;

import com.sxt.system.domain.Permission;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分页查询
	 */
	private Integer page = 1;
	private Integer limit = 10;
	
}
