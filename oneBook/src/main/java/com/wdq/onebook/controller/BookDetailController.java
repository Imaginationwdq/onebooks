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

import com.wdq.onebook.entity.BookDetailEntity;
import com.wdq.onebook.service.BookDetailService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.R;



/**
 * 图书详情表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@RestController
@RequestMapping("onebook/bookdetail")
public class BookDetailController {
    @Autowired
    private BookDetailService bookDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("onebook:bookdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = bookDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{bookId}")
    @RequiresPermissions("onebook:bookdetail:info")
    public R info(@PathVariable("bookId") Integer bookId){
		BookDetailEntity bookDetail = bookDetailService.getById(bookId);

        return R.ok().put("bookDetail", bookDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("onebook:bookdetail:save")
    public R save(@RequestBody BookDetailEntity bookDetail){
		bookDetailService.save(bookDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("onebook:bookdetail:update")
    public R update(@RequestBody BookDetailEntity bookDetail){
		bookDetailService.updateById(bookDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("onebook:bookdetail:delete")
    public R delete(@RequestBody Integer[] bookIds){
		bookDetailService.removeByIds(Arrays.asList(bookIds));

        return R.ok();
    }

}
