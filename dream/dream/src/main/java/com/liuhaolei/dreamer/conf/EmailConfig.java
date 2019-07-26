package com.liuhaolei.dreamer.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "spring.mail")
@Component
@Data
public class EmailConfig {
	
	private String host;
	private int port;
	private String username;
	private String password;
	
 
   
}
