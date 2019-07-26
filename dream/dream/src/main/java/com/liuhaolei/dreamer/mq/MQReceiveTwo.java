package com.liuhaolei.dreamer.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.liuhaolei.dreamer.conf.MQConfig;

@RabbitListener(queues = {MQConfig.QUEUE_B})
@Component
public class MQReceiveTwo {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RabbitHandler
	public void process(String content) {
		
		logger.info("我是消费者2接收到的信息： " + content);
		
	}
}
