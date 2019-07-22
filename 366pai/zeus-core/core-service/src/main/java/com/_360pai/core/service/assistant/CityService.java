package com._360pai.core.service.assistant;


import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.account.TUser;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.assistant.Area;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.assistant.Town;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.vo.assistant.ProvinceCityVo;
import com._360pai.core.vo.enrolling.web.EnrollingCityVO;
import com._360pai.core.vo.enrolling.web.EnrollingDetailInfoVo;

import java.util.List;

public interface CityService{


    City getByCityId(Integer cityId);

    /**
     *
     *获取所以的城市列表
     */
    List<City> getAllCity();

    /**
     *
     *获取所有区县列表
     */
    List<Area> getAllArea();

    Object pageCities();

    Object pageProvinces();

    /**
     * 获取所有城市
     * @return
     */
	List<EnrollingCityVO> getCityList();

    List<City> getCitiesByProvinceId(Integer provinceId);

    List<Area> getAreaByCityId(Integer cityId);


    /**
     * 获取省份 城市信息
     * @return
     */
    List<ProvinceCityVo> getProvinceCityList(List<String> ids);



    /**
     * 获取城市 以及省份名称
     * @return
     */
    List<ProvinceCityVo> getAllProvinceCityList();

    String getCityName(Integer id);

    String getProvinceName(Integer id);

    String getAreaName(Integer id);

    Integer getProvinceId(Integer id);

    String getCityName(TDisposalRequirement detail);

    String getCityName(Asset detail);

    String getCityName(EnrollingDetailInfoVo detail);

    String getCityName(TDisposeProvider detail);

    String getCityName(TCompany detail);

    String getCityName(TUser detail);

    List<Town> getTownsByAreaId(Integer areaId);
}