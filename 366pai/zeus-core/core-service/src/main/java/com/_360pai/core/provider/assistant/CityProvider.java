package com._360pai.core.provider.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.facade.assistant.CityFacade;
import com._360pai.core.facade.assistant.req.CityReq;
import com._360pai.core.facade.assistant.resp.CityListResp;
import com._360pai.core.facade.assistant.resp.CityResp;
import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.model.assistant.Area;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.model.assistant.Town;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.assistant.ProvinceService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zxiao
 * @Title: CityProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/29 15:58
 */
@Component
@Service(version = "1.0.0")
public class CityProvider implements CityFacade {

    @Autowired
    private CityService cityService;
    @Autowired
    private ProvinceService provinceService;
    @Resource
    private RedisCachemanager redisCachemanager;

    private static String excludeProvinceIds = "1,2,9,22,32,33";
    private static String excludeCityIds = "";


    @Override
    public Object pageCities() {
        return cityService.pageCities();
    }

    @Override
    public Object pageProvinces() {
        return cityService.pageProvinces();
    }

    @Override
    public ResponseModel getAllAreas() {
        Map<String, Object> result = new HashMap<>();
        String cache = (String) redisCachemanager.get(SystemConstant.ALL_AREA_KEY);
        if (StringUtils.isNotEmpty(cache)) {
            Map<String, JSONObject> data = JSON.parseObject(cache, Map.class);
            result.put("areas", data);
            return ResponseModel.succ(result);
        }
        Map<String, JSONObject> data = new LinkedHashMap<>();
        List<City> cities = cityService.getAllCity();
        for (City city : cities) {
            List<Area> areas = cityService.getAreaByCityId(city.getId());
            for (Area area : areas) {
                JSONObject json = new JSONObject();
                json.put("id", area.getId());
                json.put("name", area.getName());
                json.put("cityId", city.getId());
                json.put("cityName", city.getName());
                data.put(area.getId() + "", json);
            }
        }
        redisCachemanager.set(SystemConstant.ALL_AREA_KEY, JSON.toJSONString(data),7200L);
        result.put("areas", data);
        return ResponseModel.succ(result);
    }

    @Override
    public CityListResp getAllCities() {
        CityListResp resp = new CityListResp();
        Map<String, CityVo> cityVos = new LinkedHashMap<>();
        List<Province> provinces = provinceService.getAllProvince();
        for (Province province : provinces) {
            List<City> cities = cityService.getCitiesByProvinceId(province.getId());
            for (City city : cities) {
                if (city.getId() < 0) {
                    continue;
                }
                CityVo cityVo = new CityVo();
                cityVo.setId(city.getId());
                cityVo.setName(city.getName());
                cityVo.setProvinceId(province.getId());
                cityVo.setProvinceName(province.getName());
                cityVos.put(cityVo.getId()+"", cityVo);
            }
        }
        resp.setCities(cityVos);
        return resp;
    }

    @Override
    public CityResp getAllProvinces() {
        CityResp resp = new CityResp();
        List<Province> provinces = provinceService.getAllProvince();
        if (provinces.size() > 0) {
            List<CityResp.Province> provinceList = new ArrayList<>();
            for (Province province : provinces) {
                CityResp.Province p = new CityResp.Province();
                p.setId(province.getId() + "");
                p.setName(province.getName());
                provinceList.add(p);
            }
            resp.setProvinces(provinceList);
        }
        return resp;
    }

    @Override
    public CityResp getCitiesByProvinceId(CityReq req) {
        CityResp resp = new CityResp();
        if (req.getProvinceId() == null) {
            resp.setCities(Collections.EMPTY_LIST);
            return resp;
        }
        List<City> cities = cityService.getCitiesByProvinceId(req.getProvinceId());
        List<CityResp.City> cityList = new ArrayList<>();
        //List<String> provinceIds = Arrays.asList(excludeProvinceIds.split(","));
        //if (!provinceIds.contains(req.getProvinceId() + "")) {
        //    CityResp.City vo = new CityResp.City();
        //    vo.setId("");
        //    vo.setName("全" + cityService.getProvinceName(req.getProvinceId()));
        //    cityList.add(vo);
        //}
        CityResp.City vo = new CityResp.City();
        vo.setId("");
        vo.setName("全" + cityService.getProvinceName(req.getProvinceId()));
        cityList.add(vo);
        for (City city: cities) {
            if (city.getId() < 0) {
                continue;
            }
            CityResp.City c = new CityResp.City();
            c.setId(city.getId() + "");
            c.setName(city.getName());
            cityList.add(c);
        }
        resp.setCities(cityList);
        return resp;
    }

    @Override
    public CityResp getAreasByCityId(CityReq req) {
        CityResp resp = new CityResp();
        if (req.getCityId() == null) {
            resp.setAreas(Collections.EMPTY_LIST);
            return resp;
        }
        List<Area> list = cityService.getAreaByCityId(req.getCityId());
        List<CityResp.Area> resultList = new ArrayList<>();
        List<String> cityIds = Arrays.asList(excludeCityIds.split(","));
        if (!cityIds.contains(req.getCityId() + "")) {
            CityResp.Area vo = new CityResp.Area();
            vo.setId("");
            vo.setName("全" + cityService.getCityName(req.getCityId()));
            resultList.add(vo);
        }
        for (Area item: list) {
            CityResp.Area c = new CityResp.Area();
            c.setId(item.getId() + "");
            c.setName(item.getName());
            resultList.add(c);
        }
        resp.setAreas(resultList);
        return resp;
    }

    @Override
    public CityResp getTownsByAreaId(CityReq req) {
        CityResp cityResp = new CityResp();

        if (req.getAreaId() == null) {
            cityResp.setTowns(Collections.EMPTY_LIST);
            return cityResp;
        }

        List<CityResp.Town> townList = new ArrayList<>();
        List<Town> towns = cityService.getTownsByAreaId(req.getAreaId());

        if(towns != null && towns.size() > 0) {
            CityResp.Town newTown = new CityResp.Town();
            String areaName = cityService.getAreaName(req.getAreaId());
            newTown.setId("");
            newTown.setName("全" + areaName);
            townList.add(newTown);

            for (Town item: towns) {
                CityResp.Town c = new CityResp.Town();
                c.setId(item.getId() + "");
                c.setName(item.getName());
                townList.add(c);
            }

        }

        cityResp.setTowns(townList);
        return cityResp;
    }
}
