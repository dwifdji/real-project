package com._360pai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
public class RestPartnerWar extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RestPartnerWar.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(RestPartnerWar.class);
    }
}
