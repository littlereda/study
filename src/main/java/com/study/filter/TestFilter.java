package com.study.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author hbc
 * @version 1.0
 * @description 过滤器
 * @date 2023/3/6 14:03
 */
@Slf4j
@WebFilter(filterName = "TestFilter")
public class TestFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        log.info("进入过滤器");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    /***
     * 注册过滤器的几种方式
     * 1、之前是在web.xml中注册
     * 2、在启动类上添加@ServletComponentScan注解自动扫描
     * 3、过滤器上添加@Component注解
     * 4、添加WebConfig配置注册（推荐，能够定义过滤url）
     * */

}
