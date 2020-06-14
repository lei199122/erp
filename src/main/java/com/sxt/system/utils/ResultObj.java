package com.sxt.system.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {
	
	
	public static final ResultObj LOGIN_SUCCESS = new ResultObj("登录成功");
	public static final ResultObj LOGIN_ERROR = new ResultObj(-1, "登录失败，用户名或密码错误");
	public static final ResultObj ADD_SUCCESS = new ResultObj("添加成功");
	public static final ResultObj UPDATE_SUCCESS = new ResultObj("更新成功");
	public static final ResultObj DELETE_SUCCESS = new ResultObj("删除成功");
	public static final ResultObj RESET_SUCCESS = new ResultObj("重置成功");
	public static final ResultObj DISPATCH_SUCCESS = new ResultObj("分配成功");
	public static final ResultObj CLEAR_SUCCESS = new ResultObj("清空成功");
	
	public static final ResultObj ADD_ERROR  = new ResultObj("添加失败");
	public static final ResultObj UPDATE_ERROR = new ResultObj("更新失败");
	public static final ResultObj DELETE_ERROR = new ResultObj("删除失败");
	public static final ResultObj RESET_ERROR = new ResultObj("重置失败");
	public static final ResultObj DISPATCH_ERROR = new ResultObj("分配失败");
	public static final ResultObj CLEAR_ERROR = new ResultObj("清空失败");
	
	private Integer code = 200;
	private String msg = "";
	
	public ResultObj(String msg) {
		super();
		this.msg = msg;
	}
}
