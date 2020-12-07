package com.wdq.onebook.dao;

import com.wdq.onebook.entity.RoleMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdq.onebook.entity.vo.MenuRolemenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 角色权限表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@Mapper
public interface RoleMenuDao extends BaseMapper<RoleMenuEntity> {

    List<MenuRolemenuVO> getRoleMenuTreeList(@Param("roleId") Integer roleId);
}
