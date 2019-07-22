package com._360pai.arch.web.log;

import com._360pai.arch.common.constant.SystemConstant;
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

    @Bean
    public Queue accountRegisterMessage() {
        return new Queue(SystemConstant.RABBITMQ_ACCOUNT_REGISTER_QUEUE);
    }

    @Bean
    public Queue userApplyApproveMessage() {
        return new Queue(SystemConstant.RABBITMQ_USER_APPLY_APPROVE_QUEUE);
    }

    @Bean
    public Queue companyApplyApproveMessage() {
        return new Queue(SystemConstant.RABBITMQ_COMPANY_APPLY_APPROVE_QUEUE);
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

    @Bean
    public Queue testDelayMessage() {
        return new Queue(SystemConstant.RABBITMQ_TEST_DELAY_QUEUE);
    }

    @Bean
    public Queue testMessage() {
        return new Queue(SystemConstant.RABBITMQ_TEST_QUEUE);
    }

    @Bean
    public Binding testDelayMessageBinding() {
        return BindingBuilder.bind(testDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_TEST_DELAY_QUEUE).noargs();
    }

    @Bean
    public Queue auctionActivityEndDelayMessage() {
        return new Queue(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_END_QUEUE);
    }

    @Bean
    public Binding auctionActivityEndDelayMessageBinding() {
        return BindingBuilder.bind(auctionActivityEndDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_END_QUEUE).noargs();
    }

    @Bean
    public Queue auctionActivityDutchPriceDelayMessage() {
        return new Queue(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_DUTCH_PRICE_QUEUE);
    }

    @Bean
    public Binding auctionActivityDutchPriceDelayMessageBinding() {
        return BindingBuilder.bind(auctionActivityDutchPriceDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_DUTCH_PRICE_QUEUE).noargs();
    }

    @Bean
    public Queue auctionOrderPayTimeoutDelayMessage() {
        return new Queue(SystemConstant.RABBITMQ_AUCTION_ORDER_PAY_TIMEOUT_QUEUE);
    }

    @Bean
    public Binding auctionOrderPayTimeoutDelayMessageBinding() {
        return BindingBuilder.bind(auctionOrderPayTimeoutDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_AUCTION_ORDER_PAY_TIMEOUT_QUEUE).noargs();
    }

    @Bean
    public Queue auctionActivitySignTimeoutDelayMessage() {
        return new Queue(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_SIGN_TIMEOUT_KEY);
    }

    @Bean
    public Binding auctionActivitySignTimeoutDelayMessageBinding() {
        return BindingBuilder.bind(auctionActivitySignTimeoutDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_AUCTION_ACTIVITY_SIGN_TIMEOUT_KEY).noargs();
    }

    @Bean
    public Queue enrollingActivitySignUpEndDelayMessage() {
        return new Queue(SystemConstant.RABBITMQ_ENROLLING_ACTIVITY_SIGN_UP_END_QUEUE);
    }

    @Bean
    public Binding enrollingActivitySignUpEndDelayMessageBinding() {
        return BindingBuilder.bind(enrollingActivitySignUpEndDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_ENROLLING_ACTIVITY_SIGN_UP_END_QUEUE).noargs();
    }

    @Bean
    public Queue disposalDeadlineDelayMessage() {
        return new Queue(SystemConstant.RABBITMQ_DISPOSAL_DEADLINE_QUEUE);
    }

    @Bean
    public Binding disposalDeadlineDelayMessageBinding() {
        return BindingBuilder.bind(disposalDeadlineDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_DISPOSAL_DEADLINE_QUEUE).noargs();
    }

    @Bean
    public Queue providerSurveyDeadlineDelayMessage() {
        return new Queue(SystemConstant.RABBITMQ_PROVIDER_SURVEY_QUEUE);
    }

    @Bean
    public Queue enrollingEndPayEnqueueDelayMessage() {
        return new Queue(SystemConstant.ENROLLING_END_PAY_TIME_QUEUE);
    }

    @Bean
    public Queue disposalPayExpiredMessage() {
        return new Queue(SystemConstant.RABBITMQ_DISPOSAL_PAY_EXPIRED);
    }

    @Bean
    public Binding disposalPayExpiredMessageBinding() {
        return BindingBuilder.bind(disposalPayExpiredMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_DISPOSAL_PAY_EXPIRED).noargs();
    }

    @Bean
    public Queue withfudigPayExpiredMessage() {
        return new Queue(SystemConstant.RABBITMQ_WITHFUDIG_PAY_EXPIRED);
    }

    @Bean
    public Binding withfudigPayExpiredMessageBinding() {
        return BindingBuilder.bind(withfudigPayExpiredMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_WITHFUDIG_PAY_EXPIRED).noargs();
    }

    @Bean
    public Queue compradorPayExpiredMessage() {
        return new Queue(SystemConstant.RABBITMQ_COMPRADOR_PAY_EXPIRED);
    }

    @Bean
    public Binding compradorPayExpiredMessageBinding() {
        return BindingBuilder.bind(compradorPayExpiredMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_COMPRADOR_PAY_EXPIRED).noargs();
    }

    @Bean
    public Binding enrollingEndPayEnqueueDelayMessageBinding() {
        return BindingBuilder.bind(enrollingEndPayEnqueueDelayMessage()).to(delayExchange()).with(SystemConstant.ENROLLING_END_PAY_TIME_QUEUE).noargs();
    }

    @Bean
    public Binding providerSurveyDeadlineDelayMessageBinding() {
        return BindingBuilder.bind(providerSurveyDeadlineDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_PROVIDER_SURVEY_QUEUE).noargs();
    }

    @Bean
    public Queue subordinateAccountRegisterSecondQueue() {
        return new Queue(SystemConstant.RABBITMQ_SUBORDINATE_ACCOUNT_REGISTER_SECOND_QUEUE);
    }

    @Bean
    public Binding subordinateAccountRegisterSecondQueueBinding() {
        return BindingBuilder.bind(testDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_SUBORDINATE_ACCOUNT_REGISTER_SECOND_QUEUE).noargs();
    }

    @Bean
    public Queue subordinateAuthFinishSecondQueue() {
        return new Queue(SystemConstant.RABBITMQ_SUBORDINATE_AUTH_FINISH_SECOND_QUEUE);
    }

    @Bean
    public Binding subordinateAuthFinishSecondQueueBinding() {
        return BindingBuilder.bind(testDelayMessage()).to(delayExchange()).with(SystemConstant.RABBITMQ_SUBORDINATE_AUTH_FINISH_SECOND_QUEUE).noargs();
    }
}
