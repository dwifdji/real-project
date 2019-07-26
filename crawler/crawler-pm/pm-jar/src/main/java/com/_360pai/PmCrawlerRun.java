package com._360pai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/7 17:01
 */
@SpringBootApplication
@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
public class PmCrawlerRun {
    public static void main(String[] args) {
        SpringApplication.run(PmCrawlerRun.class);
    }

}
