package com.wdq.onebook.controller;

import java.text.ParseException;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wdq.onebook.common.utils.DateUtils;
import com.wdq.onebook.components.TokenManager;
import com.wdq.onebook.entity.MenuEntity;
import com.wdq.onebook.entity.RoleMenuEntity;
import com.wdq.onebook.entity.UsersEntity;
import com.wdq.onebook.entity.vo.MenuRolemenuVO;
import com.wdq.onebook.service.MenuService;
import com.wdq.onebook.service.RoleMenuService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.wdq.onebook.entity.RoleEntity;
import com.wdq.onebook.service.RoleService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 角色表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
@RestController
@RequestMapping("onebook/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private MenuService MenuService;

    @Autowired
    TokenManager tokenManager;
    /**
     * 列表
     */
    @PostMapping("/treeList")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleService.queryPage(params);
        List<RoleEntity> roleList = (List<RoleEntity>) page.getList();
        //再将用户权限列表和用户拼装
        for(RoleEntity entity : roleList){
            List<MenuRolemenuVO> roleMenuTreeList = roleMenuService.getRoleMenuTreeList(entity.getRoleId());
            entity.setChildren(roleMenuTreeList);
        }
        page.setList(roleList);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    public R info(@PathVariable("roleId") Integer roleId){
		RoleEntity role = roleService.getById(roleId);

        return R.ok().put("role", role);
    }


    /**
     * 添加角色
     */
    @PostMapping("/addRole")
    public R addRole(@RequestParam Map<String, Object> params, HttpServletRequest request){
        String roleName = (String) params.get("roleName");
        // 检查角色名称是否已存在
        RoleEntity role = roleService.getRoleInfoByRoleName(roleName);
        if (role != null) {
            return R.error("该角色已存在，请更换角色名称！");
        }
        String token = request.getHeader("token");
        String username = tokenManager.getUserInfoFromToken(request.getHeader("token"));
        String roleRemake = (String) params.get("roleRemake");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRemake(roleRemake);
        roleEntity.setName(roleName);
        roleEntity.setUpdateAccount(username);
        boolean save = roleService.save(roleEntity);
        if (!save) {
            R.error("添加失败，请稍后再试！");
        }
        return R.ok("添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RoleEntity role){
		roleService.updateById(role);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/deleteByRoleId")
    public R delete(@RequestParam Map<String, Object> params){
        Integer roleId = Integer.parseInt((String)params.get("roleId"));
        boolean b = roleService.removeById(roleId);
        if (b) return R.ok("删除成功！");
        return R.error("删除失败！");
    }

    /**
     * 权限分配
     */
    @PostMapping("/allotRights")
    public R allotRights(@RequestParam Map<String, Object> params,HttpServletRequest request){
        Integer roleId = Integer.parseInt((String)params.get("roleId"));
        roleMenuService.remove(new QueryWrapper<RoleMenuEntity>()
                .eq("role_id",roleId));
        String menuIds = (String)params.get("menuIds");
        String[] split = menuIds.split(",");
        List<Integer> menuIdss = new ArrayList<>();
        for (int i = 0; i < split.length; i++){
            menuIdss.add(Integer.parseInt(split[i]));
        }
        for (int i = 0; i < menuIdss.size(); i++){
            Integer menuId = menuIdss.get(i);
            // 根据menuid查询上级菜单
            MenuEntity menuEntity = MenuService.getOne(new QueryWrapper<MenuEntity>().eq("menu_id", menuId));
            Integer parent = menuEntity.getParent();
            if (parent == -1) continue;
            MenuEntity menu2 = MenuService.getOne(new QueryWrapper<MenuEntity>().eq("menu_id", parent));
            Integer menuId1 = menu2.getMenuId();
            boolean flag = menuIdss.contains(menuId1);
            if (flag) continue;
            menuIdss.add(menuId1);
        }
        // 批量插入
        String username = tokenManager.getUserInfoFromToken(request.getHeader("token"));
        Collection<RoleMenuEntity> roleMenus =new ArrayList<RoleMenuEntity>();
        for (int i = 0; i < menuIdss.size(); i++){
            RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
            roleMenuEntity.setRoleId(roleId);
            roleMenuEntity.setMenuId(menuIdss.get(i));
            roleMenuEntity.setUpdateAccount(username);
            roleMenus.add(roleMenuEntity);
        }
        roleMenuService.saveBatch(roleMenus);

        return R.ok("权限分配成功");
    }

    /**
     * 更新状态
     */
    @RequestMapping("/updateStatusByRoleId")
    public R updateStatusByRoleId(@RequestParam Map<String, Object> params,HttpServletRequest request) throws ParseException {
        // 先查询账号是否存在
        Integer roleId = Integer.parseInt((String)params.get("roleId"));
        Integer status = Integer.parseInt((String)params.get("status"));
        if(StringUtils.isEmpty(roleId) ||StringUtils.isEmpty(status)) return R.error("系统错误");
        RoleEntity role = roleService.getById(roleId);
        if(role == null) return R.error("角色不存在！");
        if(role.getStatus().equals(status)) return R.ok("更新成功！");
        // 更新状态
        role.setStatus(status);
        String usernameAdmin = tokenManager.getUserInfoFromToken(request.getHeader("token"));
        role.setUpdateAccount(usernameAdmin);
        role.setUpdateTime(DateUtils.getCurrentDate());
        boolean b = roleService.update(role, new UpdateWrapper<RoleEntity>().eq("role_id", roleId));
        if (b) return R.ok("更新成功！");
        return R.error("更新失败！");
    }
}
