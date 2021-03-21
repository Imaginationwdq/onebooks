package com.wdq.onebook.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.common.utils.Query;
import com.wdq.onebook.dao.CategoryDao;
import com.wdq.onebook.entity.CategoryEntity;
import com.wdq.onebook.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("CategoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    /**
     * 查询树形列表
     * @param params
     * @return
     */
    @Override
    public IPage<CategoryEntity> getTreeList(Map<String, Object> params) {
        //先查询等级为1的节点
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>().eq("level",1)
        );
        List<CategoryEntity> cates = page.getRecords();
        dealChildren(cates);
        page.setRecords(cates);
        return page;
    }

    /**
     * 获取所有的子分类
     * @param cates
     */
    @Override
    public void dealChildren(List<CategoryEntity> cates) {
        for (int i = 0; i < cates.size(); i++) {
            CategoryEntity cate = cates.get(i);
            List<CategoryEntity> children = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent",cate.getCategoryId()));
            dealChildren(children);
            if (children.size() > 0)
                cate.setChildren(children);
        }
    }

}
