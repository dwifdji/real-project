package com._360pai.core.service.account;


import com._360pai.core.condition.account.TUserApplyRecordCondition;
import com._360pai.core.model.account.TUserApplyRecord;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserVerifyApplicationService{

    int saveUserApplyRecord(TUserApplyRecord userApplyRecord);

    int updateUserApplyRecord(TUserApplyRecord userApplyRecord);

    public PageInfo<TUserApplyRecord> getUserApplyRecord(TUserApplyRecordCondition userApplyRecord, int page, int perPage, String orderStr);

    TUserApplyRecord  getUserApplyRecordById(Long id);

    List<TUserApplyRecord> getApplyRecordByAccountId(Integer accountId,String status);
}