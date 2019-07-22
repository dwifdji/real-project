package com.winback.core.service.assistant.impl;



import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.Device;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.enums.DeviceType;
import com.winback.arch.common.enums.EmailEnum;
import com.winback.arch.common.enums.MessageTemplateEnum;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.ExceptionEmailUtil;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.condition.assistant.TAppMessageTemplateCondition;
import com.winback.core.condition.connect.TAccountConnectMapCondition;
import com.winback.core.dao._case.TCaseDao;
import com.winback.core.dao._case.TCaseStepRecordDao;
import com.winback.core.dao.assistant.TAppMessageDao;
import com.winback.core.dao.assistant.TAppMessageTemplateDao;
import com.winback.core.dao.connect.TAccountConnectMapDao;
import com.winback.core.dto.assistant.PushMsgDto;
import com.winback.core.model._case.TCase;
import com.winback.core.model._case.TCaseStepRecord;
import com.winback.core.model.assistant.TAppMessage;
import com.winback.core.model.assistant.TAppMessageTemplate;
import com.winback.core.model.connect.TAccountConnectMap;
import com.winback.core.service.assistant.AssistantService;
import com.winback.core.service.assistant.PushMessageService;
import com.winback.gateway.controller.req.email.EmailSendReq;
import com.winback.gateway.controller.req.push.SinglePushReq;
import com.winback.gateway.facade.EmailFacade;
import com.winback.gateway.facade.GeTuiFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *
 * 消息推送实现类
 *
 */
