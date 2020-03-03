package com.zuikc.travel.web.servlet;

import com.zuikc.travel.domain.PageBean;
import com.zuikc.travel.domain.Route;
import com.zuikc.travel.service.RouteService;
import com.zuikc.travel.util.BeanFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = BeanFactory.getBean("routeService",RouteService.class);

    /**
     * 根据类别id,旅游线路等进行分页查询
     * @param request
     * @param response
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) {
        // 接收参数
        String currentPageStr = request.getParameter("currentPage");
        String rname = request.getParameter("rname");
        String cidStr = request.getParameter("cid");
        // 参数类型转换
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        // 设置每页显示记录数量
        int pageSize = 5;
        // 调用service查询并返回结果集
        PageBean<Route> ps = routeService.pageQuery(cid,rname,currentPage,pageSize);
        // 响应查询结果
        try {
            writeValue(ps,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据线路id查询线路的详细信息
     * @param request
     * @param response
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) {
        // 获取参数
        String rid = request.getParameter("rid");
        // 调用service查询
        Route route = routeService.findOne(rid);
        // 转为json写回客户端
        try {
            writeValue(route,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
