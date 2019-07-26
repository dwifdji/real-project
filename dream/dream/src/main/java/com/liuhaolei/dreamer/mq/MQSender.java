package com.liuhaolei.dreamer.mq;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuhaolei.dreamer.common.MailEnum;
import com.liuhaolei.dreamer.conf.MQConfig;

@Component
public class MQSender implements RabbitTemplate.ConfirmCallback{

    private final Logger logger = LoggerFactory.getLogger(MQSender.class);
    @Autowired
    private ObjectMapper objectMapper;


    //由于rabbitTemplate的scope属性设置为ConfigurableBeanFactory.SCOPE_PROTOTYPE，所以不能自动注入
    private RabbitTemplate rabbitTemplate;
    
    @Autowired
    public MQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }

    /**
     * 发送字符串
     * @param content
     */
    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_A, MQConfig.ROUTINGKEY_A, content, correlationId);
    }
    
    /**
     * 自己封装传输json数据
     * @param mailEnum
     */
    public void sendMsgObject(MailEnum mailEnum) {
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        try {
        	CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            byte[] mailEnumBytes = getBytesFromObject(mailEnum);
            rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_A, MQConfig.ROUTINGKEY_B, mailEnumBytes, correlationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
     }
    
    /**
     * 直接通过配置进行json数据传输
     * @param mailEnum
     */
    public void sendMsgJson(MailEnum mailEnum) {
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        try {
            
        	CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
            rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_A, MQConfig.ROUTINGKEY_A, mailEnum, correlationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
     }
    
    
    //对象转化为字节码
    public  byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }
    
    /**
     * 广播发送所有的数据
     * @param content
     */
    public void sendAll(String content) {
    	
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_B,"", content);
    }

    /**
     * 生产者回调函数当消费者成功进行消费的时候
     * 会回调该函数
     */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		 logger.info(" 回调id:" + correlationData);
	        if (ack) {
	            logger.info("消息成功消费");
	        } else {
	            logger.info("消息消费失败:" + cause);
	        }
	} 

	 
}
