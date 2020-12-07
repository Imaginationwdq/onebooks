package com.wdq.onebook.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.entity.UsersEntity;

import java.text.ParseException;
import java.util.Map;

/**
 * 用户表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
public interface UsersService extends IService<UsersEntity> {

    PageUtils queryPage(Map<String, Object> params) throws ParseException;

    //根据username查询password
    String getPasswordByUsername(String username);

    // 根据username查询 用户信息
    UsersEntity getUserInfoByUsername(String username);

    //检测账号、密码是否存在
    boolean checkLogin(String username, String password) throws Exception;
}

