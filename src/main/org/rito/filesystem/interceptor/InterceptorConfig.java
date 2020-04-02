package org.rito.filesystem.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xigua
 * FrameWork4中的已经被弃用了，
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //多个拦截器组成一个拦截链
        //addPathPatterns   用于添加拦截规则
        //addInterceptor    用于添加拦截器到拦截链中
        //只有走DispatcherServlet的请求才会走拦截器链

        //registry.addInterceptor(work1()).addPathPatterns("/*");
        registry.addInterceptor(work2()).addPathPatterns("/*");
    }

    @Bean
    public Work1 work1() {
        return new Work1();
    }

    @Bean
    public Work2 work2() {
        return new Work2();
    }
}
