package com.tzCloud.arch.web.log;

import com.tzCloud.arch.common.constant.SystemConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述 rabbitMQ 配置类
 *
 * @author : whisky_vip
 * @date : 2018/8/30 16:29
 */
@Configuration
public class RabbitMqConfiguration {
    @Bean
    public Queue logMessage() {
        return new Queue(SystemConstant.RABBITMQ_ROUTING_KEY_OPERATOR_QUEUE);
    }

    @Bean
    public Queue emailMessage() {
        return new Queue(SystemConstant.RABBITMQ_SEND_EMAIL_QUEUE);
    }

    @Bean
    public Queue messageMessage() {
        return new Queue(SystemConstant.RABBITMQ_SEND_SMS_QUEUE);
    }

 	@Bean
    public Queue emailExceptionMessage() {
        return new Queue(SystemConstant.RABBITMQ_SEND_EXCEPTION_EMAIL_QUEUE);
    }

    @Bean
    public Queue exceptionMessage() {
        return new Queue(SystemConstant.RABBITMQ_EXCEPTION_QUEUE);
    }


    /**
     * 延迟队列用exchange
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(SystemConstant.RABBITMQ_DELAY_EXCHANGE, "x-delayed-message", true, false, args);
    }


}
