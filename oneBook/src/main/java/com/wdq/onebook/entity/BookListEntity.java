package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户的图书列表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@Data
@TableName("ob_book_list")
public class BookListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer bookListId;
	/**
	 * 图书编号
	 */
	private Integer bookId;
	/**
	 * 用户编号
	 */
	private Integer userId;
	/**
	 * 数量
	 */
	private Integer num;
	/**
	 * 状态(0:用户图书列表,1:购物车图书列表)
	 */
	private String status;
	/**
	 * 创建日期
	 */
	private Date createTime;

	@TableField(exist = false)
	private BookDetailEntity bookDetail;
}
