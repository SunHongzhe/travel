package com.zuikc.travel.dao.impl;

import com.zuikc.travel.dao.UserDao;
import com.zuikc.travel.domain.User;
import com.zuikc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/25 13:20
 * @version: 1.0
 */
public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    @Override
    public User findByName(String username) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
        } catch (DataAccessException e) {
        }
        return user;
    }

    @Override
    public int save(User user) {
        int colNum = 0;
        try {
            String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            colNum = template.update(sql, user.getUsername(), user.getPassword(), user.getName(),
                    user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),
                    user.getStatus(), user.getCode());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return colNum;
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int updateStatus(User user) {
        int colNum = 0;
        try {
            String sql = "update tab_user set status = 'Y' where uid = ?";
            colNum = template.update(sql, user.getUid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return colNum;
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),
                    username, password);
        } catch (DataAccessException e) {

        }
        return user;
    }
}