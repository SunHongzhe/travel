package com.zuikc.travel.dao.impl;

import com.zuikc.travel.dao.FavoriteDao;
import com.zuikc.travel.domain.Favorite;
import com.zuikc.travel.domain.Route;
import com.zuikc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/29 18:02
 * @version: 1.0
 */
public class FavoriteDaoImpl implements FavoriteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据rid&uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ? ";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
            return favorite;
        } catch (DataAccessException e) {

        }
        return favorite;
    }

    /**
     * 根据rid&uid添加收藏信息
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values(?, ?, ?)";
        template.update(sql,rid,new Date(), uid);
    }

    /**
     * 根据用户id和线路id删除收藏信息
     * @param rid
     * @param uid
     */
    @Override
    public void delete(int rid, int uid) {
        String sql = "delete from tab_favorite where rid = ? and uid = ? ";
        template.update(sql, rid, uid);
    }

    /**
     * 分页查询用户收藏信息
     * @param uid
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findByPage(int uid, int start, int pageSize) {
        String sql = "select * from tab_route r,tab_favorite f WHERE r.rid = f.rid and f.uid = ? LIMIT ?, ?";
        List<Route> routeList = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), uid, start, pageSize);
        return routeList;
    }


    /**
     * 根据rid查询线路收藏信息
     * @param uid
     * @return
     */
    @Override
    public int findTotalCountByUid(int uid) {
        int count = 0;
        try {
            String sql = "select count(*) from tab_favorite where uid = ?";
            count = template.queryForObject(sql, Integer.class, uid);
            return count;
        } catch (DataAccessException e) {
            System.out.println("findCountByRid");
            e.printStackTrace();
        }
        return count;
    }

}