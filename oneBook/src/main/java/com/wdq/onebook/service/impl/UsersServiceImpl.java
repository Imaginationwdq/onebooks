package com.wdq.onebook.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wdq.onebook.common.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.Query;

import com.wdq.onebook.dao.UsersDao;
import com.wdq.onebook.entity.UsersEntity;
import com.wdq.onebook.service.UsersService;


@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) throws ParseException {
        String username = (String)params.get("username");
        String status = (String) params.get("status");
        String roleName = (String) params.get("roleName");
        IPage<UsersEntity> page = this.page(
                new Query<UsersEntity>().getPage(params),
                new QueryWrapper<UsersEntity>()
                        .like(StringUtils.isNotBlank(username),"username", username)
                        .like(StringUtils.isNotBlank(roleName),"role_name", roleName)
                        .eq(!org.springframework.util.StringUtils.isEmpty(status),"status",status)
        );
        List<UsersEntity> users = page.getRecords();
        //对出生日期的时间格式进行处理，转换成 yyyy-MM-dd 格式
        for (UsersEntity user : users) {
            Date birthday = user.getBirthday();
            if (birthday == null) continue;
            user.setBirthday(DateUtils.dealDateFormat(birthday,DateUtils.DATE_PATTERN));
        }
        return new PageUtils(page);
    }


    /**
     * 根据用户名查询密码
     * @param username
     * @return
     */
    @Override
    public String getPasswordByUsername(String username) {
        UsersEntity usersEntity = this.getBaseMapper().selectOne(
                new QueryWrapper<UsersEntity>()
                .eq("username", username)
                .select("password"));
        if (usersEntity == null) {
            return null;
        }
        return usersEntity.getPassword();
    }

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public UsersEntity getUserInfoByUsername(String username) {
        return getBaseMapper().selectOne(
                new QueryWrapper<UsersEntity>()
                .eq("username", username));
    }

    /**
     * 检测账号、密码是否存在
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean checkLogin(String username, String password) throws Exception {
        UsersEntity usersEntity = this.getBaseMapper().selectOne(
                new QueryWrapper<UsersEntity>()
                        .eq("username", username)
                        .select("password"));
        if (usersEntity == null) {
            throw new Exception("账号不存在！");
        }else {
            if (!usersEntity.getPassword().equals(password)) {
                throw new Exception("密码不正确！");
            }
        }
        return true;
    }

}