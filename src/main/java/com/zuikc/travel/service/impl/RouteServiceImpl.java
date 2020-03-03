package com.zuikc.travel.service.impl;

import com.zuikc.travel.dao.FavoriteDao;
import com.zuikc.travel.dao.RouteDao;
import com.zuikc.travel.dao.RouteImgDao;
import com.zuikc.travel.dao.SellerDao;
import com.zuikc.travel.domain.PageBean;
import com.zuikc.travel.domain.Route;
import com.zuikc.travel.domain.RouteImg;
import com.zuikc.travel.domain.Seller;
import com.zuikc.travel.service.RouteService;
import com.zuikc.travel.util.BeanFactory;

import java.util.List;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/28 19:06
 * @version: 1.0
 */
public class RouteServiceImpl implements RouteService {

    private RouteDao routeDao = BeanFactory.getBean("routeDao", RouteDao.class);
    private RouteImgDao routeImgDao = BeanFactory.getBean("routeImgDao", RouteImgDao.class);
    private SellerDao sellerDao = BeanFactory.getBean("sellerDao", SellerDao.class);
    private FavoriteDao favoriteDao = BeanFactory.getBean("favoriteDao", FavoriteDao.class);

    /**
     * 根据类别id及线路信息等进行分页查询
     *
     * @param cid
     * @param rname
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, String rname, int currentPage, int pageSize) {
        // 调用dao查询总记录数
        int totalCount = routeDao.findTotalCount(cid, rname);
        // 计算开始查询位置
        int start = (currentPage - 1) * pageSize;
        // 调用dao查询有效数据
        List<Route> list = routeDao.findByPage(cid, rname, start, pageSize);
        // 封装pageBean
        PageBean<Route> ps = new PageBean<>();
        ps.setCurrentPage(currentPage);
        ps.setPageSize(pageSize);
        ps.setTotalCount(totalCount);
        ps.setList(list);
        // 返回查询结果
        return ps;
    }

    /**
     * 根据线路id查询线路详细信息
     *
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        // 调用routeDao查询线路信息
        Route route = routeDao.findByRid(Integer.parseInt(rid));
        // 调用routeImgDao查询图片信息
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        // 将图片信息设置到route中
        route.setRouteImgList(routeImgList);
        // 调用sellerDao查询商家信息
        Seller seller = sellerDao.findSellerBySid(route.getSid());
        // 将商家信息设置到route中
        route.setSeller(seller);
        // 返回查询结果
        return route;
    }
}