package com.zuikc.travel.service.impl;

import com.zuikc.travel.dao.FavoriteDao;
import com.zuikc.travel.dao.RouteDao;
import com.zuikc.travel.domain.Favorite;
import com.zuikc.travel.domain.PageBean;
import com.zuikc.travel.domain.Route;
import com.zuikc.travel.service.FavoriteService;
import com.zuikc.travel.util.BeanFactory;
import com.zuikc.travel.util.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/29 18:02
 * @version: 1.0
 */
public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = BeanFactory.getBean("favoriteDao", FavoriteDao.class);
    private RouteDao routeDao = BeanFactory.getBean("routeDao", RouteDao.class);

    /**
     * 根据线路id及用户id查询是否收藏
     *
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(int rid, int uid) {
        // 调用dao层查询
        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);
        return favorite != null;
    }

    /**
     * 根据用户id及线路id添加收藏信息
     *
     * @param rid
     * @param uid
     */
    @Override
    public void addFavorite(int rid, int uid) {
        favoriteDao.add(rid, uid);
        routeDao.updateFavoriteCount(rid, 1);
    }

    /**
     * 根据rid&uid删除收藏信息
     *
     * @param rid
     * @param uid
     */
    @Override
    public void delFavorite(int rid, int uid) {
        favoriteDao.delete(rid, uid);
        routeDao.updateFavoriteCount(rid, -1);
    }

    @Override
    public PageBean<Route> findByPage(int uid, int currentPage, int pageSize) {
        // 查询用户总收藏数
        int totalCount = favoriteDao.findTotalCountByUid(uid);
        // 机选查询开始位置
        int start = (currentPage - 1) * pageSize;
        // 调用dao查询数据
        List<Route> routeList = favoriteDao.findByPage(uid, start, pageSize);
        // 设置参数
        PageBean<Route> pb = new PageBean<>();
        pb.setTotalCount(totalCount);
        pb.setPageSize(pageSize);
        pb.setCurrentPage(currentPage);
        pb.setList(routeList);
        // 返回查询结果
        return pb;
    }
}