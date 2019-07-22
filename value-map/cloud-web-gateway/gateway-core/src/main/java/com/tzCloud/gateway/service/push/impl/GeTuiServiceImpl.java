package com.tzCloud.gateway.service.push.impl;

import com.alibaba.fastjson.JSON;
import com.gexin.rp.sdk.base.IPushResult;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;
import com.tzCloud.gateway.dao.push.TGatewayGetuiRecordDao;
import com.tzCloud.gateway.model.push.TGatewayGetuiRecord;
import com.tzCloud.gateway.service.push.GeTuiService;
import com.tzCloud.gateway.common.connect.GeTuiUtils;
import com.tzCloud.gateway.controller.req.push.SinglePushReq;
import com.tzCloud.gateway.service.push.GeTuiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

 /**
 * 描述：个推服务实现
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/17 10:00
 */
@Slf4j
@Service
public class GeTuiServiceImpl implements GeTuiService {

    @Autowired
    private GatewayProperties gatewayProperties;


    @Autowired
    private TGatewayGetuiRecordDao gatewayGetuiRecordDao;

    @Override
    public ResponseModel pushMessageToSingle(SinglePushReq req) {
        IPushResult result = null;
        try {

             result = GeTuiUtils.pushMessageToSingle(gatewayProperties,req.getClientId(),req.getTitle(),req.getText(),req.getLogo(),req.getLogoUrl(),req.getUrl());

        }catch (Exception e){
            log.error("个推单体推送异常，异常信息为：{}",e);
        }


        //保存个推请求记录
        saveGeTuiRecord(req,result);

        // TODO: 2019/1/24 解析结果 job重新推送 
        Map<String, Object> respInfo =  result.getResponse();

        return ResponseModel.succ();
    }

    /**
     *
     *保存个推记录
     *
     */
    private void saveGeTuiRecord(SinglePushReq req ,IPushResult result) {

        TGatewayGetuiRecord record = new TGatewayGetuiRecord();

        record.setBusType(req.getBusType());
        record.setDeleteFlag(false);
        record.setReqParam(JSON.toJSONString(req));
        record.setRespParam(JSON.toJSONString(result));
        record.setCreateTime(DateUtil.getSysTime());
        record.setClientId(req.getClientId());
        record.setType(1);
        record.setStatus(1);
        gatewayGetuiRecordDao.insert(record);
    }
}
