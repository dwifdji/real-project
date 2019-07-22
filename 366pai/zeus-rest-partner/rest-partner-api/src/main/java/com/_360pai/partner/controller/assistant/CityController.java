package com._360pai.partner.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.CityFacade;
import com._360pai.core.facade.assistant.CommonFacade;
import com._360pai.core.facade.assistant.req.CityReq;
import com._360pai.core.facade.assistant.resp.CityResp;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: CityController
 * @ProjectName zeus-rest-web
 * @Description:
 * @date 2018/8/29 15:57
 */
@RestController
public class CityController {

    @Reference(version = "1.0.0")
    private CityFacade cityFacde;

    @Reference(version = "1.0.0")
    private CommonFacade commonFacade;
    @GetMapping(value = "/agency/pageCities")
    public ResponseModel getPageCities(CityReq req) {
        Object object = cityFacde.pageCities();
        return ResponseModel.succ(object);
    }


    @GetMapping(value = "/agency/getAllCities")
    public ResponseModel getAllCities(CityReq req) {
        return ResponseModel.succ(cityFacde.getAllCities());
    }

    @GetMapping(value = "/agency/getAllProvinces")
    public ResponseModel getAllProvinces() {
        CityResp resp = cityFacde.getAllProvinces();
        return ResponseModel.succ(resp.getProvinces());
    }

    @GetMapping(value = "/agency/getCitiesByProvince")
    public ResponseModel getCitiesByProvince(CityReq req) {
        CityResp resp = cityFacde.getCitiesByProvinceId(req);
        return ResponseModel.succ(resp.getCities());
    }

    @GetMapping(value = "/agency/getAreasByCity")
    public ResponseModel getAreasByCity(CityReq req) {
        CityResp resp = cityFacde.getAreasByCityId(req);
        return ResponseModel.succ(resp.getAreas());
    }


    /**
     * 所有城市订单
     */
    @GetMapping(value = "/agency/all_cities")
    public ResponseModel getAllCities() {
        return commonFacade.getAllCities();
    }

}
