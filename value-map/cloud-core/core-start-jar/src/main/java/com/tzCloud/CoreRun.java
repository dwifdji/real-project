package com.tzCloud;

import com.tzCloud.core.hanLP.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
public class CoreRun {
    public static void main(String[] args) {
        SpringApplication.run(CoreRun.class);
    }
}
