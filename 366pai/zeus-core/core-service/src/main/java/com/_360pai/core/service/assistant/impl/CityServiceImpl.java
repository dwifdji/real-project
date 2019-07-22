package com._360pai.core.service.assistant.impl;

import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.assistant.AreaCondition;
import com._360pai.core.condition.assistant.CityCondition;
import com._360pai.core.condition.assistant.TownCondition;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.assistant.TownDao;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.account.TUser;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.assistant.Area;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.model.assistant.Town;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.vo.assistant.ProvinceCityVo;
import com._360pai.core.vo.enrolling.web.EnrollingCityVO;
import com._360pai.core.vo.enrolling.web.EnrollingDetailInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/17 9:51
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private AreaDao areaDao;
    @Autowired
    private TownDao townDao;

    @Resource
    private RedisCachemanager redisCachemanager;

    @Override
    public City getByCityId(Integer cityId) {
        CityCondition condition = new CityCondition();
        condition.setId(cityId);
        return cityDao.selectFirst(condition);
    }

    @Override
    public List<City> getAllCity() {
        CityCondition condition = new CityCondition();
        return cityDao.selectList(condition);
    }

    @Override
    public List<Area> getAllArea() {
        AreaCondition condition = new AreaCondition();
        return areaDao.selectList(condition);
    }

    @Override
    public Object pageCities() {
        return cityDao.pageCities();
    }

    @Override
    public Object pageProvinces() {
        return cityDao.pageProvinces();
    }

    @Override
	public List<EnrollingCityVO> getCityList() {
		return cityDao.getCityList();
	}

    @Override
    public List<City> getCitiesByProvinceId(Integer provinceId) {
        CityCondition cityCondition = new CityCondition();
        cityCondition.setProvinceId(provinceId);
        return cityDao.selectList(cityCondition);
    }

    @Override
    public List<Area> getAreaByCityId(Integer cityId) {
        AreaCondition condition = new AreaCondition();
        condition.setCityId(cityId);
        return areaDao.selectList(condition);
    }


    @Override
    public List<ProvinceCityVo> getProvinceCityList(List<String> ids) {



        List<ProvinceCityVo> list = cityDao.getProvinceCityList(ids);


        return list;


    }


    @Override
    public List<ProvinceCityVo> getAllProvinceCityList() {
        return cityDao.getAllProvinceCityList();
    }

    @Override
    public String getCityName(Integer id) {
        return cityDao.getName(id);
    }

    @Override
    public String getProvinceName(Integer id) {
        return provinceDao.getName(id);
    }

    @Override
    public String getAreaName(Integer id) {
        return areaDao.getName(id);
    }

    @Override
    public Integer getProvinceId(Integer id) {
        return cityDao.getProvinceId(id);
    }

    @Override
    public String getCityName(TDisposalRequirement detail) {
        String cityName = "";
        if (StringUtils.isNotBlank(detail.getAreaId())) {
            if (detail.getAreaId().contains(",")) {
                Area area = areaDao.selectById(detail.getAreaId().split(",")[0]);
                if (area != null) {
                    City city = cityDao.selectById(area.getCityId());
                    if (city != null) {
                        cityName = city.getName() + "等多地";
                    }
                }
            } else {
                Area area = areaDao.selectById(detail.getAreaId().split(",")[0]);
                if (area != null) {
                    cityName = cityDao.getName(area.getCityId());
                }
            }
        } else if (StringUtils.isNotBlank(detail.getCityId())) {
            if (detail.getCityId().contains(",")) {
                City city = cityDao.selectById(detail.getCityId().split(",")[0]);
                if (city != null) {
                    cityName = city.getName() + "等多地";
                }
            } else {
                cityName = cityDao.getName(detail.getCityId().split(",")[0]);
            }
        } else if (StringUtils.isNotBlank(detail.getProvinceId())) {
            if (detail.getProvinceId().contains(",")) {
                Province province = provinceDao.selectById(detail.getProvinceId().split(",")[0]);
                if (province != null) {
                    cityName = province.getName() + "等多地";
                }
            } else {
                Province province = provinceDao.selectById(detail.getProvinceId().split(",")[0]);
                if (province != null) {
                    cityName = province.getName();
                }
            }
        }
        return cityName;
    }

    @Override
    public String getCityName(Asset detail) {
        String cityName = "";
        if (StringUtils.isNotBlank(detail.getAreaId())) {
            if (detail.getAreaId().contains(",")) {
                Area area = areaDao.selectById(detail.getAreaId().split(",")[0]);
                if (area != null) {
                    City city = cityDao.selectById(area.getCityId());
                    if (city != null) {
                        cityName = city.getName() + "等多地";
                    }
                }
            } else {
                Area area = areaDao.selectById(detail.getAreaId().split(",")[0]);
                if (area != null) {
                    City city = cityDao.selectById(area.getCityId());
                    if (city != null) {
                        Province province = provinceDao.selectById(city.getProvinceId());
                        if (province != null) {
                            cityName = province.getName() + city.getName() + area.getName();
                        }
                    }
                }
            }
        } else if (StringUtils.isNotBlank(detail.getCityId())) {
            if (detail.getCityId().contains(",")) {
                City city = cityDao.selectById(detail.getCityId().split(",")[0]);
                if (city != null) {
                    cityName = city.getName() + "等多地";
                }
            } else {
                cityName = cityDao.getName(detail.getCityId().split(",")[0]);
            }
        } else if (StringUtils.isNotBlank(detail.getProvinceId())) {
            if (detail.getProvinceId().contains(",")) {
                Province province = provinceDao.selectById(detail.getProvinceId().split(",")[0]);
                if (province != null) {
                    cityName = province.getName() + "等多地";
                }
            } else {
                Province province = provinceDao.selectById(detail.getProvinceId().split(",")[0]);
                if (province != null) {
                    cityName = province.getName();
                }
            }
        }
        return cityName;
    }

    @Override
    public String getCityName(EnrollingDetailInfoVo detail) {
        String cityName = "";
        if (StringUtils.isNotBlank(detail.getAreaId())) {
            if (detail.getAreaId().contains(",")) {
                Area area = areaDao.selectById(detail.getAreaId().split(",")[0]);
                if (area != null) {
                    City city = cityDao.selectById(area.getCityId());
                    if (city != null) {
                        cityName = city.getName() + "等多地";
                    }
                }
            } else {
                Area area = areaDao.selectById(detail.getAreaId().split(",")[0]);
                if (area != null) {
                    City city = cityDao.selectById(area.getCityId());
                    if (city != null) {
                        Province province = provinceDao.selectById(city.getProvinceId());
                        if (province != null) {
                            cityName = province.getName() + city.getName() + area.getName();
                        }
                    }
                }
            }
        } else if (StringUtils.isNotBlank(detail.getCityId())) {
            if (detail.getCityId().contains(",")) {
                City city = cityDao.selectById(detail.getCityId().split(",")[0]);
                if (city != null) {
                    cityName = city.getName() + "等多地";
                }
            } else {
                City city = cityDao.selectById(detail.getCityId().split(",")[0]);
                if (city != null) {
                    //Province province = provinceDao.selectById(city.getProvinceId());
                    //if (province != null) {
                    //	cityName = province.getName() + city.getName();
                    //}
                    cityName = city.getName();
                }
            }
        } else if (StringUtils.isNotBlank(detail.getProvinceId())) {
            if (detail.getProvinceId().contains(",")) {
                Province province = provinceDao.selectById(detail.getProvinceId().split(",")[0]);
                if (province != null) {
                    cityName = province.getName() + "等多地";
                }
            } else {
                Province province = provinceDao.selectById(detail.getProvinceId().split(",")[0]);
                if (province != null) {
                    cityName = province.getName();
                }
            }
        }
        return cityName;
    }

    @Override
    public String getCityName(TDisposeProvider detail) {
        String cityName = "--";
        if (StringUtils.isNotBlank(detail.getRegionArea())) {
            String areaName = areaDao.getName(detail.getRegionArea());
            cityName = cityDao.getName(detail.getRegion());
            String provinceName = provinceDao.getName(detail.getRegionProvince());
            return provinceName + cityName + areaName;
        } else if (StringUtils.isNotBlank(detail.getRegion())) {
            cityName = cityDao.getName(detail.getRegion());
            String provinceName = provinceDao.getName(detail.getRegionProvince());
            return provinceName + cityName;
        } else if (StringUtils.isNotBlank(detail.getRegionProvince())) {
            String provinceName = provinceDao.getName(detail.getRegionProvince());
            if (StringUtils.isNotBlank(provinceName)) {
                cityName = provinceName;
            }
        }
        return cityName;
    }

    @Override
    public String getCityName(TCompany detail) {
        String cityName = "--";
        if (StringUtils.isNotBlank(detail.getAreaId())) {
            String areaName = areaDao.getName(detail.getAreaId());
            cityName = cityDao.getName(detail.getCityId());
            String provinceName = provinceDao.getName(detail.getProvinceId());
            return provinceName + cityName + areaName;
        } else if (StringUtils.isNotBlank(detail.getCityId())) {
            cityName = cityDao.getName(detail.getCityId());
            String provinceName = provinceDao.getName(detail.getProvinceId());
            return provinceName + cityName;
        } else if (StringUtils.isNotBlank(detail.getProvinceId())) {
            String provinceName = provinceDao.getName(detail.getProvinceId());
            if (StringUtils.isNotBlank(provinceName)) {
                cityName = provinceName;
            }
        }
        return cityName;
    }

    @Override
    public String getCityName(TUser item) {
        String cityName = "--";
        if (StringUtils.isNotBlank(item.getAreaId())) {
            String areaName = areaDao.getName(item.getAreaId());
            cityName = cityDao.getName(item.getCityId());
            String provinceName = provinceDao.getName(item.getProvinceId());
            return provinceName + cityName + areaName;
        } else if (StringUtils.isNotBlank(item.getCityId())) {
            cityName = cityDao.getName(item.getCityId());
            String provinceName = provinceDao.getName(item.getProvinceId());
            return provinceName + cityName;
        } else if (StringUtils.isNotBlank(item.getProvinceId())) {
            String provinceName = provinceDao.getName(item.getProvinceId());
            if (StringUtils.isNotBlank(provinceName)) {
                cityName = provinceName;
            }
        }
        return cityName;
    }


    @Override
    public List<Town> getTownsByAreaId(Integer areaId) {
        TownCondition townCondition = new TownCondition();
        townCondition.setAreaId(areaId);

        List<Town> towns = townDao.selectList(townCondition);
        return towns;
    }
}