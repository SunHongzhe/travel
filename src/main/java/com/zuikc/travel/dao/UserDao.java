package com.zuikc.travel.dao;

import com.zuikc.travel.domain.User;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/25 13:19
 * @version: 1.0
 */
public interface UserDao {

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * 保存用户
     * @param user
     * @return
     */
    int save(User user);

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 更改用户状态码
     * @param user
     * @return
     */
    int updateStatus(User user);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
}