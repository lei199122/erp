package com.sxt.business.vo;

import java.io.Serializable;

import com.sxt.business.domain.Goods;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsVo extends Goods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer page = 1;
	private Integer limit = 10;
}
