package com.wdq.onebook.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.Query;

import com.wdq.onebook.dao.RoleDao;
import com.wdq.onebook.entity.RoleEntity;
import com.wdq.onebook.service.RoleService;
import org.springframework.util.StringUtils;


@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 查询条件
        String status = (String) params.get("status");
        String name = (String) params.get("name");
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
                        .eq(!StringUtils.isEmpty(status),"status",status)
                        .eq(!StringUtils.isEmpty(name),"name",name)
        );
        return new PageUtils(page);
    }

    /**
     * 根据rolename查询角色信息
     * @param roleName
     * @return
     */
    @Override
    public RoleEntity getRoleInfoByRoleName(String roleName) {
        return baseMapper.selectOne(
                new QueryWrapper<RoleEntity>()
                        .eq("name", roleName));
    }

    /**
     * 根据roleId查询角色信息
     * @param roleId
     * @return
     */
    @Override
    public RoleEntity getRoleInfoByRoleId(int roleId) {
        return baseMapper.selectOne(
                new QueryWrapper<RoleEntity>()
                        .eq("role_id", roleId));
    }

}