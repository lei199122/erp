package com.sxt.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sxt.system.domain.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 雷卫
 * @since 2020-03-26
 */
public interface UserMapper extends BaseMapper<User> {
	
	Integer queryUserMaxOrderNum();
	
	//保存用户和角色之间的关联
	void saveUserRole(@Param("uid")Integer userId, @Param("rid")Integer rid);
}
