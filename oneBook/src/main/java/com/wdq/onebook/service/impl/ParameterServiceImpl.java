package com.wdq.onebook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.dao.ParameterDao;
import com.wdq.onebook.entity.ParameterEntity;
import com.wdq.onebook.service.ParameterService;
import org.springframework.stereotype.Service;

/**
 * 动态参数
 */
@Service("ParameterService")
public class ParameterServiceImpl extends ServiceImpl<ParameterDao, ParameterEntity> implements ParameterService {

}
