package com.wdq.onebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.entity.BookDetailEntity;

import java.util.Map;

/**
 * 图书详情表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
public interface BookDetailService extends IService<BookDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

