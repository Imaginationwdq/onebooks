package com.wdq.onebook.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.Query;

import com.wdq.onebook.dao.IconDao;
import com.wdq.onebook.entity.IconEntity;
import com.wdq.onebook.service.IconService;


@Service("iconService")
public class IconServiceImpl extends ServiceImpl<IconDao, IconEntity> implements IconService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<IconEntity> page = this.page(
                new Query<IconEntity>().getPage(params),
                new QueryWrapper<IconEntity>()
        );
        List<IconEntity> records = page.getRecords();
        Map<String,String> iconMap = new HashMap<String, String>();
        for(IconEntity entity : records){
            iconMap.put(entity.getIconId().toString(),entity.getRemake());
        }
        List list = new ArrayList<String>();
        list.add(iconMap);
        IPage page1 = new Page();
        page1.setRecords(list);
        page1.setTotal(page.getTotal());
        page1.setSize(page.getSize());
        page1.setCurrent(page.getCurrent());
        return new PageUtils(page1);
    }

}