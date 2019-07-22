package com._360pai.core.service;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.model.FddSignatuer.OpenMemberReq;
import com._360pai.core.model.FddSignatuer.OpenMemberResp;
import com._360pai.core.model.FddSignatuer.UploadTemplateReq;
import com._360pai.core.model.fdd.GatewayFddCallBackRecord;
import com._360pai.gateway.controller.req.fdd.*;

/**
 * 描述：法大大签章服务接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 14:50
 */
public interface FddSignatureService {

    /**
     * 法大大开通会员接口
     *
     * @param req 开户请求参数
     */
    OpenMemberResp openMember(OpenMemberReq req);

    /**
     * 模板上传接口
     *
     * @param req 上传参数接口
     */
    ResponseModel uploadTemplate(UploadTemplateReq req);


    /**
     * 自动签章接口
     *
     * @param req 签章接口请求参数
     */
    ExtSignContractResp extSignContract(ExtSignContractReq req);



    /**
     * 法大大生成合同接口
     *
     * @param req 公共请求参数
     * @param   param
     * GenerateDelegationReq 委托协议请求参数
     * GenerateAdditionalReq  委托补充协议
     * GenerateDealReq 成交确认协议
     * GenerateEnrollingDelegationReq 预招商委托协议
     */
    GenerateContractResp generateContract(GenerateContractComReq req,Object param);



    /**
     * 签章回调处理
     *
     * @param req 签章接口请求参数
     */
    GenerateContractResp extSignNotify(FddCallBackReq req);


    /**
     * 保存签章回调信息
     *
     * @param req 签章接口请求参数
     */
    ResponseModel saveCallBackInfo(GatewayFddCallBackRecord req);


    /**
     * 更新回调信息
     *
     * @param req 签章接口请求参数
     */
    void updateCallBackInfo(GatewayFddCallBackRecord req);


    /**
     *
     *定时任务查询签章的状态
     *
     */
     void querySignQuartz();



    /**
     *
     *定时将未成功的通知重新通知
     *
     */
    void notifyBusQuartz();


    /**
     * 会员信息修改
     *
     * @param req 修改的请求参数
     */
    FCommResp updateMemInfo(UpdateMemInfoReq req);
}
