package com.zuikc.travel.util;

import javax.servlet.http.Cookie;

/**
 * @program: travel
 * @description:
 * @author: Sun
 * @create: 2020/02/27 22:01
 * @version: 1.0
 */
public class CookieUtils {
    public static Cookie getCookie(Cookie[] cookies, String name) {
       if (cookies != null && cookies.length > 0) {
           for (Cookie cookie : cookies) {
               if (name.equals(cookie.getName()))
                   return cookie;
           }
       }
       return null;
    }
}