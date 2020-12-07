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

import com.wdq.onebook.entity.IconEntity;
import com.wdq.onebook.service.IconService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.R;



/**
 * 图标表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@RestController
@RequestMapping("onebook/icon")
public class IconController {
    @Autowired
    private IconService iconService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("onebook:icon:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = iconService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{iconId}")
    @RequiresPermissions("onebook:icon:info")
    public R info(@PathVariable("iconId") Integer iconId){
		IconEntity icon = iconService.getById(iconId);

        return R.ok().put("icon", icon);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("onebook:icon:save")
    public R save(@RequestBody IconEntity icon){
		iconService.save(icon);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("onebook:icon:update")
    public R update(@RequestBody IconEntity icon){
		iconService.updateById(icon);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("onebook:icon:delete")
    public R delete(@RequestBody Integer[] iconIds){
		iconService.removeByIds(Arrays.asList(iconIds));

        return R.ok();
    }

}
