package com.winback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
public class RestWebRun {
    public static void main(String[] args) {
        SpringApplication.run(RestWebRun.class);
    }
}
