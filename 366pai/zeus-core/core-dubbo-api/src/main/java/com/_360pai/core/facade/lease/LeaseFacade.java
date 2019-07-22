package com._360pai.core.facade.lease;


import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.resp.LeaseStaffResp;
import com._360pai.core.facade.lease.req.LeaseReq;

import java.util.List;

/**
 * 租赁权拍卖
 *
 */
public interface LeaseFacade {


    ResponseModel saveLeaseStaff(LeaseReq.LeaseStaff req);

    ResponseModel getLeaseStaffInfo(LeaseReq.LeaseStaffInfo req);

    ResponseModel getLeaseStaffList(LeaseReq.LeaseStaffInfo req);

    ResponseModel updateAuditInfo(LeaseReq.LeaseStaffInfo req);


    ResponseModel updateLeaseStaff(LeaseReq.LeaseStaff req);


    List<LeaseStaffResp> getLeaseStaffInfoByAccountId(Integer accountId);


    ResponseModel myLeaseAuditList(LeaseReq.leaseAsset req);


}
