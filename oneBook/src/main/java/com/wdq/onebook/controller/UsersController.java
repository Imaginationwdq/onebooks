package com.wdq.onebook.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Map;

import com.wdq.onebook.components.TokenManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wdq.onebook.entity.UsersEntity;
import com.wdq.onebook.service.UsersService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.R;


/**
 * 用户表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
@RestController
@RequestMapping("onebook/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @Autowired
    private TokenManager tokenManager;

    /**
     * 登录
     */
    @PostMapping("/login")
    public R login(@RequestParam Map<String, Object> params) {
        String username = (String)params.get("username");
        String password1 = (String)params.get("password");
        String password = usersService.getPasswordByUsername(username);
        if (password == null) return R.error("用户名不存在，请先注册！");
        if (!password.equals(password1)){
            return R.error("密码错误！");
        }
        String token = tokenManager.createToken(username);
        return R.ok("登录成功！").put("token",token);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) throws ParseException {
        PageUtils page = usersService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("onebook:users:info")
    public R info(@PathVariable("userId") Integer userId){
		UsersEntity users = usersService.getById(userId);

        return R.ok().put("users", users);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("onebook:users:save")
    public R save(@RequestBody UsersEntity users){
		usersService.save(users);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("onebook:users:update")
    public R update(@RequestBody UsersEntity users){
		usersService.updateById(users);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("onebook:users:delete")
    public R delete(@RequestBody Integer[] userIds){
		usersService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
