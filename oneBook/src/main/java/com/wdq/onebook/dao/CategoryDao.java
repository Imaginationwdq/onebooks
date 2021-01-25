package com.wdq.onebook.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdq.onebook.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;
/**
 * 分类列表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

}
