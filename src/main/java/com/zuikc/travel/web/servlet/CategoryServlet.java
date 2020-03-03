package com.zuikc.travel.web.servlet;

import com.zuikc.travel.domain.Category;
import com.zuikc.travel.service.CategoryService;
import com.zuikc.travel.util.BeanFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService service = BeanFactory.getBean("categoryService",CategoryService.class);

    /**
     * 分类信息查询
     * @param request
     * @param response
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) {
        // 调用service查询信息
        List<Category> cs = service.findAll();

        // 响应数据
        try {
            writeValue(cs,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
