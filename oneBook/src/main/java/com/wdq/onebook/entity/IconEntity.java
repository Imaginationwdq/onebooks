package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 图标表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@Data
@TableName("ob_icon")
public class IconEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 图标编号
	 */
	@TableId
	private Integer iconId;
	/**
	 * 描述
	 */
	private String remake;

}
