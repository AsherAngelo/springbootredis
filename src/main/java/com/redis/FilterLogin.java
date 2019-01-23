package com.redis;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by 赵梦杰 on 2018/8/30.
 */
public class FilterLogin implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if(req.getRequestURI().contains("jsp")){
            chain.doFilter(request,response);
            return;
        }
        Object str = req.getSession().getAttribute("user");
        if(str==null){
            resp.sendRedirect("http://localhost:8077/redistest/index.jsp");
        }
    }

    public void destroy() {

    }
}
