package com.sxt.system.vo;

import java.io.Serializable;

import com.sxt.system.domain.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper =  false)
public class RoleVo extends Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分页参数
	 */
	private Integer page = 1;
	private Integer limit = 10;

}
