package com.wdq.onebook.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.Query;

import com.wdq.onebook.dao.BookDetailDao;
import com.wdq.onebook.entity.BookDetailEntity;
import com.wdq.onebook.service.BookDetailService;


@Service("bookDetailService")
public class BookDetailServiceImpl extends ServiceImpl<BookDetailDao, BookDetailEntity> implements BookDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BookDetailEntity> page = this.page(
                new Query<BookDetailEntity>().getPage(params),
                new QueryWrapper<BookDetailEntity>()
        );

        return new PageUtils(page);
    }

}