package com.winback.core.service.assistant.impl;

import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.enums.MessageTemplateEnum;
import com.winback.arch.core.sysconfig.properties.SystemProperties;
import com.winback.core.model.applet.TAppletMessage;
import com.winback.core.service.assistant.AppletMessageService;
import com.winback.core.service.assistant.PushAppletMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xdrodger
 * @Title: PushAppletMessageServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/3/13 16:00
 */
@Slf4j
@Service
public class PushAppletMessageServiceImpl implements PushAppletMessageService {
    @Autowired
    private AppletMessageService appletMessageService;
    @Autowired
    private SystemProperties systemProperties;

    @Override
    public void pushContractNewArrivalMsg(String typeName) {
        TAppletMessage message = new TAppletMessage();
        message.setExtBindId(-1);
        message.setType(MessageTemplateEnum.AppletType.TYPE_1.getType());
        message.setTitle(MessageTemplateEnum.AppletType.TYPE_1.getTitle());
        message.setContent(String.format(MessageTemplateEnum.AppletType.TYPE_1.getContent(), typeName));
        JSONObject param = new JSONObject();
        param.put("typeName", typeName);
        message.setParam(param.toJSONString());
        appletMessageService.save(message);
    }

    @Override
    public void pushContractOrderBeAboutToPayTimeOutMsg(Integer extBindId, Integer orderId, String orderNo) {
        TAppletMessage message = new TAppletMessage();
        message.setExtBindId(extBindId);
        message.setType(MessageTemplateEnum.AppletType.TYPE_2.getType());
        message.setTitle(MessageTemplateEnum.AppletType.TYPE_2.getTitle());
        message.setContent(MessageTemplateEnum.AppletType.TYPE_2.getContent());
        JSONObject param = new JSONObject();
        param.put("orderId", orderId);
        param.put("orderNo", orderNo);
        message.setParam(param.toJSONString());
        appletMessageService.save(message);
    }

    @Override
    public void pushContractOrderPayTimeOutMsg(Integer extBindId, Integer orderId, String orderNo) {
        TAppletMessage message = new TAppletMessage();
        message.setExtBindId(extBindId);
        message.setType(MessageTemplateEnum.AppletType.TYPE_3.getType());
        message.setTitle(MessageTemplateEnum.AppletType.TYPE_3.getTitle());
        message.setContent(MessageTemplateEnum.AppletType.TYPE_3.getContent());
        JSONObject param = new JSONObject();
        param.put("orderId", orderId);
        param.put("orderNo", orderNo);
        message.setParam(param.toJSONString());
        appletMessageService.save(message);
    }

    @Override
    public void pushContractOrderPaySuccessMsg(Integer extBindId) {
        TAppletMessage message = new TAppletMessage();
        message.setExtBindId(extBindId);
        message.setType(MessageTemplateEnum.AppletType.TYPE_4.getType());
        message.setTitle(MessageTemplateEnum.AppletType.TYPE_4.getTitle());
        message.setContent(MessageTemplateEnum.AppletType.TYPE_4.getContent());
        JSONObject param = new JSONObject();
        param.put("myOrders", "我的订单");
        message.setParam(param.toJSONString());
        appletMessageService.save(message);
    }

    @Override
    public void pushContractDownloadSuccessMsg(Integer extBindId) {
        TAppletMessage message = new TAppletMessage();
        message.setExtBindId(extBindId);
        message.setType(MessageTemplateEnum.AppletType.TYPE_5.getType());
        message.setTitle(MessageTemplateEnum.AppletType.TYPE_5.getTitle());
        message.setContent(MessageTemplateEnum.AppletType.TYPE_5.getContent());
        JSONObject param = new JSONObject();
        param.put("servicePhone", systemProperties.getAppletServicePhone());
        message.setParam(param.toJSONString());
        appletMessageService.save(message);
    }

    @Override
    public void pushContractInvoiceApplySuccessMsg(Integer extBindId) {
        TAppletMessage message = new TAppletMessage();
        message.setExtBindId(extBindId);
        message.setType(MessageTemplateEnum.AppletType.TYPE_6.getType());
        message.setTitle(MessageTemplateEnum.AppletType.TYPE_6.getTitle());
        message.setContent(MessageTemplateEnum.AppletType.TYPE_6.getContent());
        JSONObject param = new JSONObject();
        message.setParam(param.toJSONString());
        appletMessageService.save(message);
    }
}
