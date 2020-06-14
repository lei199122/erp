package com.sxt.system.utils;

import java.io.Serializable;
import java.util.List;

import com.sxt.system.domain.User;

import lombok.Data;

@Data
public class ActiverUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private User user;
	private List<String>roles;
	private List<String>permissions;

}
