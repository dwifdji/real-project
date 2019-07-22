package com.tzCloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
public class ValuationCoreRunJar{

    public static void main(String[] args) {
        SpringApplication.run(ValuationCoreRunJar.class);
    }
}
