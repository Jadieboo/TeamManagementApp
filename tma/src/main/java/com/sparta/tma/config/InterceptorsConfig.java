package com.sparta.tma.config;

import com.sparta.tma.interceptor.StaticResourcesInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Bean
    public StaticResourcesInterceptor interceptor() {
        return new StaticResourcesInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("inside addInterceptors method");

        logger.info("registry: {}", registry.toString());


        registry.addInterceptor(interceptor())
                .addPathPatterns("/css/**", "/images/**")
                .excludePathPatterns("/static/**/*");

        logger.info("registry: {}", registry);


    }



}
