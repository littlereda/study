package com.study.config;

import com.study.interceptor.TestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hbc
 * @version 1.0
 * @description 拦截器配置
 * @date 2023/3/6 15:20
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /** 不需要拦截地址 */
    public static final String[] excludeUrls = { "/login", "/logout", "/refresh" };

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(getTestInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .order(1);
    }

    public TestInterceptor getTestInterceptor(){
        return new TestInterceptor();
    }
}
