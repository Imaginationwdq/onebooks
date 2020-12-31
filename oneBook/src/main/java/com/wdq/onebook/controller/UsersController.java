package com.wdq.onebook.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wdq.onebook.common.utils.DateUtils;
import com.wdq.onebook.components.TokenManager;
import com.wdq.onebook.entity.RoleEntity;
import com.wdq.onebook.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wdq.onebook.entity.UsersEntity;
import com.wdq.onebook.service.UsersService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.R;

import javax.servlet.http.HttpServletRequest;


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
    private RoleService roleService;

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
     * 添加用户
     */
    @PostMapping("/addUser")
    public R addRole(@RequestParam Map<String, Object> params, HttpServletRequest request){
        String username = (String) params.get("username");
        // 检查角色名称是否已存在
        UsersEntity user = usersService.getUserInfoByUsername(username);
        if (user != null) {
            return R.error("该账号已存在，请更换账号！");
        }
        String usernameAdmin = tokenManager.getUserInfoFromToken(request.getHeader("token"));
        String password = (String) params.get("password");
        String name = (String) params.get("name");
        int roleId = Integer.parseInt((String) params.get("roleId")) ;
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername(username);
        usersEntity.setPassword(password);
        usersEntity.setName(name);
        usersEntity.setRoleId(roleId);
        // 根据roleId查询角色名称
        RoleEntity role = roleService.getRoleInfoByRoleId(roleId);
        usersEntity.setRoleName(role.getName());
        usersEntity.setStatus(1);
        usersEntity.setUpdateAccount(usernameAdmin);
        boolean save = usersService.save(usersEntity);
        if (!save) {
            R.error("添加失败，请稍后再试！");
        }
        return R.ok("添加成功");
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteByUserId")
    public R deleteByUserId(@RequestParam Map<String, Object> params){
        Integer userId = Integer.parseInt((String)params.get("userId"));
        boolean b = usersService.removeById(userId);
        if (b) return R.ok("删除成功！");
        return R.error("删除失败！");
    }

    /**
     * 更新状态
     */
    @RequestMapping("/updateStatusByUserId")
    public R updateStatusByUserId(@RequestParam Map<String, Object> params,HttpServletRequest request) throws ParseException {
        // 先查询账号是否存在
        Integer userId = Integer.parseInt((String)params.get("userId"));
        Integer status = Integer.parseInt((String)params.get("status"));
        UsersEntity user = usersService.getById(userId);
        if(user == null) return R.error("用户不存在！");
        if(user.getStatus().equals(status)) return R.ok("更新成功！");
        // 更新状态
        user.setStatus(status);
        String usernameAdmin = tokenManager.getUserInfoFromToken(request.getHeader("token"));
        user.setUpdateAccount(usernameAdmin);
        user.setUpdateTime(DateUtils.getCurrentDate());
        boolean b = usersService.update(user, new UpdateWrapper<UsersEntity>().eq("user_id", userId));
        if (b) return R.ok("更新成功！");
        return R.error("更新失败！");
    }

    /**
     * 更新用户角色
     */
    @RequestMapping("/updateRoleByUserId")
    public R updateRoleByUserId(@RequestParam Map<String, Object> params,HttpServletRequest request){
        // 先查询账号是否存在
        Integer userId = Integer.parseInt((String)params.get("userId"));
        Integer roleId = Integer.parseInt((String)params.get("roleId"));
        UsersEntity user = usersService.getById(userId);
        if(user == null) return R.error("用户不存在！");
        if(user.getRoleId().equals(roleId)) return R.ok("更新成功！");
        // 更新状态
        user.setRoleId(roleId);
        // 根据roleId查询角色名称
        RoleEntity role = roleService.getRoleInfoByRoleId(roleId);
        if (role == null) return R.error("角色不存在！");
        user.setRoleName(role.getName());
        String usernameAdmin = tokenManager.getUserInfoFromToken(request.getHeader("token"));
        user.setUpdateAccount(usernameAdmin);
        boolean b = usersService.update(user, new UpdateWrapper<UsersEntity>().eq("user_id", userId));
        if (b) return R.ok("更新成功！");
        return R.error("更新失败！");
    }
}
