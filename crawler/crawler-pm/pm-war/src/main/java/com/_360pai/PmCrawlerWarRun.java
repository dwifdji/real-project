package com._360pai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/7 17:01
 */
@SpringBootApplication
@ImportResource({"classpath:disconf.xml"})
public class PmCrawlerWarRun extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PmCrawlerWarRun.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PmCrawlerWarRun.class);
    }


}
