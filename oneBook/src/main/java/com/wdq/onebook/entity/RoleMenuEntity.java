package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 角色权限表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@Data
@TableName("ob_role_menu")
public class RoleMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 * 权限编号
	 */
	private Integer menuId;
	/**
	 * 操作人账号
	 */
	private String updateAccount;
	/**
	 * 子节点
	 */
	@TableField(exist = false)
	private Object children;
}
