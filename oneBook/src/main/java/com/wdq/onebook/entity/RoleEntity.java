package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 角色表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
@Data
@TableName("ob_role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色编号
	 */
	@TableId
	private Integer roleId;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色状态(0:无效,1:有效)
	 */
	private Integer status;
	/**
	 * 角色描述
	 */
	private String remake;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 更新日期
	 */
	private Date updateTime;
	/**
	 * 更新账号
	 */
	private String updateAccount;
	/**
	 * 子节点
	 */
	@TableField(exist = false)
	private Object children;

}
