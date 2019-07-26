package com.liuhaolei.dreamer.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import lombok.Data;

@Configuration
@Data
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class MQConfig implements RabbitListenerConfigurer{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private String host;

	private int port;

	private String username;

	private String password;
 
	
	public static final String EXCHANGE_A = "my-mq-exchange_A";
	public static final String EXCHANGE_B = "my-mq-exchange_B";
	public static final String EXCHANGE_C = "my-mq-exchange_C";

	public static final String QUEUE_A = "QUEUE_A";
	public static final String QUEUE_B = "QUEUE_B";
	public static final String QUEUE_C = "QUEUE_C";

	public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
	public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
	public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";
	
	/**
	 * mq的连接工厂
	 * @return
	 */
	@Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }
	
	/**
	 * mq操作对象
	 * @return
	 */
	@Bean
    public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

	/**
	 * 对象序列化对象
	 * @return
	 */
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 监听器转换配置
     */
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    /**
     * 监听器配置处理池
     * @return
     */
    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    /**
     * 监听器转json为自定义对象
     * 一定要记得对象一定要实现可序列化接口
     * @return
     */
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    /**
     * 针对消费者配置
     * 1. 设置交换机类型
     * 2. 将队列绑定到交换机
     FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
     HeadersExchange ：通过添加属性key-value匹配
     DirectExchange:按照routingkey分发到指定队列
     TopicExchange:多关键字匹配
     */
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE_A);
    }
    
    /**
     * 获取队列A
     * @return
     */
    @Bean
    public Queue queueA() {
        return new Queue(QUEUE_A, true); //队列持久
    }
    
    /**
     * 获取队列B
     * @return
     */
    @Bean
    public Queue queueB() {
        return new Queue(QUEUE_B, true); //队列持久
    }

    
    /**
     * 获取队列C
     * @return
     */
    @Bean
    public Queue queueC() {
        return new Queue(QUEUE_C, true); //队列持久
    }
    
    /**
     * 绑定信息配置且一个交换机可以绑定可以绑定
     * 多个消息队列
     * @return
     */
    @Bean
    public Binding binding() {
 
        return BindingBuilder.bind(queueA()).to(defaultExchange()).with(MQConfig.ROUTINGKEY_A);
    }

    /**
     * 绑定信息配置
     * @return
     */
    @Bean
    public Binding bindingB() {
 
        return BindingBuilder.bind(queueB()).to(defaultExchange()).with(MQConfig.ROUTINGKEY_B);
    }
    
   
    
   
    
  //配置fanout_exchange(广播发送交换机)
//    @Bean
//    FanoutExchange fanoutExchange() {
//        return new FanoutExchange(MQConfig.EXCHANGE_B);
//    }
//    
//    @Bean
//    Binding bindingExchangeA() {
//        return BindingBuilder.bind(queueA()).to(fanoutExchange());
//    }
//
//    @Bean
//    Binding bindingExchangeB() {
//        return BindingBuilder.bind(queueB()).to(fanoutExchange());
//    }
//    
//    @Bean
//    Binding bindingExchangeC() {
//        return BindingBuilder.bind(queueC()).to(fanoutExchange());
//    }
}
