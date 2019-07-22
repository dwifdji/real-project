package com._360pai.core.provider.account;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.CityPartnerFacade;
import com._360pai.core.facade.account.req.CityPartnerReq;
import com._360pai.core.model.account.TCityPartner;
import com._360pai.core.service.account.TCityPartnerService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * create by liuhaolei on 2018/11/1
 */
@Component
@Service(version = "1.0.0")
public class CityPartnerProvider implements CityPartnerFacade {

    @Autowired
    private TCityPartnerService tCityPartnerService;

    @Override
    public ResponseModel saveCityPartner(CityPartnerReq.SaveCityPanrnerReq saveCityPanrnerReq) {

        if(StringUtils.isNotBlank(saveCityPanrnerReq.getContactPhone())) {
          TCityPartner tCityPartner = tCityPartnerService.getCityPantnerByPhone(saveCityPanrnerReq.getContactPhone());
          if(tCityPartner != null) {
            return ResponseModel.fail("您已经申请过合作伙伴");
          }
        }

        Integer insertCount = tCityPartnerService.saveCityPartner(saveCityPanrnerReq);
        if(insertCount == 1) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }
}
