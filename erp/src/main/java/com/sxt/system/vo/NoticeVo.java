package com.sxt.system.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.sxt.system.domain.Notice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeVo extends Notice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分页查询
	 */
	private  Integer page = 1;
	private  Integer limit = 10;
	
	/**
	 * 查询条件
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date startTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date endtime;
}
