package com.winback.arch.common.constant;

/**
 * @author xdrodger
 * @Title: MQKeyConstant
 * @ProjectName winback
 * @Description:
 * @date 2019/1/24 16:27
 */
public class MQKeyConstant {

    /**
     * 日志队列名称
     */
    public static final String RABBITMQ_ROUTING_KEY_OPERATOR_QUEUE = "win:operatorLogQueue";
    /**
     * 短信队列名称
     */
    public static final String SEND_SMS_QUEUE = "win:aliSmsQueue";
    /**
     * 邮件队列名称
     */
    public static final String SEND_EMAIL_QUEUE = "win:emailQueue";
    /**
     * 延迟队列exchange名称
     */
    public static final String DELAY_EXCHANGE = "win:delayExchange";

    /**
     * 异常监控邮件队列名称
     */
    public static final String SEND_EXCEPTION_EMAIL_QUEUE = "win:emailExceptionQueue";
    /**
     * 异常信息队列名称
     */
    public static final String EXCEPTION_QUEUE = "win:exceptionQueue";

    /**
     * 合同订单支付超时队列名称
     */
    public static final String CONTRACT_ORDER_PAY_TIME_OUT_QUEUE = "win:contractOrderPayTimeOutQueue";

    /**
     * 合同订单即将支付超时队列名称
     */
    public static final String CONTRACT_ORDER_BE_ABOUT_TO_PAY_TIME_OUT_QUEUE = "win:contractOrderBeAboutToPayTimeOutQueue";

    /**
     * 案件风控审核通过队列名称
     */
    public static final String CASE_RISK_CHECK_SUCCESS_QUEUE = "win:caseRiskCheckSuccessQueue";

    /**
     * 案件完成队列名称
     */
    public static final String CASE_SUCCESS_QUEUE = "win:caseSuccessQueue";
}
