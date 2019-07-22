package com._360pai.arch.web.log;

import com._360pai.arch.common.OperatorLogModel;
import com._360pai.arch.common.constant.SystemConstant;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 日志处理类
 *
 * @author : whisky_vip
 * @date : 2018/8/23 16:38
 */
@Component
@RabbitListener(queues = SystemConstant.RABBITMQ_ROUTING_KEY_OPERATOR_QUEUE)
@Slf4j
public class OperatorLogHandler {

    @Autowired
    private OperatorLogDao        operatorLogDao;
    @Autowired
    private OperatorLogMongodbDao operatorLogMongodbDao;
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RabbitHandler
    public void process(String message) {
        OperatorLogModel operatorLogModel = JSON.parseObject(message, OperatorLogModel.class);
        OperatorLogModel operatorLogModel1 = null;
        try {
            operatorLogModel1 = operatorLogDao.insertLog(operatorLogModel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OperatorLogModel operatorLogModel2 = null;
        try {
            operatorLogModel1 = (operatorLogModel1 == null) ? operatorLogModel : operatorLogModel;
            operatorLogModel2 = operatorLogMongodbDao.insertLog(operatorLogModel1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(operatorLogModel2.getException())) {
            try {
                log.info("queue={}, message={}, enqueue", SystemConstant.RABBITMQ_EXCEPTION_QUEUE, JSON.toJSONString(operatorLogModel2));
                this.rabbitTemplate.convertAndSend(SystemConstant.RABBITMQ_EXCEPTION_QUEUE, JSON.toJSONString(operatorLogModel2));
            } catch (AmqpException e) {
                e.printStackTrace();
                log.error("发送rabbitMq失败 请求：{}", e);
            }
        }
    }
}
