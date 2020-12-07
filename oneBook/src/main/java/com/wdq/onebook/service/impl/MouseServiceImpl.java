package com.wdq.onebook.service.impl;

import com.wdq.onebook.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.Query;

import com.wdq.onebook.dao.MouseDao;
import com.wdq.onebook.entity.MouseEntity;
import com.wdq.onebook.service.MouseService;


@Service("mouseService")
public class MouseServiceImpl extends ServiceImpl<MouseDao, MouseEntity> implements MouseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MouseEntity> page = this.page(
                new Query<MouseEntity>().getPage(params),
                new QueryWrapper<MouseEntity>().orderBy(true, false, "time")
        );

        return new PageUtils(page);
    }

    /**
     * 鼠标点击次数
     * @param params
     */
    @Override
    public void clickNum(Map<String, Object> params) {
        //查询到第一条
        //TODO 如果表为空，用limit是错误的，修改bug
        MouseEntity mouseEntity = this.baseMapper.selectOne(params);
        //判断是否是当天的记录,如果是，就num+1，如果不是，就插入
        if (mouseEntity == null){
            //查不到该按钮
            params.put("num",1);
            this.baseMapper.addClick(params);
        }
        else {
            Date time = mouseEntity.getTime();
            String format = DateUtils.format(time);
            System.out.println(format);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String format1 = formatter.format(date);
            if (format1.equals(format)) {
                //存在当天的数据，更新这条数据
                MouseEntity mouseEntity2 = this.baseMapper.selectOne(params);
                Integer mouseId = mouseEntity2.getMouseId();
                Map<String,Object> params2 = new HashMap<>();
                params2.put("num",(mouseEntity.getNum()+1));
                params2.put("mouseId",mouseEntity.getMouseId());
                this.baseMapper.updateClick(params2);

            } else {
                params.put("num", 1);
                this.baseMapper.addClick(params);
            }
        }
    }

}