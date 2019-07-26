package com.liuhaolei.dreamer.mq;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.liuhaolei.dreamer.common.MailEnum;
import com.liuhaolei.dreamer.conf.MQConfig;
import com.liuhaolei.dreamer.util.EmailService;

@Component
public class MailQueueReceive {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EmailService emailService;
	
	@RabbitListener(queues = MQConfig.QUEUE_A)
	public void process(@Payload MailEnum mailEnum) {
		try {
			
			emailService.senEmail("15538068782@163.com", "1573621793@qq.com", mailEnum.getMsgSubject(), mailEnum.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送邮件异常{}", e.getMessage());
		}
		
	}
	
	@RabbitListener(queues = MQConfig.QUEUE_B)
	public void process(byte[] bytes) {
		try {
			MailEnum mailEnum = (MailEnum) getObjectFromBytes(bytes);
			emailService.senEmail("15538068782@163.com", "1573621793@qq.com", mailEnum.getMsgSubject(), mailEnum.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("发送邮件异常{}", e.getMessage());
		}
		
	}
	
	
	public Object getObjectFromBytes(byte[] objBytes) throws Exception {
		if (objBytes == null || objBytes.length == 0) {
			return null;
		}
		ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
		ObjectInputStream oi = new ObjectInputStream(bi);
		return oi.readObject();
	}

}
