package com.winback.arch.web.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Throwables;
import com.winback.arch.common.OperatorLogModel;
import com.winback.arch.common.constant.MQKeyConstant;
import com.winback.arch.common.constant.RedisKeyConstant;
import com.winback.arch.common.utils.JsonUtil;
import com.winback.arch.core.redis.RedisCachemanager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;

/**
 * 描述 日志aop 抽象类
 *
 * @author : whisky_vip
 * @date : 2018/9/10 15:08
 */
public abstract class AbstractLogAspect {

    private static Logger                        log         = LoggerFactory.getLogger(AbstractLogAspect.class);
    private static ThreadLocal<Long>             startTime   = new ThreadLocal<>();
    private static ThreadLocal<OperatorLogModel> operatorLog = new ThreadLocal<>();

    @Autowired
    private AmqpTemplate      rabbitTemplate;
    @Autowired
    private LogUtils          logUtils;
    @Resource
    private RedisCachemanager redisCachemanager;
    private String            tableKey = RedisKeyConstant.TABLE + "t_account";

    /**
     * 描述 切面
     *
     * @author : whisky_vip
     * @date : 2018/9/10 15:29
     */
    @Pointcut("execution(* *..controller..*.*(..))")
    public void webLog() {
    }

    /**
     * 描述 获取应用名称
     *
     * @return 应用名称
     * @author : whisky_vip
     * @date : 2018/9/10 15:29
     */
    protected abstract String getApplicationName();

    @Before("webLog()")
    public void doBeforeInServiceLayer(JoinPoint joinPoint) {

        try {
            startTime.set(System.currentTimeMillis());
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest       request    = attributes.getRequest();

            startTime.set(System.currentTimeMillis());

            operatorLog.set(new OperatorLogModel());
            OperatorLogModel operatorLogModel = operatorLog.get();
            String           methodName       = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
            logUtils.doBefore(operatorLogModel, methodName, joinPoint.getArgs());

            operatorLogModel.setAccountId(getAccountInfo(request).getAccountId());
            operatorLogModel.setAccountType(getAccountInfo(request).getAccountType());
            operatorLogModel.setApplicationName(getApplicationName());
            operatorLogModel.setPartyId(getPartyId(operatorLogModel.getAccountId(), operatorLogModel.getApplicationName()));
            operatorLogModel.setStartTime(new Date(startTime.get()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Integer getPartyId(Integer accountId, String applicationName) {
        if (accountId == null || accountId == -1) {
            return -1;
        }
        //if (SystemConstant.APPLICATION_NAME_PLATFORM.equals(applicationName) || SystemConstant.APPLICATION_NAME_PARTNER.equals(applicationName)) {
        //    String cacheStr = (String) redisCachemanager.hGet(tableKey, accountId + "");
        //    if (StringUtils.isNotEmpty(cacheStr)) {
        //        JSONObject account = JSON.parseObject(cacheStr);
        //        if (SystemConstant.APPLICATION_NAME_PLATFORM.equals(applicationName)) {
        //            Integer currentPartyId = account.getInteger("currentPartyId");
        //            if (currentPartyId != null) {
        //                return currentPartyId;
        //            }
        //        } else {
        //            Integer agencyId = account.getInteger("agencyId");
        //            if (agencyId != null) {
        //                return agencyId;
        //            }
        //        }
        //    }
        //}
        return -1;
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            return proceedingJoinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            OperatorLogModel operatorLogModel = operatorLog.get();
            //operatorLogModel.setException(e.getMessage() == null ? Throwables.getStackTraceAsString(e) : e.getMessage());
            operatorLogModel.setException(Throwables.getStackTraceAsString(e));
            startTime.remove();
            log.error("异常请求：exception:{},operatorLog:{},", e.getMessage(), JsonUtil.toJSON(operatorLog.get()));
            sendToRabbitMq();
            throw e;
        }
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) {
        try {
            String           retString        = JSONObject.toJSONString(ret);
            OperatorLogModel operatorLogModel = operatorLog.get();
            operatorLogModel.setResult(retString);
            operatorLogModel.setEndTime(new Date(System.currentTimeMillis()));
            operatorLogModel.setSpendTime(System.currentTimeMillis() - startTime.get());
            startTime.remove();
            if (log.isDebugEnabled()) {
                log.debug("请求：{}", JsonUtil.toJSON(operatorLog.get()));
            }
            sendToRabbitMq();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendToRabbitMq() {
        try {
            this.rabbitTemplate.convertAndSend(MQKeyConstant.RABBITMQ_ROUTING_KEY_OPERATOR_QUEUE, JSON.toJSONString(operatorLog.get()));
            operatorLog.remove();
        } catch (AmqpException e) {
            e.printStackTrace();
            log.error("发送rabbitMq失败 请求：{}", e);
        }
    }

    /**
     * 描述 返回用户信息
     *
     * @param request 请求信息
     * @return 用户id 和 type
     * @author : whisky_vip
     * @date : 2018/9/10 15:30
     */
    public abstract AccountInfo getAccountInfo(HttpServletRequest request);

    public static class AccountInfo implements Serializable {
        private Integer accountId;
        private String  accountType;

        public AccountInfo(Integer accountId, String accountType) {
            this.accountId = accountId;
            this.accountType = accountType;
        }

        private Integer getAccountId() {
            return accountId;
        }

        private String getAccountType() {
            return accountType;
        }

    }
}
