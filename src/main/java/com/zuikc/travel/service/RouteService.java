package com.zuikc.travel.service;

import com.zuikc.travel.domain.PageBean;
import com.zuikc.travel.domain.Route;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/28 19:05
 * @version: 1.0
 */
public interface RouteService {

    /**
     * 根据类别id及线路进行分页查询
     * @param cid
     * @param rname
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Route> pageQuery(int cid, String rname, int currentPage, int pageSize);


    /**
     * 根据线路id查询线路详细信息
     * @param rid
     * @return
     */
    Route findOne(String rid);
}