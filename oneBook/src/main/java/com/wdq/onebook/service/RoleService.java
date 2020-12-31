package com.wdq.onebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.entity.RoleEntity;

import java.util.Map;

/**
 * 角色表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
public interface RoleService extends IService<RoleEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //根据rolename查询角色信息
    RoleEntity getRoleInfoByRoleName(String roleName);

    // 根据roleId查询角色信息
    RoleEntity getRoleInfoByRoleId(int roleId);
}

