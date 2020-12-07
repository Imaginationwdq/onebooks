package com.wdq.onebook.dao;

import com.wdq.onebook.entity.MouseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 点击次数表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-21 17:44:09
 */
@Mapper
public interface MouseDao extends BaseMapper<MouseEntity> {

    void addClick(Map<String, Object> params);


    MouseEntity selectOne(Map<String, Object> params);

    MouseEntity selectTop();


    void updateClick(Map<String, Object> params2);
}
