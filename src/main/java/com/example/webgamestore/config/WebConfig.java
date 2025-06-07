package com.example.webgamestore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/css/**", "/js/**", "/images/**")
                .addResourceLocations("classpath:/static/", "classpath:/static/css/", 
                                    "classpath:/static/js/", "classpath:/static/images/");
    }
} 