package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 菜单表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
@Data
@TableName("ob_menu")
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 菜单编号
	 */
	@TableId
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

}
