package com.magic.aimai.video.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Eric Xie on 2017/8/30 0030.
 */


@WebFilter("/*")
public class AuthFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(request.getRequestURI().indexOf("page") >= 0){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String ua = request.getHeader("User-Agent");
        // 拦截除 iPhone Android 微信 和 上传接口 外的视频请求
        // 放行 上传接口
        if (request.getRequestURI().indexOf("res") >= 0){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 放行 ios
        if(ua.indexOf("iPhone") >= 0 && ua.indexOf("MicroMessenger") < 0 && ua.indexOf("Mozilla") < 0){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        // 放行 android
        if(ua.indexOf("Lavf") >= 0 && ua.indexOf("MicroMessenger") < 0){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String referer = request.getHeader("referer");
        if (null == referer) {
            response.sendRedirect(request.getContextPath() + "/page/notFound");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
