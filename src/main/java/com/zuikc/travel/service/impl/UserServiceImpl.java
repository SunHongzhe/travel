package com.zuikc.travel.service.impl;

import com.zuikc.travel.dao.UserDao;
import com.zuikc.travel.domain.User;
import com.zuikc.travel.service.UserService;
import com.zuikc.travel.util.BeanFactory;
import com.zuikc.travel.util.MailUtils;
import com.zuikc.travel.util.UuidUtil;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/25 13:19
 * @version: 1.0
 */
public class UserServiceImpl implements UserService {

    private UserDao dao = BeanFactory.getBean("userDao",UserDao.class);

    /**
     * 判断用户名是否重复
     * @param username
     * @return
     */
    @Override
    public boolean findUsername(String username) {
        User user = dao.findByName(username);
        return user == null;
    }

    @Override
    public boolean register(User user) {
        // 保存用户信息
        // 设置激活码,唯一字符串
        user.setCode(UuidUtil.getUuid());
        // 设置激活状态
        user.setStatus("N");
        // 调用dao保存
        int colNum = dao.save(user);
        // 发送激活邮件
        String content = "<a href='http://127.0.0.1:80/travel/user/active?code="+ user.getCode() +"'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return colNum == 1;
    }

    @Override
    public boolean active(String code) {
        // 调用dao查询用户是否存在
        User user = dao.findByCode(code);
        if (user != null ) {
            // 用户存在
            int colNum = dao.updateStatus(user);
            return colNum == 1;
        } else {
            // 用户不存在
            return false;
        }


    }

    @Override
    public User login(User user) {
        // 调用dao查询用户是否存在
        User loginUser = dao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        return loginUser;
    }


}