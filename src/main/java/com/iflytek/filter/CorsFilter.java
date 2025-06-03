package com.iflytek.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        // 初始化方法，可以不实现
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 设置跨域响应头
        res.setHeader("Access-Control-Allow-Origin", "*"); // 允许所有来源
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // 允许的方法
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // 允许的请求头
        res.setHeader("Access-Control-Allow-Credentials", "true"); // 允许携带 cookie
        res.setHeader("Access-Control-Max-Age", "3600"); // 预检请求缓存时间

        // 如果是预检请求（OPTIONS），直接返回 200
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // 继续执行后续过滤器链
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // 销毁方法，可以不实现
    }
}
