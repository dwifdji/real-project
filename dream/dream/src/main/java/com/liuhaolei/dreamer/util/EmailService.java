package com.liuhaolei.dreamer.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.liuhaolei.dreamer.conf.EmailConfig;

@Component
public class EmailService {
	
	@Autowired
	private EmailConfig maConfig;
	 
	
	public void senEmail(String mailFrom, String mailTo, String subject, String text) {
		
	 	JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(maConfig.getHost());
        javaMailSender.setPort(maConfig.getPort());
        javaMailSender.setUsername(maConfig.getUsername());
        javaMailSender.setPassword(maConfig.getPassword());
        
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(mailFrom);
			message.setTo(mailTo);
			message.setSubject(subject);
			message.setText(text);
			javaMailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
