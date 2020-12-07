package com.wdq.onebook.dao;

import com.wdq.onebook.entity.MenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
@Mapper
public interface MenuDao extends BaseMapper<MenuEntity> {
	
}
