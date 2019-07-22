package com._360pai.core.service.assistant;


import com._360pai.core.model.assistant.PartnerAgency;
import com.github.pagehelper.PageInfo;

public interface PartnerAgencyService {


    PageInfo selectPartnerAgenciesList(int page, int perPage, String orderNum);

    int addPartnerAgency(PartnerAgency params);

    int editPartnerAgency(PartnerAgency params);

    int deletePartnerAgency(PartnerAgency params);
}