package com.wdq.onebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.entity.MouseEntity;

import java.util.Map;

/**
 * 点击次数表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-21 17:44:09
 */
public interface MouseService extends IService<MouseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void clickNum(Map<String, Object> params);
}

