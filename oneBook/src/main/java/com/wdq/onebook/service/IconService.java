package com.wdq.onebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.entity.IconEntity;

import java.util.Map;

/**
 * 图标表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
public interface IconService extends IService<IconEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

