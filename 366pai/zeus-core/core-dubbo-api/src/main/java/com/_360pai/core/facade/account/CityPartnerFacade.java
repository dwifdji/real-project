package com._360pai.core.facade.account;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.CityPartnerReq;

public interface CityPartnerFacade {

    ResponseModel saveCityPartner(CityPartnerReq.SaveCityPanrnerReq saveCityPanrnerReq);
}
