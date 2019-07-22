package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.PartnerAgencyReq;
import com.github.pagehelper.PageInfo;

public interface PartnerAgenciesFacade {

    PageInfo selectPartnerAgenciesList(PartnerAgencyReq req);

    int addPartnerAgency(PartnerAgencyReq req);

    int editPartnerAgency(PartnerAgencyReq req);

    int deletePartnerAgency(PartnerAgencyReq req);
}
