package com.wdq.onebook.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wdq.onebook.entity.BookListEntity;
import com.wdq.onebook.service.BookListService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.R;



/**
 * 用户的图书列表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@RestController
@RequestMapping("onebook/booklist")
public class BookListController {
    @Autowired
    private BookListService bookListService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("onebook:booklist:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bookListService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{bookListId}")
    @RequiresPermissions("onebook:booklist:info")
    public R info(@PathVariable("bookListId") Integer bookListId){
		BookListEntity bookList = bookListService.getById(bookListId);

        return R.ok().put("bookList", bookList);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("onebook:booklist:save")
    public R save(@RequestBody BookListEntity bookList){
		bookListService.save(bookList);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("onebook:booklist:update")
    public R update(@RequestBody BookListEntity bookList){
		bookListService.updateById(bookList);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("onebook:booklist:delete")
    public R delete(@RequestBody Integer[] bookListIds){
		bookListService.removeByIds(Arrays.asList(bookListIds));

        return R.ok();
    }

}
