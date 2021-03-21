package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 静态参数表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-3-15 11:06:48
 */
@Data
@TableName("ob_property")
public class PropertyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 静态参数编号
	 */
	@TableId
	private Integer propertyId;
	/**
     * 分类编号
	 */
	private Integer categoryId;
	/**
	 * 参数名称
	 */
	private String propertyName;

    /**
     * 参数名称
	 */
	@TableField(exist = false)
	private String[] propertyTagArray;

	/**
	 * 标签（逗号分隔）
	 */
	private String propertyTag;

	/**
     * 前端页面需要
	 */
	@TableField(exist = false)
	private boolean inputVisible = false;

	/**
	 * 前端页面需要
	 */
	@TableField(exist = false)
	private String	inputValue;
}
