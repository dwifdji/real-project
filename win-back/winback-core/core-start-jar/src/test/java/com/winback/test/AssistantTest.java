package com.winback.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winback.core.model.assistant.TComArea;
import com.winback.core.model.assistant.TComCity;
import com.winback.core.model.assistant.TComProvince;
import com.winback.core.service.assistant.CityService;
import com.winback.core.service.assistant.DataMigrationService;
import com.winback.core.service.assistant.TFileService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: AssistantTest
 * @ProjectName winback
 * @Description:
 * @date 2019/2/14 13:23
 */
public class AssistantTest extends TestBase{
    @Autowired
    private CityService cityService;
    @Autowired
    private DataMigrationService dataMigrationService;
    @Autowired
    private TFileService tFileService;
    private static String excludeProvinceCodes = "110000,120000,310000,500000,810000,820000";
    private static String excludeCityCodes = "810000,820000";

    @Test
    public void createCityFile() {
        JSONArray jsonArray = new JSONArray();
        List<TComProvince> list = cityService.getAllProvinces();
        for (TComProvince item : list) {
            JSONObject data = new JSONObject();
            data.put("name", item.getName());
            data.put("code", item.getCode());
            data.put("cityList", getCityList(item));
            jsonArray.add(data);
        }
        System.out.println(jsonArray.toJSONString());
        System.out.println("--");
    }

    private JSONArray getCityList(TComProvince province) {
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
            data.put("areaList", getAreaList(item));
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

    @Test
    public void testImportContractFileToDb() {
        String path = "/Users/xdrodger/tmp/soyin/contract";
        dataMigrationService.importContractFileToDb(path);
    }

    @Test
    public void testWatermark() {
        try {
            File file = new File("/Users/xdrodger/tmp/soyin/contract/企业发展/增资扩股协议/公司增资扩股协议书范本/1.jpeg");
            String resultUrl = tFileService.watermark(file);
            System.out.println(resultUrl);
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testfindWrongContract() {
        dataMigrationService.findWrongContract();
        System.out.println("--");
    }

}
