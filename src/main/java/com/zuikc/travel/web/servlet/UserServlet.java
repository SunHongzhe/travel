package com.zuikc.travel.web.servlet;

import com.zuikc.travel.domain.ResultInfo;
import com.zuikc.travel.domain.User;
import com.zuikc.travel.service.UserService;
import com.zuikc.travel.util.BeanFactory;
import com.zuikc.travel.util.CookieUtils;
import com.zuikc.travel.util.ImageCodeUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @program: travel
 * @description: 用户注册登录操作处理
 * @author: Sun
 * @create: 2020/02/26 16:20
 * @version: 1.0
 */

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    private UserService service = BeanFactory.getBean("userService",UserService.class);

    /**
     * 生成验证码
     *
     * @param request
     * @param response
     */
    public void checkCode(HttpServletRequest request, HttpServletResponse response) {
        // 设置向页面输出内容的MIME类型
        response.setContentType("image/jpeg");
        // 禁止图片缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        // 获取生成的验证码图片
        BufferedImage image = ImageCodeUtils.creatImage(80, 30);
        HttpSession session = request.getSession();
        // 存储本次生成的验证码信息
        session.setAttribute("CHECKCODE_SERVER", ImageCodeUtils.CHECKCODE_SERVER);
        // 向页面输出验证码
        try {
            ImageIO.write(image, "JPG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找用户名是否重复
     *
     * @param request
     * @param response
     */
    public void findUsername(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        boolean flag = service.findUsername(username);
        ResultInfo info = new ResultInfo();
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("用户名太受欢迎了,换一个吧亲");
        }
        try {
            writeValue(info, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册操作
     * @param request
     * @param response
     */
    public void register(HttpServletRequest request, HttpServletResponse response) {
        // 校验验证码
        String check = request.getParameter("check");
        // 从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        if (!check.equalsIgnoreCase(checkcode_server)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            try {
                writeValue(info, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        // 验证码正确,开始注册用户
        // 获取参数
        Map<String, String[]> map = request.getParameterMap();
        // 封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        // 调用service完成注册
        boolean flag = service.register(user);
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败");
        }
        try {
            writeValue(info, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 激活
     * @param request
     * @param response
     */
    public void active(HttpServletRequest request, HttpServletResponse response) {
        // 获取激活码
        String code = request.getParameter("code");
        // 判断是否存在激活码
        if (code != null && code.length() > 0) {
            // 调用service判断是否激活成功
            boolean flag = service.active(code);
            try {
                if (flag) {
                    // 激活成功
                    response.sendRedirect(request.getContextPath() + "/active_ok.html");
                } else {
                    // 激活失败
                    response.sendRedirect(request.getContextPath() + "/active_failed.html");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 恶意访问
            try {
                response.sendRedirect(request.getContextPath() + "/error/404.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 登录
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request, HttpServletResponse response) {
        // 获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        // 封装对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 调用service查询
        User loginUser = service.login(user);
        ResultInfo info = new ResultInfo();
        // 判断
        if (loginUser == null) {
            // 用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }

        if (loginUser != null && "N".equals(loginUser.getStatus())) {
            // 用户未激活
            info.setFlag(false);
            info.setErrorMsg("您的账号尚未激活,请激活后再试");
        }

        if (loginUser != null && "Y".equals(loginUser.getStatus())) {
            // 登录成功,标记登录信息
            info.setFlag(true);
            request.getSession().setAttribute("user",loginUser);
            // 判读是否选择自动登录
            String checkbox = request.getParameter("checkbox");
            if ("true".equals(checkbox)) {
                Cookie cookie = new Cookie("user",loginUser.getUsername() + "#" + loginUser.getPassword());
                cookie.setPath(request.getContextPath());
                cookie.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(cookie);
            }
        }
        try {
            writeValue(info,response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 用户登录状态展示
     * @param request
     * @param response
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) {
        ResultInfo info = new ResultInfo();
        // 获取用户信息
        User user = (User) request.getSession().getAttribute("user");
        // 判断是否为登录状态
        if (user == null) {
            info.setFlag(false);
        } else {
            info.setFlag(true);
            info.setErrorMsg(user.getName());
        }

        try {
            writeValue(info,response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出
     * @param request
     * @param response
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) {
        // 销毁session
        request.getSession().invalidate();
        // 销毁记录登录状态的cookie
        Cookie cookie = CookieUtils.getCookie(request.getCookies(), "user");
        if (cookie != null) {
            cookie.setPath(request.getContextPath());
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        // 跳转到主页
        try {
            response.sendRedirect(request.getContextPath() + "/index.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}