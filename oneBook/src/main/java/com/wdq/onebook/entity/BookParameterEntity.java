package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 图书动态参数表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-3-15 11:06:48
 */
@Data
@TableName("ob_book_parameter")
public class BookParameterEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 图书参数编号
	 */
	@TableId
	private Integer bookParameterId;
	/**
	 * 图书编号
	 */
	private Integer bookId;
	/**
	 * 参数编号
	 */
	private Integer parameterId;

	/**
	 * 参数标签
	 */
	private String parameterTag;

}
