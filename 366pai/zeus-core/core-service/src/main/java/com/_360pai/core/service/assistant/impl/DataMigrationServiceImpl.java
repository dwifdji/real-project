package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.PasswordEncryption;
import com._360pai.arch.common.utils.PinYin4jUtils;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.AgencyEnum;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.asset.AssetDataDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.dao.disposal.TDisposeLevelDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDataDao;
import com._360pai.core.dto.AssetDto;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.account.*;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.AssetData;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityData;
import com._360pai.core.service.assistant.DataMigrationService;
import com._360pai.core.utils.BusinessUtil;
import com._360pai.gateway.controller.req.fdd.FCommResp;
import com._360pai.gateway.controller.req.fdd.UpdateMemInfoReq;
import com._360pai.gateway.facade.FddSignatureFacade;
import com._360pai.gateway.facade.WxFacade;
import com._360pai.gateway.resp.wx.MpUserInfoResp;
import com._360pai.gateway.resp.wx.MpUserListResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xdrodger
 * @Title: DataMigrationServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/26 13:43
 */
@Service
@Slf4j
public class DataMigrationServiceImpl implements DataMigrationService {
    @Autowired
    private DataMigrationDao dataMigrationDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private TAccountDao tAccountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TUserDao tUserDao;
    @Autowired
    private PartyDao partyDao;
    @Autowired
    private TPartyDao tPartyDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private TCompanyDao tCompanyDao;
    @Autowired
    private AgencyDao agencyDao;
    @Autowired
    private TAgencyDao tAgencyDao;
    @Autowired
    private AgencyApplicationDao agencyApplicationDao;
    @Autowired
    private TAgencyApplyRecordDao agencyApplyRecordDao;
    @Autowired
    private AgencyPortalDao agencyPortalDao;
    @Autowired
    private UserVerifyApplicationDao userVerifyApplicationDao;
    @Autowired
    private TUserApplyRecordDao tUserApplyRecordDao;
    @Autowired
    private CompanyVerifyApplicationDao companyVerifyApplicationDao;
    @Autowired
    private TCompanyApplyRecordDao tCompanyApplyRecordDao;
    @Autowired
    private AccountCompanyMapDao accountCompanyMapDao;
    @Autowired
    private TAgencyApplyRecordDao tAgencyApplyRecordDao;
    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;
    @Autowired
    private AssetDao assetDao;
    @Autowired
    private EnrollingActivityDao enrollingActivityDao;
    @Autowired
    private TDisposalRequirementDao disposalRequirementDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private TUserApplyRecordDao userApplyRecordDao;
    @Autowired
    private TDisposeProviderApplyDao tDisposeProviderApplyDao;
    @Autowired
    private TDisposeProviderDao tDisposeProviderDao;
    @Autowired
    private TDisposeLevelDao tDisposeLevelDao;
    @Autowired
    private AssetDataDao assetDataDao;
    @Autowired
    private AuctionActivityDao activityDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private EnrollingActivityDataDao enrollingActivityDataDao;
    @Reference(version = "1.0.0")
    private WxFacade wxFacade;
    @Autowired
    private TAccountExtBindDao extBindDao;

    private static final Pattern KEY_PATTERN = Pattern.compile("[\\d]");

    private String formatKey(String key) {
        Matcher matcher = KEY_PATTERN.matcher(key);
        return (matcher.replaceAll("").trim());
    }

    @Transactional
    @Override
    public void migrateAgencyData() {
        List<String> licenses = dataMigrationDao.getLicensesNeedToMigrate();
        log.info("migrate agency,size={},licenses={}", licenses.size(),licenses.toString());
        for (String license : licenses) {
            log.info("start migrate agency,license={}", license);
            doMigrateAgency(license);
            log.info("migrate agency end,license={}", license);
        }
    }


