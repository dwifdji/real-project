package com._360pai.core.facade.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.req.CityReq;
import com._360pai.core.facade.assistant.resp.CityListResp;
import com._360pai.core.facade.assistant.resp.CityResp;

public interface CityFacade {

    Object pageCities();

    Object pageProvinces();

    ResponseModel getAllAreas();

    CityListResp getAllCities();

    CityResp getAllProvinces();

    CityResp getCitiesByProvinceId(CityReq req);

    CityResp getAreasByCityId(CityReq req);

    CityResp getTownsByAreaId(CityReq req);
}
