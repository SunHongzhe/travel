package com.zuikc.travel.dao.impl;

import com.zuikc.travel.dao.RouteDao;
import com.zuikc.travel.domain.Route;
import com.zuikc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/28 19:07
 * @version: 1.0
 */
public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {
        // 定义sql模板
        String sql = "select count(*) from tab_route where 1 = 1 ";
        // 判断查询条件
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }

        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ?");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    /**
     * 分页查询
     * @param cid
     * @param rname
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, String rname, int start, int pageSize) {
        // 定义sql模板
        String sql = "select * from tab_route where 1 = 1 ";
        // 判断参数
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (cid != 0) {
            sb.append(" and cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0  && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" limit ?, ? ");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();
        return  template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }

    /**
     * 根据rid查询线路信息
     * @param rid
     * @return
     */
    @Override
    public Route findByRid(int rid) {
        Route route = null;
        try {
            String sql = "select * from tab_route where rid = ? ";
            route = template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return route;
    }

    /**
     * 根据rid更改线路的收藏信息
     * @param rid
     * @param i
     */
    @Override
    public void updateFavoriteCount(int rid, int i) {
        PreparedStatement pst = null;
        Connection con = null;
        try {
            con = JDBCUtils.getConnection();
            con.setAutoCommit(false);
            String sql = "update tab_route set count = count + ? where rid = ?";
            pst = con.prepareStatement(sql);
            pst.setObject(1, i);
            pst.setObject(2, rid);
            pst.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            JDBCUtils.close(con,pst);
        }
    }


}