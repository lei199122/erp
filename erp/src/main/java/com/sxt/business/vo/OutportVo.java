package com.sxt.business.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.sxt.business.domain.Outport;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OutportVo extends Outport implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private Integer page = 1;
	private Integer limit = 10;
	
	/**
	 * 查询条件
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endTime;
}	
