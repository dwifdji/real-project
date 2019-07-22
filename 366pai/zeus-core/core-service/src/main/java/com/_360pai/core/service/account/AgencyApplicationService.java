package com._360pai.core.service.account;


import com._360pai.core.model.account.TAgencyApplyRecord;

public interface AgencyApplicationService{

    public  boolean saveAgencyApplyRecord(TAgencyApplyRecord agencyApplyRecord);

    public  boolean updateAgencyApplyRecord(TAgencyApplyRecord agencyApplyRecord);

    TAgencyApplyRecord getAgencyApplyById(Long id);

}