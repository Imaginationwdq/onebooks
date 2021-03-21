package com.wdq.onebook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.dao.BookPropertyDao;
import com.wdq.onebook.entity.BookPropertyEntity;
import com.wdq.onebook.service.BookPropertyService;
import org.springframework.stereotype.Service;

/**
 * 图书静态参数
 */
@Service("BookPropertyService")
public class BookPropertyServiceImpl extends ServiceImpl<BookPropertyDao, BookPropertyEntity> implements BookPropertyService {

}
