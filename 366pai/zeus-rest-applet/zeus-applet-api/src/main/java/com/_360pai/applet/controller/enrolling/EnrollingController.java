package com._360pai.applet.controller.enrolling;

import com._360pai.applet.controller.AbstractController;
import com._360pai.applet.controller.account.resp.AccountBaseInfo;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.facade.enrolling.EnrollingAppletFacade;
import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.EnrollingRealImportFacade;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.shop.req.ShopEnrollingReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class EnrollingController extends AbstractController {


    @Reference(version = "1.0.0")
    private EnrollingAppletFacade enrollingAppletFacade;


    @Reference(version = "1.0.0")
    private EnrollingImportFacade enrollingImportFacade;

    @Reference(version = "1.0.0")
    private EnrollingWebFacade enrollingWebFacade;



    @Reference(version = "1.0.0")
    private EnrollingRealImportFacade enrollingRealImportFacade;


    /**
     * 获取招商详情信息
     * @param req
     * @return
     */
    @GetMapping(value = "/open/shop/getEnrollingDetail")
    public ResponseModel getEnrollingDetail(ShopEnrollingReq.comReq req) {

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getCurrentPartyId());

        return enrollingAppletFacade.getEnrollingDetail(req);
    }




    /**
     * 获取报名信息
     * @param req
     * @return
     */
    @GetMapping(value = "/confined/shop/getEnrollingApplyList")
    public ResponseModel getEnrollingApplyList(ShopEnrollingReq.comReq req) {


        return enrollingAppletFacade.getEnrollingApplyList(req);
    }




    /**
     * 获取长城资产信息
     * @param req
     * @return
     */
    @GetMapping(value = "/confined/getUploadActivityDetails")
    public ResponseModel getUploadActivityDetails(ShopEnrollingReq.comReq req) {

        EnrollingImportReq.getUploadActivityDetailsReq importReq = new EnrollingImportReq.getUploadActivityDetailsReq();
        importReq.setActivityId(req.getActivityId());
        importReq.setType("web");

        return enrollingImportFacade.getUploadActivityDetails(importReq);
    }




    /**
     * 获取招商公告信息
     * @param req
     * @return
     */
    @GetMapping(value = "/open/shop/getEnrollingAnnouncement")
    public ResponseModel getEnrollingAnnouncement(ShopEnrollingReq.comReq req) {

        return enrollingAppletFacade.getEnrollingAnnouncement(req);
    }





    /**
     * 预招商提醒
     * @param req
     * @return
     */
    @PostMapping(value = "/confined/shop/enrollingNotifyOrCancel")
    public ResponseModel enrollingNotifyOrCancel(@RequestBody ShopEnrollingReq.comReq req) {

        //校验参数
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(baseInfo==null){
            return ResponseModel.fail(ApiCallResult.NOT_LOGIN.getDesc());
        }

        Integer partyId = baseInfo.getCurrentPartyId();
        Integer accountId = baseInfo.getAccountId();

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        EnrollingReq.activityIdTypeReq activityIdTypeReq = new EnrollingReq.activityIdTypeReq();
        if (partyId != null){
            activityIdTypeReq.setPartyId(String.valueOf(partyId));

        }
        activityIdTypeReq.setAccountId(String.valueOf(accountId));
        activityIdTypeReq.setMobile(baseInfo.getMobile());
        activityIdTypeReq.setActivityId(req.getActivityId());
        activityIdTypeReq.setPathType((short)1);
        if("1".equals(req.getType())){

            return enrollingWebFacade.activityRemind(activityIdTypeReq);
        }

        //取消提醒
        return enrollingWebFacade.cancelMyRemind(activityIdTypeReq);

    }





    /**
     * 关注预招商
     * @param req
     * @return
     */
    @PostMapping(value = "/confined/shop/enrollingFavorAuction")
    public ResponseModel enrollingFavorAuction(@RequestBody ShopEnrollingReq.comReq req) {

        //校验参数
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(baseInfo==null){
            return ResponseModel.fail(ApiCallResult.NOT_LOGIN.getDesc());
        }

        if(req.getShopId()==null){
            return ResponseModel.fail(ApiCallResult.EMPTY);

        }

        Integer partyId = baseInfo.getCurrentPartyId();
        EnrollingReq.activityIdTypeReq activityIdTypeReq = new  EnrollingReq.activityIdTypeReq();
        if(StringUtils.isBlank(String.valueOf(req.getActivityId()))){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        activityIdTypeReq.setAccountId(baseInfo.getAccountId().toString());
        activityIdTypeReq.setActivityId(req.getActivityId());
        if (partyId != null){
            activityIdTypeReq.setPartyId(String.valueOf(partyId));

        }

        activityIdTypeReq.setFoucsType(EnrollingEnum.FOCUS_TYPE.APPLET.getType());
        activityIdTypeReq.setResourceId(Integer.valueOf(req.getShopId()));

        if("1".equals(req.getType())){
            return enrollingWebFacade.activityFocus(activityIdTypeReq);

        }

        //取消关注
        return enrollingWebFacade.cancelMyFocus(activityIdTypeReq);


    }




    /**
     * 预招商报名
     * @param req
     * @return
     */
    @PostMapping(value = "/confined/shop/enrollingApply")
    public ResponseModel enrollingApply(@RequestBody ShopEnrollingReq.comReq req) {


        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(baseInfo==null){
            return ResponseModel.fail(ApiCallResult.NOT_LOGIN.getDesc());
        }

        Integer partyId = baseInfo.getCurrentPartyId();
         if(partyId == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        if(StringUtils.isBlank(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        EnrollingReq.activityIdTypeReq activityIdTypeReq = new EnrollingReq.activityIdTypeReq();

        activityIdTypeReq.setActivityId(req.getActivityId());
        activityIdTypeReq.setPartyId(String.valueOf(partyId));
        return enrollingWebFacade.activityApply(activityIdTypeReq);


    }




    /**
     * 预招商关注
     * @param req
     * @return
     */
    @GetMapping(value = "/confined/shop/myFocusEnrollingActivity")
    public ResponseModel myFocusEnrollingActivity(ShopEnrollingReq.comReq req) {

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        EnrollingReq.activitiesListReq activitiesListReq =new EnrollingReq.activitiesListReq();
        if (accountBaseInfo.getCurrentPartyId() != null){
            activitiesListReq.setPartyId(accountBaseInfo.getCurrentPartyId().toString());
        }
        if(accountBaseInfo.getAccountId()==null){
            PageInfoResp page = new PageInfoResp();
            page.setTotal(0);
            page.setHasNextPage(false);
            page.setList(new ArrayList());
            return ResponseModel.succ(page);
        }
        activitiesListReq.setAccountId(accountBaseInfo.getAccountId().toString());
        activitiesListReq.setPage(req.getPage());
        activitiesListReq.setPerPage(req.getPerPage());
        activitiesListReq.setFocusType(EnrollingEnum.FOCUS_TYPE.APPLET.getType());


        return enrollingWebFacade.focusList(activitiesListReq);



    }




    /**
     * 判断预招商是否有关注过
     * @param req
     * @return
     */
    @GetMapping(value = "/confined/shop/getEnrollingConcernedFlag")
    public ResponseModel getEnrollingConcernedFlag(ShopEnrollingReq.comReq req) {

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        if(accountBaseInfo.getShopId()==null){
            return ResponseModel.fail("未开通店铺！");
        }

        req.setShopId(String.valueOf(accountBaseInfo.getShopId()));


        return enrollingAppletFacade.getEnrollingConcernedFlag(req);

    }




    /**
     * 获取标的信息
     * @param req
     * @return
     */
    @GetMapping(value = "/open/shop/getMatterInfo")
    public ResponseModel getMatterInfo(ShopEnrollingReq.comReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        EnrollingReq.activityIdTypeReq activityIdTypeReq = new EnrollingReq.activityIdTypeReq();

        //认证通过
        activityIdTypeReq.setSeeStatus("authorization");
        activityIdTypeReq.setActivityId(req.getActivityId());
        return enrollingWebFacade.getMatterInfo(activityIdTypeReq);

    }



    /**
     * 获取物权招商导入详情
     */

    @GetMapping(value = "/confined/enrollingActivity/getRealUploadActivityDetails")
    public ResponseModel getRealUploadActivityDetails(EnrollingImportReq.getUploadActivityDetailsReq req) {
        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setType("web");
        return enrollingRealImportFacade.getRealUploadActivityDetails(req);
    }
}
