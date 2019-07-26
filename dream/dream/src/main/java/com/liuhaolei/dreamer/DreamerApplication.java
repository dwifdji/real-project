package com.liuhaolei.dreamer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.Order;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Controller;

import com.liuhaolei.dreamer.common.fiter.CrossDomainFilter;
import com.liuhaolei.dreamer.common.fiter.JwtFilter;

@SpringBootApplication
@ComponentScan(basePackages = {"com.liuhaolei.dreamer"}, 
includeFilters = @Filter(type = FilterType.ANNOTATION, classes = {Controller.class}))
public class DreamerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DreamerApplication.class, args);
	}
	
	
	@Bean
	@Order(1)
	public FilterRegistrationBean registBean() {
		
		FilterRegistrationBean bean = new FilterRegistrationBean();
		
		bean.addUrlPatterns("/*");
		
		bean.setFilter(new CrossDomainFilter());
		
		return bean;
	}
	
	
	@Bean
	@Order(2)
	public FilterRegistrationBean jwtBean() {
		
		FilterRegistrationBean bean = new FilterRegistrationBean();
		
		bean.addUrlPatterns("/confind/*");
		
		bean.setFilter(new JwtFilter());
		
		return bean;
	}
}


