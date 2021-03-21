package com.wdq.onebook.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.dao.ParameterDao;
import com.wdq.onebook.dao.PropertyDao;
import com.wdq.onebook.entity.ParameterEntity;
import com.wdq.onebook.entity.PropertyEntity;
import com.wdq.onebook.service.ParameterService;
import com.wdq.onebook.service.PropertyService;
import org.springframework.stereotype.Service;

/**
 * 静态参数
 */
@Service("PropertyService")
public class PropertyServiceImpl extends ServiceImpl<PropertyDao, PropertyEntity> implements PropertyService {

}
