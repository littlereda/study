package com.study.config;

import com.study.filter.TestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @author hbc
 * @version 1.0
 * @description 配置类
 * @date 2023/3/6 14:32
 */
@Configuration
public class WebConfig {

    /**
     * 注册过滤器
     * */
    @Bean
    public FilterRegistrationBean testFilter(){
        TestFilter testFilter = new TestFilter();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(testFilter);
        ArrayList<String> urls = new ArrayList<>();
        /**
         * 匹配请求路径
         * */
        urls.add("/*");
        filterRegistrationBean.setUrlPatterns(urls);
        /**
         * 链式调用
         * 设置执行顺序
         * */
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
