package com.example.gradecalculator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/chat").setViewName("chat");
        registry.addViewController("/help").setViewName("help");
        registry.addViewController("/navbar").setViewName("navbar");
        registry.addViewController("/grades").setViewName("grades");
        registry.addViewController("/settings").setViewName("settings");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/footer").setViewName("footer");
        registry.addViewController("/imprint").setViewName("imprint");
        registry.addViewController("/privacyPolicy").setViewName("privacyPolicy");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

}
