package com.wdq.onebook.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wdq.onebook.entity.RoleMenuEntity;
import com.wdq.onebook.service.RoleMenuService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.R;



/**
 * 角色权限表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@RestController
@RequestMapping("onebook/rolemenu")
public class RoleMenuController {
    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleMenuService.queryPage(params);

        return R.ok().put("page", page);
    }



    /**
     * 信息
     */
    @RequestMapping("/info/{roleMenuId}")
    public R info(@PathVariable("roleMenuId") Integer roleMenuId){
		RoleMenuEntity roleMenu = roleMenuService.getById(roleMenuId);

        return R.ok().put("roleMenu", roleMenu);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody RoleMenuEntity roleMenu){
		roleMenuService.save(roleMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RoleMenuEntity roleMenu){
		roleMenuService.updateById(roleMenu);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] roleMenuIds){
		roleMenuService.removeByIds(Arrays.asList(roleMenuIds));

        return R.ok();
    }

}
