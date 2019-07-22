package com._360pai.core.service.lease;


import com._360pai.arch.common.ResponseModel;
import com._360pai.core.condition.lease.TLeaseApplyCondition;
import com._360pai.core.facade.lease.req.LeaseReq;
import com._360pai.core.model.lease.TLeaseApply;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 租赁权拍卖 员工
 */
public interface LeaseAuctionService {


    /**
     *
     *获取参拍审核列表
     */
    PageInfo myLeaseAuditList(LeaseReq.leaseAsset req);


    /**
     *
     *获取参拍审核列表
     */
    PageInfo competeApplyList(LeaseReq.leaseAsset req);


    /**
     *
     *更新 参拍审核信息
     */
    void updateLeaseApply(TLeaseApply apply);


    /**
     *
     *天剑 参拍审核信息
     */
    void saveLeaseApply(TLeaseApply apply);


    /**
     *
     *天剑 参拍审核信息
     */
    TLeaseApply getLeaseApply(TLeaseApplyCondition condition);



    /**
     *
     *获取领导审核类表
     */
    PageInfo getLeadAuditList(LeaseReq.leaseAsset req);


    /**
     *
     *领导审核标的信息
     */
    ResponseModel leadAudit(LeaseReq.leaseAsset req);



    List<TLeaseApply> getLeaseApplyList(TLeaseApplyCondition condition);


    ResponseModel leaseRelease(LeaseReq.leaseAsset req);



    /**
     *
     *获取审核记录
     */
    PageInfo getAuditRecordList(LeaseReq.leaseAsset req);


    PageInfo myApplyLeaseRecord(LeaseReq.leaseAsset req);
}
