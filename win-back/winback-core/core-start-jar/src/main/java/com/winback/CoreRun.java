package com.winback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/1/14 11:25
 */
@SpringBootApplication
@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
public class CoreRun {
    public static void main(String[] args) {
        SpringApplication.run(CoreRun.class);
    }
}
