package com.winback.core.service.account;

import com.winback.core.model.account.TSysStaff;

/**
 * @author xdrodger
 * @Title: StaffService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 14:00
 */
public interface StaffService {
    TSysStaff findByMobile(String mobile);

    TSysStaff findById(Integer id);
}
