package com._360pai.core.provider.assistant;


import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.EmailService;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.core.facade.assistant.CommonFacade;
import com._360pai.core.facade.assistant.resp.TokenResp;
import com._360pai.core.facade.assistant.vo.BankVo;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.asset.AssetProperty;
import com._360pai.core.model.assistant.*;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.asset.AssetPropertyService;
import com._360pai.core.service.assistant.*;
import com._360pai.core.vo.common.AssetPropertyVo;
import com._360pai.gateway.controller.req.wx.WXACodeUnLimitReq;
import com._360pai.gateway.facade.WxFacade;
import com._360pai.gateway.resp.wxpay.WXACodeUnLimitResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageSerializable;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 描述：公共Facade接口实现
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 14:16
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class CommonProvider implements CommonFacade {

    @Autowired
    private AssetPropertyService assetPropertyService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private BankService bankService;

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Autowired
    private DataMigrationService dataMigrationService;
    @Autowired
    private GatewayMqSender mqSender;

    @Autowired
    private EmailService emailService;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Reference(version = "1.0.0")
    private WxFacade wxFacade;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private CommonService commonService;
    @Autowired
    private TBankService tBankService;
    private static String excludeProvinceIds = "1,2,9,22,32,33";
    private static String excludeCityIds = "";
    @Autowired
    private TFileService tFileService;
    @Autowired
    private AssistantService assistantService;

    @Override
    public TokenResp getQiNiuToken() {

        Auth auth = Auth.create(gatewayProperties.getAccessKey(), gatewayProperties.getSecretKey());

        long expireSeconds = 3600;

        String upToken = auth.uploadToken(gatewayProperties.getBucket(), null, expireSeconds,null);

        TokenResp resp = new TokenResp();
        resp.setUpToken(upToken);
        resp.setExpires(expireSeconds);
        resp.setDomain(gatewayProperties.getDomain());
        return resp;
    }


    @Override
    public ResponseModel getPropertyByType(String type) {
        List<AssetProperty> list = assetPropertyService.getAssetPropertyListByType(type);

        List<AssetPropertyVo> listVo = new ArrayList<>();

        for(AssetProperty property:list){
            AssetPropertyVo vo = new AssetPropertyVo();
            vo.setTabId(String.valueOf(property.getId()));
            vo.setTabName(property.getName());
            listVo.add(vo);
        }

        PageSerializable resp = new PageSerializable();
        resp.setTotal(listVo.size());
        resp.setList(listVo);
        return ResponseModel.succ(resp);
     }

    @Override
    public ResponseModel getAllCities() {
        Map<String, Object> result = new HashMap<>();
        String cache = (String) redisCachemanager.get(SystemConstant.ALL_PROVINCE_CITY_AREA_KEY);
        if (StringUtils.isNotEmpty(cache)) {
            JSONArray jsonArray = JSON.parseArray(cache);
            result.put("list", jsonArray);
            return ResponseModel.succ(result);
        }
        JSONArray jsonArray = getProvinceList();
        //缓存省份城市信息
        redisCachemanager.set(SystemConstant.ALL_PROVINCE_CITY_AREA_KEY,jsonArray.toJSONString(),7200L);
        result.put("list", jsonArray);
        return ResponseModel.succ(result);
    }

    private JSONArray getProvinceList() {
        JSONArray jsonArray = new JSONArray();
        List<Province> list = provinceService.getAllProvince();
        for (Province item : list) {
            JSONObject data = new JSONObject();
            data.put("name", item.getName());
            data.put("id", item.getId() + "");
            data.put("cities", getCityList(item, true));
            jsonArray.add(data);
        }
        return jsonArray;
    }

    private JSONArray getCityList(Province province, Boolean flag) {
        JSONArray jsonArray = new JSONArray();
        List<String> provinceIds = Arrays.asList(excludeProvinceIds.split(","));
        if (!provinceIds.contains(province.getId() + "")) {
            JSONObject data = new JSONObject();
            data.put("name", "全" + province.getName());
            data.put("id", "");
            jsonArray.add(data);
        }
        List<City> list = cityService.getCitiesByProvinceId(province.getId());
        for (City item : list) {
            if (item.getId() < 0) {
                continue;
            }
            JSONObject data = new JSONObject();
            data.put("name", item.getName());
            data.put("id", item.getId() + "");
            if (flag) {
                data.put("areas", getAreaList(item));
            }
            jsonArray.add(data);
        }
        return jsonArray;
    }

    private JSONArray getAreaList(City city) {
        JSONArray jsonArray = new JSONArray();
        List<String> cityIds = Arrays.asList(excludeCityIds.split(","));
        if (!cityIds.contains(city.getId() + "")) {
            JSONObject data = new JSONObject();
            data.put("name", "全" + city.getName());
            data.put("id", "");
            jsonArray.add(data);
        }
        List<Area> list = cityService.getAreaByCityId(city.getId());
        for (Area item : list) {
            JSONObject data = new JSONObject();
            data.put("name", item.getName());
            data.put("id", item.getId() + "");
            jsonArray.add(data);
        }
        return jsonArray;
    }

    @Override
    public ListResp<BankVo> getAllBanks(String type) {
        if ("1".equals(type)) {
            return tBankService.getAllBanks();
        }
        return bankService.getAllBanks();
    }

    @Override
    public ResponseModel getAgencyInfo(String code) {

        TAgency agency = agencyService.findByAgencyCode(code);

        if(agency==null){
            return ResponseModel.fail("无此机构！");
        }

        if(!"ONLINE".equals(agency.getWebsiteStatus())){
            return ResponseModel.fail("该机构未启用！");
        }

        AgencyVo vo = new AgencyVo();

        vo.setId(agency.getId());
        vo.setName(agency.getName());
        vo.setImageUrl(agency.getImgUrl());
        vo.setLogoUrl(agency.getLogoUrl());
        vo.setIntroduction(agency.getIntroduction());
        vo.setCode(agency.getCode());
        vo.setMobile(agency.getTrusteePhone());
        vo.setLicenseImg(tFileService.watermark(agency.getLicenseImg()));
        vo.setQualificationImg(tFileService.watermark(agency.getQualificationImg()));
        if (StringUtils.isEmpty(agency.getAppletQrCode())) {
            String appletQrCode = getAgencyAppletQrCode(agency.getId() + "");
            if (StringUtils.isNotEmpty(appletQrCode)) {
                AgencyReq.UpdateReq req = new AgencyReq.UpdateReq();
                req.setId(agency.getId());
                req.setAppletQrCode(appletQrCode);
                agencyService.updateAgency(req);
            }
            vo.setAppletQrCode(appletQrCode);
        } else {
            vo.setAppletQrCode(agency.getAppletQrCode());
        }
        vo.setShowAppletQrCode(systemProperties.getShowAppletQrCode());
        return ResponseModel.succ(vo);
    }

    private String getAgencyAppletQrCode(String agencyId) {
        WXACodeUnLimitReq wxaCodeUnLimitReq = new WXACodeUnLimitReq();
        if (systemProperties.getPutAppletHomePage()) {
            wxaCodeUnLimitReq.setPage(systemProperties.getWebsiteAppletHomePage());
        }
        wxaCodeUnLimitReq.setScene(AccountEnum.InviteType.JG.getKey() + agencyId);
        WXACodeUnLimitResp wxaCodeUnLimitResp = wxFacade.getWXACodeUnLimit(wxaCodeUnLimitReq);
        if (wxaCodeUnLimitResp == null || !wxaCodeUnLimitResp.getCode().equals("000")) {
            log.error("获取子站点小程序二维码失败，入参={}，出参={}",JSON.toJSONString(wxaCodeUnLimitReq), JSON.toJSONString(wxaCodeUnLimitResp));
            return "";
        }
        return wxaCodeUnLimitResp.getImgUrl();
    }

    @Override
    public String getEmailConfig(String code) {

        TSmsEmailConfig config = emailService.configSmsEmailConfig(code);

        if(config==null){
            return null;
        }

        return JSON.toJSONString(config);
    }

    @Override
    public void migrateAgencyData() {
        dataMigrationService.migrateAgencyData();
    }

    @Override
    public void migrateAccountData() {
        dataMigrationService.migrateAccountData();
    }

    @Override
    public void syncCompanyFadadaEmial() {
        dataMigrationService.syncCompanyFadadaEmial();
    }

    @Override
    public void syncAgencyFadadaEmial() {
        dataMigrationService.syncAgencyFadadaEmial();
    }

    @Override
    public void testDelayQueue(long delay) {
        JSONObject data = new JSONObject();
        data.put("message", RandomNumberGenerator.wordGenerator(3));
        data.put("delay", delay);
        data.put("enqueueTime", System.currentTimeMillis());
        mqSender.testDelayEnqueue(data.toJSONString(), delay);
    }

    @Override
    public void testQueue(String message) {
        if (StringUtils.isEmpty(message)) {
            message =  RandomNumberGenerator.wordGenerator(3);
        }
        mqSender.testEnqueue(message);
    }

    @Override
    public String saveExternalImgUrl(String imgUrl) {
        return commonService.saveExternalImgUrl(imgUrl);
    }

    @Override
    public List<String> getAppletAccountListNeedRepair(Map<String, Object> params) {
        return dataMigrationService.getAppletAccountListNeedRepair(params);
    }

    @Override
    public void syncProvinceId() {
        dataMigrationService.syncProvinceId();
    }

    @Override
    public void syncAuctionActivityBusTypeName() {
        dataMigrationService.syncAuctionActivityBusTypeName();
    }

    @Override
    public void syncAssetData() {
        dataMigrationService.syncAssetData();
    }

    @Override
    public void syncEnrollingActivityData() {
        dataMigrationService.syncEnrollingActivityData();
    }

    @Override
    public void syncOldSubscribeMp360PaiUserToDb() {
        dataMigrationService.syncOldSubscribeMp360PaiUserToDb();
    }

    @Override
    public void syncProvincePinyin() {
        dataMigrationService.syncProvincePinyin();
    }

    @Override
    public void syncAgencyPinyin() {
        dataMigrationService.syncAgencyPinyin();
    }

    @Override
    public void removeActivityExpireKeyInRedisEndAtOver2Days() {
        assistantService.removeActivityExpireKeyInRedisEndAtOver2Days();
    }


}

