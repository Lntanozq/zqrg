package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否完成登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    //定义匹配器
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;


        
        //1、获取本次请求
        String requestURI = request.getRequestURI();
        log.info("拦截请求：{}",requestURI);
        //2、定义不需要存储的请求路径
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };

        //3、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }
        //4、判断登录状态
        if (request.getSession().getAttribute("employee")!=null){
            log.info("用户已经登录,id为{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.serCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }

        if (request.getSession().getAttribute("user")!=null){
            log.info("用户已经登录,id为{}",request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.serCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }

        //5、如果未登录，通过输出流向客户端页面响应数据
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 检查本次匹配是否需要放行
     * @param urls
     * @param requestURL
     * @return
     */
    public boolean check(String[] urls,String requestURL){
        for (String url : urls) {
            boolean match=PATH_MATCHER.match(url,requestURL);
            if (match) return true;
        }
        return false;
    }
}
