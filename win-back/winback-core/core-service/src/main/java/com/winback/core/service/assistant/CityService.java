package com.winback.core.service.assistant;

import com.winback.core.model.assistant.TComArea;
import com.winback.core.model.assistant.TComCity;
import com.winback.core.model.assistant.TComProvince;

import java.util.List;

/**
 * 城市相关
 */
public interface CityService {

    List<TComProvince> getAllProvinces();

    List<TComCity> getCitiesByProvinceCode(String provinceCode);

    List<TComArea> getAreasByCityCode(String cityCode);

    String getProvinceName(String provinceCode);

    String getCityName(String cityCode);

    String getAreaName(String areaCode);

    String getFullName(String cityCode, String areaCode);

    TComProvince getProvince(String provinceCode);

    TComCity getCity(String cityCode);
}