@Slf4j
@Service
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private TAppMessageTemplateDao appMessageTemplateDao;


    @Autowired
    private TAppMessageDao appMessageDao;

    @Reference(version = "1.0.0")
    private GeTuiFacade geTuiFacade;

    @Autowired
    private AssistantService assistantService;


    @Autowired
    private TAccountConnectMapDao accountConnectMapDao;

    @Autowired
    private TCaseStepRecordDao caseStepRecordDao;

    @Autowired
    private TCaseDao caseDao;



    @Reference(version = "1.0.0")
    private EmailFacade emailFacade;


    @Override
    public void pushMsg(PushMsgDto msg) {

        //异步推送消息
        new Thread(()->pushMsgThread(msg)).start();

    }

    private void pushMsgThread(PushMsgDto msg) {

        if(msg.getType()==null){

            log.error("模板类型为空",JSON.toJSONString(msg));

            return;
        }

        //获取模板信息
        TAppMessageTemplateCondition condition = new TAppMessageTemplateCondition();
        condition.setType(msg.getType().getType());
        condition.setDeleteFlag(false);

        TAppMessageTemplate template = appMessageTemplateDao.selectFirst(condition);

        if(template==null){

            log.error("表中的配置为空，请先配置",JSON.toJSONString(msg));

            return;
        }

        try {
            //发送推送
            if(MessageTemplateEnum.SEND_TYPE.PUSH_APP.getType().equals(template.getSendType())){

                sendAppPush(template,msg);

                //发送邮件
            }else if(MessageTemplateEnum.SEND_TYPE.EMAIL.getType().equals(template.getSendType())){

                sendEmail(template,msg);
            }
        }catch (Exception e){

            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, JSON.toJSONString(msg),"推送消息异常", null,e);


            log.error("推送异常，异常信息为：{}",e);
        }


    }

    /**
     *
     *邮件发送
     */
    private void sendEmail(TAppMessageTemplate template, PushMsgDto msg) {

        //获取发送人列表
        List<String> emailList =  getEmailList(template.getPushIds(),msg.getTargetList());
        if(emailList==null||emailList.size()<1){
            return;
        }
        //模板信息
        String context =generateTemplate(msg.getParam(),template.getContent());

        EmailSendReq emailSendReq = new EmailSendReq();
        emailSendReq.setContent(context);
        emailSendReq.setTitle(StringUtils.isEmpty(template.getTitle())?"系统邮件":template.getTitle());
        emailSendReq.setEmail(emailList);

        emailFacade.sendEmail(emailSendReq);


    }

    /**
     *
     *获取发送的邮件信息
     */
    private List<String> getEmailList(String pushIds, List<String> targetList) {

        if(targetList!=null&&targetList.size()>0){

            return targetList;
        }

        return Arrays.asList(pushIds.split(","));

     }


    /**
     *
     *App推送
     */
    private void sendAppPush(TAppMessageTemplate template, PushMsgDto msg) {

        //模板信息
        String context =generateTemplate(msg.getParam(),template.getContent());

        template.setContent(context);

        //发送人信息
        if(msg.getTargetList()==null||msg.getTargetList().size()<1){
            String accountId = template.getPushIds();

            if(StringUtils.isEmpty(accountId)){
                log.error("发送人为空，请确认参数，{}",JSON.toJSONString(msg));
                return;
            }

            //当为发送全部时调用发送全部接口
            if(accountId.equals("all")){
                sendAllUser(template,msg);

                return;
            }

            List<String> userList = new ArrayList<>();

            String[] ids = accountId.split(",");

            for(int i=0;i<ids.length;i++){
                userList.add(ids[i]);
            }

            msg.setTargetList(userList);
        }

        //向特定用户推送消息
        sendTargetMsg(template,msg);



    }

    private void sendTargetMsg(TAppMessageTemplate template, PushMsgDto msg) {

        //同一个推送 推送过滤clientId
        Map<String,String> clientIdMap = new HashMap<>();

        for(String accountId: msg.getTargetList()){

            if("-1".equals(accountId)){
                continue;
            }

            Map<String,String>  map = getClientId(accountId);

            String clientId =map==null?null:map.get("clientId");

            SinglePushReq req = new SinglePushReq();
            req.setClientId(clientId);
            req.setTitle(template.getTitle());
            req.setText(template.getContent());
            req.setMsgType(Integer.valueOf(template.getPushType()));
            req.setBusType(template.getType());
            req.setChannel(map==null?null:map.get("deviceType"));
            //保存站内消息表

            Integer msgId = saveAppMsg(template,accountId,msg);
            //跳转个人中心时 消息id 主键  案件列表不加id
            if(PushEnum.PUSH_TYPE.MESSAGE.getType().equals(String.valueOf(req.getMsgType()))&&!MessageTemplateEnum.TYPE.TYPE_32.getType().equals(msg.getType().getType())){
                req.setMajorKey(String.valueOf(msgId));
            }

            //跳转案件详情页
            if(PushEnum.PUSH_TYPE.CASE_STEP.getType().equals(String.valueOf(req.getMsgType()))||PushEnum.PUSH_TYPE.CASE_LIBRARY.getType().equals(String.valueOf(req.getMsgType()))){
                req.setMajorKey(getCaseId(msg.getParam()));
            }
            if(StringUtils.isEmpty(clientId)){
                //ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, JSON.toJSONString(msg),"推送获取clientId为空", JSON.toJSONString(map),null);
                continue;
            }

            if(!clientIdMap.containsKey(clientId)){
                geTuiFacade.pushMessageToSingle(req);
            }
            clientIdMap.put(clientId,clientId);

        }


    }




    private String getCaseId(JSONObject param) {

        if(param==null){
            return null;
        }

        return param.getString("caseId");
     }

    private Integer saveAppMsg(TAppMessageTemplate template, String accountId, PushMsgDto msg) {

        TAppMessage appMessage = new TAppMessage();

        appMessage.setAccountId(Integer.valueOf(accountId));
        appMessage.setContent(template.getContent());
        appMessage.setTitle(StringUtils.isEmpty(msg.getType().getTypeName())?"系统消息":msg.getType().getTypeName());
        appMessage.setDeleteFlag(false);
        appMessage.setCreateTime(DateUtil.getSysTime());
        appMessage.setParam(msg.getParam()==null?null:msg.getParam().toJSONString());
        appMessage.setType(msg.getType().getType());
        appMessageDao.insert(appMessage);

        return appMessage.getId();

    }

    private Map<String,String> getClientId(String accountId) {
        Map<String,String> map = new HashMap<>();

        //获取redis 里面的 clientId
        Device device = assistantService.getDevice(Integer.valueOf(accountId));

        if(device ==null){
            return null;
        }


        map.put("deviceType",device.getDeviceType());

        if(device !=null){
            map.put("clientId",device.getNotificationToken());
            return map;
        }

        //获取不到获取表里面的
        TAccountConnectMapCondition condition = new TAccountConnectMapCondition();
        condition.setAccountId(Integer.valueOf(accountId));
        condition.setDeleteFlag(false);

        TAccountConnectMap connectMap = accountConnectMapDao.selectFirst(condition);
        
        if(connectMap!=null){
            map.put("clientId",device.getNotificationToken());
          }
        return map;
    }


    /**
     *
     *消息推送给全部用户
     */
    private void sendAllUser(TAppMessageTemplate template,PushMsgDto msg) {

        //个推推送全部用户

        pushAllUser(template);

        TAppMessage allUserMsg = new TAppMessage();
        allUserMsg.setAccountId(-1);
        allUserMsg.setTitle(StringUtils.isEmpty(msg.getType().getTypeName())?"系统消息":msg.getType().getTypeName());
        allUserMsg.setContent(template.getContent());
        allUserMsg.setCreateTime(DateUtil.getSysTime());
        allUserMsg.setDeleteFlag(false);
        allUserMsg.setType(msg.getType().getType());
        allUserMsg.setParam(msg.getParam()==null?null:msg.getParam().toJSONString());
        appMessageDao.insert(allUserMsg);
    }

    private void pushAllUser(TAppMessageTemplate template) {
        SinglePushReq req = new SinglePushReq();

        req.setTitle(template.getTitle());
        req.setText(template.getContent());
        req.setMsgType(Integer.valueOf(template.getPushType()));
        req.setMajorKey("1");
        req.setBusType(template.getType());
        geTuiFacade.pushMessageToAll(req);
    }


    private String generateTemplate(JSONObject param, String template){

        if(param==null){
            return template;
        }

        Map<String, Object> jsonMap = JSONObject.toJavaObject(param, Map.class);

         for (Object s : jsonMap.keySet()) {
            template = template.replaceAll("\\$\\{".concat(s.toString()).concat("\\}")
                    , jsonMap.get(s.toString()).toString());
        }
        return template;
    }


    @Override
    public void pushStepMsg(Integer recordId) {

        try {
            //根据id 获取步骤
            if(recordId==null){
                return;
            }
            TCaseStepRecord stepRecord = caseStepRecordDao.selectById(recordId);
            if(stepRecord==null){
                return;
            }

            new Thread(()->pushStepMsgThread(stepRecord)).start();

        }catch (Exception e){
            log.error("推送案件步骤异常，异常信息为：{}",e);
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, String.valueOf(recordId),"推送案件步骤异常", null,e);

        }

    }

    private void pushStepMsgThread(TCaseStepRecord stepRecord) {

        ResponseModel responseModel = pushStepRecord(stepRecord);

        //推送成功更新步骤状态
        if(ApiCallResult.SUCCESS.getCode().equals(responseModel.getCode())){
            stepRecord.setPushStatus(ApiCallResult.SUCCESS.getCode());
            stepRecord.setUpdateTime(DateUtil.getSysTime());
            caseStepRecordDao.updateById(stepRecord);
        }
    }

    private ResponseModel pushStepRecord(TCaseStepRecord stepRecord) {

        TCase tCase = caseDao.selectById(stepRecord.getCaseId());


        if(tCase==null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        if(tCase.getAccountId()==-1){
            return ResponseModel.succ();
        }
        stepRecord.setAccountId(tCase.getAccountId());

        Map<String,String> map = getClientId(String.valueOf(tCase.getAccountId()));


        String clientId = map==null?null:map.get("clientId");

        SinglePushReq req = new SinglePushReq();
        req.setClientId(clientId);
        req.setTitle("1".equals(stepRecord.getCaseType())?"诉讼步骤更新":"执行步骤更新");
        req.setText(stepRecord.getPushMsg());
        req.setMsgType(Integer.valueOf(PushEnum.PUSH_TYPE.CASE_STEP.getType()));
        req.setBusType(MessageTemplateEnum.TYPE.TYPE_40.getType());
        req.setChannel(map==null?null:map.get("deviceType"));
        //保存站内消息表
        Integer msgId = saveStepMsg(stepRecord,req);
        //跳转个人中心时 消息id 主键
        req.setMajorKey(tCase.getCaseId());


        //没有登录态 不推送
        if(map == null){
            return ResponseModel.succ();
        }

        if(StringUtils.isEmpty(clientId)){
            ExceptionEmailUtil.appExceptionEmail(EmailEnum.IMPORTANT_LEVEL.SERIOUS,EmailEnum.MODULE_TYPE.PUSH, JSON.toJSONString(stepRecord),"推送获取clientId为空", JSON.toJSONString(stepRecord),null);
         }


        return  geTuiFacade.pushMessageToSingle(req);
    }



    private Integer saveStepMsg(TCaseStepRecord stepRecord,SinglePushReq req) {

        TAppMessage appMessage = new TAppMessage();

        appMessage.setAccountId(Integer.valueOf(stepRecord.getAccountId()));
        appMessage.setContent(stepRecord.getPushMsg());
        appMessage.setTitle(StringUtils.isEmpty(req.getTitle())?"系统消息":req.getTitle());
        appMessage.setDeleteFlag(false);
        appMessage.setCreateTime(DateUtil.getSysTime());
        appMessageDao.insert(appMessage);

        return appMessage.getId();
    }
}
