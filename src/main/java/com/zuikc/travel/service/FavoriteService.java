package com.zuikc.travel.service;

import com.zuikc.travel.domain.PageBean;
import com.zuikc.travel.domain.Route;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/29 18:01
 * @version: 1.0
 */
public interface FavoriteService {


    /**
     * 根据线路id及用户id查询用户是否收藏此线路
     * @param rid
     * @param uid
     * @return
     */
    boolean isFavorite(int rid, int uid);

    /**
     * 根据线路id及用户id添加收藏信息
     * @param rid
     * @param uid
     */
    void addFavorite(int rid, int uid);

    /**
     * 根据rid&uid删除收藏信息
     * @param rid
     * @param uid
     */
    void delFavorite(int rid, int uid);

    /**
     * 分页查询用户收藏信息
     * @param uid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> findByPage(int uid, int currentPage, int pageSize);
}