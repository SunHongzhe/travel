package com.zuikc.travel.service;

import com.zuikc.travel.domain.User;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/25 13:18
 * @version: 1.0
 */
public interface UserService {
    /**
     * 判断用户名是否重复
     * @param username
     * @return
     */
    boolean findUsername(String username);

    /**
     * 注册用户
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 注册激活
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 登录
     * @param user
     * @return
     */
    User login(User user);
}