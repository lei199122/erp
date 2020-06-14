package com.sxt.system.vo;

import java.io.Serializable;

import com.sxt.system.domain.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//分页
	private Integer page = 1;
	private Integer limit = 10;
}
