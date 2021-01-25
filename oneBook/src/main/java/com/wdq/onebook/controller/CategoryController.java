package com.wdq.onebook.controller;

import com.wdq.onebook.common.utils.R;
import com.wdq.onebook.components.TokenManager;
import com.wdq.onebook.entity.CategoryEntity;
import com.wdq.onebook.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 分类表
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
    public R list(@RequestParam Map<String, Object> params){
        return R.ok().put("page",categoryService.getTreeList(params));
    }

}
