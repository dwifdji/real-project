package com._360pai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

/**
 * 描述: 启动类
 *
 * @author : whisky_vip
 * @date : 2018/8/27 13:38
 */
@SpringBootApplication
@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
public class RestAdminRun {
    public static void main(String[] args) {
        SpringApplication.run(RestAdminRun.class);
    }
}
