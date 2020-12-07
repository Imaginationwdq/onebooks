package com.wdq.onebook.dao;

import com.wdq.onebook.entity.BookListEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户的图书列表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@Mapper
public interface BookListDao extends BaseMapper<BookListEntity> {
	
}
