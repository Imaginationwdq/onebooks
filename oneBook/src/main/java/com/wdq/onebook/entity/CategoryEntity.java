package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分类表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@Data
@TableName("ob_categorys")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类编号
	 */
	@TableId
	private Integer categoryId;
	/**
	 * 分类名称
	 */
	private String categoryName;
	/**
	 * 分类上级编号(-1:最顶级)
	 */
	private Integer parent;
	/**
	 * 分类顶级编号(-1:最顶级)
	 */
	private Integer top;
	/**
	 * 分类层级
	 */
	private String level;
	/**
	 * 分类状态(0:无效,1:有效)
	 */
	private String status;
	/**
	 * 子节点
	 */
	@TableField(exist = false)
	private List<CategoryEntity> children;
}
