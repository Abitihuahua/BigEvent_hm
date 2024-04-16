package org.ithuahua.config;

import org.ithuahua.intercepters.LoginIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginIntercepter loginIntercepter;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录接口和注册接口不拦截
        registry.addInterceptor(loginIntercepter).excludePathPatterns("/user/login","/user/register");
    }
}
