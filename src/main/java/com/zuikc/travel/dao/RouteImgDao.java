package com.zuikc.travel.dao;

import com.zuikc.travel.domain.RouteImg;

import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/29 14:04
 * @version: 1.0
 */
public interface RouteImgDao {


    /**
     * 根据线路id查询线路图片信息
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);
}