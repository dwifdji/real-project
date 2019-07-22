package com.winback.core.provider.assistant;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.util.Auth;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.Device;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.constant.RedisKeyConstant;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.arch.core.sysconfig.properties.GatewayProperties;
import com.winback.core.facade.assistant.AssistantFacade;
import com.winback.core.facade.assistant.req.AppAssistantReq;
import com.winback.core.facade.assistant.req.AppletAssistantReq;
import com.winback.core.facade.assistant.resp.AppAssistantResp;
import com.winback.core.facade.assistant.vo.Area;
import com.winback.core.facade.assistant.vo.City;
import com.winback.core.facade.assistant.vo.HelpItem;
import com.winback.core.facade.assistant.vo.Province;
import com.winback.core.model.assistant.TBuriedPoint;
import com.winback.core.model.assistant.TComArea;
import com.winback.core.model.assistant.TComCity;
import com.winback.core.model.assistant.TComProvince;
import com.winback.core.service.assistant.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xdrodger
 * @Title: AssistantProvider
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:20
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class AssistantProvider implements AssistantFacade {

    @Autowired
    private HelpItemService helpItemService;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Autowired
    private CityService cityService;
    @Autowired
    private BuriedPointService buriedPointService;
    @Autowired
    private DataMigrationService dataMigrationService;
    @Autowired
    private AssistantService assistantService;
    @Resource
    private RedisCachemanager redisCachemanager;

    private static String excludeProvinceCodes = "110000,120000,310000,500000,810000,820000";
    private static String excludeCityCodes = "810000,820000";

    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public ListResp<HelpItem> getHelpItemList(AppReq req) {
        return helpItemService.getHelpItemList(req.getSideType());
    }

    @Override
    public HelpItem getHelpItem(AppAssistantReq.GetHelpItemReq req) {
        return helpItemService.getHelpItem(req.getItemId());
    }

    @Override
    public ResponseModel getQiNiuToken(String fileType) {
        Auth auth = Auth.create(gatewayProperties.getAccessKey(), gatewayProperties.getSecretKey());
        long expireSeconds = 3600;
        String key = getKey(fileType);
        String upToken = auth.uploadToken(gatewayProperties.getBucket(), key, expireSeconds,null);
        Map<String, Object> data = new HashMap<>();
        data.put("upToken", upToken);
        data.put("expires", expireSeconds);
        data.put("domain", gatewayProperties.getDomain());
        if (StringUtils.isNotBlank(key)) {
            data.put("fileUrl", "https://" + gatewayProperties.getDomain() + "/" + key);
            data.put("key", key);
        }
        return ResponseModel.succ(data);
    }

    private String getKey(String fileType) {
        if (StringUtils.isBlank(fileType)) {
            return null;
        }
        return getTimeStamp() + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "") + fileType;
    }

    private String getTimeStamp() {
        return df.format(new Date());
    }

    @Override
    public ResponseModel getAllCities(String type) {
        Map<String, Object> result = new HashMap<>();
        String cache = (String) redisCachemanager.get(RedisKeyConstant.ALL_CITY_KEY + type);
        if (StringUtils.isNotEmpty(cache)) {
            JSONArray jsonArray = JSON.parseArray(cache);
            result.put("list", jsonArray);
            return ResponseModel.succ(result);
        }
        JSONArray jsonArray = new JSONArray();
        List<TComProvince> list = cityService.getAllProvinces();
        for (TComProvince item : list) {
            JSONObject data = new JSONObject();
            data.put("name", item.getName());
            data.put("code", item.getCode());
            Boolean flag = false;
            if ("1".equals(type)) {
                flag = true;
            }
            data.put("cityList", getCityList(item, flag));
            jsonArray.add(data);
        }
        //缓存省份城市信息
        redisCachemanager.set(RedisKeyConstant.ALL_CITY_KEY + type,jsonArray.toJSONString(),7200L);
        result.put("list", jsonArray);
        return ResponseModel.succ(result);
    }

    private JSONArray getCityList(TComProvince province, Boolean flag) {
        JSONArray jsonArray = new JSONArray();
        List<String> provinceCodes = Arrays.asList(excludeProvinceCodes.split(","));
        if (!provinceCodes.contains(province.getCode())) {
            JSONObject data = new JSONObject();
            data.put("name", "全" + province.getName());
            data.put("code", "");
            jsonArray.add(data);
        }
        List<TComCity> list = cityService.getCitiesByProvinceCode(province.getCode());
        for (TComCity item : list) {
            JSONObject data = new JSONObject();
            data.put("name", item.getName());
            data.put("code", item.getCode());
            if (flag) {
                data.put("areaList", getAreaList(item));
            }
            jsonArray.add(data);
        }
        return jsonArray;
    }

    private JSONArray getAreaList(TComCity city) {
        JSONArray jsonArray = new JSONArray();
        List<String> cityCodes = Arrays.asList(excludeCityCodes.split(","));
        if (!cityCodes.contains(city.getCode())) {
            JSONObject data = new JSONObject();
            data.put("name", "全" + city.getName());
            data.put("code", "");
            jsonArray.add(data);
        }
        List<TComArea> list = cityService.getAreasByCityCode(city.getCode());
        for (TComArea item : list) {
            JSONObject data = new JSONObject();
            data.put("name", item.getName());
            data.put("code", item.getCode());
            jsonArray.add(data);
        }
        return jsonArray;
    }

    @Override
    public ListResp<Province> getAllProvinces() {
        List<TComProvince> list = cityService.getAllProvinces();
        List<Province> resultList = new ArrayList<>();
        for (TComProvince province : list) {
            Province vo = new Province();
            BeanUtils.copyProperties(province, vo);
            resultList.add(vo);
        }
        ListResp<Province> resp = new ListResp<>();
        resp.setList(resultList);
        return resp;
    }

    @Override
    public ListResp<City> getCitiesByProvinceCode(String provinceCode) {
        ListResp<City> resp = new ListResp<>();
        if (StringUtils.isEmpty(provinceCode)) {
            resp.setList(Collections.EMPTY_LIST);
            return resp;
        }
        List<City> resultList = new ArrayList<>();
        List<String> provinceCodes = Arrays.asList(excludeProvinceCodes.split(","));
        if (!provinceCodes.contains(provinceCode)) {
            TComProvince province = cityService.getProvince(provinceCode);
            City vo = new City();
            vo.setCode("");
            vo.setName("全" + province.getName());
            vo.setProvinceCode(province.getCode());
            vo.setProvinceName(province.getName());
            resultList.add(vo);
        }
        List<TComCity> list = cityService.getCitiesByProvinceCode(provinceCode);
        for (TComCity city : list) {
            City vo = new City();
            BeanUtils.copyProperties(city, vo);
            resultList.add(vo);
        }
        resp.setList(resultList);
        return resp;
    }

    @Override
    public ListResp<Area> getAreasByCityCode(String cityCode) {
        ListResp<Area> resp = new ListResp<>();
        if (StringUtils.isEmpty(cityCode)) {
            resp.setList(Collections.EMPTY_LIST);
            return resp;
        }
        List<Area> resultList = new ArrayList<>();
        List<String> cityCodes = Arrays.asList(excludeCityCodes.split(","));
        if (!cityCodes.contains(cityCode)) {
            TComCity city = cityService.getCity(cityCode);
            Area vo = new Area();
            vo.setCode("");
            vo.setName("全" + city.getName());
            vo.setCityCode(city.getCode());
            vo.setCityName(city.getName());
            resultList.add(vo);
        }
        List<TComArea> list = cityService.getAreasByCityCode(cityCode);
        for (TComArea area : list) {
            Area vo = new Area();
            BeanUtils.copyProperties(area, vo);
            resultList.add(vo);
        }
        resp.setList(resultList);
        return resp;
    }

    @Override
    public Integer buriedPointInsert(AppAssistantReq.BuriedPointReq req) {
        TBuriedPoint tBuriedPoint = new TBuriedPoint();
        BeanUtils.copyProperties(req, tBuriedPoint);
        return buriedPointService.insert(tBuriedPoint);
    }

    @Override
    public Integer buriedPointInsert(AppletAssistantReq.BuriedPointReq req) {
        TBuriedPoint tBuriedPoint = new TBuriedPoint();
        BeanUtils.copyProperties(req, tBuriedPoint);
        return buriedPointService.insert(tBuriedPoint);
    }

    @Override
    public Integer buriedPointUpdate(AppAssistantReq.BuriedPointUpdateReq req) {
        TBuriedPoint tBuriedPoint = new TBuriedPoint();
        BeanUtils.copyProperties(req, tBuriedPoint);
        return buriedPointService.update(tBuriedPoint);
    }

    @Override
    public void importContractFileToDb(String filePath) {
        dataMigrationService.importContractFileToDb(filePath);
    }

    @Override
    public void resetContractImage(String filePath) {
        dataMigrationService.resetContractImage(filePath);
    }

    @Override
    public void resetContractName(String filePath) {
        dataMigrationService.resetContractName(filePath);
    }

    @Override
    public void simpleSaveDevice(Integer accountId, Device device) {
        assistantService.simpleSaveDevice(accountId, device);
    }

    @Override
    public AppAssistantResp.CheckUpdateResp checkUpdate(AppAssistantReq.CheckUpdateReq req) {
        return assistantService.checkUpdate(req);
    }
}
