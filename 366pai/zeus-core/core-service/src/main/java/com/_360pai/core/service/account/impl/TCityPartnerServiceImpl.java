package com._360pai.core.service.account.impl;

import com._360pai.core.condition.account.TCityPartnerCondition;
import com._360pai.core.dao.account.TCityPartnerDao;
import com._360pai.core.facade.account.req.CityPartnerReq;
import com._360pai.core.model.account.TCityPartner;
import com._360pai.core.service.account.TCityPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TCityPartnerServiceImpl implements TCityPartnerService {

    @Autowired
    private TCityPartnerDao tCityPartnerDao;

    @Override
    public Integer saveCityPartner(CityPartnerReq.SaveCityPanrnerReq saveCityPanrnerReq) {
        TCityPartner cityPartner = new TCityPartner();

        cityPartner.setContactName(saveCityPanrnerReq.getContactName());
        cityPartner.setContactPhone(saveCityPanrnerReq.getContactPhone());
        cityPartner.setCityId(Integer.parseInt(saveCityPanrnerReq.getCityId()));
        cityPartner.setOfficeSize(saveCityPanrnerReq.getOfficeSize());
        cityPartner.setPosition(saveCityPanrnerReq.getPosition());
        cityPartner.setCreatedAt(new Date());

        int insertCount = tCityPartnerDao.insert(cityPartner);
        return insertCount;
    }

    @Override
    public TCityPartner getCityPantnerByPhone(String contactPhone) {

        TCityPartnerCondition tCityPartnerCondition = new TCityPartnerCondition();
        tCityPartnerCondition.setContactPhone(contactPhone);
        TCityPartner tCityPartner = tCityPartnerDao.selectFirst(tCityPartnerCondition);
        return tCityPartner;
    }
}
