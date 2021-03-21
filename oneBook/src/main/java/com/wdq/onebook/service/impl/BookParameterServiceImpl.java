package com.wdq.onebook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.dao.BookParameterDao;
import com.wdq.onebook.entity.BookParameterEntity;
import com.wdq.onebook.service.BookParameterService;
import org.springframework.stereotype.Service;

/**
 * 图书动态参数
 */
@Service("BookParameterService")
public class BookParameterServiceImpl extends ServiceImpl<BookParameterDao, BookParameterEntity> implements BookParameterService {

}
