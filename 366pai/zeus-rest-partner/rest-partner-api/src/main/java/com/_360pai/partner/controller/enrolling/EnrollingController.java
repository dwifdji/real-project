package com._360pai.partner.controller.enrolling;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.core.facade.enrolling.EnrollingFacade;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.partner.controller.AbstractController;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
import com._360pai.partner.controller.account.resp.AgencyBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：机构后台预招商接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:09
 */
@RestController
public class EnrollingController extends AbstractController {

    @Reference(version = "1.0.0")
    private EnrollingFacade enrollingFacade;

    @Reference(version = "1.0.0")
    private EnrollingWebFacade enrollingWebFacade;


    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    /**
     * 我的预招商列表
     */
    @GetMapping(value = "/agency/enrolling_activity/my_enrolling_activities")
    public ResponseModel enrollingActivities(EnrollingReq.activitiesListReq req) {
        //参数校验
    	AgencyBaseInfo loginAgency = loadCurLoginAgency();
    	if(loginAgency == null) {
    		return ResponseModel.fail(ApiCallResult.EMPTY);
    	}
    	req.setAgencyId(String.valueOf(loginAgency.getAgencyId()));
        return enrollingFacade.getEnrollingActivityList(req);
    }
    
    /**
     * 查询平台预招商活动列表
     */
    @GetMapping(value = "/agency/my_agency_portal/available_enrolling_activities")
    public ResponseModel availableEnrollingActivities(EnrollingReq.activitiesListReq req) {
    	//预留机构参数
//    	req.setAgencyId();
    	if(StringUtils.isBlank(req.getUnionStatus())) {
    		return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
    	}
    	
    	return enrollingFacade.getAvailableEnrollingActivities(req);
    }
    
    /**
     * 查询已经推广到机构主站的预招商活动
     */
    @GetMapping(value = "/agency/my_agency_portal/promo_merchant_enrolling_activities")
    public ResponseModel promoEnrollingActivities(EnrollingReq.activitiesListReq req) {
    	//预留机构参数
//    	req.setAgencyId();
//    	req.setUnionStatus(EnrollingEnum.QUERY_STATUS);
    	return enrollingFacade.getAvailableEnrollingActivities(req);
    }

    /**
     * 预招商查看基本信息接口
     */
    @GetMapping(value = "/agency/enrolling_activity/base_info")
    public ResponseModel baseInfo(EnrollingReq.activityIdTypeReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setReqType(EnrollingEnum.REQ_TYPE.ADMIN.getType());
        return enrollingWebFacade.myActivityInfo(req);

     }

    /**
     * 预招商活动详情
     */
    @GetMapping(value = "/agency/enrolling_activity/detail")
    public ResponseModel detail(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return enrollingFacade.getDetail(req);
    }

    /**
     * 预招商报名列表
     */
    @GetMapping(value = "/agency/enrolling_activity/apply_list")
    public ResponseModel enrollingOrders(EnrollingReq.activityIdReq req) {

       //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingFacade.enrollingOrders(req);
    }


    /**
     * 编辑预招商详情接口
     */
    @PostMapping(value = "/agency/enrolling_activity/update_detail")
    public ResponseModel updateDetail(@RequestBody EnrollingReq.updateDetailReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingFacade.updateDetail(req);
    }

   
    /**
     * 机构后台修改佣金
     *  
     */
    @PostMapping("/agency/enrolling_activity/update_agency_activty")
    public ResponseModel updateAgencyActivity(@RequestBody EnrollingReq.agencyUpdateReq agencyUpdateReq) {
    	if(StringUtils.isBlank(agencyUpdateReq.getStatus())) {
    		return ResponseModel.fail(ApiCallResult.EMPTY);
    	}
    	
    	return enrollingFacade.updateAgencyActivity(agencyUpdateReq);
    }




    /**
     * 机构后台上传预招商
     *
     */
    @PostMapping("/agency/enrolling_activity/save_activity")
    public ResponseModel saveActivity(@RequestBody EnrollingReq.saveActivityReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        if(accountBaseInfo==null){
            return ResponseModel.fail("请先登录！");
        }

        JSONObject jsonObject = JSON.parseObject(req.getFields());

        Integer categoryId = jsonObject.getInteger("templateId"); //获取当前的模板id
        //获取模板对应的类型
        String enrollingType = EnrollingEnum.TEMPLATE_TYPE.getDesc(String.valueOf(categoryId));
        req.setPartyId(accountBaseInfo.getAgencyId());
        req.setEnrollingType(enrollingType);
        req.setTemplateId(categoryId);
        req.setThirdPartyStatus(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType());

        return enrollingWebFacade.saveActivity(req);
    }



    /**
     * 机构后台获取其发布的预招商活动
     *
     */
    @GetMapping("/agency/enrolling_activity/my_activity")
    public ResponseModel myActivity(EnrollingReq.activitiesListReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        req.setPartyId(String.valueOf(accountBaseInfo.getAgencyId()));
        req.setThirdPartyStatus(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType());
        return enrollingWebFacade.myEnrollingActivities(req);
    }



