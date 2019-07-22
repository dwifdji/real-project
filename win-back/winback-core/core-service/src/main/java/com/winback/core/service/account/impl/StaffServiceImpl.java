package com.winback.core.service.account.impl;

import com.winback.core.dao.account.TSysStaffDao;
import com.winback.core.model.account.TSysStaff;
import com.winback.core.service.account.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xdrodger
 * @Title: StaffServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 14:00
 */
@Slf4j
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private TSysStaffDao staffDao;

    @Override
    public TSysStaff findByMobile(String mobile) {
        return staffDao.findByMobile(mobile);
    }

    @Override
    public TSysStaff findById(Integer id) {
        return staffDao.selectById(id);
    }
}
