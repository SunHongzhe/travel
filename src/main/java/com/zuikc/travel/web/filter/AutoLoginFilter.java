package com.zuikc.travel.web.filter;

import com.zuikc.travel.domain.User;
import com.zuikc.travel.service.UserService;
import com.zuikc.travel.util.BeanFactory;
import com.zuikc.travel.util.CookieUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AutoLoginFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        // 先判断session中是否有user信息
        if (user != null) {
            // 已经登录过了,放行
            chain.doFilter(request, resp);
        }else {
            // 再去cookie中查看是否存在保存用户登录信息的cookie
            Cookie[] cookies = request.getCookies();
            Cookie cookie = CookieUtils.getCookie(cookies, "user");
            if (cookie == null) {
                // 不存在说明用户未选择自动登录功能,放行
                chain.doFilter(request,resp);
            } else {
                // 存在说明用户选择自动登录,获取cookie的值
                String cookieValue = cookie.getValue();
                // 获取用户名及密码
                String username = cookieValue.split("#")[0];
                String password = cookieValue.split("#")[1];
                // 判断用户是否存在
                User loginUser = new User();
                loginUser.setUsername(username);
                loginUser.setPassword(password);
                // 调用service判断
                UserService service = BeanFactory.getBean("userService",UserService.class);
                User autoLoginUser = service.login(loginUser);
                if (autoLoginUser == null) {
                    // 不存在此用户或用户cookie被篡改,放行
                    chain.doFilter(request,resp);
                } else {
                    // 用户存在,保存用户信息后放行
                    session.setAttribute("user",autoLoginUser);
                    chain.doFilter(request,resp);
                }
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

    public void destroy() {
    }

}
