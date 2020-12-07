package com.wdq.onebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.entity.RoleMenuEntity;
import com.wdq.onebook.entity.vo.MenuRolemenuVO;

import java.util.List;
import java.util.Map;

/**
 * 角色权限表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
public interface RoleMenuService extends IService<RoleMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据roleId查询权限树形列表
     */
    List<MenuRolemenuVO> getRoleMenuTreeList(Integer roleId);
}

