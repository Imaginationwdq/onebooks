package com.wdq.onebook.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wdq.onebook.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 分类列表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
public interface CategoryService extends IService<CategoryEntity> {
    /**
     * 查询树形列表
     * @param params
     * @return
     */
    IPage<CategoryEntity> getTreeList(Map<String, Object> params);

    /**
     * 获取所有的子分类
     * @param cates
     */
    void dealChildren(List<CategoryEntity> cates);
}