    @Override
    public boolean doMigrateAgency(String license) {
        Agency agency = agencyDao.getByLicense(license);
        if (agency == null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAgency newAgency = tAgencyDao.getByLicense(license);
        if (newAgency != null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        AgencyApplication applyRecord  = agencyApplicationDao.getApprovedByLicense(license);
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAgencyApplyRecord newApplyRecord = tAgencyApplyRecordDao.getApprovedByLicense(license);
        if (newApplyRecord != null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        newApplyRecord = new TAgencyApplyRecord();
        BeanUtils.copyProperties(applyRecord, newApplyRecord);
        newApplyRecord.setId(null);
        newApplyRecord.setCityId(applyRecord.getCityId() + "");
        newApplyRecord.setRegisterCityId(applyRecord.getRegisterCityId() + "");
        int result = tAgencyApplyRecordDao.insert(newApplyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        newAgency = copyAgency(agency);
        result = tAgencyDao.insert(newAgency);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        AgencyPortal agencyPortal = agencyPortalDao.getByAgencyId(agency.getId());
        AgencyPortal newAgencyPortal = new AgencyPortal();
        newAgencyPortal.setAgencyId(newAgency.getId());
        newAgencyPortal.setStatus(AgencyEnum.AgencyPortalStatus.CLOSED.getKey());
        result = agencyPortalDao.insert(newAgencyPortal);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return true;
    }

    private TAgency copyAgency(Agency agency) {
        TAgency newAgency = new TAgency();
        newAgency.setId(null);
        newAgency.setName(agency.getName());
        newAgency.setMobile(agency.getMobile());
        newAgency.setCode(agency.getCode());
        newAgency.setShortName(agency.getShortName());
        newAgency.setRegisterAddress(agency.getRegisterAddress());
        newAgency.setAddress(agency.getAddress());
        newAgency.setRegisterCityId(agency.getRegisterCityId() + "");
        newAgency.setCityId(agency.getCityId() + "");
        newAgency.setLicense(agency.getLicense());
        newAgency.setLicenseImg(agency.getLicenseImg());
        newAgency.setLegalPerson(agency.getLegalPerson());
        newAgency.setIdCard(agency.getIdCard());
        newAgency.setIdCardFrontImg(agency.getIdCardFrontImg());
        newAgency.setIdCardBackImg(agency.getIdCardBackImg());
        newAgency.setQualifiedBegin(agency.getQualifiedBegin());
        newAgency.setQualifiedEnd(agency.getQualifiedEnd());
        newAgency.setQualificationImg(agency.getQualificationImg());
        newAgency.setQualificationNumber(agency.getQualificationNumber());
        newAgency.setAuthorizationImg(agency.getAuthorizationImg());
        newAgency.setAccountLicense(agency.getAccountLicense());
        newAgency.setBusinessBegin(agency.getBusinessBegin());
        newAgency.setBusinessEnd(agency.getBusinessEnd());
        newAgency.setRemark(agency.getRemark());
        newAgency.setServeBuyerPercent(agency.getServeBuyerPercent());
        newAgency.setServeSellerPercent(agency.getServeSellerPercent());
        newAgency.setStatus(1);
        newAgency.setSelfIntroduction(agency.getSelfIntroduction());
        newAgency.setIntroduction(agency.getIntroduction());
        newAgency.setFadadaId(agency.getFadadaId());
        newAgency.setFadadaEmail(agency.getFadadaEmail());
        newAgency.setDfftId(agency.getMemCode());
        newAgency.setPayBind(agency.getPayBind() ? 1 : 0);
        newAgency.setIsChannelAgent(agency.getIsChannelAgent() ? 1 : 0);
        newAgency.setLogoUrl(agency.getLogoUrl());
        newAgency.setCreateTime(agency.getCreatedAt());
        newAgency.setUpdateTime(agency.getCreatedAt());
        return newAgency;
    }

    @Transactional
    @Override
    public void migrateAccountData() {
        List<String> mobiles = dataMigrationDao.getMobilesNeedToMigrate();
        log.info("migrate account,size={},mobiles={}", mobiles.size(),mobiles.toString());
        for (String mobile : mobiles) {
            log.info("start migrate account,mobile={}", mobile);
            doMigrateAccount(mobile);
            log.info("migrate account end,mobile={}", mobile);
        }
    }

    @Override
    public boolean doMigrateAccount(String mobile) {
        Account account = accountDao.getByMobile(mobile);
        if (account == null) {
            log.error("migrate account fail, mobile={} not exist", mobile);
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAccount newAccount = migrateAccount(mobile);
        User user = userDao.getByAccountId(account.getId());
        List<Company> companyList = companyDao.getByAdminId(account.getId());
        if (user == null && companyList.size() == 0) {
            log.info("migrate common account end, mobile={}", mobile);
            return true;
        }
        int result;
        if (user != null) {
            log.info("start migrate user, mobile={}", mobile);
            migrateUser(user, newAccount);
            log.info("migrate user end, mobile={}", mobile);
        }
        if (companyList.size() > 0) {
            if (companyList.size() > 1) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            Company company = companyList.get(0);
            log.info("start migrate company, mobile={}", mobile);
            migrateCompany(company, newAccount);
            log.info("migrate company end, mobile={}", mobile);
        }
        return true;
    }

    @Transactional
    @Override
    public void syncCompanyFadadaEmial() {
        List<Integer> companyIds = dataMigrationDao.getCompanyIdByFadadaEmailIsNull();
        int result;
        for (Integer companyId : companyIds) {
            TCompany company = tCompanyDao.selectById(companyId);
            if (StringUtils.isNotEmpty(company.getFadadaEmail())) {
                continue;
            }
            company.setFadadaEmail(BusinessUtil.genFadadaEmail());
            company.setUpdateTime(new Date());
            result = tCompanyDao.updateById(company);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            if (StringUtils.isNotEmpty(company.getFadadaId())) {
                //UpdateMemInfoReq updateMemInfoReq = new UpdateMemInfoReq();
                //updateMemInfoReq.setCustomer_id(company.getFadadaId());
                //updateMemInfoReq.setEmail(company.getFadadaEmail());
                //FCommResp fCommResp = fddSignatureFacade.updateMemInfo(updateMemInfoReq);
                //if (fCommResp == null || !fCommResp.getCode().equals("000")) {
                //    log.error("更新企业法大大信息失败，入参={}，出参={}", JSON.toJSONString(updateMemInfoReq), JSON.toJSONString(fCommResp));
                //}
            }
        }
    }

    @Transactional
    @Override
    public void syncAgencyFadadaEmial() {
        List<Integer> agencyIds = dataMigrationDao.getAgencyIdByFadadaEmailIsNull();
        int result;
        for (Integer agencyId : agencyIds) {
            TAgency agency = tAgencyDao.selectById(agencyId);
            if (StringUtils.isNotEmpty(agency.getFadadaEmail())) {
                continue;
            }
            TCompany company = tCompanyDao.getByLicense(agency.getLicense());
            if (company != null) {
                if (StringUtils.isNotEmpty(company.getFadadaEmail())) {
                    agency.setFadadaEmail(company.getFadadaEmail());
                    agency.setUpdateTime(new Date());
                    result = tAgencyDao.updateById(agency);
                    if (result == 0) {
                        throw new BusinessException(ApiCallResult.FAILURE);
                    }
                } else {
                    agency.setFadadaEmail(BusinessUtil.genFadadaEmail());
                    agency.setUpdateTime(new Date());
                    result = tAgencyDao.updateById(agency);
                    if (result == 0) {
                        throw new BusinessException(ApiCallResult.FAILURE);
                    }
                    company.setFadadaEmail(agency.getFadadaEmail());
                    company.setUpdateTime(new Date());
                    result = tCompanyDao.updateById(company);
                    if (result == 0) {
                        throw new BusinessException(ApiCallResult.FAILURE);
                    }
                }
            } else {
                agency.setFadadaEmail(BusinessUtil.genFadadaEmail());
                agency.setUpdateTime(new Date());
                result = tAgencyDao.updateById(agency);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            }



            if (StringUtils.isNotEmpty(agency.getFadadaId())) {
                //UpdateMemInfoReq updateMemInfoReq = new UpdateMemInfoReq();
                //updateMemInfoReq.setCustomer_id(agency.getFadadaId());
                //updateMemInfoReq.setEmail(agency.getFadadaEmail());
                //FCommResp fCommResp = fddSignatureFacade.updateMemInfo(updateMemInfoReq);
                //if (fCommResp == null || !fCommResp.getCode().equals("000")) {
                //    log.error("更新机构法大大信息失败，入参={}，出参={}", JSON.toJSONString(updateMemInfoReq), JSON.toJSONString(fCommResp));
                //}
            }
        }
    }

    @Override
    public List<String> getAppletAccountListNeedRepair(Map<String, Object> params) {
        int page = 1;
        int perPage = 50;
        PageInfo pageInfo;
        List<String> mobiles = new ArrayList<>();
        while (true) {
            pageInfo = tAccountDao.getAppletAccountListNeedRepair(page, perPage, params);
            List<TAccount> list = pageInfo.getList();
            for (TAccount account : list) {
                try {
                    if (!PasswordEncryption.authenticate("360PAI2018", account.getPassword())) {
                        continue;
                    }
                    mobiles.add(account.getMobile());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
        return mobiles;
    }


    @Override
    public void syncProvinceId() {
        syncAssetProvinceId();
        syncEnrollingActivityProvinceId();
        syncTDisposalRequirementProvinceId();
        syncTDisposeProviderProvinceId();
        syncTDisposeProviderApplyRecordProvinceId();
    }

    @Override
    public void syncAuctionActivityBusTypeName() {
        int page = 1;
        int perPage = 10;
        while (true) {
            PageInfo<AuctionActivity> pageInfo = dataMigrationDao.getNeedToSyncBusTypeNameActivityId(page, perPage, Maps.newHashMap(), "");
            for (AuctionActivity activity : pageInfo.getList()) {
                AssetData assetData = assetDataDao.findAssetData(activity.getAssetId());
                if (assetData == null) {
                    continue;
                }
                syncAuctionActivityBusTypeName(activity, assetData);
            }
            if (!pageInfo.isHasNextPage()) {
                break;
            }
        }

    }

    @Override
    public void syncAssetData() {
        int page = 1;
        while (true) {
            log.info("同步AssetData省份信息，page={}", page);
            PageInfo<AssetData>  pageInfo = assetDataDao.find(page, 50);
            List<AssetData> list = pageInfo.getList();
            for (AssetData item : list) {
                log.info("同步AssetData省份信息，page={}，assetId={}", page, item.getAssetId());
                try {
                    JSONObject content = item.getContent();
                    if (content != null && content.containsKey("templateDate")) {
                        JSONArray templateDate = content.getJSONArray("templateDate");
                        for (int i =0; i <  templateDate.size(); i ++) {
                            JSONObject json = (JSONObject) templateDate.get(i);
                            String type = json.getString("type");
                            if (!"CITY".equals(type)) {
                                continue;
                            }
                            if (!json.containsKey("val")) {
                                continue;
                            }
                            JSONArray val = json.getJSONArray("val");
                            JSONArray newVal = new JSONArray();
                            for (int j = 0; j < val.size(); j ++) {
                                JSONObject regionJson = (JSONObject) val.get(j);
                                String cityId = regionJson.getString("id");
                                if (StringUtils.isBlank(cityId)) {
                                    continue;
                                }
                                City city = cityDao.selectById(cityId);
                                if (city != null) {
                                    Province province = provinceDao.selectById(city.getProvinceId());
                                    if (province != null) {
                                        regionJson.put("provinceId", province.getId());
                                        regionJson.put("provinceName", province.getName());
                                        newVal.add(regionJson);
                                    }
                                }
                            }
                            json.put("val", newVal);
                            content.put("templateDate", templateDate);
                            assetDataDao.updateById(item);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            page ++;
        }
    }

    @Override
    public void syncEnrollingActivityData() {
        int page = 1;
        while (true) {
            log.info("同步EnrollingActivityData省份信息，page={}", page);
            PageInfo<EnrollingActivityData>  pageInfo = enrollingActivityDataDao.find(page, 50);
            List<EnrollingActivityData> list = pageInfo.getList();
            for (EnrollingActivityData item : list) {
                log.info("同步EnrollingActivityData省份信息，page={}，activityId={}", page, item.getActivityId());
                try {
                    JSONObject content = item.getContent();
                    if (content != null && content.containsKey("templateDate")) {
                        JSONArray templateDate = content.getJSONArray("templateDate");
                        for (int i =0; i <  templateDate.size(); i ++) {
                            JSONObject json = (JSONObject) templateDate.get(i);
                            String type = json.getString("type");
                            if (!"CITY".equals(type)) {
                                continue;
                            }
                            if (!json.containsKey("val")) {
                                continue;
                            }
                            JSONArray val = json.getJSONArray("val");
                            JSONArray newVal = new JSONArray();
                            for (int j = 0; j < val.size(); j ++) {
                                JSONObject regionJson = (JSONObject) val.get(j);
                                String cityId = regionJson.getString("id");
                                if (StringUtils.isBlank(cityId)) {
                                    continue;
                                }
                                City city = cityDao.selectById(cityId);
                                if (city != null) {
                                    Province province = provinceDao.selectById(city.getProvinceId());
                                    if (province != null) {
                                        regionJson.put("provinceId", province.getId());
                                        regionJson.put("provinceName", province.getName());
                                        newVal.add(regionJson);
                                    }
                                }
                            }
                            json.put("val", newVal);
                            content.put("templateDate", templateDate);
                            enrollingActivityDataDao.updateById(item);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //if (page > 1) {
            //    break;
            //}
            if (!pageInfo.isHasNextPage()) {
                break;
            }
            page ++;
        }
    }

    @Override
    public void syncOldSubscribeMp360PaiUserToDb() {
        String nextOpenId = "";
        while (true) {
            try {
                MpUserListResp resp = wxFacade.getMpUserList(nextOpenId);
                log.info("同步360拍卖公众号老用户到数据库，获取公众号用户列表，nextOpenId={}", resp.getNextOpenId());
                if (resp.getList() == null || resp.getList().size() == 0 || StringUtils.isBlank(resp.getNextOpenId())) {
                    break;
                }
                for (String openId : resp.getList()) {
                    try {
                        log.info("同步360拍卖公众号老用户到数据库，openId={}", openId);
                        TAccountExtBind extBind = extBindDao.findMp360PaiByOpenId(openId);
                        if (extBind != null) {
                            log.warn("openId={}，已存在", openId);
                            continue;
                        }
                        MpUserInfoResp userInfoResp = wxFacade.getMpUserInfo(openId);
                        if (userInfoResp == null || StringUtils.isBlank(userInfoResp.getOpenId())) {
                            log.error("openId={}，用户信息获取失败", openId);
                        }
                        extBind = new TAccountExtBind();
                        extBind.setExtUserId(openId);
                        extBind.setUnionId(userInfoResp.getUnionId());
                        extBind.setNickName(StringEscapeUtils.escapeJava(userInfoResp.getNickName()));
                        extBind.setHeadImgUrl(userInfoResp.getHeadImgUrl());
                        extBind.setExtType(AccountEnum.ExtType.MP_360PAI.getKey());
                        int result = extBindDao.insert(extBind);
                        if (result == 0) {
                            log.error("openId={}，用户信息写入数据库失败", openId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                nextOpenId = resp.getNextOpenId();
                if (StringUtils.isBlank(nextOpenId)) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info("同步360拍卖公众号老用户到数据库，完成");

    }

    @Override
    public void syncProvincePinyin() {
        List<Province> list = provinceDao.selectAll();
        for (Province item : list) {
            if (item.getName().equals("重庆市")) {
                item.setPinyin("chongqingshi");
            } else {
                item.setPinyin(PinYin4jUtils.hanziToPinyinRemoveBrackets(item.getName()));
            }
            provinceDao.updateById(item);
        }
    }

    @Override
    public void syncAgencyPinyin() {
        List<TAgency> list = tAgencyDao.selectAll();
        for (TAgency item : list) {
            if (StringUtils.isNotBlank(item.getPinyin())) {
                continue;
            }
            item.setPinyin(PinYin4jUtils.hanziToPinyinRemoveBrackets(item.getName()));
            tAgencyDao.updateById(item);
        }
    }

    private void syncAuctionActivityBusTypeName(AuctionActivity activity, AssetData assetData) {
        try {
            List<String> busTypeName = new ArrayList<>();
            JSONObject jsonObject = assetData.getContent();
            JSONArray templateDate = jsonObject.getJSONArray("templateDate");
            for (Object o : templateDate) {
                JSONObject item = (JSONObject) o;
                AssetDto json = item.toJavaObject(AssetDto.class);
                String key = formatKey(json.getKey());
                if ("jzwlx".equals(key)) {
                    busTypeName.add(json.getVal().get(0));
                }
            }
            activity.setBusTypeName(String.join(",", busTypeName));
            activityDao.updateById(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




        private void syncAssetProvinceId() {
        int page = 1;
        int perPage = 20;
        PageInfo pageInfo;
        while (true) {
            pageInfo = assetDao.getListByPage(page, perPage);
            try {
                List<Asset> list = pageInfo.getList();
                for (Asset item : list) {
                    if (StringUtils.isNotEmpty(item.getProvinceId())) {
                        continue;
                    }
                    String provinceIds = getProvinceIds(item.getCityId());
                    if (StringUtils.isEmpty(provinceIds)) {
                        continue;
                    }
                    item.setProvinceId(provinceIds);
                    assetDao.updateById(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
    }

    private void syncEnrollingActivityProvinceId() {
        int page = 1;
        int perPage = 20;
        PageInfo pageInfo;
        while (true) {
            pageInfo = enrollingActivityDao.getListByPage(page, perPage);
            try {
                List<EnrollingActivity> list = pageInfo.getList();
                for (EnrollingActivity item : list) {
                    if (StringUtils.isNotEmpty(item.getProvinceId())) {
                        continue;
                    }
                    String provinceIds = getProvinceIds(item.getCityId());
                    if (StringUtils.isEmpty(provinceIds)) {
                        continue;
                    }
                    item.setProvinceId(provinceIds);
                    enrollingActivityDao.updateById(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
    }

    private void syncTDisposalRequirementProvinceId() {
        int page = 1;
        int perPage = 20;
        PageInfo pageInfo;
        while (true) {
            pageInfo = disposalRequirementDao.getListByPage(page, perPage);
            try {
                List<TDisposalRequirement> list = pageInfo.getList();
                for (TDisposalRequirement item : list) {
                    if (StringUtils.isNotEmpty(item.getProvinceId())) {
                        continue;
                    }
                    String provinceIds = getProvinceIds(item.getCityId());
                    if (StringUtils.isEmpty(provinceIds)) {
                        continue;
                    }
                    item.setProvinceId(provinceIds);
                    disposalRequirementDao.updateById(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
    }

    private void syncTDisposeProviderProvinceId() {
        int page = 1;
        int perPage = 20;
        PageInfo pageInfo;
        while (true) {
            pageInfo = tDisposeProviderDao.getListByPage(page, perPage, null, "");
            try {
                List<TDisposeProvider> list = pageInfo.getList();
                for (TDisposeProvider item : list) {
                    if (StringUtils.isNotEmpty(item.getRegionProvince())) {
                        continue;
                    }
                    if (StringUtils.isEmpty(item.getRegion())) {
                        continue;
                    }
                    City city = cityDao.selectById(item.getRegion());
                    if (city == null) {
                        continue;
                    }
                    item.setRegionProvince(city.getProvinceId() + "");
                    tDisposeProviderDao.updateById(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
    }

    private void syncTDisposeProviderApplyRecordProvinceId() {
        int page = 1;
        int perPage = 20;
        PageInfo pageInfo;
        while (true) {
            pageInfo = tDisposeProviderApplyDao.getListByPage(page, perPage, null, "");
            try {
                List<TDisposeProviderApply> list = pageInfo.getList();
                for (TDisposeProviderApply item : list) {
                    if (StringUtils.isNotEmpty(item.getRegionProvince())) {
                        continue;
                    }
                    if (StringUtils.isEmpty(item.getRegion())) {
                        continue;
                    }
                    City city = cityDao.selectById(item.getRegion());
                    if (city == null) {
                        continue;
                    }
                    item.setRegionProvince(city.getProvinceId() + "");
                    tDisposeProviderApplyDao.updateById(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (pageInfo.isHasNextPage()) {
                page ++;
            } else {
                break;
            }
        }
    }

    private String getProvinceIds(String cityIdStr) {
        if (StringUtils.isEmpty(cityIdStr)) {
            return "";
        }
        String[] cityIds = cityIdStr.split(",");
        Set<String> provinceIds = new HashSet<>();
        for (String cityId : cityIds) {
            Integer provinceId = cityDao.getProvinceId(cityId);
            if (provinceId == null) {
                continue;
            }
            provinceIds.add(provinceId + "");
        }
        return String.join(",", provinceIds);
    }

    private TAccount migrateAccount(String mobile) {
        Account account = accountDao.getByMobile(mobile);
        if (account == null) {
            log.error("migrate account fail, mobile={} not exist", mobile);
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAccount newAccount = tAccountDao.getByMobile(mobile);
        if (newAccount != null) {
            log.error("migrate account fail, mobile={} migrated", mobile);
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        newAccount= copyAccount(account);
        int result = tAccountDao.insert(newAccount);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return newAccount;
    }

    private TAccount copyAccount(Account account) {
        TAccount newAccount= new TAccount();
        newAccount.setMobile(account.getMobile());
        newAccount.setPassword(account.getPasswordHash());
        newAccount.setRegisterSource(account.getRegisterSource());
        newAccount.setSource(account.getSource());
        newAccount.setStatus(1);
        newAccount.setCreateTime(account.getCreatedAt());
        newAccount.setUpdateTime(account.getUpdatedAt());
        if (account.getDefaultAgencyId() != null) {
            Agency defaultAgency = agencyDao.selectById(account.getDefaultAgencyId());
            TAgency newDefaultAgency = tAgencyDao.getByLicense(defaultAgency.getLicense());
            newAccount.setDefaultAgencyId(newDefaultAgency.getId());
        }
        if (account.getAgencyId() != null) {
            Agency agency= agencyDao.selectById(account.getAgencyId());
            TAgency newAgency = tAgencyDao.getByLicense(agency.getLicense());
            newAccount.setAgencyId(newAgency.getId());
            newAccount.setIsAgencyAdmin(account.getIsAgencyAdmin() ? 1 : 0);
        }
        return newAccount;
    }

    private TUser migrateUser(User user, TAccount newAccount) {
        TUser newUser = tUserDao.getByCertificateNumber(user.getCertificateNumber());
        if (newUser != null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        Party party = partyDao.selectById(user.getId());
        TParty newParty = new TParty();
        newParty.setType(party.getType().name());
        newParty.setCategory(party.getCategory());
        newParty.setIsChannelAgent(party.getIsChannelAgent());
        newParty.setIsInBlackList(party.getIsInBlackList());
        newParty.setCreateTime(user.getCreatedAt());
        newParty.setUpdateTime(user.getCreatedAt());
        int result = tPartyDao.insert(newParty);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        newUser = copyUser(user, newAccount, newParty);
        result = tUserDao.insert(newUser);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        UserVerifyApplication applyRecord = userVerifyApplicationDao.getApprovedByCertificateNumber(user.getCertificateNumber());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TUserApplyRecord newApplyRecord = tUserApplyRecordDao.getApprovedByCertificateNumber(user.getCertificateNumber());
        if (newApplyRecord != null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        newApplyRecord = new TUserApplyRecord();
        BeanUtils.copyProperties(applyRecord, newApplyRecord);
        newApplyRecord.setId(null);
        newApplyRecord.setAccountId(newAccount.getId());
        newApplyRecord.setCityId(applyRecord.getCityId() + "");
        result = tUserApplyRecordDao.insert(newApplyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return newUser;
    }

    private TUser copyUser(User user, TAccount newAccount, TParty newParty) {
        TUser newUser = new TUser();
        newUser.setId(newParty.getId());
        newUser.setAccountId(newAccount.getId());
        if (user.getDefaultAgencyId() != null) {
            Agency defaultAgency = agencyDao.selectById(user.getDefaultAgencyId());
            TAgency newDefaultAgency = tAgencyDao.getByLicense(defaultAgency.getLicense());
            newUser.setDefaultAgencyId(newDefaultAgency.getId());
        }
        newUser.setName(user.getName());
        newUser.setCertificateNumber(user.getCertificateNumber());
        newUser.setCertificateBegin(user.getCertificateBegin());
        newUser.setCertificateEnd(user.getCertificateEnd());
        newUser.setMobile(user.getMobile());
        newUser.setCityId(user.getCityId() + "");
        newUser.setStatus(1);
        newUser.setCertificateFrontImg(user.getCertificateFrontImg());
        newUser.setCertificateBackImg(user.getCertificateBackImg());
        newUser.setAddress(user.getAddress());
        newUser.setFadadaId(user.getFadadaId());
        newUser.setDfftId(user.getMemCode());
        newUser.setPayBind(user.getPayBind() ? 1 : 0);
        newUser.setCreateTime(user.getCreatedAt());
        newUser.setUpdateTime(user.getCreatedAt());
        return newUser;
    }

    private TCompany migrateCompany(Company company, TAccount newAccount) {
        TCompany newCompany = tCompanyDao.getByLicense(company.getLicense());
        if (newCompany != null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        Party party = partyDao.selectById(company.getId());
        TParty newParty = new TParty();
        newParty.setType(party.getType().name());
        newParty.setCategory(party.getCategory());
        newParty.setIsChannelAgent(party.getIsChannelAgent());
        newParty.setIsInBlackList(party.getIsInBlackList());
        newParty.setCreateTime(company.getCreatedAt());
        newParty.setUpdateTime(company.getCreatedAt());
        int result = tPartyDao.insert(newParty);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        newCompany = copyCompany(company, newAccount, newParty);
        result = tCompanyDao.insert(newCompany);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        CompanyVerifyApplication applyRecord = companyVerifyApplicationDao.getApprovedByLicense(company.getLicense());
        if (applyRecord != null) {
            TCompanyApplyRecord newApplyRecord = tCompanyApplyRecordDao.getApprovedByLicense(company.getLicense());
            if (newApplyRecord != null) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            newApplyRecord = new TCompanyApplyRecord();
            BeanUtils.copyProperties(applyRecord, newApplyRecord);
            newApplyRecord.setId(null);
            newApplyRecord.setAccountId(newAccount.getId());
            newApplyRecord.setCityId(applyRecord.getCityId() + "");
            newApplyRecord.setRegisterCityId(applyRecord.getRegisterCityId() + "");
            result = tCompanyApplyRecordDao.insert(newApplyRecord);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        AccountCompanyMap accountCompanyMap = new AccountCompanyMap();
        accountCompanyMap.setAccountId(newAccount.getId());
        accountCompanyMap.setCompanyId(newCompany.getId());
        accountCompanyMap.setName(newCompany.getName());
        result = accountCompanyMapDao.insert(accountCompanyMap);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return newCompany;
    }

    private TCompany copyCompany(Company company, TAccount newAccount, TParty newParty) {
        TCompany newCompany = new TCompany();
        newCompany.setId(newParty.getId());
        newCompany.setName(company.getName());
        newCompany.setAccountId(newAccount.getId());
        newCompany.setMobile(company.getMobile());
        newCompany.setDefaultAgencyId(company.getDefaultAgencyId());
        if (company.getDefaultAgencyId() != null) {
            Agency defaultAgency = agencyDao.selectById(company.getDefaultAgencyId());
            TAgency newDefaultAgency = tAgencyDao.getByLicense(defaultAgency.getLicense());
            newCompany.setDefaultAgencyId(newDefaultAgency.getId());
        }
        newCompany.setStatus(1);
        newCompany.setRegisterAddress(company.getRegisterAddress());
        newCompany.setAddress(company.getAddress());
        newCompany.setRegisterCityId(company.getRegisterCityId() + "");
        newCompany.setCityId(company.getCityId() + "");
        newCompany.setLicense(company.getLicense());
        newCompany.setLicenseImg(company.getLicenseImg());
        newCompany.setLegalPerson(company.getLegalPerson());
        newCompany.setIdCard(company.getIdCard());
        newCompany.setIdCardFrontImg(company.getIdCardFrontImg());
        newCompany.setIdCardBackImg(company.getIdCardBackImg());
        if (company.getAgencyId() != null) {
            Agency agency= agencyDao.selectById(company.getAgencyId());
            TAgency newAgency = tAgencyDao.getByLicense(agency.getLicense());
            newCompany.setAgencyId(newAgency.getId());
        }
        newCompany.setQualifiedBegin(company.getQualifiedBegin());
        newCompany.setQualifiedEnd(company.getQualifiedEnd());
        newCompany.setAuthorizationImg(company.getAuthorizationImg());
        newCompany.setAccountLicense(company.getAccountLicense());
        newCompany.setFadadaId(company.getFadadaId());
        newCompany.setFadadaEmail(company.getFadadaEmail());
        newCompany.setDfftId(company.getMemCode());
        newCompany.setPayBind(company.getPayBind() ? 1 : 0);
        newCompany.setChannelPay(company.getChannelPay() ? 1 : 0);
        newCompany.setBankAccountName(company.getBankAccountName());
        newCompany.setBankAccountNumber(company.getBankAccountNumber());
        newCompany.setBankId(company.getBankId());
        newCompany.setCreateTime(company.getCreatedAt());
        newCompany.setUpdateTime(company.getCreatedAt());
        newCompany.setCategory(newParty.getCategory());
        return newCompany;
    }
}
