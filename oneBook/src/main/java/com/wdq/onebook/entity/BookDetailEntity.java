package com.wdq.onebook.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 图书详情表
 * 
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@Data
@TableName("ob_book_detail")
public class BookDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 图书编号
	 */
	@TableId
	private Integer bookId;
	/**
	 * 分类编号
	 */
	private Integer categoryId;
	/**
	 * 书名
	 */
	private String title;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 图书标价
	 */
	private double price;

	/**
	 * 描述
	 */
	private String remake;
	/**
	 * 状态(0:无效,1:上架, 2:下架,3:已卖出）
	 */
	private String status;
	/**
	 * 正面照片
	 */
	private String frontimg;
	/**
	 * 反面照片
	 */
	private String backimg;
	/**
	 * 侧面照片
	 */
	private String sideimg;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 更新日期
	 */
	private Date updateTime;

}
