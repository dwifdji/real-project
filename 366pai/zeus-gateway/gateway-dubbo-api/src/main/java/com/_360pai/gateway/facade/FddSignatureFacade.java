package com._360pai.gateway.facade;

import com._360pai.arch.common.ResponseModel;
import com._360pai.gateway.controller.req.fdd.*;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 14:25
 */
public interface FddSignatureFacade {


    /**
     * 法大大开户接口
     *
     * @param req 法大大开户接口参数
     */
    FOpenMemberResp openMember(FOpenMemberReq req);


    /**
     * 模板上传接口
     *
     * @param req 上传参数接口
     */
    FCommResp uploadTemplate(FUploadTemplateReq req);



    /**
     * 会员信息修改
     *
     * @param req 修改的请求参数
     */
    FCommResp updateMemInfo(UpdateMemInfoReq req);


    /**
     * 签署合同接口
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
     * GenerateBankDelegationReq 银行类委托协议
     *
     *
     * GenerateCreditDelegationReq 债权预招商
     *
     * GenerateRealDelegationReq 物权预招商
     *
     *
     * GenerateServiceAdvisoryReq 咨询服务合同
     *
     * GenerateLeaseDelegationReq 租赁权拍卖委托合同
     *
     * LeaseGenerateDealReq 租赁权成交确认书
     *
     * LeaseWithoutLicenseReq 租赁协议 无证版
     *LeaseHasLicenseReq  租赁协议 有证版
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
     *
     */
    ResponseModel saveCallBackInfo(GatewayFddCallBackRecordReq req);


    /**
     * 更新签章回调信息
     *
     *
     */
    void updateCallBackInfo(GatewayFddCallBackRecordReq req);




    /**
     *
     * 定时任务查询签章的状态
     *
     */
    void querySignQuartz();



    /**
     *
     * 定时任务 重新发起 通知gateway 以及core 方
     *
     */
    void notifyBusQuartz();

}
