package com.ffcs.config;

import com.ffcs.controller.OrderController;
import com.ffcs.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by urbo on 2018/8/19.
 */
@Component
public class UserConfig implements HandlerInterceptor {

    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserConfig.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();

        if (null == cookies) {
            logger.debug("没有cookies===");
        } else {
            //遍历cookie如果找到登陆状态则返回true执行原来controller的方法
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("uid")) {
                    return true;
                }
            }
        }
        //没有找到登陆状态则重定向到登陆页,返回false,不执行原来controller的方法
        response.sendRedirect("login.html");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
