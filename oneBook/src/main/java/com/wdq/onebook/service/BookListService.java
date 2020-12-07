package com.wdq.onebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.entity.BookListEntity;

import java.util.Map;

/**
 * 用户的图书列表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
public interface BookListService extends IService<BookListEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

