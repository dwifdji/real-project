package com.winback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/1/14 11:26
 */
@SpringBootApplication
@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
public class CoreWar extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CoreWar.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(CoreWar.class);
    }

}
