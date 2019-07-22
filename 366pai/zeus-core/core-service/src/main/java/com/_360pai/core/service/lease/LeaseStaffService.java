package com._360pai.core.service.lease;

import com._360pai.core.condition.lease.TLeaseStaffCondition;
import com._360pai.core.facade.lease.req.LeaseReq;
import com._360pai.core.model.lease.TLeaseStaff;
import com.github.pagehelper.PageInfo;

import java.util.List;


/**
 * 租赁权拍卖 员工
 */
public interface LeaseStaffService {

    Integer insert(TLeaseStaff leaseStaff);

    Integer update(TLeaseStaff leaseStaff);

    TLeaseStaff getLeaseStaffById(Integer id);


    PageInfo getLeaseStaffList(LeaseReq.LeaseStaffInfo req);



    TLeaseStaff getLeaseStaffByCondition(TLeaseStaffCondition condition);


    List<TLeaseStaff> getLeaseStaffList(TLeaseStaffCondition condition);

}
