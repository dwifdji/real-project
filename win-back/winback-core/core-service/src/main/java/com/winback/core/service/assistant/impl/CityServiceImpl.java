package com.winback.core.service.assistant.impl;

import com.winback.core.condition.assistant.TComAreaCondition;
import com.winback.core.condition.assistant.TComCityCondition;
import com.winback.core.condition.assistant.TComProvinceCondition;
import com.winback.core.dao.assistant.TComAreaDao;
import com.winback.core.dao.assistant.TComCityDao;
import com.winback.core.dao.assistant.TComProvinceDao;
import com.winback.core.model.assistant.TComArea;
import com.winback.core.model.assistant.TComCity;
import com.winback.core.model.assistant.TComProvince;
import com.winback.core.service.assistant.CityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 城市相关
 */
@Slf4j
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private TComCityDao cityDao;
    @Autowired
    private TComProvinceDao provinceDao;
    @Autowired
    private TComAreaDao areaDao;

    @Override
    public List<TComProvince> getAllProvinces() {
        TComProvinceCondition condition = new TComProvinceCondition();
        condition.setDeleteFlag(false);
        return provinceDao.selectList(condition);
    }

    @Override
    public List<TComCity> getCitiesByProvinceCode(String provinceCode) {
        TComCityCondition condition = new TComCityCondition();
        condition.setProvinceCode(provinceCode);
        condition.setDeleteFlag(false);
        return cityDao.selectList(condition);
    }

    @Override
    public List<TComArea> getAreasByCityCode(String cityCode) {
        TComAreaCondition condition = new TComAreaCondition();
        condition.setCityCode(cityCode);
        condition.setDeleteFlag(false);
        return areaDao.selectList(condition);
    }

    @Override
    public String getProvinceName(String provinceCode) {
        return provinceDao.getName(provinceCode);
    }

    @Override
    public String getCityName(String cityCode) {
        if (StringUtils.isEmpty(cityCode)) {
            return "";
        }
        TComCityCondition condition = new TComCityCondition();
        condition.setCode(cityCode);
        condition.setDeleteFlag(false);
        TComCity city = cityDao.selectFirst(condition);
        if (city != null) {
            return city.getName();
        }
        return "";
    }

    @Override
    public String getAreaName(String areaCode) {
        if (StringUtils.isEmpty(areaCode)) {
            return "";
        }
        TComAreaCondition condition = new TComAreaCondition();
        condition.setCode(areaCode);
        condition.setDeleteFlag(false);
        TComArea area = areaDao.selectFirst(condition);
        if (area != null) {
            return area.getName();
        }
        return "";
    }

    @Override
    public String getFullName(String cityCode, String areaCode) {
        String fullName = "";
        TComCity city = cityDao.findBy(cityCode);
        if (city != null) {
            String provinceName = provinceDao.getName(city.getProvinceCode());
            String areaName = areaDao.getName(areaCode);
            if(!StringUtils.isEmpty(areaName)){
                fullName = provinceName + "/" + city.getName() + "/" + areaName;
            }else{
                fullName = provinceName + "/" + city.getName();
            }
        }
        return fullName;
    }

    @Override
    public TComProvince getProvince(String provinceCode) {
        TComProvinceCondition condition = new TComProvinceCondition();
        condition.setDeleteFlag(false);
        condition.setCode(provinceCode);
        List<TComProvince> list = provinceDao.selectList(condition);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public TComCity getCity(String cityCode) {
        TComCityCondition condition = new TComCityCondition();
        condition.setDeleteFlag(false);
        condition.setCode(cityCode);
        List<TComCity> list = cityDao.selectList(condition);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
