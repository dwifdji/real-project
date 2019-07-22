package com._360pai.web.controller.account;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.CityPartnerFacade;
import com._360pai.core.facade.account.req.CityPartnerReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by liuhaolei on 2018/11/1
 */

@RestController
@RequestMapping(value = "/open/account")
public class CityPartnerController {

    @Reference(version = "1.0.0")
    private CityPartnerFacade cityPartnerFacade;

    @PostMapping("/city_partner/saveCityPartner")
    public ResponseModel savaCityPartner(@RequestBody CityPartnerReq.SaveCityPanrnerReq saveCityPanrnerReq) {

        //后续参数校验
        return cityPartnerFacade.saveCityPartner(saveCityPanrnerReq);
    }
}
