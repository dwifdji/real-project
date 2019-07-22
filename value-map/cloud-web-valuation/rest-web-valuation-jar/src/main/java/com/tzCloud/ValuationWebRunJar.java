package com.tzCloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath:disconf.xml"})
@EnableConfigurationProperties
@SpringBootApplication(exclude=
        {DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
public class ValuationWebRunJar {

    public static void main(String[] args) {
        SpringApplication.run(ValuationWebRunJar.class);
    }



}
