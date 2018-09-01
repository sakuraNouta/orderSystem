package com.ffcs;

import com.ffcs.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by urbo on 2018/8/19.
 */
@Configuration
public class MySpringMVCConfig implements WebMvcConfigurer {

    @Autowired
    private UserConfig userConfig;
    /**
     *  自定义拦截器
     *  继承WebMvcConfigurerAdapter,重写addInterceptors方法
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userConfig).addPathPatterns("/*.html")
                .excludePathPatterns("/login.html", "/register.html",
                        "/js/**","/css/**","/img/**","/upload/**");
    }

}
