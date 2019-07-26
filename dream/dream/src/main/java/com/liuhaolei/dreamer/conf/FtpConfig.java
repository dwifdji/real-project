package com.liuhaolei.dreamer.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "ftp")
@Component
public class FtpConfig {
	private String address;
	
	private Integer port;
	
	private String username;
	
	private String password;
	
	private String basepath;

}
