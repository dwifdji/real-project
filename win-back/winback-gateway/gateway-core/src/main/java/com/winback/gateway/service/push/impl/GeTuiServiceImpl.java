package com.winback.gateway.service.push.impl;

import com.alibaba.fastjson.JSON;
import com.gexin.rp.sdk.base.IPushResult;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.enums.DeviceType;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.gateway.common.connect.GeTuiUtils;
import com.winback.core.commons.constants.PushEnum;
import com.winback.gateway.controller.req.push.SinglePushReq;
import com.winback.gateway.dao.push.TGatewayGetuiRecordDao;
import com.winback.gateway.model.push.TGatewayGetuiRecord;
import com.winback.gateway.service.push.GeTuiService;
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
            //透传推送
            result = GeTuiUtils.pushMessageToSingle(gatewayProperties,req.getClientId(),req.getTitle(),req.getText(),req.getChannel(),req.getLogoUrl(),req.getUrl(),req.getMajorKey(),req.getMsgType());
          /*  if(DeviceType.ANDROID.getKey().equals(req.getChannel())){
                //通知推送
                result = GeTuiUtils.pushMessageToSingleLinkTemplate(gatewayProperties,req.getClientId(),req.getTitle(),req.getText(),req.getLogo(),req.getLogoUrl(),req.getUrl(),req.getMajorKey(),req.getMsgType());
            }*/
        }catch (Exception e){

            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, JSON.toJSONString(req),"个推推送消息异常", null,e);

            log.error("个推单体推送异常，异常信息为：{}",e);
        }

        ResponseModel responseModel = getResponseModel(result);

        //保存个推请求记录
        saveGeTuiRecord(req,result,responseModel);

        //个推推送消息不成功 提示
        if(!ApiCallResult.SUCCESS.getCode().equals(responseModel.getCode())){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, JSON.toJSONString(req),"个推推送消息异常", JSON.toJSONString(responseModel),null);

        }


        return responseModel;
    }

     private ResponseModel getResponseModel(IPushResult result) {

         ResponseModel model = new ResponseModel();

         //当返回为null 是说明是系统异常了
         if(result==null){
             model.setCode(ApiCallResult.EXCEPTION.getCode());
             model.setDesc(ApiCallResult.EXCEPTION.getDesc());
             return model;
         }

         Map<String, Object> respMap = result.getResponse();

         String respCode = respMap.get("result").toString();

         if(PushEnum.RESP_CODE.OK.getType().equals(respCode)){
             return ResponseModel.succ();
         }


         model.setCode(respCode);
         model.setDesc(respMap.get("result").toString());


         return model;
     }

     /**
     *
     *保存个推记录
     *
     */
    private void saveGeTuiRecord(SinglePushReq req ,IPushResult result,ResponseModel responseModel) {

        try {
            TGatewayGetuiRecord record = new TGatewayGetuiRecord();

            record.setBusType(req.getBusType());
            record.setDeleteFlag(false);
            record.setReqParam(JSON.toJSONString(req));
            record.setRespParam(result==null?JSON.toJSONString(responseModel):JSON.toJSONString(result));
            record.setCreateTime(DateUtil.getSysTime());
            record.setClientId(req.getClientId());
            record.setType(DeviceType.ANDROID.getKey().equals(req.getChannel())?0:1);
            record.setStatus(ApiCallResult.SUCCESS.getCode().equals(responseModel.getCode())?1:0);
            gatewayGetuiRecordDao.insert(record);
        }catch (Exception e){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, JSON.toJSONString(req),"保存推送消息异常", null,e);

            log.error("保存推送记录异常，异常信息为：{}",e);
        }


    }


     @Override
     public ResponseModel pushMessageToAll(SinglePushReq req) {
         IPushResult result = null;
         try {

             //result = GeTuiUtils.pushMessageToAll(gatewayProperties,req.getClientId(),req.getTitle(),req.getText(),req.getMajorKey(),req.getMsgType());

             result = GeTuiUtils.pushMessageToAllLinkTemplate(gatewayProperties,req.getClientId(),req.getTitle(),req.getText(),req.getMajorKey(),req.getMsgType());


         }catch (Exception e){
             ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, JSON.toJSONString(req),"推送全部用户异常", null,e);

             log.error("推送全体用户异常，异常信息为：{}",e);
         }

         ResponseModel responseModel = getResponseModel(result);

         //保存个推请求记录
         saveGeTuiRecord(req,result,responseModel);

         //个推推送消息不成功 提示
         if(!ApiCallResult.SUCCESS.getCode().equals(responseModel.getCode())){
             ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, JSON.toJSONString(req),"推送全体消息异常", JSON.toJSONString(responseModel),null);

         }
         return responseModel;
    }
 }
