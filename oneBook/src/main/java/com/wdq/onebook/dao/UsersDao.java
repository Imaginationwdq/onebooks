package com.wdq.onebook.dao;

import com.wdq.onebook.entity.UsersEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
@Mapper
public interface UsersDao extends BaseMapper<UsersEntity> {
	
}
