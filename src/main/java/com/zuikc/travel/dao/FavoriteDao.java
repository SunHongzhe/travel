package com.zuikc.travel.dao;

import com.zuikc.travel.domain.Favorite;
import com.zuikc.travel.domain.Route;

import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/29 18:02
 * @version: 1.0
 */
public interface FavoriteDao {

    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    Favorite findByRidAndUid(int rid, int uid);

    /**
     * 根据rid和uid添加收藏信息
     * @param rid
     * @param uid
     */
    void add(int rid, int uid);

    /**
     * 根据uid查询收藏线路总数
     * @param Uid
     * @return
     */
    int findTotalCountByUid(int Uid);

    /**
     * 根据路线id和用户id删除收藏信息
     * @param rid
     * @param uid
     */
    void delete(int rid, int uid);

    /**
     * 进行分页查询用户收藏信息
     * @param uid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int uid, int start, int pageSize);
}