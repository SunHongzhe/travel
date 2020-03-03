package com.zuikc.travel.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @program: February
 * @description:
 * @author: Sun
 * @create: 2020/02/14 15:37
 * @version: 1.0
 */
public class JDBCUtils {
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    static {
        try {
            // 加载配置文件
            Properties pro = new Properties();
            InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            pro.load(in);
            // 初始化连接池
            dataSource = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 获取连接池对象
     * @return javax.sql.DataSource
     */
    public static DataSource getDataSource(){
        return dataSource;
    }

    /**
     * @Description: 获取连接Connection对象
     * @return java.sql.Connection
     * @throws SQLException
     */
   /* public static Connection getConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection == null) {
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }*/
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }



    // 释放资源
    private static void close(ResultSet rs, Statement stmt, Connection con) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection con, Statement stmt) {
        close(null,stmt,con);
    }

    public static void close(Statement stmt) {
        close(null,stmt,null);
    }

    public static void close(Statement stmt, Connection con){
        close(null, stmt, con);
    }

    public static void close(Connection con) {
        close(null,null,con);
    }

}