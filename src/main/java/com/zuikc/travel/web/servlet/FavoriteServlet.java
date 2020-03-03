package com.zuikc.travel.web.servlet;

import com.zuikc.travel.domain.PageBean;
import com.zuikc.travel.domain.Route;
import com.zuikc.travel.domain.User;
import com.zuikc.travel.service.FavoriteService;
import com.zuikc.travel.util.BeanFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {

    private FavoriteService favoriteService = BeanFactory.getBean("favoriteService",FavoriteService.class);


    /**
     * 判断根据线路id判断用户是否收藏
     * @param request
     * @param response
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) {
        // 获取线路rid
        String ridStr = request.getParameter("rid");
        // 获取当前登录的用户
        User user = (User) request.getSession().getAttribute("user");
        // 响应标记
        boolean flag = false;
        try {
            // 判断用户是否存在
            if (user == null) {
                // 用户未登录
                writeValue(flag,response);
            } else  {
                // 用户已登录
                int rid = Integer.parseInt(ridStr);
                // 调用service进行查询
                flag = favoriteService.isFavorite(rid,user.getUid());
                // 响应结果
                writeValue(flag,response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据用户uid和路线rid添加收藏信息
     * @param request
     * @param response
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) {
        // 获取线路rid
        String rid = request.getParameter("rid");
        // 获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        // 用户id
        int uid;
        // 判断用户是否登录
        if (user == null) {
            // 用户未登录
            return;
        } else{
            uid = user.getUid();
        }

        // 调用service进行添加收藏信息
        favoriteService.addFavorite(Integer.parseInt(rid),uid);
    }

    public void delFavorite(HttpServletRequest request, HttpServletResponse response) {
        // 获取rid
        String rid = request.getParameter("rid");
        // 获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        // 用户id
        int uid;
        // 判断用户是否存在
        if (user == null) {
            // 用户未登录
            return;
        } else {
            uid = user.getUid();
        }
        // 调用service进行删除
        favoriteService.delFavorite(Integer.parseInt(rid),uid);
    }

    /**
     * 分页展示我的收藏
     * @param request
     * @param response
     */
    public void myFavorite(HttpServletRequest request, HttpServletResponse response) {
        // 获取用户id
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return;
        }
        int uid = user.getUid();
        // 获取参数
        String currentPageStr = request.getParameter("currentPage");
        // 参数类型转换及初始化
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        // 初始化每页显示记录数
        int pageSize = 12;
        // 调用service进行查询
        PageBean<Route> pb = favoriteService.findByPage(uid,currentPage,pageSize);
        // 响应查询结果
        try {
            writeValue(pb,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
