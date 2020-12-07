package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 点击次数表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-21 17:44:09
 */
@Data
@TableName("ob_mouse")
public class MouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer mouseId;
	/**
	 * 按钮名称
	 */
	private String name;
	/**
	 * 点击次数
	 */
	private Integer num;
	/**
	 * 日期
	 */
	private Date time;

}
