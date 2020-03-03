package com.zuikc.travel.dao;

import com.zuikc.travel.domain.Seller;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/29 14:04
 * @version: 1.0
 */
public interface SellerDao {


    /**
     * 根据商家id查询商家详细信息
     * @param sid
     * @return
     */
    Seller findSellerBySid(int sid);
}