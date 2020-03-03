package com.zuikc.travel.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @program: February
 * @description:
 * @author: Sun
 * @create: 2020/02/24 18:30
 * @version: 1.0
 */
public class JedisPoolUtils {
    private static JedisPool jedisPool;

    static {
        // 获取配置文件
        InputStream in = JedisPoolUtils.class.getClassLoader().getResourceAsStream("jedis.properties");
        // 创建properties对象
        Properties prop = new Properties();
        // 关联文件
        try {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 获取数据,设置到JedisPoolConfig中
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(prop.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(prop.getProperty("maxIdle")));
        // 初始化JedisPool
        jedisPool = new JedisPool(config,prop.getProperty("host"),
                Integer.parseInt(prop.getProperty("port")));
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void close() {
        jedisPool.close();
    }

}