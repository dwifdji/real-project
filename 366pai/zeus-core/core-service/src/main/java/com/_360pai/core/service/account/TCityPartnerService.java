package com._360pai.core.service.account;

import com._360pai.core.facade.account.req.CityPartnerReq;
import com._360pai.core.model.account.TCityPartner;

public interface TCityPartnerService {


    Integer saveCityPartner(CityPartnerReq.SaveCityPanrnerReq saveCityPanrnerReq);

    TCityPartner getCityPantnerByPhone(String contactPhone);
}
