package com._360pai.web.controller.lease;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.lease.LeaseAuctionFacade;
import com._360pai.core.facade.lease.LeaseFacade;
import com._360pai.core.facade.lease.req.LeaseReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *租赁权拍卖
 */
@RestController
public class LeaseAuctionController extends AbstractController {
	

    @Reference(version = "1.0.0")
    private LeaseAuctionFacade leaseAuctionFacade;



    /**
     * 获取经办人审核列表
     */
    @GetMapping(value = "/confined/myLeaseAuditList")
    public ResponseModel myLeaseAuditList(LeaseReq.leaseAsset req) {

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        req.setPartyId(baseInfo.getPartyPrimaryId());
    	return leaseAuctionFacade.myLeaseAuditList(req);
    }






    /**
     * 参拍资质审核列表
     */
    @GetMapping(value = "/confined/competeApplyList")
    public ResponseModel competeApplyList(LeaseReq.leaseAsset req) {

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return leaseAuctionFacade.competeApplyList(req);
    }




    /**
     * 参拍资质审核
     */
    @PostMapping(value = "/confined/competeApply")
    public ResponseModel competeApply(@RequestBody  LeaseReq.leaseAsset req) {

        if(req.getId()==null||StringUtils.isEmpty(req.getType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return leaseAuctionFacade.competeApply(req);
    }




    /**
     * 获取领导审核列表
     */
    @GetMapping(value = "/confined/getLeadAuditList")
    public ResponseModel getLeadAuditList(LeaseReq.leaseAsset req) {

        if(StringUtils.isEmpty(req.getType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();

        req.setAccountId(baseInfo.getAccountId());
        return leaseAuctionFacade.getLeadAuditList(req);
    }




    /**
     * 领导审核活动
     */
    @PostMapping(value = "/confined/leadAudit")
    public ResponseModel leadAudit(@RequestBody  LeaseReq.leaseAsset req) {
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();

        if(StringUtils.isEmpty(req.getType())||StringUtils.isEmpty(req.getAssetId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setAccountId(baseInfo.getAccountId());
        return ResponseModel.succ(leaseAuctionFacade.leadAudit(req));
    }




    /**
     * 报名资质审核
     */
    @PostMapping(value = "/confined/lease/applyActivity")
    public ResponseModel applyActivity(@RequestBody  LeaseReq.leaseAsset req) {

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();


        if(baseInfo.getPartyPrimaryId()==null){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }

        req.setPartyId(baseInfo.getPartyPrimaryId());


        return leaseAuctionFacade.applyActivity(req);
    }




    /**
     * 发布接口
     */
    @PostMapping(value = "/confined/leaseRelease")
    public ResponseModel leaseRelease(@RequestBody  LeaseReq.leaseAsset req) {

        if(StringUtils.isEmpty(req.getAssetId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();


        if(baseInfo.getPartyPrimaryId()==null){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }

        req.setPartyId(baseInfo.getPartyPrimaryId());


        return ResponseModel.succ(leaseAuctionFacade.leaseRelease(req));
    }




    /**
     * 标的审核记录
     */
    @GetMapping(value = "/confined/auditRecordList")
    public ResponseModel auditRecordList(LeaseReq.leaseAsset req) {

        if(StringUtils.isEmpty(req.getAssetId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }


        return leaseAuctionFacade.auditRecordList(req);
    }



    /**
     * 我的报名记录
     */
    @GetMapping(value = "/confined/myApplyRecordList")
    public ResponseModel myApplyLeaseRecord(LeaseReq.leaseAsset req) {

        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(baseInfo.getPartyPrimaryId() == null){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }

        req.setPartyId(baseInfo.getPartyPrimaryId());

        return leaseAuctionFacade.myApplyLeaseRecord(req);
    }



    /**
     * 租赁权状态判断
     */
    @GetMapping(value = "/confined/getLeadFlag")
    public ResponseModel getLeadFlag(LeaseReq.leaseAsset req) {


        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();


        req.setPartyId(baseInfo.getPartyPrimaryId());

        req.setAccountId(baseInfo.getAccountId());
        return leaseAuctionFacade.getLeadFlag(req);
    }
}
