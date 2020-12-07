package com.wdq.onebook.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wdq.onebook.entity.MouseEntity;
import com.wdq.onebook.service.MouseService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.R;



/**
 * 点击次数表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-21 17:44:09
 */
@RestController
@RequestMapping("onebook/mouse")
public class MouseController {
    @Autowired
    private MouseService mouseService;

    /**
     * 鼠标点击次数
     */
    @PostMapping("/clickNum")
    public R clickNum(@RequestParam Map<String, Object> params){
        mouseService.clickNum(params);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = mouseService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{mouseId}")
    @RequiresPermissions("onebook:mouse:info")
    public R info(@PathVariable("mouseId") Integer mouseId){
		MouseEntity mouse = mouseService.getById(mouseId);

        return R.ok().put("mouse", mouse);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("onebook:mouse:save")
    public R save(@RequestBody MouseEntity mouse){
		mouseService.save(mouse);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("onebook:mouse:update")
    public R update(@RequestBody MouseEntity mouse){
		mouseService.updateById(mouse);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("onebook:mouse:delete")
    public R delete(@RequestBody Integer[] mouseIds){
		mouseService.removeByIds(Arrays.asList(mouseIds));

        return R.ok();
    }

}
