package com.zuikc.travel.service;

import com.zuikc.travel.domain.Category;

import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/28 15:11
 * @version: 1.0
 */
public interface CategoryService {
    /**
     * 查询所有分类信息
     * @return
     */
    List<Category> findAll();

}