    /**
     * 机构预招商 招商会信息
     *
     */
    @GetMapping("/agency/enrolling_activity/my_activity_info")
    public ResponseModel myActivityInfo(EnrollingReq.activityIdTypeReq req) {
        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingWebFacade.myActivityInfo(req);
    }


    /**
     * 机构预招商 获取标的信息
     *
     */
    @GetMapping("/agency/enrolling_activity/get_matter_info")
    public ResponseModel getMatterInfo(EnrollingReq.activityIdTypeReq req) {
        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        //认证通过
        req.setSeeStatus("authorization");
        return enrollingWebFacade.getMatterInfo(req);
    }






    /**
     * 机构预招商 报名信息
     *
     */
    @GetMapping("/agency/enrolling_activity/apply_activity_list")
    public ResponseModel applyActivityList(EnrollingReq.activityIdReq req) {
        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        //认证通过
        return enrollingWebFacade.applyActivityList(req);    }




    /**
     * 机构预招商 发布抵押物预招商招商进展
     *
     */
    @PostMapping("/agency/enrolling_activity/save_progress")
    public ResponseModel saveProgress(@RequestBody EnrollingReq.saveProgress req) {
        return enrollingWebFacade.saveProgress(req);
    }


    /**
     * 机构预招商 抵押物预招商招商列表接口
     *
     */
    @GetMapping("/agency/enrolling_activity/get_progress_list")
    public ResponseModel getProgressList(EnrollingReq.activityIdReq req) {

        return enrollingWebFacade.getProgressList(req);
    }



    /**
     * 机构预招商 保存线下成交信息
     *
     */
    @PostMapping("/agency/enrolling_activity/save_result")
    public ResponseModel saveProgress(@RequestBody EnrollingReq.saveResult req) {


        return enrollingWebFacade.saveResult(req);
    }



    /**
     * 机构预招商 抵押物招商结果信息
     *
     */
    @GetMapping("/agency/enrolling_activity/result_info")
    public ResponseModel resultInfo(EnrollingReq.activityIdTypeReq req) {

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();

        if(baseInfo==null){
            return ResponseModel.fail("请先登录！");
        }
        req.setPartyId(String.valueOf(baseInfo.getAgencyId()));
        req.setSource(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType());
        return enrollingWebFacade.resultInfo(req);
    }


    /**
     * 个人中心-抵押物预招商支付佣金
     */
    @GetMapping("/agency/enrolling_activity/pay_commission")
    public ResponseModel resultInfo(EnrollingReq.payCommission req) {

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();

        AgencyResp agencyResp = accountFacade.getAgencyById(baseInfo.getAgencyId());

        if(agencyResp.getPayBind()!=1){
            return ResponseModel.fail(ApiCallResult.NO_DFFT_CAN_NOT_UPLOAD_ERROR.getCode(),ApiCallResult.NO_DFFT_CAN_NOT_UPLOAD_ERROR.getDesc());

        }
        req.setPartyId(String.valueOf(baseInfo.getAgencyId()));
        req.setMemCode(agencyResp.getDfftId());
        req.setName(agencyResp.getName());
        req.setSource(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType());
        return enrollingWebFacade.payCommission(req);
    }


    /**
     * 个人中心-签署模板
     */
    @GetMapping("/agency/enrolling_activity/sign_contract")
    public ResponseModel signContract(EnrollingReq.activityIdTypeReq req) {

        return enrollingWebFacade.signContract(req);
    }



    /**
     * 微信支付接口
     */
    @PostMapping("/agency/enrolling_activity/wx_pay")
    public ResponseModel enrollingWxPay(@RequestBody EnrollingReq.activityIdTypeReq req ) {

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();

        req.setPartyId(String.valueOf(baseInfo.getAgencyId()));

        return  enrollingWebFacade.wxPay(req);
    }


    /**
     * 预招商修改
     */
    @PostMapping("/agency/enrolling_activity/update_activity")
    public ResponseModel updateActivity(@RequestBody EnrollingReq.saveActivityReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        if(accountBaseInfo==null){
            return ResponseModel.fail(ApiCallResult.NOT_LOGIN.getDesc());
        }

        if(StringUtils.isBlank(req.getActivityId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setPartyId(accountBaseInfo.getAgencyId());
        req.setThirdPartyStatus(EnrollingEnum.ENROLLING_THIRD_PARTY_STATUS.AGENCY.getType());
        return enrollingWebFacade.updateActivity(req);
    }


    /**
     * 查询预招商发布订单的支付状态
     */
    @GetMapping("/agency/enrolling_activity/get_order_status")
    public ResponseModel getOrderStatus(EnrollingReq.submitOrder req) {


        return enrollingWebFacade.getSubmitOrderStatus(req);
    }



    /**
     * 连拍机构修改获取活动信息
     */
    @GetMapping("/agency/enrolling_activity/get_enrolling_detail")
    public ResponseModel getEnrollingDetail(EnrollingReq.activityIdReq req) {


        if(StringUtils.isBlank(req.getActivityId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return enrollingWebFacade.getEnrollingDetail(req);
    }
}
