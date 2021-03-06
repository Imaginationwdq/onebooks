package com.wdq.onebook.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Auther: wdq
 * @Date: 2020/11/17 15:23
 * @Description:
 */
@Data
public class MenuRolemenuVO {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单编号
     */
    private Integer menuId;
    /**
     * 图标编号
     */
    private Integer iconId;
    /**
     * 菜单名称
     */
    private String authName;
    /**
     * 菜单层级(1:一级导航菜单,2:二级导航菜单,3:三级导航菜单)
     */
    private String level;
    /**
     * 菜单路径(顶级菜单的路径为空)
     */
    private String path;
    /**
     * 菜单上级编号(-1:最顶级)
     */
    private Integer parent;
    /**
     * 菜单顶级编号(-1:最顶级)
     */
    private Integer top;
    /**
     * 菜单状态(0无效,1:有效)
     */
    private Integer status;
    /**
     * 排序
     */
    private String sort;

    /**
     * 角色权限编号
     */
    @TableId
    private Integer roleMenuId;
    /**
     * 角色编号
     */
    private Integer roleId;

    /**
     * 操作人账号
     */
    private String updateAccount;
    /**
     * 子节点
     */
    private Object children;
}
