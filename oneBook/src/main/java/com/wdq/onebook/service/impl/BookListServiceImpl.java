package com.wdq.onebook.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.Query;

import com.wdq.onebook.dao.BookListDao;
import com.wdq.onebook.entity.BookListEntity;
import com.wdq.onebook.service.BookListService;


@Service("bookListService")
public class BookListServiceImpl extends ServiceImpl<BookListDao, BookListEntity> implements BookListService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        IPage<BookListEntity> page = this.page(
                new Query<BookListEntity>().getPage(params),
                new QueryWrapper<BookListEntity>()
                        .like(StringUtils.isNotBlank(userId), "user_id", userId)
                        .eq("status", 0)
        );
        return new PageUtils(page);
    }

}