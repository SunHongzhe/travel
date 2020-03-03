package com.zuikc.travel.dao;

import com.zuikc.travel.domain.Category;

import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/28 15:12
 * @version: 1.0
 */
public interface CategoryDao {

    /**
     * 查询所有分类信息
     * @return
     */
    List<Category> findAll();
}