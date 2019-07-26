package com.mybatisPlus.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mybatisPlus.demo"}, 
includeFilters = @Filter(type = FilterType.ANNOTATION, classes = {Controller.class}))
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
