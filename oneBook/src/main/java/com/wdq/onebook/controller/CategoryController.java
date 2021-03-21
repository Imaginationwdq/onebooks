package com.wdq.onebook.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wdq.onebook.common.utils.R;
import com.wdq.onebook.components.TokenManager;
import com.wdq.onebook.entity.CategoryEntity;
import com.wdq.onebook.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 商品分类表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
@RestController
@RequestMapping("onebook/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    TokenManager tokenManager;

    /**
     * 列表
     */
    @PostMapping("/treeList")
    public R list(@RequestParam Map<String, Object> params) {
        return R.ok().put("page", categoryService.getTreeList(params));
    }

    /**
     * 添加商品分类
     */
    @PostMapping("/addCate")
    public R addRole(@RequestParam Map<String, Object> params) {
        String categoryName = (String) params.get("categoryName");
        String parentString = (String) params.get("parent");
        String level = (String) params.get("level");
        if (Integer.parseInt(level) > 3) {
            return R.error("最多3级分类");
        }
        if (parentString.length() != 0) {
            String[] split = parentString.split(",");
            int parent = Integer.parseInt(split[split.length - 1]);
            // 判断这个名字是否在同级重复
            List<CategoryEntity> list = categoryService.list(new QueryWrapper<CategoryEntity>().eq("parent", parent));
            if (list.size() != 0) {
                for (CategoryEntity categoryEntity : list) {
                    if (categoryEntity.getCategoryName().equals(categoryName)) {
                        return R.error("分类名称重复");
                    }
                }
            }
            CategoryEntity one = categoryService.getOne(new QueryWrapper<CategoryEntity>().eq("category_id", parent));
            if (one == null) {
                return R.error("父级分类不存在");
            }
            int top = one.getTop();
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setParent(parent);
            categoryEntity.setTop(top);
            categoryEntity.setStatus("1");
            categoryEntity.setCategoryName(categoryName);
            categoryEntity.setLevel(level);
            boolean save = categoryService.save(categoryEntity);
            if (!save) {
                return R.error(level + "级分类添加失败");
            }
        } else {
            // 判断这个名字是否在同级重复
            List<CategoryEntity> list = categoryService.list(new QueryWrapper<CategoryEntity>().eq("parent", -1));
            for (CategoryEntity categoryEntity : list) {
                if (categoryEntity.getCategoryName().equals(categoryName)) {
                    return R.error("分类名称重复");
                }
            }
            // 根目录
            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setParent(-1);
            categoryEntity.setStatus("1");
            categoryEntity.setCategoryName(categoryName);
            categoryEntity.setLevel(level);
            boolean save = categoryService.save(categoryEntity);
            if (!save) {
                return R.error(level + "级分类添加失败");
            }
            CategoryEntity cate = categoryService.getOne(new QueryWrapper<CategoryEntity>().eq("category_name", categoryName));
            cate.setTop(cate.getCategoryId());
            boolean update = categoryService.update(cate, new UpdateWrapper<CategoryEntity>().eq("category_name", categoryName));
            if (!update) {
                return R.error(level + "级分类更新top失败");
            }
        }
        return R.ok(level + "级分类添加成功");
    }

    /**
     * 删除商品分类
     */
    @PostMapping("/deleteCate")
    public R deleteByUserId(@RequestParam Map<String, Object> params) {
        int categoryId = Integer.parseInt((String) params.get("categoryId"));
        CategoryEntity categoryEntity = categoryService.getById(categoryId);
        List<CategoryEntity> cates = new ArrayList<>();
        cates.add(categoryEntity);
        categoryService.dealChildren(cates);
        for (CategoryEntity category : cates){
            boolean b = categoryService.removeById(category.getCategoryId());
        }
        return R.ok();
    }

}
