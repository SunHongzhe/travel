package com.zuikc.travel.service.impl;

import com.zuikc.travel.dao.CategoryDao;
import com.zuikc.travel.domain.Category;
import com.zuikc.travel.service.CategoryService;
import com.zuikc.travel.util.BeanFactory;
import com.zuikc.travel.util.JedisPoolUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/28 15:11
 * @version: 1.0
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = BeanFactory.getBean("categoryDao", CategoryDao.class);
    private Jedis jedis = JedisPoolUtils.getJedis();

    /**
     * 查询所有分类信息
     * @return
     */
    @Override
    public List<Category> findAll() {
        // 先从redis中查询
        Set<String> temps = jedis.zrange("category", 0, -1);
        List<Category> cs = new ArrayList<>();
        if (temps == null || temps.size() == 0) {
            // 如果redis中没有,再从数据库查询
            cs = dao.findAll();
            // 将数据存入redis
            for (Category c : cs) {
                jedis.zadd("category", c.getCid(), c.getCname());
            }
        }
        // 如果redis中没有,为了保证前台页面一致性,再从redis中获取一次
        cs.clear();
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        for (Tuple tuple : categorys) {
            Category category = new Category();
            category.setCname(tuple.getElement());
            category.setCid((int) tuple.getScore());
            cs.add(category);
        }
        return cs;
    }
}