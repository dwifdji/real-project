package com._360pai.core.facade.lease;


import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.resp.LeaseStaffResp;
import com._360pai.core.facade.lease.req.LeaseReq;

/**
 * 租赁权拍卖
 *
 */
public interface LeaseAuctionFacade {



    ResponseModel myLeaseAuditList(LeaseReq.leaseAsset req);



    ResponseModel competeApplyList(LeaseReq.leaseAsset req);


    ResponseModel competeApply(LeaseReq.leaseAsset req);


    ResponseModel getLeadAuditList(LeaseReq.leaseAsset req);


    ResponseModel leadAudit(LeaseReq.leaseAsset req);


    ResponseModel applyActivity(LeaseReq.leaseAsset req);



    ResponseModel leaseRelease(LeaseReq.leaseAsset req);


    ResponseModel auditRecordList(LeaseReq.leaseAsset req);


    ResponseModel myApplyLeaseRecord(LeaseReq.leaseAsset req);


    ResponseModel getLeadFlag(LeaseReq.leaseAsset req);
}
