package com.tzCloud.arch.core.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HeadersCORSFilter());
        registration.addUrlPatterns("/*");
        registration.setName("headersCORSFilter");
        registration.setOrder(1);
        return registration;
    }
}
