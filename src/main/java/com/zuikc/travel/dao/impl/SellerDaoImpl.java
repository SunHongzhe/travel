package com.zuikc.travel.dao.impl;

import com.zuikc.travel.dao.SellerDao;
import com.zuikc.travel.domain.Seller;
import com.zuikc.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/29 14:05
 * @version: 1.0
 */
public class SellerDaoImpl implements SellerDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据商家id查询商家详细信息
     * @param sid
     * @return
     */
    @Override
    public Seller findSellerBySid(int sid) {
        Seller seller = null;
        try {
            String sql = "select * from tab_seller where sid = ?";
            seller = template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class),sid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return seller;
    }


}