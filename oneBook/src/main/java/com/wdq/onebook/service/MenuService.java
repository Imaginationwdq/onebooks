package com.wdq.onebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.entity.MenuEntity;

import java.util.Map;

/**
 * 菜单表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
public interface MenuService extends IService<MenuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryTreeListPage(Map<String, Object> params);
}

