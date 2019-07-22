package com.winback.core.aspact;

import com.google.common.base.Throwables;
import com.winback.arch.common.constant.MQKeyConstant;
import com.winback.core.service.assistant.ExceptionService;
import com.winback.core.service.assistant.PushAppMessageService;
import com.winback.core.service.assistant.PushAppletMessageService;
import com.winback.core.service.contract.ContractOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xdrodger
 * @Title: DelayMqReceiver
 * @ProjectName zeus
 * @Description: 延迟队列出队
 * @date 2018/11/5 16:48
 */
@Component
@Slf4j
public class DelayMqReceiver {
    @Autowired
    private ContractOrderService contractOrderService;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private PushAppMessageService pushAppMessageService;
    @Autowired
    private PushAppletMessageService pushAppletMessageService;
    /**
     *  合同订单即将支付超时队列出队
     */
    @RabbitListener(queues = MQKeyConstant.CONTRACT_ORDER_BE_ABOUT_TO_PAY_TIME_OUT_QUEUE)
    public void contractOrderBeAboutToPayTimeOutDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue", MQKeyConstant.CONTRACT_ORDER_BE_ABOUT_TO_PAY_TIME_OUT_QUEUE, message);
        try {
            Integer orderId = Integer.parseInt(message);
            contractOrderService.beAboutToPayTimeOut(orderId);
        } catch (Exception e) {
            log.error("合同订单即将支付超时处理异常，异常信息为：", e);
            exceptionService.processDequeueException(MQKeyConstant.CONTRACT_ORDER_BE_ABOUT_TO_PAY_TIME_OUT_QUEUE, message, Throwables.getStackTraceAsString(e));
        }
    }

    /**
     *  合同订单支付超时队列出队
     */
    @RabbitListener(queues = MQKeyConstant.CONTRACT_ORDER_PAY_TIME_OUT_QUEUE)
    public void contractOrderPayTimeOutDequeue(String message) {
        log.info("delay_queue={}, message={}, dequeue", MQKeyConstant.CONTRACT_ORDER_PAY_TIME_OUT_QUEUE, message);
        try {
            Integer orderId = Integer.parseInt(message);
            contractOrderService.payTimeOut(orderId);
        } catch (Exception e) {
            log.error("合同订单支付超时处理异常，异常信息为：", e);
            exceptionService.processDequeueException(MQKeyConstant.CONTRACT_ORDER_PAY_TIME_OUT_QUEUE, message, Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 异常信息队列出队
     */
    @RabbitListener(queues = MQKeyConstant.EXCEPTION_QUEUE)
    public void processExceptionDequeue(String message) {
        log.info("queue={}, message={}, dequeue",MQKeyConstant.EXCEPTION_QUEUE, message);
        try {
            exceptionService.processGlobalException(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 案件风控审核通过队列出队
     */
    @RabbitListener(queues = MQKeyConstant.CASE_RISK_CHECK_SUCCESS_QUEUE)
    public void processCaseRiskCheckSuccessDequeue(String message) {
        log.info("queue={}, message={}, dequeue",MQKeyConstant.CASE_RISK_CHECK_SUCCESS_QUEUE, message);
        try {
            pushAppMessageService.pushFranchiseeSubordinatePublishedCaseRiskCheckSuccessMsgIfNeed(message);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(MQKeyConstant.CASE_RISK_CHECK_SUCCESS_QUEUE, message, Throwables.getStackTraceAsString(e));
        }
    }

    /**
     * 案件完成队列出队
     */
    @RabbitListener(queues = MQKeyConstant.CASE_SUCCESS_QUEUE)
    public void processCaseSuccessDequeue(String message) {
        log.info("queue={}, message={}, dequeue",MQKeyConstant.CASE_SUCCESS_QUEUE, message);
        try {
            pushAppMessageService.pushFranchiseeSubordinatePublishedCaseSuccessMsgIfNeed(message);
        } catch (Exception e) {
            e.printStackTrace();
            exceptionService.processDequeueException(MQKeyConstant.CASE_SUCCESS_QUEUE, message, Throwables.getStackTraceAsString(e));
        }
    }
}
