package com.winback.arch.web.log;

import com.winback.arch.common.constant.MQKeyConstant;
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
    public Queue logQueue() {
        return new Queue(MQKeyConstant.RABBITMQ_ROUTING_KEY_OPERATOR_QUEUE);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(MQKeyConstant.SEND_EMAIL_QUEUE);
    }

    @Bean
    public Queue messageMQueue() {
        return new Queue(MQKeyConstant.SEND_SMS_QUEUE);
    }

    @Bean
    public Queue emailExceptionQueue() {
        return new Queue(MQKeyConstant.SEND_EXCEPTION_EMAIL_QUEUE);
    }

    @Bean
    public Queue exceptionQueue() {
        return new Queue(MQKeyConstant.EXCEPTION_QUEUE);
    }

    @Bean
    public Queue caseRiskCheckSuccessQueue() {
        return new Queue(MQKeyConstant.CASE_RISK_CHECK_SUCCESS_QUEUE);
    }

    @Bean
    public Queue caseSuccessQueue() {
        return new Queue(MQKeyConstant.CASE_SUCCESS_QUEUE);
    }

    /**
     * 延迟队列用exchange
     */
    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(MQKeyConstant.DELAY_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue contractOrderPayTimeOutQueue() {
        return new Queue(MQKeyConstant.CONTRACT_ORDER_PAY_TIME_OUT_QUEUE);
    }

    @Bean
    public Binding contractOrderPayTimeOutQueueBinding() {
        return BindingBuilder.bind(contractOrderPayTimeOutQueue()).to(delayExchange()).with(MQKeyConstant.CONTRACT_ORDER_PAY_TIME_OUT_QUEUE).noargs();
    }

    @Bean
    public Queue contractOrderBeAboutToPayTimeOutQueue() {
        return new Queue(MQKeyConstant.CONTRACT_ORDER_BE_ABOUT_TO_PAY_TIME_OUT_QUEUE);
    }

    @Bean
    public Binding contractOrderBeAboutToPayTimeOutQueueBinding() {
        return BindingBuilder.bind(contractOrderBeAboutToPayTimeOutQueue()).to(delayExchange()).with(MQKeyConstant.CONTRACT_ORDER_BE_ABOUT_TO_PAY_TIME_OUT_QUEUE).noargs();
    }
}
