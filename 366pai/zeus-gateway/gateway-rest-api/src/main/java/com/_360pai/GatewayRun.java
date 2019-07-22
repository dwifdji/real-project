package com._360pai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com._360pai.**.core.dao.mapper")
@ImportResource({ "classpath:disconf.xml" })
@EnableConfigurationProperties
@EnableAsync
public class GatewayRun {

    public static void main(String[] args) {
        SpringApplication.run(GatewayRun.class, args);
    }

}