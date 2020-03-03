package com.zuikc.travel.dao;

import com.zuikc.travel.domain.Route;

import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/28 19:06
 * @version: 1.0
 */
public interface RouteDao {

    /**
     * 根据类别id及线路信息查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    int findTotalCount(int cid, String rname);

    /**
     * 分页查询
     * @param cid
     * @param rname
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int cid, String rname, int start, int pageSize);

    /**
     * 根据线路id查询线路详细信息
     * @param rid
     * @return
     */
    Route findByRid(int rid);

    /**
     * 修改线路的收藏次数
     * @param rid
     * @param i
     */
    void updateFavoriteCount(int rid, int i);
}