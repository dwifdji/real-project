package com._360pai.core.service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

public interface SmsService {

    /**
     * 发送短信接口
     *
     * @param phoneNums     手机号码
     * @param templeteCode  模板代码
     * @param templateParam 模板替换参数
     * @param outId         提供给业务方扩展字段
     * @return
     * @throws ClientException
     */
    SendSmsResponse sendSms(String phoneNums, String templeteCode,
                            String templateParam, String outId) throws ClientException;

    /**
     * 查询短信发送明细
     *
     * @param phoneNumber
     * @param bizId       业务流水号
     * @return
     * @throws ClientException
     */
    QuerySendDetailsResponse querySendDetails(String phoneNumber, String bizId) throws ClientException;

    void cacheSmsCode(String templateParam, String key);
}
