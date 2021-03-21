package com.wdq.onebook.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdq.onebook.entity.BookPropertyEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 图书静态参数
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@Mapper
public interface BookPropertyDao extends BaseMapper<BookPropertyEntity> {

}
