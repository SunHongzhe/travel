package com.zuikc.travel.dao.impl;

import com.zuikc.travel.dao.RouteImgDao;
import com.zuikc.travel.domain.RouteImg;
import com.zuikc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/29 14:04
 * @version: 1.0
 */
public class RouteImgDaoImpl implements RouteImgDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据线路id查询图片详细信息
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findByRid(int rid) {
        List<RouteImg> routeImgList = null;
        try {
            String sql = "select * from tab_route_img where rid = ? ";
            routeImgList = template.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return routeImgList;
    }
}