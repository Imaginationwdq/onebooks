package com.wdq.onebook.common.utils;

import com.wdq.onebook.entity.UsersEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @Auther: wdq
 * @Date: 2020/11/13 15:23
 * @Description:
 */

public class UserInfoUtils {

    //获取当前登录用户
    public static UsersEntity getUserInfo() {
        UsersEntity user = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession();
            user = (UsersEntity)session.getAttribute("userInfo");
        }catch (Exception e) {
        }
        return user;
    }
}
