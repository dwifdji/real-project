package com._360pai.core.service.lease.impl;


import com._360pai.core.condition.lease.TLeaseStaffCondition;
import com._360pai.core.dao.lease.TLeaseStaffDao;
import com._360pai.core.facade.lease.req.LeaseReq;
import com._360pai.core.model.lease.TLeaseStaff;
import com._360pai.core.service.lease.LeaseStaffService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 描述
 *

 */
@Service
public class LeaseStaffServiceImpl implements LeaseStaffService {
    @Autowired
    private TLeaseStaffDao leaseStaffDao;

    @Override
    public Integer insert(TLeaseStaff leaseStaff) {
        return leaseStaffDao.insert(leaseStaff);
    }

    @Override
    public Integer update(TLeaseStaff leaseStaff) {
        return leaseStaffDao.updateById(leaseStaff);
    }


    @Override
    public TLeaseStaff getLeaseStaffById(Integer id) {
        return leaseStaffDao.selectById(id);
    }


    @Override
    public PageInfo getLeaseStaffList(LeaseReq.LeaseStaffInfo req) {

        PageHelper.startPage(req.getPage(),req.getPerPage());
        TLeaseStaffCondition condition = new TLeaseStaffCondition();
        condition.setComId(Integer.valueOf(req.getComId()));
        condition.setIsDelete(false);
        List<TLeaseStaff> list = leaseStaffDao.selectList(condition);

        return new PageInfo<>(list);
    }

    @Override
    public TLeaseStaff getLeaseStaffByCondition(TLeaseStaffCondition condition) {
        return leaseStaffDao.selectFirst(condition);
    }


    @Override
    public List<TLeaseStaff> getLeaseStaffList(TLeaseStaffCondition condition) {
        return leaseStaffDao.selectList(condition);
    }
}
