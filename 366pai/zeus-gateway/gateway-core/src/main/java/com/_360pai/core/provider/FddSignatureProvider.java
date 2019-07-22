package com._360pai.core.provider;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.model.FddSignatuer.OpenMemberReq;
import com._360pai.core.model.FddSignatuer.OpenMemberResp;
import com._360pai.core.model.FddSignatuer.UploadTemplateReq;
import com._360pai.core.model.fdd.GatewayFddCallBackRecord;
import com._360pai.core.service.FddSignatureService;
import com._360pai.gateway.controller.req.fdd.*;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：东方付通Facade接口实现
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 14:28
 */
@Component
@Service(version = "1.0.0")
public class FddSignatureProvider implements FddSignatureFacade {

    private final Logger logger = LoggerFactory.getLogger(FddSignatureProvider.class);


    @Autowired
    private FddSignatureService fddSignatureService;



    @Override
    public FCommResp uploadTemplate(FUploadTemplateReq req) {
        
        UploadTemplateReq upload_req = new UploadTemplateReq();

        BeanUtils.copyProperties(req, upload_req);
        
        return getUploadTemplateResp(fddSignatureService.uploadTemplate(upload_req));
    }

    @Override
    public FCommResp updateMemInfo(UpdateMemInfoReq req) {
        return fddSignatureService.updateMemInfo(req);
    }


    @Override
    public ExtSignContractResp extSignContract(ExtSignContractReq req) {



        return fddSignatureService.extSignContract(req);
    }



    /**
     *
     * 生成合同协议
     *
     */
    @Override
    public GenerateContractResp generateContract(GenerateContractComReq req, Object param) {

        //参数校验



        return fddSignatureService.generateContract(req,param);
    }


    /**
     *
     * 签章参数返回
     */
    @Override
    public GenerateContractResp extSignNotify(FddCallBackReq req) {

        logger.info("gateway收到异步签章通知结果，参数为：{}", JSON.toJSONString(req));

        return fddSignatureService.extSignNotify(req);

    }

    @Override
    public ResponseModel saveCallBackInfo(GatewayFddCallBackRecordReq req) {

        GatewayFddCallBackRecord record = new GatewayFddCallBackRecord();

        BeanUtils.copyProperties(req, record);


        return fddSignatureService.saveCallBackInfo(record);
    }

    @Override
    public void updateCallBackInfo(GatewayFddCallBackRecordReq req) {

        GatewayFddCallBackRecord record = new GatewayFddCallBackRecord();

        BeanUtils.copyProperties(req, record);

        fddSignatureService.updateCallBackInfo(record);
    }

    /**
     *
     *定时任务查询签章的状态
     */
    @Override
    public void querySignQuartz() {


        fddSignatureService.querySignQuartz();


    }




    @Override
    public FOpenMemberResp openMember(FOpenMemberReq req) {

        //校验请求参数
        String msg = checkReqParam(req);

        if(StringUtils.isNotEmpty(msg)){

            return FOpenMemberResp.fail(msg);
        }

        OpenMemberReq open_req = new OpenMemberReq();

        BeanUtils.copyProperties(req, open_req);

        return getOpenMemberResp(fddSignatureService.openMember(open_req));
    }

    /**
     *
     *校验法大大开户请求参数
     */
    private String checkReqParam(FOpenMemberReq req) {

        //必填参数不能为空
        if(StringUtils.isEmpty(req.getCustomer_name())||StringUtils.isEmpty(req.getId_card())||StringUtils.isEmpty(req.getMobile())){

            return "开户缺少必填参数！";
        }

        return null;
    }


    private FCommResp getUploadTemplateResp(ResponseModel responseModel) {
        FCommResp resp = new FCommResp();
        resp.setCode(responseModel.getCode());

        resp.setDesc(responseModel.getDesc());
        return resp;

    }




    private FOpenMemberResp getOpenMemberResp(OpenMemberResp openMemberResp) {
        FOpenMemberResp resp = new FOpenMemberResp();
        resp.setCustomer_id(openMemberResp.getCustomer_id());
        resp.setCode(openMemberResp.getCode());
        resp.setDesc(openMemberResp.getDesc());
        return resp;

    }

    /**
     *
     *定时任务通知 将通知失败的信息重新推送
     *
     */
    @Override
    public void notifyBusQuartz() {



        fddSignatureService.notifyBusQuartz();

    }





}
