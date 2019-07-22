package com._360pai.core.service.account;

import com._360pai.core.model.account.TCompanyApplyRecord;

import java.util.List;


public interface CompanyVerifyApplicationService{

    boolean saveCompanyApplyRecord(TCompanyApplyRecord companyApplyRecord);
    boolean updateCompanyApplyRecord(TCompanyApplyRecord companyApplyRecord);
    TCompanyApplyRecord findCompanyApplyRecordById(Long id);
    List<TCompanyApplyRecord> getApplyRecordByAccountId(Integer accountId,String status);
}