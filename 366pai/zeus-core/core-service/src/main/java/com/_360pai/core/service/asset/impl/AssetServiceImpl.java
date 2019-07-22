package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.NumberFormatUtils;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.condition.activity.AuctionActivityCopyCondition;
import com._360pai.core.condition.asset.AssetCondition;
import com._360pai.core.condition.asset.AssetDataCopyCondition;
import com._360pai.core.condition.asset.AssetDataDraftsCondition;
import com._360pai.core.condition.asset.TPreemptivePersonCondition;
import com._360pai.core.dao.account.AgencyCopyDao;
import com._360pai.core.dao.account.CompanyDao;
import com._360pai.core.dao.account.UserDao;
import com._360pai.core.dao.activity.AuctionActivityCopyDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.asset.*;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dto.AssetDto;
import com._360pai.core.dto.AssetRes;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.asset.resp.AssetListResp;
import com._360pai.core.facade.asset.resp.AssetResp;
import com._360pai.core.facade.asset.vo.AssetVo;
import com._360pai.core.model.account.AgencyCopy;
import com._360pai.core.model.account.Company;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.User;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.AuctionActivityCopy;
import com._360pai.core.model.activity.JointActivityMap;
import com._360pai.core.model.asset.*;
import com._360pai.core.model.assistant.City;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.asset.AssetPropertyService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.asset.TAssetTemplateCategoryService;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.list.TreeList;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 15:14
 */
@Slf4j
@Service
public class AssetServiceImpl implements AssetService {

    @Resource
    private AssetDao assetDao;
    @Resource
    private AssetCopyDao assetCopyDao;
    @Resource
    private AgencyCopyDao agencyCopyDao;
    @Resource
    private SystemProperties systemProperties;
    @Resource
    private AgencyService agencyService;
    @Resource
    private AssetPropertyService assetPropertyService;
    @Resource
    private AccountService accountService;
    @Resource
    private AuctionActivityDao auctionActivityDao;
    @Resource
    private AuctionActivityCopyDao auctionActivityCopyDao;
    @Resource
    private AssetDataDao assetDataDao;
    @Resource
    private TPreemptivePersonDao tPreemptivePersonDao;
    @Resource
    private AssetDataCopyDao assetDataCopyDao;
    @Resource
    private TAssetFieldGroupDao tAssetFieldGroupDao;
    @Resource
    private TAssetFieldModelDao assetFieldModelDao;
    @Resource
    private TAssetFieldDao tAssetFieldDao;
    @Resource
    private TAssetTemplateCategoryDao tAssetTemplateCategoryDao;
    @Resource
    private TAssetTemplateCategoryService tAssetTemplateCategoryService;
    @Resource
    private CityService cityService;
    @Resource
    private AssetService assetService;
    @Resource
    private AssetDataDraftsDao assetDataDraftsDao;
    @Resource
    private CityDao cityDao;
    @Resource
    private SmsHelperService smsHelperService;
    @Resource
    private UserDao userDao;
    @Resource
    private CompanyDao companyDao;


    @Autowired
    private GatewayMqSender mqSender;


    private static final String SHBC = "SHBC";

    @Override
    public Asset getAsset(Integer id) {
        return assetDao.selectById(id);
    }

    @Override
    public List<Asset> getAllAsset() {
        return assetDao.selectAll();
    }

    @Override
    public AssetListResp getAllAssetByPage(AssetReq.QueryReq req) {
        AssetListResp resp = new AssetListResp();
        Map<String, Object> params = (Map<String, Object>) JSON.toJSON(req);
        params.put("busType", AssetEnum.BusType.AUCTION.getKey());
        PageInfo pageInfo = assetDao.getAssetList(req.getPage(), req.getPerPage(), params, " id desc ");
        List<AssetVo> itemsList = new ArrayList<>();
        List<Asset> assets = pageInfo.getList();
        for (Asset asset : assets) {
            try {
                itemsList.add(processAsset(asset));
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(asset.getId(), e);
            }
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public AssetVo getAssetById(Integer id) {
        Asset asset = getAsset(id);
        if (asset == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        return processAsset(asset);
    }

    @Override
    public AssetResp getAsset(AssetReq.AssetIdReq req) {
        AssetResp resp = new AssetResp();
        Asset asset = assetDao.selectById(req.getAssetId());
        if (asset == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (req.getPartyId() != null) {
            if (!asset.getPartyId().equals(req.getPartyId())) {
                throw new BusinessException(ApiCallResult.FAILURE, "只能查看自己的拍品");
            }
        }
        resp.setAsset(processAsset(asset));
        return resp;
    }

    private AssetVo processAsset(Asset asset) {
        AssetVo assetVo = RespConvertUtil.convertToAssetVo(asset);
        if (AssetEnum.Status.DELIVERING.equals(asset.getStatus())) {
            AuctionActivity auctionActivity = auctionActivityDao.getLatestByAssetId(asset.getId());
            if (auctionActivity != null) {
                assetVo.setActivityId(auctionActivity.getId());
            }
        }
        if (asset.getPropertyId() != null) {
            AssetProperty assetProperty = assetPropertyService.getById(asset.getPropertyId());
            if (assetProperty != null) {
                assetVo.setPropertyName(assetProperty.getName());
            }
        }
        if (StringUtils.isEmpty(asset.getCityName())) {
            String cityIds = asset.getCityId();
            if (StringUtils.isNotEmpty(cityIds)) {
                String cityId = cityIds.split(",")[0];
                City city = cityDao.selectById(cityId);
                if (city != null) {
                    assetVo.setCityName(city.getName());
                }
            }
        }
        //agency
        TAgency agency = agencyService.findByAgencyId(asset.getAgencyId());
        if (agency != null) {
            assetVo.setAgency(RespConvertUtil.convertToAgencyVo(agency));
        }
        // 委托人 相关信息
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
            PartyAccount seller = new PartyAccount();
            seller.setName(agency.getName());
            seller.setMobile(agency.getMobile());
            seller.setId(agency.getId());
            assetVo.setSeller(seller);
        } else {
            assetVo.setSeller(accountService.getPartyAccountById(asset.getPartyId()));
        }
        assetVo.setAssetDatas(Collections.EMPTY_LIST);
        assetVo.setCityName(cityService.getCityName(asset));


        // 是否是租赁权，如果是租赁权进行标的状态修改
        if("-1".equals(assetVo.getCategoryId().toString())) {
            setLeaseAssetStatus(assetVo);
            assetVo.setCategoryName("租赁权拍卖");
        }

        return assetVo;
    }

    private void setLeaseAssetStatus(AssetVo assetVo) {
        if(!AssetEnum.LeaseStatus.WAITING_RELEASE.getKey().equals(assetVo.getSubStatus())) {
            if(StringUtils.isNotBlank(AssetEnum.LeaseStatus.getRecordValueByKey(assetVo.getSubStatus()))) {
                assetVo.setStatus(assetVo.getSubStatus());
                String newDesc = AssetEnum.LeaseStatus.WAITING_SECOND_REVIEW.getKey().equals(assetVo.getSubStatus()) ?
                        AssetEnum.LeaseStatus.getShowValueByKey(assetVo.getSubStatus()):AssetEnum.LeaseStatus.getRecordValueByKey(assetVo.getSubStatus());

                assetVo.setStatusDesc(newDesc);
            }
        }
    }

    @Override
    public boolean updateAssetById(Asset asset) {
        return assetDao.updateById(asset) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel addAsset(String fields, Integer partyPrimaryId, String comeFrom, Integer spvId, AssetReq.AddReq req) {
        System.out.println("fields = " + fields);
        JSONObject jsonObject = JSON.parseObject(fields);

        //当前模板ID
        Integer templateCategoryId1 = jsonObject.getInteger("templateId");
        TAssetTemplateCategory tAssetTemplateCategory = tAssetTemplateCategoryDao.selectById(templateCategoryId1);
        //模板ID
        Integer agencyId;
        Asset asset = new Asset();
        AuctionActivity auctionActivity = new AuctionActivity();

        JSONArray templateDate = jsonObject.getJSONArray("templateDate");
        JSONArray errorArray = new JSONArray();
        JSONArray choiceArray = new JSONArray();
        JSONArray mcArray = new JSONArray();
        JSONArray sfzArray = new JSONArray();
        agencyId = conversionTemplateData(null, asset, templateCategoryId1, auctionActivity, errorArray, templateDate, choiceArray, mcArray, sfzArray);
        if (!choiceArray.isEmpty()) {
            if (choiceArray.size() > 1) {
                log.info("二选一数据为：{}", choiceArray);
                return ResponseModel.fail(ApiCallResult.EMPTY, choiceArray);
            }
        }

        if (!errorArray.isEmpty()) {
            log.info("数据错误 数据为：{}", errorArray);
            return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
        }
        AssetCondition condition = new AssetCondition();
        condition.setName(asset.getName());
        condition.setCategoryId(templateCategoryId1);
        Asset asset1;
        try {
            asset1 = assetDao.selectOneResult(condition);
        } catch (Exception e) {
            log.info("操作失败的标的的类型为：{}，名称为：{}", templateCategoryId1, asset.getName());
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "添加失败");
        }
        if (asset1 != null) {
            log.info("操作失败的标的的类型为：{}，名称为：{}", templateCategoryId1, asset.getName());
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该标的已被添加");
        }
        asset.setCategoryId(templateCategoryId1);
        asset.setPartyId(partyPrimaryId);
        asset.setAgencyId(agencyId);
        asset.setDetail("");
        asset.setStatus(AssetEnum.Status.DELIVERING);
        asset.setComeFrom(comeFrom);
        asset.setOnlined(1);
        asset.setJointStatus(getJoinStatus(jsonObject));
        if (AssetEnum.ComeFrom.PLATFORM.getKey().equals(comeFrom)) {
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(partyPrimaryId);
            if (accountBaseDto.getOperOffline()) {
                asset.setOnlined(0);
            }
        }
        asset.setOldData(false);

        int insert = assetDao.insert(asset);
        if (insert <= 0) {
            log.info("新增拍品数据失败，数据为：{}", JSON.toJSONString(asset));
            throw new BusinessException("添加拍品失敗");
        }
        String assetCode = getAssetCode(asset.getId());
        asset.setCode(assetCode);
        int id = assetDao.updateById(asset);
        if (id <= 0) {
            log.info("修改拍品数据失败，数据为：{}", JSON.toJSONString(asset));
            throw new BusinessException("添加拍品失敗");
        }
        AssetData assetData = new AssetData();
        assetData.setContent(jsonObject);
        assetData.setAssetId(asset.getId());
        if (spvId != null) {
            asset.setSpvId(spvId);
        }
        int insert2 = assetDataDao.insert(assetData);
        if (insert2 <= 0) {
            log.info("新增拍品数据失败，数据为：{}", JSON.toJSONString(assetData));
            throw new BusinessException("添加失败");
        }

        auctionActivity.setAssetId(asset.getId());
        auctionActivity.setStatus(ActivityEnum.Status.AGENCY_PENDING);
        auctionActivity.setAssetCategoryGroupId(tAssetTemplateCategory.getCategoryId());
        auctionActivity.setCategoryId(templateCategoryId1);
        auctionActivity.setAssetName(asset.getName());
        auctionActivity.setBusTypeName(asset.getBusTypeName());
        //设置可见性
        auctionActivity.setVisibilityLevel(ActivityEnum.VisibilityLevel.PLATFORM_DEFAULT.getKey());
        auctionActivity.setCode(asset.getCode() + "-" + RandomNumberGenerator.numberGenerator(5));
        auctionActivity.setAgencyId(asset.getAgencyId());

        for (int i = 0; i < sfzArray.size(); i++) {
            JSONObject jsonObject1 = sfzArray.getJSONObject(i);
            Integer gradingSecond = jsonObject1.getInteger("gradingSecond");
            for (int j = 0; j < mcArray.size(); j++) {
                JSONObject jsonObject2 = mcArray.getJSONObject(j);
                Integer gradingSecond2 = jsonObject2.getInteger("gradingSecond");
                if (gradingSecond2.equals(gradingSecond)) {
                    TPreemptivePerson preemptivePerson = new TPreemptivePerson();
                    preemptivePerson.setPreemptivePersonName(jsonObject2.getJSONArray("val").getString(0));
                    preemptivePerson.setPreemptivePersonCard(jsonObject1.getJSONArray("val").getString(0));
                    preemptivePerson.setLevel(gradingSecond.toString());
                    preemptivePerson.setAssetId(asset.getId());
                    preemptivePerson.setDelFlag(TPreemptivePerson.NODEL);
                    tPreemptivePersonDao.insert(preemptivePerson);
                    break;
                }
            }
        }

        //特殊处理 减价拍
        boolean equals = auctionActivity.getMode().equals(ActivityEnum.ActivityMode.DUTCH.getKey());
        if (equals) {
            Date beginAt = auctionActivity.getBeginAt();
            Integer reductionPeriod = auctionActivity.getReductionPeriod();

            Calendar gc = new GregorianCalendar();
            gc.setTime(beginAt);
            gc.add(GregorianCalendar.MINUTE, reductionPeriod);

            SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NORM_DATETIME_PATTERN);
            String format = sdf.format(gc.getTime());
            Date parse = DateUtil.parse(format, DateUtil.NORM_DATETIME_PATTERN);
            auctionActivity.setReducedAt(parse);
        }
        int insert1 = auctionActivityDao.insert(auctionActivity);
        if (insert1 <= 0) {
            log.info("新增活动数据失败，数据为：{}", JSON.toJSONString(auctionActivity));
            throw new BusinessException("添加活动失败");
        }
        //saveJointMap(auctionActivity,req,comeFrom,asset.getJointStatus(),asset);//保存联拍表
        releaseLotNotify(asset);

        //删除草稿箱
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(comeFrom)) {
            delDrafts(SystemConstant.AGENCY_DRAFTS_PREFIX_KEY + partyPrimaryId, AssetEnum.Drafts.AUCTION.getKey());
        } else {
            delDrafts(partyPrimaryId + "", AssetEnum.Drafts.AUCTION.getKey());
        }
        return ResponseModel.succ(auctionActivity.getId());
    }

    private void saveJointMap(AuctionActivity auctionActivity, AssetReq.AddReq req, String comeFrom, Integer jointStatus, Asset asset) {

        try {
            //为机构发布时添加
            if (AssetEnum.ComeFrom.AGENCY.getKey().equals(comeFrom)) {

                if (0 == jointStatus) {
                    List<JointActivityMap> jointActivityMapList = new ArrayList<>();
                    JointActivityMap map = new JointActivityMap();
                    map.setAgencyId(asset.getPartyId());
                    map.setActivityId(auctionActivity.getId());
                    map.setIsDel(0);
                    map.setCreateTime(DateUtil.getSysTime());
                    jointActivityMapList.add(map);
                    auctionActivityDao.batchSaveJoint(jointActivityMapList);
                }


            }

        } catch (Exception e) {
            log.error("上拍保存连拍异常，异常信息为{}", e);
        }


    }

    private Integer getJoinStatus(JSONObject jsonObject) {
        Boolean jointStatus = jsonObject.getBoolean("jointStatus");
        if (jointStatus != null) {
            if (jointStatus) {
                return 0;
            } else {
                return 1;
            }
        }
        return 0;
    }

    private void delDrafts(String partyPrimaryId, String type) {
        AssetDataDraftsCondition assetDataDraftsCondition = new AssetDataDraftsCondition();
        assetDataDraftsCondition.setType(type);
        assetDataDraftsCondition.setPartyId(partyPrimaryId);
        assetDataDraftsCondition.setDelFlag(AssetDataDrafts.notDel);
        AssetDataDrafts assetDataDrafts = assetDataDraftsDao.selectOneResult(assetDataDraftsCondition);
        if (null != assetDataDrafts) {
            assetDataDrafts.setDelFlag(AssetDataDrafts.Del);  //删除
            assetDataDraftsDao.updateById(assetDataDrafts);
        }
    }

    private void releaseLotNotify(Asset asset) {
        try {
            if (AssetEnum.ComeFrom.PLATFORM.getKey().equals(asset.getComeFrom())) {
                smsHelperService.releaseLotNotify(accountService.getNotifierMobile(asset.getPartyId()), asset.getName(), asset.getReservePrice());
                AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(asset.getPartyId());
                smsHelperService.releaseLotToAgencyNotify(accountService.getAgencyNotifierMobile(asset.getAgencyId()), asset.getName(), accountBaseDto.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            mqSender.sendTryCatchExceptionEmail(asset.getId(), e);
        }
    }

    private Integer conversionTemplateData(Integer agencyId,
                                           Asset asset,
                                           Integer categotyId,
                                           AuctionActivity auctionActivity,
                                           JSONArray errorArray,
                                           JSONArray templateDate,
                                           JSONArray choiceArray,
                                           JSONArray mcArray,
                                           JSONArray sfzArray) {
        List<String> busTypeName = new ArrayList<>();
        label:
        for (int i = 0; i < templateDate.size(); i++) {
            AssetDto json = templateDate.getJSONObject(i).toJavaObject(AssetDto.class);
            String key = formatKey(json.getKey());
            if ("1".equals(json.getFmNum())) {
                if (json.getVal().isEmpty()) {
                    continue;
                }
                if (StringUtils.isEmpty(json.getVal().get(0))) {
                    continue;
                }
                boolean numeric = NumberValidationUtils.isNumeric(json.getVal().get(0));
                if (!numeric) {
                    json.setErrorMsg("请输入正确的数值");
                    errorArray.add(json);
                }
            }
            //期望拍卖时间
            switch (key) {
                case "assetName":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("拍品名称不能为空");
                        errorArray.add(json);
                        break label;
                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("拍品名称不能为空");
                        errorArray.add(json);
                    } else {
                        findAssetName(asset, categotyId, json);
                    }

                    break;
                case "xzpmfs":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("拍卖方式不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("拍卖方式不能为空");
                        errorArray.add(json);

                    }
                    if (json.getValNum().get(0).equals(65)) {
                        asset.setExpectedMode(AssetEnum.ExpectedMode.ENGLISH);
                        auctionActivity.setMode(AssetEnum.ExpectedMode.ENGLISH.getKey());
                    } else if (json.getValNum().get(0).equals(66)) {
                        asset.setExpectedMode(AssetEnum.ExpectedMode.DUTCH);
                        auctionActivity.setMode(AssetEnum.ExpectedMode.DUTCH.getKey());
                    } else if (json.getValNum().get(0).equals(197)) {
                        asset.setExpectedMode(AssetEnum.ExpectedMode.SEALED);
                        auctionActivity.setMode(AssetEnum.ExpectedMode.SEALED.getKey());
                    } else if (json.getValNum().get(0).equals(207)) {
                        asset.setExpectedMode(AssetEnum.ExpectedMode.PUBLIC);
                        auctionActivity.setMode(AssetEnum.ExpectedMode.PUBLIC.getKey());
                    }
                    break;
                case "images":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("浏览图片不能为空");
                        errorArray.add(json);

                    } else {
                        JSONArray image = new JSONArray();
                        for (String imgs : json.getVal()) {
                            JSONObject jsonImg = JSONObject.parseObject(imgs);
                            String url = jsonImg.getString("url");
                            image.add(url);
                        }
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("images", image);
                        asset.setExtra(jsonObject2);
                    }

                    break;
                case "tuneReportUrl":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("尽调文件URL不能为空");
                        errorArray.add(json);

                    } else {
                        JSONArray image = new JSONArray();
                        for (String imgs : json.getVal()) {
                            JSONObject jsonImg = JSONObject.parseObject(imgs);
                            String url = jsonImg.getString("url");
                            image.add(url);
                        }
                        JSONObject jsonObject2 = new JSONObject();
                        jsonObject2.put("images", image);
                        asset.setTuneReportUrl(jsonObject2);
                    }
                    break;
                case "deposit":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("保证金不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("保证金不能为空");
                        errorArray.add(json);

                    }

                    boolean positiveDecimalOrInteger = NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0));
                    if (!positiveDecimalOrInteger) {
                        json.setErrorMsg("请输入正确的保证金数值，不能为负值");
                        errorArray.add(json);

                    } else {
                        auctionActivity.setDeposit(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }

                    break;
                case "city":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("城市不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("城市不能为空");
                        errorArray.add(json);

                    } else {
                        Set<String> provinces = new LinkedHashSet<>();
                        Set<String> cities = new LinkedHashSet<>();
                        Set<String> areas = new LinkedHashSet<>();
                        for (String val : json.getVal()) {
                            JSONObject jsonObject3 = JSONObject.parseObject(val);
                            if (jsonObject3.containsKey("id") && StringUtils.isNotBlank(jsonObject3.getString("id"))) {
                                cities.add(jsonObject3.getString("id"));
                            }
                            if (jsonObject3.containsKey("provinceId") && StringUtils.isNotBlank(jsonObject3.getString("provinceId"))) {
                                provinces.add(jsonObject3.getString("provinceId"));
                            }
                            if (jsonObject3.containsKey("areaId") && StringUtils.isNotBlank(jsonObject3.getString("areaId"))) {
                                areas.add(jsonObject3.getString("areaId"));
                            }
                        }
                        asset.setCityId(String.join(",", cities));
                        asset.setProvinceId(String.join(",", provinces));
                        asset.setAreaId(String.join(",", areas));
                    }
                    break;
                case "increment":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("加价幅度不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("加价幅度不能为空");
                        errorArray.add(json);

                    }
                    if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的加价幅度数值，不可为负值");
                        errorArray.add(json);

                    } else {
                        auctionActivity.setIncrement(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                    break;
                case "reduction":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("减价幅度不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("减价幅度不能为空");
                        errorArray.add(json);

                    }
                    if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的减价幅度数值，不可为负值");
                        errorArray.add(json);

                    } else {
                        auctionActivity.setReduction(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }


                    break;
                case "reductionPeriod":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("减价周期不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("减价周期不能为空");
                        errorArray.add(json);

                    }
                    if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的减价周期数值，不可为负值");
                        errorArray.add(json);

                    } else {
                        auctionActivity.setReductionPeriod(Integer.valueOf(json.getVal().get(0)));
                    }


                    break;
                case "agencyName":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("拍卖机构不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("拍卖机构不能为空");
                        errorArray.add(json);

                    } else {
                        String s = json.getVal().get(0);
                        JSONObject jsonObject3 = JSONObject.parseObject(s);
                        Integer id = jsonObject3.getInteger("id");
                        if (id == null) {
                            throw new BusinessException("送拍机构不能为空");
                        }
                        agencyId = id;
                        asset.setAgencyId(id);
                    }
                    break;
                case "startingPrice":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("起拍价不能为空");
                        errorArray.add(json);
                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("起拍价不能为空");
                        errorArray.add(json);
                    }
                    if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的起拍价数值，不可为负值");
                        errorArray.add(json);
                    } else {
                        auctionActivity.setStartingPrice(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                        asset.setStartingPrice(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }
                    break;
                case "reservePrice":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("保留价不能为空");
                        errorArray.add(json);
                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("保留价不能为空");
                        errorArray.add(json);
                    }
                    if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的保留价数值，不可为负值");
                        errorArray.add(json);
                    } else {
                        auctionActivity.setReservePrice(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                        asset.setReservePrice(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }

                    break;
                case "previewAt":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("预展时间不能为空");
                        errorArray.add(json);
                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("预展时间不能为空");
                        errorArray.add(json);
                    }
                    boolean date = DateUtil.isValidDate(json.getVal().get(0));
                    if (!date) {
                        json.setErrorMsg("请输入正确的日期");
                        errorArray.add(json);
                    } else {
                        Date parse = DateUtil.parse(json.getVal().get(0), DateUtil.NORM_DATETIME_PATTERN);
                        boolean after = parse.before(new Date());
                        if (after) {
                            json.setErrorMsg("预展时间必须大于当前时间");
                            errorArray.add(json);
                            auctionActivity.setPreviewAt(DateUtil.parse(json.getVal().get(0), DateUtil.NORM_DATETIME_PATTERN));
                        } else {
                            auctionActivity.setPreviewAt(DateUtil.parse(json.getVal().get(0), DateUtil.NORM_DATETIME_PATTERN));
                        }
                    }


                    break;
                case "tuneReport":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("尽调报告价格不能为空");
                        errorArray.add(json);
                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("尽调报告价格不能为空");
                        errorArray.add(json);
                    }
                    Float integer = Float.valueOf((json.getVal().get(0)));
                    if (integer < 100) {
                        json.setErrorMsg("尽调报告价格不能小于100");
                        errorArray.add(json);
                    }
                    if (integer > 1500) {
                        json.setErrorMsg("尽调报告价格不能大于1500");
                        errorArray.add(json);
                    } else {
                        asset.setTuneReport(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }

                    break;
                case "tuneReportAuthorization":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("尽调报告授权记录不能为空");
                        errorArray.add(json);
                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("尽调报告授权记录不能为空");
                        errorArray.add(json);
                    } else {
                        Boolean isCheck = Boolean.valueOf(json.getVal().get(0));
                        asset.setTuneReportAuthorization(isCheck);
                    }
                    break;
                case "commissionPercentSeller":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("卖方佣金比不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("卖方佣金比不能为空");
                        errorArray.add(json);

                    }
                    if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的卖方佣金比数值，不可为负值");
                        errorArray.add(json);

                    } else {
                        auctionActivity.setCommissionPercentSeller(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }


                    break;
                case "commissionPercentBuyer":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("买方佣金比不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("买方佣金比不能为空");
                        errorArray.add(json);

                    }
                    if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的买方佣金比数值，不可为负值");
                        errorArray.add(json);

                    } else {
                        auctionActivity.setCommissionPercentBuyer(new BigDecimal(json.getVal().get(0)).setScale(2, BigDecimal.ROUND_HALF_UP));
                    }


                    break;
                case "contactName":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("项目联系人不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("项目联系人不能为空");
                        errorArray.add(json);

                    } else {
                        asset.setContactName(json.getVal().get(0));
                    }
                    break;
                case "contactPhone":
                case "econtactPhone":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("联系人联系方式不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("联系人联系方式不能为空");
                        errorArray.add(json);

                    } else {
                        asset.setContactPhone(json.getVal().get(0));
                    }
                    break;
                case "qwpmsj":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("开始结束时间不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("开始结束时间不能为空");
                        errorArray.add(json);

                    } else {
                        Date beginAt = DateUtil.parse(json.getVal().get(0), DateUtil.NORM_DATETIME_PATTERN);
                        Date endAt = DateUtil.parse(json.getVal().get(1), DateUtil.NORM_DATETIME_PATTERN);
                        asset.setBeginAt(beginAt);
                        asset.setEndAt(endAt);

                        Date previewAt = auctionActivity.getPreviewAt();


                        boolean after = previewAt.after(beginAt);
                        if (after) {
                            json.setErrorMsg("开始时间不能小于预展时间");
                            errorArray.add(json);

                        }
                        if (!endAt.after(beginAt)) {
                            json.setErrorMsg("结束时间不能小于等于开始时间");
                            errorArray.add(json);
                        } else {
                            if (1 == systemProperties.getCheckPreviewAt()) {
                                log.info("预展时间为：{}，开始时间为：{}", DateUtil.format(previewAt, DateUtil.NORM_DATETIME_PATTERN), DateUtil.format(beginAt, DateUtil.NORM_DATETIME_PATTERN));
                                int day = DateUtil.differentDaysByMillisecond(previewAt, beginAt);
                                if (day < systemProperties.getDifferenceDay()) {
                                    json.setErrorMsg("开始时间不能小于预展时间" + systemProperties.getDifferenceDay() + "天");
                                    errorArray.add(json);
                                } else {
                                    auctionActivity.setBeginAt(beginAt);
                                    auctionActivity.setEndAt(endAt);
                                }
                            } else {
                                auctionActivity.setBeginAt(beginAt);
                                auctionActivity.setEndAt(endAt);
                            }
                        }

                    }
                    break;
                case "payDays":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("支付时间不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("支付时间不能为空");
                        errorArray.add(json);

                    }
                    if (!NumberValidationUtils.isPositiveInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的数值，不可为负值");
                        errorArray.add(json);

                    } else {
                        asset.setPayDays(Integer.parseInt(json.getVal().get(0)));
                    }


                    break;
                case "debtPrincipal":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("债权本金不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("债权本金不能为空");
                        errorArray.add(json);

                    }
                    if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的债权本金数值，不可为负值");
                        errorArray.add(json);

                    } else {
                        asset.setDebtPrincipal(new BigDecimal(json.getVal().get(0)));
                    }

                    break;
                case "debtInterest":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("债权利息不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("债权利息不能为空");
                        errorArray.add(json);

                    }
                    if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的债权利息数值，不可为负值");
                        errorArray.add(json);

                    } else {
                        asset.setDebtInterest(new BigDecimal(json.getVal().get(0)));
                    }
                    break;
                case "handoverDays":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("线下签约时间不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("线下签约时间不能为空");
                        errorArray.add(json);

                    }
                    if (!NumberValidationUtils.isPositiveInteger(json.getVal().get(0))) {
                        json.setErrorMsg("请输入正确的数值，不可为负值");
                        errorArray.add(json);

                    } else {

                        asset.setHandoverDays(Integer.parseInt(json.getVal().get(0)));
                    }
                    break;

                case "yxgmrmc":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("优先购买人不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("优先购买人身份证不能为空");
                        errorArray.add(json);
                    } else {
                        sfzArray.add(json);
                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("优先购买人不能为空");
                        errorArray.add(json);

                    } else {
                        mcArray.add(json);
                    }
                    break;
                case "yxgmrsfz":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("优先购买人身份证不能为空");
                        errorArray.add(json);

                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("优先购买人身份证不能为空");
                        errorArray.add(json);
                    } else {
                        sfzArray.add(json);
                    }
                    break;
                case "specialDetail":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("特别告知不能为空");
                        errorArray.add(json);
                    }
                    if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("特别告知不能为空");
                        errorArray.add(json);
                    } else {
                        asset.setSpecialDetail(json.getVal().get(0));
                    }
                    break;
                case "transferAnnouncementUrl":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("该选项不能为空");
                        choiceArray.add(json);
                    } else if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("该选项不能为空");
                        choiceArray.add(json);
                    }
                    break;
                case "claimsTransferUrl":
                    if (json.getVal().isEmpty()) {
                        json.setErrorMsg("该选项不能为空");
                        choiceArray.add(json);
                    } else if (StringUtils.isEmpty(json.getVal().get(0))) {
                        json.setErrorMsg("该选项不能为空");
                        choiceArray.add(json);
                    }

                    break;

                case "jzwlx":
                    if (!json.getVal().isEmpty()) {
                        busTypeName.add(json.getVal().get(0));
                    }
                    break;
                default:
            }
        }
        asset.setBusTypeName(String.join(",", busTypeName));
        return agencyId;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel assetEdit(AssetReq.AddReq req) {
        Integer assetId = req.getAssetId();
        String fields = req.getFields();
        log.info("正在请求修改拍品的ID为：{}，修改参数为：{}", assetId, fields);
        log.info("修改拍品的ID为========{}，开始修改", assetId);
        Asset asset = assetDao.selectById(assetId);
        if (asset == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "拍品不存在");
        }
        if (req.getSideType().getKey().equals(SideType.PLATFORM.getKey())) {
            if (!asset.getPartyId().equals(req.getPartyId())) {
                log.info("上传拍品的上传人ID为：{}，登录人ID为：{}", asset.getPartyId(), req.getPartyId());
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "仅能修改本人上传的拍品");
            }
            AssetEnum.Status status1 = asset.getStatus();
            if (!(status1.equals(AssetEnum.Status.REJECT) || status1.equals(AssetEnum.Status.FAILED) || status1.equals(AssetEnum.Status.WITHDRAW))) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "拍品修改状态不正确");
            }
        }

        if (req.getSideType().getKey().equals(SideType.PLATFORM.getKey())) {
            AssetEnum.Status status = asset.getStatus();
            if (AssetEnum.Status.FAILED.getKey().equals(status.getKey())) {
                log.info("模板数据1为：{}", fields);
                Object object = assetService.addAsset(fields, req.getPartyId(), asset.getComeFrom(), req.getSpvId(), req);
                return (ResponseModel) object;
            }
        }

        if (req.getSideType().getKey().equals(SideType.AGENCY.getKey())) {
            String comeFrom = asset.getComeFrom();

            AssetEnum.Status status = asset.getStatus();
            if (AssetEnum.ComeFrom.AGENCY.getKey().equals(comeFrom)) {
                if (AssetEnum.Status.FAILED.getKey().equals(status.getKey())) {
                    log.info("模板数据2为：{}", fields);
                    Object object = assetService.addAsset(fields, req.getPartyId(), asset.getComeFrom(), req.getSpvId(), req);
                    return (ResponseModel) object;
                }
            }
        }

        AuctionActivity auctionActivity = auctionActivityDao.getLatestByAssetId(assetId);
        AssetData assetData = assetDataDao.findAssetData(assetId);
        Integer agencyId = asset.getAgencyId();
        JSONObject jsonObject = JSON.parseObject(fields);
        JSONArray templateDate = jsonObject.getJSONArray("templateDate");
        JSONArray errorArray = new JSONArray();
        JSONArray choiceArray = new JSONArray();
        Asset copyAsset = new Asset();
        BeanUtils.copyProperties(asset, copyAsset);
        JSONArray mcArray = new JSONArray();
        JSONArray sfzArray = new JSONArray();
        Integer integer = conversionTemplateData(agencyId, asset, asset.getCategoryId(), auctionActivity, errorArray, templateDate, choiceArray, mcArray, sfzArray);

        if (SideType.AGENCY.equals(req.getSideType()) || SideType.ADMIN.equals(req.getSideType())) {
            if (!asset.getReservePrice().equals(copyAsset.getReservePrice())) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "机构和ADMIN平台不能修改保留价");
            }
        }

        if (!choiceArray.isEmpty()) {
            if (choiceArray.size() > 1) {
                log.info("二选一数据为：{}", choiceArray);
                return ResponseModel.fail(ApiCallResult.EMPTY, choiceArray);
            }
        }

        if (!errorArray.isEmpty()) {
            log.info("数据错误 数据为：{}", errorArray);
            return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
        }

        asset.setAgencyId(integer);
        AssetEnum.Status status = asset.getStatus();
        boolean sendNotify = false;
        if (req.getSideType().getKey().equals(SideType.PLATFORM.getKey())) {
            if (AssetEnum.Status.WITHDRAW.getKey().equals(status.getKey()) ||
                    AssetEnum.Status.REJECT.getKey().equals(status.getKey())) {
                asset.setStatus(AssetEnum.Status.DELIVERING);
                auctionActivity.setStatus(ActivityEnum.Status.AGENCY_PENDING);
                sendNotify = true;
            }
        }
        int id = assetDao.updateById(asset);
        if (id < 0) {
            log.info("修改标的失败数据为：{}", JSON.toJSONString(asset));
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改标的失败");
        }

        //修改时先删除原有的优先购买人
        TPreemptivePersonCondition condition = new TPreemptivePersonCondition();
        condition.setAssetId(asset.getId());
        List<TPreemptivePerson> tPreemptivePeople = tPreemptivePersonDao.selectList(condition);
        for (TPreemptivePerson preemptivePerson : tPreemptivePeople) {
            preemptivePerson.setDelFlag(TPreemptivePerson.DEL);
            tPreemptivePersonDao.updateById(preemptivePerson);
        }
        for (int i = 0; i < sfzArray.size(); i++) {
            JSONObject jsonObject1 = sfzArray.getJSONObject(i);
            Integer gradingSecond = jsonObject1.getInteger("gradingSecond");
            for (int j = 0; j < mcArray.size(); j++) {
                JSONObject jsonObject2 = mcArray.getJSONObject(j);
                Integer gradingSecond2 = jsonObject2.getInteger("gradingSecond");
                if (gradingSecond2.equals(gradingSecond)) {
                    TPreemptivePerson preemptivePerson = new TPreemptivePerson();
                    preemptivePerson.setPreemptivePersonName(jsonObject2.getJSONArray("val").getString(0));
                    preemptivePerson.setPreemptivePersonCard(jsonObject1.getJSONArray("val").getString(0));
                    preemptivePerson.setLevel(gradingSecond.toString());
                    preemptivePerson.setAssetId(asset.getId());
                    preemptivePerson.setDelFlag(TPreemptivePerson.NODEL);
                    tPreemptivePersonDao.insert(preemptivePerson);
                    break;
                }
            }
        }


        assetData.setContent(jsonObject);
        int i2 = assetDataDao.updateById(assetData);
        if (i2 < 0) {
            log.info("修改标的data失败数据为：{}", JSON.toJSONString(assetData));
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改标的失败");
        }
        auctionActivity.setAssetName(asset.getName());
        auctionActivity.setAgencyId(asset.getAgencyId());
        auctionActivity.setBusTypeName(asset.getBusTypeName());
        int i1 = auctionActivityDao.updateById(auctionActivity);
        if (i1 < 0) {
            log.info("修改活动失败数据为：{}", JSON.toJSONString(auctionActivity));
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改活动数据失败");
        }
        log.info("修改拍品的ID为：{}，修改后的参数为：{}，结束修改", assetId, assetData);
        if (sendNotify) {
            releaseLotNotify(asset);
        }
        return ResponseModel.succ(auctionActivity.getId());
    }


    @Override
    public PageInfo myAsset(AssetReq.QueryReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        Map<String, Object> params = new HashMap<>(16);
        if (req.getPropertyId() != null) {
            params.put("propertyId", req.getPropertyId());
        }
        if (StringUtils.isNotEmpty(req.getCategoryId())) {
            params.put("categoryId", req.getCategoryId());
        }
        if (req.getPartyId() != null) {
            params.put("partyId", req.getPartyId());
        }
        if (req.getCityId() != null) {
            params.put("cityId", req.getCityId());
        }
        if (req.getName() != null) {
            params.put("name", req.getName());
        }


        List<Map> maps = assetDao.myAsset(params);
        for (Map map : maps) {
            if ("-1".equals(String.valueOf(map.get("categoryId")))) {
                formatLeaseInfo(map); //格式化租赁权信息

            }
        }
        return new PageInfo<>(maps);
    }

    private void formatLeaseInfo(Map map) {

        map.put("categoryName", "租赁权拍卖");

        String statusStr = String.valueOf(map.get("statusStr"));

        String status = String.valueOf(map.get("status"));

        //大状态为内部审核时 获取小状态
        if(AssetEnum.Status.PENDING.getKey().equals(String.valueOf(map.get("status")))){

            String subStatus = String.valueOf(map.get("subStatus"));

            //终审退回时 就是待初审
            if(AssetEnum.LeaseStatus.SECOND_REVIEW_REJECTION.getKey().equals(subStatus)){
                subStatus = AssetEnum.LeaseStatus.WAITING_FIRST_REVIEW.getKey();
            }

            statusStr = AssetEnum.LeaseStatus.getValueByKey(subStatus);
            status = subStatus;
        }

        map.put("statusStr", statusStr);
        map.put("status", status);

     }

    @Override
    public AssetResp update(AssetReq.UpdateReq req) {
        AssetResp resp = new AssetResp();
        Asset asset = assetDao.selectById(req.getId());
        if (asset == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        asset = ReqConvertUtil.convertToAsset(req);
        int result = assetDao.updateById(asset);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Transactional
    @Override
    public AssetResp withdrawAsset(AssetReq.AssetIdReq req) {
        AssetResp resp = new AssetResp();
        Asset asset = assetDao.selectById(req.getAssetId());
        if (asset == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!asset.getPartyId().equals(req.getPartyId())) {
            throw new BusinessException(ApiCallResult.PERMISSION_DENIED, "无法撤回别人的标的");
        }
        if (AssetEnum.Status.PENDING.equals(asset.getStatus())) {
            throw new BusinessException(ApiCallResult.FAILURE, "不能撤回pending状态的标的");
        }
        int result;
        AuctionActivity auctionActivity = auctionActivityDao.getLatestByAssetId(asset.getId());
        if (auctionActivity != null) {
            if (!AuctionActivity.HIDDEN_STATUS.contains(auctionActivity.getStatus())) {
                throw new BusinessException(ApiCallResult.FAILURE, "无法取消已经上线的活动");
            }
            auctionActivity.setStatus(ActivityEnum.Status.CANCELLED);
            result = auctionActivityDao.updateStatus(auctionActivity.getId(), ActivityEnum.Status.CANCELLED);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        result = assetDao.updateStatus(asset.getId(), AssetEnum.Status.WITHDRAW);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return resp;
    }

    @Override
    public Object assetDetail(AssetReq.AddReq req) {
        Integer assetId = req.getAssetId();
        AssetData assetData = assetDataDao.findAssetData(assetId);
        Asset asset = assetDao.selectById(assetId);
        if (asset == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标的不存在");
        }
        if (assetData == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标的信息不存在");
        }
        if (req.getSideType().getKey().equals(SideType.PLATFORM.getKey())) {
            if (!asset.getPartyId().equals(req.getPartyId())) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "仅能查询本人上传的拍品");
            }
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("id", assetData.getId());
        map.put("assetId", assetData.getAssetId());
        map.put("jointStatus", asset.getJointStatus());//连拍标志


        JSONObject content = assetData.getContent();
        Integer templateId = content.getInteger("templateId");
        JSONArray templateDate = content.getJSONArray("templateDate");
        JSONArray newData = new JSONArray();
        //返回前端时要在json数据中加入标题和模块名称
        for (int i = 0; i < templateDate.size(); i++) {
            JSONObject jsonObject = templateDate.getJSONObject(i);
            Integer title = jsonObject.getInteger("title");
            TAssetFieldGroup tAssetFieldGroup = tAssetFieldGroupDao.selectById(title);
            if (tAssetFieldGroup != null) {
                if (SideType.AGENCY.getKey().equals(req.getSideType().getKey())) {
                    if ("委托方信息".equals(tAssetFieldGroup.getName())) {
                        jsonObject.put("titleName", "拍卖机构信息");
                    }
                } else {
                    jsonObject.put("titleName", tAssetFieldGroup.getName());
                }
            }
            Integer titleSubset = jsonObject.getInteger("titleSubset");
            if (titleSubset != null) {
                TAssetFieldModel tAssetFieldModel = assetFieldModelDao.selectById(titleSubset);
                if (tAssetFieldModel != null) {
                    jsonObject.put("titleSubsetName", tAssetFieldModel.getModelTitle());
                }
            }
            String keyStr = jsonObject.getString("key");
            String key = formatKey(keyStr);
            String type = jsonObject.getString("type");
            if (!"SELECT".equals(type)) {
                TAssetField field = tAssetFieldDao.findUnit(key);
                if (field == null) {
                    continue;
                }
                String unit = field.getUnit();
                //设置单位
                jsonObject.put("unit", unit);
            }

            if ("SEDATE".equals(type) || "DATE".equals(type)) {
                JSONArray val = jsonObject.getJSONArray("val");
                JSONArray newVal = new JSONArray();
                if (!val.isEmpty()) {
                    for (int j = 0; j < val.size(); j++) {
                        String dateStr = val.getString(j);
                        if (StringUtils.isNotEmpty(dateStr)) {
                            boolean validDate = DateUtil.isValidDate(dateStr);
                            if (validDate) {
                                String format = DateUtil.format(DateUtil.parse(dateStr, DateUtil.NORM_DATETIME_PATTERN), DateUtil.NORM_DATETIME_PATTERN);
                                newVal.add(format);
                            }
                        }
                    }
                    jsonObject.put("val", newVal);
                }
            }
            newData.add(jsonObject);
        }
        content.put("templateDate", newData);
        map.put("files", content);
        TAssetTemplateCategory tAssetTemplateCategory = tAssetTemplateCategoryDao.selectById(templateId);
        log.info("查询模板的数据为：{}", JSON.toJSONString(tAssetTemplateCategory));
        if (tAssetTemplateCategory == null) {
            log.info("查询的模板ID为：{}，原始数据为：{}", templateDate, content);
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");
        }

        if (req.getSideType().getKey().equals(SideType.AGENCY.getKey())) {
            if ("1".equals(asset.getComeFrom())) {
                map.put("permission", false);
            } else {
                map.put("permission", true);
            }
        } else {
            map.put("permission", false);
        }
        map.put("categoryId", tAssetTemplateCategory.getCategoryId());
        map.put("categoryOptionId", tAssetTemplateCategory.getCategoryOptionId());
        return map;
    }

    private List<AssetRes> convertOldData(Integer assetId, AssetCopy asset, AssetDataCopy assetData) {

        List<AssetRes> dtoArray = new TreeList<>();
        JSONObject oldContent = assetData.getContent();
        String name = asset.getName();
        convertAssetDto(dtoArray, new String[]{name}, new String[]{}, new Integer[]{}, "拍品名称", "拍品信息", "TEXT", "18", "assetName000");

        JSONArray extra = asset.getExtra().getJSONArray("images");
        Object[] strings = new Object[extra.size()];
        for (int i = 0; i < extra.size(); i++) {
            String string = extra.getString(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", string);
            jsonObject.put("name", UUID.randomUUID().toString().replace("-", ""));
            strings[i] = jsonObject;
        }
        convertAssetDto(dtoArray, strings, new String[]{}, new Integer[]{}, "拍品预展图片", "拍卖活动信息", "FIlEIMG", "20", "images000");

        AgencyCopy agency = agencyCopyDao.selectById(asset.getAgencyId());
        JSONObject agencyJson = new JSONObject();
        agencyJson.put("name", agency.getName());
        agencyJson.put("id", agency.getId());
        convertAssetDto(dtoArray, new Object[]{agencyJson}, new String[]{}, new Integer[]{}, "机构名称", "拍卖活动信息", "SEARCH", "20", "agencyName000");

        Integer assetCityId = asset.getCityId();
        City city = cityService.getByCityId(assetCityId);
        JSONObject json = new JSONObject();
        json.put("name", city.getName());
        json.put("id", city.getId());
        convertAssetDto(dtoArray, new Object[]{json}, new String[]{}, new Integer[]{}, "所在城市", "拍卖活动信息", "CITY", "20", "city000");

        AuctionActivityCopyCondition condition = new AuctionActivityCopyCondition();
        condition.setAssetId(assetId);
        List<AuctionActivityCopy> auctionActivities = auctionActivityCopyDao.selectList(condition);
        List<AuctionActivityCopy> onlineActivity = new TreeList<>();
        List<AuctionActivityCopy> filedActivity = new TreeList<>();
        AuctionActivityCopy activity = null;
        if (!auctionActivities.isEmpty() && auctionActivities.size() > 1) {
            for (AuctionActivityCopy auctionActivity : auctionActivities) {
                if (ActivityEnum.Status.ONLINE.getKey().equals(auctionActivity.getStatus())) {
                    onlineActivity.add(auctionActivity);
                } else if (ActivityEnum.Status.FAILED.getKey().equals(auctionActivity.getStatus())) {
                    filedActivity.add(auctionActivity);
                }
            }

            if (onlineActivity.size() > 0) {
                activity = auctionActivities.get(0);
            }

            if (filedActivity.size() > 0) {
                activity = auctionActivities.get(0);
            }
        }
        if (auctionActivities.size() == 1) {
            activity = auctionActivities.get(0);
        }
        if (activity != null) {
            BigDecimal deposit = activity.getDeposit();
            convertAssetDto(dtoArray, new String[]{deposit.toString()}, new String[]{}, new Integer[]{}, "保证金", "拍卖活动信息", "CITY", "20", "deposit000");

            String mode = activity.getMode();
            String[] array = new String[1];
            Integer[] idArray = new Integer[1];
            String[] numArray = new String[1];
            if (mode.equals(ActivityEnum.ActivityMode.ENGLISH.getKey())) {
                String value = ActivityEnum.ActivityMode.ENGLISH.getValue();
                array[0] = value;
                numArray[0] = "65";
                idArray[0] = 0;
            }
            if (mode.equals(ActivityEnum.ActivityMode.DUTCH.getKey())) {
                String value = ActivityEnum.ActivityMode.DUTCH.getValue();
                array[0] = value;
                numArray[0] = "66";
                idArray[0] = 0;
            }
            if (mode.equals(ActivityEnum.ActivityMode.SEALED.getKey())) {
                String value = ActivityEnum.ActivityMode.SEALED.getValue();
                array[0] = value;
                numArray[0] = "197";
                idArray[0] = 0;
            }
            if (mode.equals(ActivityEnum.ActivityMode.PUBLIC.getKey())) {
                String value = ActivityEnum.ActivityMode.PUBLIC.getValue();
                array[0] = value;
                numArray[0] = "207";
                idArray[0] = 0;
            }
            convertAssetDto(dtoArray, array, numArray, idArray, "选择拍卖方式", "拍卖活动信息", "SELECT", "20", "xzpmfs000");
            convertAssetDto(dtoArray, new Object[]{asset.getReservePrice()}, null, null, "保留价", "拍卖活动信息", "TEXT", "20", "reservePrice000");
            convertAssetDto(dtoArray, new Object[]{activity.getStartingPrice()}, null, null, "起拍价", "拍卖活动信息", "TEXT", "20", "startingPrice000");
            convertAssetDto(dtoArray, new Object[]{activity.getIncrement()}, null, null, "加价幅度", "拍卖活动信息", "TEXT", "20", "increment000");
            convertAssetDto(dtoArray, new Object[]{DateUtil.format(activity.getPreviewAt(), DateUtil.NORM_DATETIME_PATTERN)}, null, null, "预展时间", "拍卖活动信息", "DATE", "20", "previewAt000");
            convertAssetDto(dtoArray, new Object[]{activity.getCommissionPercentSeller().toString()}, null, null, "卖方佣金比", "拍卖活动信息", "TEXT", "20", "commissionPercentSeller000");
            convertAssetDto(dtoArray, new Object[]{activity.getCommissionPercentBuyer().toString()}, null, null, "买方佣金比", "拍卖活动信息", "TEXT", "20", "commissionPercentBuyer000");
            convertAssetDto(dtoArray, new Object[]{DateUtil.format(activity.getBeginAt(), DateUtil.NORM_DATETIME_PATTERN), DateUtil.format(activity.getEndAt(), DateUtil.NORM_DATETIME_PATTERN)}, null, null, "期望拍卖时间", "拍卖活动信息", "DATE", "20", "qwpmsj000");
            convertAssetDto(dtoArray, new Object[]{asset.getPayDays()}, null, null, "支付时间", "拍卖活动信息", "TEXT", "20", "payDays000");
            convertAssetDto(dtoArray, new Object[]{asset.getHandoverDays()}, null, null, "线下签约时间", "拍卖活动信息", "TEXT", "20", "handoverDays000");
            convertAssetDto(dtoArray, new Object[]{"无"}, new String[]{"15"}, new Integer[0], "优先购买人", "拍卖活动信息", "SELECT", "20", "yxgmr000");
            convertAssetDto(dtoArray, new Object[]{asset.getSpecialDetail()}, null, null, "特别告知", "拍卖活动信息", "TEXTAREA", "20", "specialDetail000");
            convertAssetDto(dtoArray, new Object[]{""}, null, null, "资产亮点", "拍卖活动信息", "TEXTAREA", "20", "zcld000");

            //委托人信息
            String name1;
            // 委托人证件号
            String idOrLicenceNo;

            User user = userDao.selectById(asset.getPartyId());
            if (user == null) {
                Company company = companyDao.selectById(asset.getPartyId());
                if (company == null) {
                    throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询委托人失败");
                } else {
                    name1 = company.getName();
                    idOrLicenceNo = company.getLicense();
                }
            } else {
                name1 = user.getName();
                idOrLicenceNo = user.getCertificateNumber();
            }


            convertAssetDto(dtoArray, new Object[]{name1}, null, null, "委托人名称", "委托方信息", "USERNAME", "21", "partyId000");
            convertAssetDto(dtoArray, new Object[]{idOrLicenceNo}, null, null, "委托人证件号码", "委托方信息", "USERPHONE", "21", "partyNumber000");


            if (2 == asset.getCategoryId()) {
                String czbh = oldContent.getString("czbh");
                convertAssetDto(dtoArray, new Object[]{czbh}, null, null, "产权证号", "标的信息", "TEXT", "19", "cqzh000");
                convertAssetDto(dtoArray, new Object[]{"有抵押", "抵押", "不动产抵押物"}, new Object[]{"1", "18", "1"}, new Object[]{0, 0, 0}, "抵押情况", "标的信息", "SELECT", "19", "dyqk000");
                convertAssetDto(dtoArray, new Object[]{"住宅"}, new Object[]{"16"}, new Object[]{0}, "建筑物类型", "标的信息", "SELECT", "19", "jzwlx000");
                convertAssetDto(dtoArray, new Object[]{"非第一顺位"}, new Object[]{"42"}, new Object[]{1}, "抵押顺位", "标的信息", "SELECT", "19", "dysw000");
                convertAssetDto(dtoArray, new Object[]{"企业"}, new Object[]{"56"}, new Object[]{1}, "债务人", "拍品信息", "SELECT", "18", "zwr000");
                String mj = oldContent.getString("mj");
                convertAssetDto(dtoArray, new Object[]{mj}, null, null, "建筑面积", "标的信息", "TEXT", "19", "jzmj000");
                String dz = oldContent.getString("dz");
                convertAssetDto(dtoArray, new Object[]{dz}, null, null, "抵押物地址", "标的信息", "TEXT", "19", "dywdz000");
                String zgedyje = oldContent.getString("zgedyje");
                convertAssetDto(dtoArray, new Object[]{zgedyje}, null, null, "最高额抵押金额", "标的信息", "TEXT", "19", "zgedyje000");
                String dysw = oldContent.getString("dysw");
                convertAssetDto(dtoArray, new Object[]{dysw}, null, null, "顺位情况", "标的信息", "TEXT", "19", "syqk000");

                String zqbj = oldContent.getString("zqbj");
                convertAssetDto(dtoArray, new Object[]{zqbj}, null, null, "债权本金", "拍品信息", "TEXT", "18", "debtPrincipal000");
                String zqbx = oldContent.getString("zqbx");
                convertAssetDto(dtoArray, new Object[]{zqbx}, null, null, "债权利息", "拍品信息", "TEXT", "18", "debtInterest000");
                String zwrmc = oldContent.getString("zwrmc");
                convertAssetDto(dtoArray, new Object[]{zwrmc}, null, null, "企业名称", "拍品信息", "TEXT", "18", "qymc000");
                String zwrsfzhm = oldContent.getString("zwrsfzhm");
                convertAssetDto(dtoArray, new Object[]{zwrsfzhm}, null, null, "营业执照", "拍品信息", "TEXT", "18", "yyzz000");

                JSONObject jsonObject = new JSONObject();
                String qt1 = oldContent.getString("qt1");
                if (StringUtils.isNotEmpty(qt1)) {
                    jsonObject.put("name", qt1.substring(qt1.lastIndexOf("/") + 1));
                    jsonObject.put("url", qt1);
                    convertAssetDto(dtoArray, new Object[]{jsonObject}, null, null, "授权委托书", "文件上传", "FIlE", "22", "swtwj000");
                }

                List<Object> dyw = new TreeList<>();
                JSONObject dywwjJsonObject = new JSONObject();
                String dywfcz = oldContent.getString("dywfcz");
                String dywtxqz = oldContent.getString("dywtxqz");
                if (StringUtils.isNotEmpty(dywfcz)) {
                    dywwjJsonObject.put("name", dywfcz.substring(dywfcz.lastIndexOf("/") + 1));
                    dywwjJsonObject.put("url", dywfcz + "?attname=" + dywfcz.substring(dywfcz.lastIndexOf("/") + 1));
                    dyw.add(dywwjJsonObject);
                }
                JSONObject jsonObject1 = new JSONObject();
                if (StringUtils.isNotEmpty(dywtxqz)) {
                    jsonObject1.put("name", dywtxqz.substring(dywtxqz.lastIndexOf("/") + 1));
                    jsonObject1.put("url", dywtxqz + "?attname=" + dywtxqz.substring(dywtxqz.lastIndexOf("/") + 1));
                    dyw.add(jsonObject1);
                }
                Object[] dywArray = dyw.toArray();
                convertAssetDto(dtoArray, dywArray, null, null, "抵押物文件", "文件上传", "FIlE", "22", "dywwj000");

                List<Object> jdwj = new TreeList<>();
                String bzht = oldContent.getString("bzht");
                String dzyht = oldContent.getString("dzyht");
                String jkht = oldContent.getString("jkht");
                if (StringUtils.isNotEmpty(bzht)) {
                    JSONObject jdwjJsonObject = new JSONObject();
                    jdwjJsonObject.put("name", bzht.substring(bzht.lastIndexOf("/") + 1));
                    jdwjJsonObject.put("url", bzht + "?attname=" + bzht.substring(bzht.lastIndexOf("/") + 1));
                    jdwj.add(jdwjJsonObject);
                }
                if (StringUtils.isNotEmpty(dzyht)) {
                    JSONObject dzyhtJsonObject = new JSONObject();
                    dzyhtJsonObject.put("name", dzyht.substring(dzyht.lastIndexOf("/") + 1));
                    dzyhtJsonObject.put("url", dzyht + "?attname=" + dzyht.substring(dzyht.lastIndexOf("/") + 1));
                    jdwj.add(dzyhtJsonObject);
                }
                if (StringUtils.isNotEmpty(jkht)) {
                    JSONObject jkhtJsonObject = new JSONObject();
                    jkhtJsonObject.put("name", jkht.substring(jkht.lastIndexOf("/") + 1));
                    jkhtJsonObject.put("url", jkht + "?attname=" + jkht.substring(jkht.lastIndexOf("/") + 1));
                    jdwj.add(jkhtJsonObject);
                }
                Object[] jdwjArray = jdwj.toArray();
                convertAssetDto(dtoArray, jdwjArray, null, null, "借贷文件", "文件上传", "FIlE", "22", "jdwj000");

                List<Object> sfwj = new TreeList<>();
                String fypjs = oldContent.getString("fypjs/fytjs");
                String qsz = oldContent.getString("fypjs/fytjs");
                if (StringUtils.isNotEmpty(fypjs)) {
                    JSONObject sfwjJsonObject = new JSONObject();
                    sfwjJsonObject.put("name", fypjs.substring(fypjs.lastIndexOf("/") + 1));
                    sfwjJsonObject.put("url", fypjs + "?attname=" + fypjs.substring(fypjs.lastIndexOf("/") + 1));
                    sfwj.add(sfwjJsonObject);
                }

                if (StringUtils.isNotEmpty(qsz)) {
                    JSONObject qszJsonObject = new JSONObject();
                    qszJsonObject.put("name", qsz.substring(qsz.lastIndexOf("/") + 1));
                    qszJsonObject.put("url", qsz + "?attname=" + qsz.substring(qsz.lastIndexOf("/") + 1));
                    sfwj.add(qszJsonObject);
                }
                Object[] sfwjArray = sfwj.toArray();
                convertAssetDto(dtoArray, sfwjArray, null, null, "司法文件", "文件上传", "FIlE", "22", "sfwj000");
            } else if (4 == asset.getCategoryId()) {
                convertAssetDto(dtoArray, new Object[]{"动产", "机械设备"}, new Object[]{"10", "2"}, new Object[]{0, 0}, "标的物类型", "标的信息", "SELECT", "19", "bdwlx000");
                String cpmc = oldContent.getString("cpmc");
                convertAssetDto(dtoArray, new Object[]{cpmc}, null, null, "产品名称", "拍品信息", "TEXT", "19", "cpmc000");
                String lx = oldContent.getString("lx");
                convertAssetDto(dtoArray, new Object[]{lx}, null, null, "设备类型", "拍品信息", "TEXT", "19", "sblx000");
                String pp = oldContent.getString("pp");
                convertAssetDto(dtoArray, new Object[]{pp}, null, null, "品牌", "拍品信息", "TEXT", "19", "pp000");
                String sbjbcs = oldContent.getString("sbjbcs");
                convertAssetDto(dtoArray, new Object[]{sbjbcs}, null, null, "设备基本参数", "拍品信息", "TEXT", "19", "sbjbcs000");
                String sl = oldContent.getString("pp");
                convertAssetDto(dtoArray, new Object[]{sl}, null, null, "数量", "拍品信息", "TEXT", "19", "sl000");
                String yt = oldContent.getString("pp");
                convertAssetDto(dtoArray, new Object[]{yt}, null, null, "用途", "拍品信息", "TEXT", "19", "yt000");
            }
        }
        return dtoArray;
    }

    private void convertAssetDto(List<AssetRes> dtoArray,
                                 Object[] values,
                                 Object[] numArray,
                                 Object[] idArray,
                                 String name,
                                 String titleName,
                                 String type,
                                 String title,
                                 String key) {
        AssetRes assetDto = new AssetRes();
        List<Object> val = assetDto.getVal();
        List<Object> valId = assetDto.getValId();
        List<Object> valNum = assetDto.getValNum();
        val.addAll(Arrays.asList(values));
        if (numArray != null) {
            valNum.addAll(Arrays.asList(numArray));
        }
        if (idArray != null) {
            valId.addAll(Arrays.asList(idArray));
        }
        assetDto.setVal(val);
        assetDto.setValId(valId);
        assetDto.setValNum(valNum);
        assetDto.setTitleName(titleName);
        assetDto.setGrading("0");
        assetDto.setName(name);
        assetDto.setGradingSecond("0");
        if ("zwr000".equals(key) || "qymc000".equals(key) || "yyzz000".equals(key)) {
            assetDto.setTitleSubset("2");
        } else {
            assetDto.setTitleSubset(null);
        }
        assetDto.setType(type);
        assetDto.setTitle(title);
        assetDto.setKey(key);
        dtoArray.add(assetDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseModel addDisposalAsset(AssetReq.AddReq req, Integer partyPrimaryId) {
        String fields = req.getFields();
        System.out.println("fields = " + fields);
        JSONObject jsonObject = JSON.parseObject(fields);

        //当前类型ID
        Integer categotyId = jsonObject.getInteger("templateId");

        Asset asset = new Asset();
        JSONArray templateDate = jsonObject.getJSONArray("templateDate");
        JSONArray errorArray = new JSONArray();
        conversionDisposalTemplateData(null, asset, categotyId, errorArray, templateDate);
        if (!errorArray.isEmpty()) {
            log.info("数据错误 数据为：{}", errorArray);
            return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
        }
        /* AssetCondition condition = new AssetCondition();
        condition.setName(asset.getName());
        condition.setCategoryId(categotyId);
        Asset asset1 = assetDao.selectOneResult(condition);
        if (asset1 != null) {
            log.info("操作失败的标的的类型为：{}，名称为：{}", categotyId, asset.getName());
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该标的已被添加");
        }*/
        TAgency shbc = agencyService.findByAgencyCode(SHBC);
        asset.setPropertyId(1);
        asset.setCategoryId(categotyId);
        asset.setBusType(Integer.valueOf(AssetEnum.BusType.DISPOSAL.getKey()));
        asset.setAgencyId(shbc.getId());
        asset.setPartyId(partyPrimaryId);
        if (SideType.AGENCY.equals(req.getSideType())) {
            asset.setComeFrom(AssetEnum.ComeFrom.AGENCY.getKey());
        } else {
            asset.setComeFrom(AssetEnum.ComeFrom.PLATFORM.getKey());
        }
        asset.setDetail("表单没有该字段");
        asset.setStatus(AssetEnum.Status.PENDING);
        int insert = assetDao.insert(asset);
        if (insert <= 0) {
            log.info("新增拍品数据失败，数据为：{}", JSON.toJSONString(asset));
            throw new BusinessException("添加拍品失敗");
        }
        AssetData assetData = new AssetData();
        assetData.setContent(jsonObject);
        assetData.setAssetId(asset.getId());
        int insert2 = assetDataDao.insert(assetData);
        if (insert2 <= 0) {
            log.info("新增拍品数据失败，数据为：{}", JSON.toJSONString(assetData));
            throw new BusinessException("添加失败");
        }
        //删除草稿箱
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
            delDrafts(SystemConstant.AGENCY_DRAFTS_PREFIX_KEY + partyPrimaryId + "", AssetEnum.Drafts.DISPOSAL.getKey());
        } else {
            delDrafts(partyPrimaryId + "", AssetEnum.Drafts.DISPOSAL.getKey());
        }
        return ResponseModel.succ(asset.getId());
    }

    @Override
    public ResponseModel addWithfudigAsset(String fields, Integer partyPrimaryId) {
        System.out.println("fields = " + fields);
        JSONObject jsonObject = JSON.parseObject(fields);

        //当前类型ID
        Integer categotyId = jsonObject.getInteger("templateId");

        Asset asset = new Asset();

        JSONArray templateDate = jsonObject.getJSONArray("templateDate");
        JSONArray errorArray = new JSONArray();
        conversionWithfudigTemplateData(null, asset, categotyId, errorArray, templateDate);
        if (!errorArray.isEmpty()) {
            log.info("数据错误 数据为：{}", errorArray);
            return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
        }
        AssetCondition condition = new AssetCondition();
        condition.setName(asset.getName());
        condition.setCategoryId(categotyId);
        Asset asset1 = assetDao.selectOneResult(condition);
        if (asset1 != null) {
            log.info("操作失败的标的的类型为：{}，名称为：{}", categotyId, asset.getName());
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该标的已被添加");
        }
        TAgency shbc = agencyService.findByAgencyCode(SHBC);
        asset.setAgencyId(shbc.getId());
        asset.setPropertyId(1);
        asset.setCategoryId(categotyId);
        asset.setBusType(Integer.valueOf(AssetEnum.BusType.WITHFUDIG.getKey()));
        asset.setPartyId(partyPrimaryId);
        asset.setDetail("表单没有该字段");
        asset.setStatus(AssetEnum.Status.PENDING);
        int insert = assetDao.insert(asset);
        if (insert <= 0) {
            log.info("新增拍品数据失败，数据为：{}", JSON.toJSONString(asset));
            throw new BusinessException("添加拍品失敗");
        }
        AssetData assetData = new AssetData();
        assetData.setContent(jsonObject);
        assetData.setAssetId(asset.getId());
        int insert2 = assetDataDao.insert(assetData);
        if (insert2 <= 0) {
            log.info("新增拍品数据失败，数据为：{}", JSON.toJSONString(assetData));
            throw new BusinessException("添加失败");
        }
        //删除草稿箱
        delDrafts(partyPrimaryId + "", AssetEnum.Drafts.INFORMATION.getKey());
        return ResponseModel.succ(asset.getId());
    }

    @Override
    public Object seeAssetDetail(AssetReq.AddReq req) {
        Integer assetId = req.getAssetId();
        AssetData assetData = assetDataDao.findAssetData(assetId);
        Asset asset = assetDao.selectById(assetId);
        if (asset == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标的不存在");
        }
        if (req.getSideType().getKey().equals(SideType.PLATFORM.getKey())) {
            if (!asset.getPartyId().equals(req.getPartyId())) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "仅能查询本人上传的拍品");
            }
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("id", assetData.getId());
        map.put("assetId", assetData.getAssetId());

        JSONObject content = assetData.getContent();
        JSONArray templateDate = content.getJSONArray("templateDate");
        JSONArray newData = new JSONArray();
        JSONArray bdxxData = new JSONArray();
        //返回前端时要在json数据中加入标题和模块名称
        for (int i = 0; i < templateDate.size(); i++) {
            JSONObject jsonObject = templateDate.getJSONObject(i);
            Integer title = jsonObject.getInteger("title");
            TAssetFieldGroup tAssetFieldGroup = tAssetFieldGroupDao.selectById(title);
            if (tAssetFieldGroup != null) {
                jsonObject.put("titleName", tAssetFieldGroup.getName());
            }
            Integer titleSubset = jsonObject.getInteger("titleSubset");
            if (titleSubset != null) {
                TAssetFieldModel tAssetFieldModel = assetFieldModelDao.selectById(titleSubset);
                if (tAssetFieldModel != null) {
                    jsonObject.put("titleSubsetName", tAssetFieldModel.getModelTitle());
                }
            }

            //过滤标的信息
            if (title.equals(19)) {
                bdxxData.add(jsonObject);
            } else {
                newData.add(jsonObject);
            }

        }
        content.put("templateDate", newData);
        content.put("bdxxDate", bdxxData);
        map.put("files", content);
        return map;
    }

    @Override
    public Map<String, Object> productDetail(AssetReq.AddReq req) {
        Integer assetId = req.getAssetId();
        AssetData assetData = assetDataDao.findAssetData(assetId);
        // 当为租赁权的时候直接返回
        if(assetData == null) {
            return null;
        }

        Asset asset = assetDao.selectById(assetId);
        if (asset == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标的不存在");
        }
        Map<String, Object> map = new TreeMap<>();
        map.put("id", assetData.getId());
        map.put("assetId", assetData.getAssetId());

        JSONObject content = assetData.getContent();
        JSONArray templateDate = content.getJSONArray("templateDate");
        JSONArray newData = new JSONArray();

        //过滤分组
        Set<Integer> titles = new TreeSet<>();
        Map<Integer, Object> maps = new TreeMap<>();

        transferTempData(templateDate, titles, maps, tAssetFieldGroupDao);

        JSONArray groupObject = new JSONArray();
        for (Integer title : titles) {
            JSONArray groupData = new JSONArray();
            for (int i = 0; i < templateDate.size(); i++) {
                JSONObject jsonObject = templateDate.getJSONObject(i);
                Integer tit = jsonObject.getInteger("title");
                if (tit.equals(title)) {
                    jsonObject.put("titleName", maps.get(title));
                    Integer titleSubset = jsonObject.getInteger("titleSubset");
                    JSONArray val = jsonObject.getJSONArray("val");
                    if (val.isEmpty()) {
                        continue;
                    }
                    if (StringUtils.isEmpty(val.getString(0))) {
                        continue;
                    }
                    String name = jsonObject.getString("name");
                    String type = jsonObject.getString("type");
                    if (name.contains("地址") && type.contains("TEXT")) {
                        jsonObject.put("type", "MAP");
                    }

                    String keyStr = jsonObject.getString("key");
                    String key = formatKey(keyStr);

                    boolean skipKey = ("SELECT".equals(type) || "DOWNSELECT".equals(type));
                    if (skipKey) {
                        String string = val.getString(0);
                        if ("请选择".equals(string)) {
                            continue;
                        }
                    }
                    if (!skipKey) {
                        TAssetField field = tAssetFieldDao.findUnit(key);
                        if (field == null) {
                            continue;
                        }

                        //判断前端是否过滤
                        if (field.getFrontShow()) {
                            continue;
                        }

                        String unit = field.getUnit();
                        //设置单位
                        jsonObject.put("unit", unit);
                        Integer fmNum = field.getFmNum();
                        //格式化数字
                        fmNumber(jsonObject, fmNum);
                        //添加单位
                        updateUnit(jsonObject, unit);

                        //设置单位
                        jsonObject.put("unit", unit);
                    }
                    if (titleSubset != null) {
                        TAssetFieldModel tAssetFieldModel = assetFieldModelDao.selectById(titleSubset);
                        if (tAssetFieldModel != null) {
                            jsonObject.put("titleSubsetName", tAssetFieldModel.getModelTitle());
                        }
                    }
                    groupData.add(jsonObject);
                }
            }

            //将大组字段在进行分析是否可扩展
            Set<Integer> grads = new TreeSet<>();
            JSONArray gradArray = new JSONArray();
            for (int i = 0; i < groupData.size(); i++) {
                JSONObject jsonObject = groupData.getJSONObject(i);
                Integer grading = jsonObject.getInteger("grading");
                grads.add(grading);
            }

            transferGradData(groupData, grads, gradArray);

            JSONArray gradArrayJSON = new JSONArray();
            for (int m = 0; m < gradArray.size(); m++) {
                JSONObject gradJSON = new JSONObject();
                JSONArray gradArrayJSONArray = gradArray.getJSONArray(m);
                JSONArray nosubarray = new JSONArray();
                Set<Integer> subInt = new TreeSet<>();

                transferGradArray(gradArrayJSONArray, nosubarray, subInt);
                JSONArray array = new JSONArray();
                transferGradArray(gradArrayJSONArray, subInt, array);
                if (m == 0) {
                    gradJSON.put("flag", true);
                } else {
                    gradJSON.put("flag", false);
                }
                gradJSON.put("kong", nosubarray);
                gradJSON.put("list", array);
                if (m == 0) {
                    gradJSON.put("title", maps.get(title));
                } else {
                    gradJSON.put("title", maps.get(title) + "" + (m + 1));
                }

                if (gradJSON.getString("title").contains("标的信息")
                        || gradJSON.getString("title").contains("文件上传")
                        || gradJSON.getString("title").contains("拍品信息")) {
                    gradArrayJSON.add(gradJSON);
                }
            }

            if (gradArrayJSON.isEmpty()) {
                continue;
            }
            groupObject.add(gradArrayJSON);
        }

        content.put("templateDate", newData);
        content.put("groups", groupObject);
        map.put("files", content);
        return map;
    }

    public void fmNumber(JSONObject jsonObject, Integer fmNum) {
        if (fmNum != null && fmNum == 1) {
            JSONArray val1 = jsonObject.getJSONArray("val");
            JSONArray val2 = new JSONArray();
            for (int j = 0; j < val1.size(); j++) {
                String string = val1.getString(j);
                if (NumberValidationUtils.isNumeric(string)) {
                    val2.add(NumberValidationUtils.formatPrice(string));
                } else {
                    val2.add(string);
                }
            }
            jsonObject.put("val", val2);
        }
    }

    private void transferGradArray(JSONArray gradArrayJSONArray, JSONArray nosubarray, Set<Integer> subInt) {
        for (int i = 0; i < gradArrayJSONArray.size(); i++) {
            JSONObject jsonObject = gradArrayJSONArray.getJSONObject(i);
            Integer titleSubset = jsonObject.getInteger("titleSubset");
            if (titleSubset == null) {
                nosubarray.add(jsonObject);
            } else {
                subInt.add(titleSubset);
            }
        }
    }

    private static void transferGradArray(JSONArray gradArrayJSONArray, Set<Integer> subInt, JSONArray array) {
        trankSubSetData(gradArrayJSONArray, subInt, array);
    }

    public static void updateUnit(JSONObject jsonObject, String unit) {
        if (unit != null) {
            JSONArray val1 = jsonObject.getJSONArray("val");
            JSONArray val2 = new JSONArray();
            for (int j = 0; j < val1.size(); j++) {
                String string = val1.getString(j);
                val2.add(string + " " + unit);
            }
            jsonObject.put("val", val2);
        }
    }


    @Override
    public Object MyDetail(AssetReq.AddReq req) {
        Integer assetId = req.getAssetId();
        AssetData assetData = assetDataDao.findAssetData(assetId);
        Asset asset = assetDao.selectById(assetId);
        if (asset == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标的不存在");
        }
        if (req.getSideType().getKey().equals(SideType.PLATFORM.getKey())) {
            if (!asset.getPartyId().equals(req.getPartyId())) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "仅能查询本人上传的拍品");
            }
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("id", assetData.getId());
        map.put("assetId", assetData.getAssetId());

        JSONObject content = assetData.getContent();
        JSONArray templateDate = content.getJSONArray("templateDate");
        Integer templateId = content.getInteger("templateId");
        JSONArray newData = new JSONArray();

        //过滤分组
        Set<Integer> titles = new HashSet<>();
        Map<Integer, Object> maps = new HashMap<>(16);

        transferTempData(templateDate, titles, maps, tAssetFieldGroupDao);

        JSONArray groupObject = new JSONArray();
        for (Integer title : titles) {
            JSONArray groupData = new JSONArray();
            for (int i = 0; i < templateDate.size(); i++) {
                JSONObject jsonObject = templateDate.getJSONObject(i);
                Integer tit = jsonObject.getInteger("title");
                if (tit.equals(title)) {
                    jsonObject.put("titleName", maps.get(title));
                    Integer titleSubset = jsonObject.getInteger("titleSubset");
                    JSONArray val = jsonObject.getJSONArray("val");
                    if (val.isEmpty()) {
                        continue;
                    }
                    if (StringUtils.isEmpty(val.getString(0))) {
                        continue;
                    }
                    String name = jsonObject.getString("name");

                    if (req.getSideType().getKey().equals(SideType.AGENCY.getKey())) {


                        if (name.contains("拍卖机构证件号码")) {
                            jsonObject.put("name", "拍卖机构统一信用代码");
                        }
                    } else {
                        if (name.contains("拍卖机构名称")) {
                            jsonObject.put("name", "委托人名称");
                        }

                        if (name.contains("拍卖机构统一信用代码")) {
                            jsonObject.put("name", "委托人证件号码");
                        }
                    }


                    String keyStr = jsonObject.getString("key");
                    String key = formatKey(keyStr);
                    boolean sideSources = (SideType.AGENCY.equals(req.getSideType()) || SideType.ADMIN.equals(req.getSideType()) || SideType.AGENCY_JOINT.equals(req.getSideType()));
                    if (sideSources && "reservePrice".equals(key)) {
                        JSONArray newVal = new JSONArray();
                        newVal.add("***");
                        jsonObject.put("val", newVal);
                    }
                    //联拍查看隐藏身份证信息
                    if (SideType.AGENCY_JOINT.equals(req.getSideType()) && ("yxgmrsfz".equals(key) || "teidNum".equals(key) || "tesfzh".equals(key) || ("partyNumber".equals(key) && "委托人证件号码".equals(jsonObject.getString("name"))))) {
                        JSONArray newVal = new JSONArray();
                        newVal.add("xxxxxxxxxxxxxxxx");
                        jsonObject.put("val", newVal);
                    }

                    String type = jsonObject.getString("type");
                    boolean skipKey = ("SELECT".equals(type) || "DOWNSELECT".equals(type));
                    if (skipKey) {
                        String string = val.getString(0);
                        if ("请选择".equals(string)) {
                            continue;
                        }
                    }
                    if (!skipKey) {
                        TAssetField field = tAssetFieldDao.findUnit(key);
                        if (field == null) {
                            continue;
                        }
                        String unit = field.getUnit();
                        //设置单位
                        jsonObject.put("unit", unit);
                        Integer fmNum = field.getFmNum();
                        //格式化数字
                        fmNumber(jsonObject, fmNum);
                        //添加单位
                        updateUnit(jsonObject, unit);
                    }


                    if (titleSubset != null) {
                        TAssetFieldModel tAssetFieldModel = assetFieldModelDao.selectById(titleSubset);
                        if (tAssetFieldModel != null) {
                            jsonObject.put("titleSubsetName", tAssetFieldModel.getModelTitle());
                        }
                    }
                    groupData.add(jsonObject);
                }
            }

            //将大组字段在进行分析是否可扩展
            Set<Integer> grads = new HashSet<>();
            JSONArray gradArray = new JSONArray();
            for (int i = 0; i < groupData.size(); i++) {
                JSONObject jsonObject = groupData.getJSONObject(i);
                Integer grading = jsonObject.getInteger("grading");
                grads.add(grading);
            }


            transferGradData(groupData, grads, gradArray);

            JSONArray gradArrayJSON = new JSONArray();
            for (int m = 0; m < gradArray.size(); m++) {
                JSONObject gradJSON = new JSONObject();
                JSONArray gradArrayJSONArray = gradArray.getJSONArray(m);
                JSONArray nosubarray = new JSONArray();
                Set<Integer> subInt = new HashSet<>();
                transferGradArray(gradArrayJSONArray, nosubarray, subInt);
                JSONArray array = new JSONArray();
                trankSubSetData(gradArrayJSONArray, subInt, array);

                if (m == 0) {
                    gradJSON.put("flag", true);
                } else {
                    gradJSON.put("flag", false);
                }
                gradJSON.put("kong", nosubarray);
                gradJSON.put("list", array);
                if (SideType.AGENCY.getKey().equals(req.getSideType().getKey())) {
                    if (m == 0) {
                        gradJSON.put("title", maps.get(title).equals("委托方信息") ? "拍卖机构信息" : maps.get(title));
                    } else {
                        gradJSON.put("title", (maps.get(title).equals("委托方信息") ? "拍卖机构信息" : maps.get(title)) + "" + (m + 1));
                    }
                } else {
                    if (m == 0) {
                        gradJSON.put("title", maps.get(title));
                    } else {
                        gradJSON.put("title", maps.get(title) + "" + (m + 1));
                    }
                }


                if (gradJSON.getString("title").contains("标的信息")) {
                    gradJSON.put("status", true);
                } else {
                    gradJSON.put("status", false);
                }

                gradArrayJSON.add(gradJSON);
            }

            if (gradArrayJSON.isEmpty()) {
                continue;
            }
            groupObject.add(gradArrayJSON);
        }
        JSONArray jsonArray = groupObject.getJSONArray(0);
        JSONObject jsonObject = jsonArray.getJSONObject(0);

        //处理拍品类型
        JSONArray kong = jsonObject.getJSONArray("kong");
        TAssetTemplateCategory tAssetTemplateCategory = tAssetTemplateCategoryService.selectByTemplateId(templateId);

        JSONObject json = new JSONObject();
        json.put("name", "拍品类型");
        json.put("type", "TEXT");
        JSONArray array = new JSONArray();
        array.add(tAssetTemplateCategory.getCategoryName());
        json.put("val", array);
        kong.add(0, json);

        JSONObject json1 = new JSONObject();
        json1.put("name", "是否债权包");
        json1.put("type", "TEXT");
        JSONArray array1 = new JSONArray();
        String categoryOptionName = tAssetTemplateCategory.getCategoryOptionName();
        if (StringUtils.isNotEmpty(categoryOptionName)) {
            array1.add(categoryOptionName);
            json1.put("val", array1);
            kong.add(1, json1);
        }


        content.put("templateDate", newData);
        content.put("groups", groupObject);
        map.put("files", content);
        return map;
    }

    @Override
    public Object makeOldData(AssetReq.AddReq req) {
        Integer assetId = req.getAssetId();
        AssetDataCopyCondition copyCondition = new AssetDataCopyCondition();
        copyCondition.setAssetId(req.getAssetId());
        AssetDataCopy assetData = assetDataCopyDao.selectFirst(copyCondition);
        AssetCopy asset = assetCopyDao.selectById(assetId);
        if (asset == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标的不存在");
        }
        if (assetData == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "标的信息不存在");
        }
        //处理老数据
        List<AssetRes> assetDtos = convertOldData(assetId, asset, assetData);
        Map<String, Object> map = new HashMap<>(16);
        map.put("userWork", "");
        map.put("templateId", asset.getCategoryId());
        map.put("templateDate", assetDtos);
        map.put("assetAndActivity", "");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("files", map);
        jsonObject.put("id", assetData.getId());
        return jsonObject;
    }

    @Override
    public boolean isSelfAsset(Integer assetId, Integer partyId) {
        Asset asset = assetService.getAsset(assetId);
//        AccountBaseDto accountBaseByPartyId = accountService.getAccountBaseByPartyId(asset.getPartyId());
        return partyId.equals(asset.getPartyId());
    }

    @Override
    public void uploadSelfReport(Integer assetId, BigDecimal tuneReport, String[] tuneReportUrl, Integer partyId) {
        Asset asset = assetService.getAsset(assetId);
        if (asset == null) {
            throw new BusinessException("参数异常");
        }
        if (!partyId.equals(asset.getPartyId())) {
            throw new BusinessException("没有权限");
        }
        asset.setTuneReport(tuneReport);
        asset.setTuneReportUrl(getTuneReportUrl(tuneReportUrl));
        asset.setUpdatedAt(new Date());
        assetDao.updateById(asset);
    }

    @Override
    public void draftsAsset(AssetReq.AddReq req, String partyPrimaryId) {
        AssetDataDrafts assetDataDrafts = new AssetDataDrafts();
        if (StringUtils.isEmpty(req.getFields())) {
            return;
        }

        AssetDataDraftsCondition condition = new AssetDataDraftsCondition();

        condition.setDelFlag(AssetDataDrafts.notDel);
        condition.setPartyId(partyPrimaryId);
        condition.setType(req.getType());

        AssetDataDrafts assetDataDrafts1 = assetDataDraftsDao.selectOneResult(condition);
        if (assetDataDrafts1 == null) {
            JSONObject jsonObject = JSONObject.parseObject(req.getFields());
            assetDataDrafts.setContent(jsonObject);
            assetDataDrafts.setType(req.getType());
            assetDataDrafts.setPartyId(partyPrimaryId);

            int insert = assetDataDraftsDao.insert(assetDataDrafts);
            if (insert > 0) {
                log.info("存入草稿箱成功，数据为：{}", JSON.toJSONString(assetDataDrafts));
            }
        } else {
            JSONObject jsonObject = JSONObject.parseObject(req.getFields());
            assetDataDrafts1.setContent(jsonObject);
            int i = assetDataDraftsDao.updateById(assetDataDrafts1);
            if (i > 0) {
                log.info("修改草稿箱成功，数据为：{}", JSON.toJSONString(assetDataDrafts));
            }
        }
    }

    @Override
    public Object findDrafts(AssetReq.AddReq req, String partyPrimaryId) {
        if (StringUtils.isEmpty(partyPrimaryId)) {
            throw new BusinessException(ApiCallResult.NO_AUTH_CAN_NOT_UPLOAD_ERROR.getCode(), "请先去认证");
        }
        AssetDataDraftsCondition dataDraftsCondition = new AssetDataDraftsCondition();
        dataDraftsCondition.setDelFlag(AssetDataDrafts.notDel);
        dataDraftsCondition.setPartyId(partyPrimaryId);
        dataDraftsCondition.setType(req.getType());
        AssetDataDrafts assetDataDrafts = assetDataDraftsDao.selectOneResult(dataDraftsCondition);
        if (assetDataDrafts == null) {
            return null;
        }
        log.info("取出草稿箱成功，数据为：{}", JSON.toJSONString(assetDataDrafts));
        Map<String, Object> map = new HashMap<>(16);
        JSONObject content = assetDataDrafts.getContent();
        Integer templateId = content.getInteger("templateId");
        JSONArray templateDate = content.getJSONArray("templateDate");

        JSONArray newData = new JSONArray();
        //返回前端时要在json数据中加入标题和模块名称
        for (int i = 0; i < templateDate.size(); i++) {
            JSONObject jsonObject = templateDate.getJSONObject(i);
            Integer title = jsonObject.getInteger("title");
            TAssetFieldGroup tAssetFieldGroup = tAssetFieldGroupDao.selectById(title);
            if (tAssetFieldGroup != null) {
                jsonObject.put("titleName", tAssetFieldGroup.getName());
            }
            Integer titleSubset = jsonObject.getInteger("titleSubset");
            if (titleSubset != null) {
                TAssetFieldModel tAssetFieldModel = assetFieldModelDao.selectById(titleSubset);
                if (tAssetFieldModel != null) {
                    jsonObject.put("titleSubsetName", tAssetFieldModel.getModelTitle());
                }
            }
            String keyStr = jsonObject.getString("key");
            String key = formatKey(keyStr);
            String type = jsonObject.getString("type");
            if (!"SELECT".equals(type)) {
                TAssetField field = tAssetFieldDao.findUnit(key);
                if (field == null) {
                    continue;
                }
                String unit = field.getUnit();
                //设置单位
                jsonObject.put("unit", unit);
            }

            if ("SEDATE".equals(type) || "DATE".equals(type)) {
                JSONArray val = jsonObject.getJSONArray("val");
                JSONArray newVal = new JSONArray();
                if (!val.isEmpty()) {
                    for (int j = 0; j < val.size(); j++) {
                        String dateStr = val.getString(j);
                        if (StringUtils.isNotEmpty(dateStr)) {
                            boolean validDate = DateUtil.isValidDate(dateStr);
                            if (validDate) {
                                String format = DateUtil.format(DateUtil.parse(dateStr, DateUtil.NORM_DATETIME_PATTERN), DateUtil.NORM_DATETIME_PATTERN);
                                newVal.add(format);
                            }
                        }
                    }
                    jsonObject.put("val", newVal);
                }
            }
            newData.add(jsonObject);
        }
        content.put("templateDate", newData);
        map.put("files", content);

        TAssetTemplateCategory tAssetTemplateCategory = tAssetTemplateCategoryDao.selectById(templateId);
        log.info("查询模板的数据为：{}", JSON.toJSONString(tAssetTemplateCategory));
        if (tAssetTemplateCategory == null) {
            log.info("查询的模板ID为：{}，原始数据为：{}", templateDate, content);
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");
        }
        map.put("categoryId", tAssetTemplateCategory.getCategoryId());
        map.put("categoryOptionId", tAssetTemplateCategory.getCategoryOptionId());
        return map;
    }

    @Override
    public List<TPreemptivePerson> getPreemptivePersons(Integer assetId) {
        TPreemptivePersonCondition condition = new TPreemptivePersonCondition();
        condition.setDelFlag(TPreemptivePerson.NODEL);
        condition.setAssetId(assetId);
        return tPreemptivePersonDao.selectList(condition);
    }

    private JSONObject getTuneReportUrl(String[] tuneReportUrl) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("images", tuneReportUrl);
        return JSONObject.parseObject(JSONObject.toJSONString(map));
    }

    public static void transferGradData(JSONArray groupData, Set<Integer> grads, JSONArray gradArray) {
        for (Integer grading : grads) {
            JSONArray gradData = new JSONArray();
            for (int i = 0; i < groupData.size(); i++) {
                JSONObject jsonObject = groupData.getJSONObject(i);
                Integer grad = jsonObject.getInteger("grading");
                if (grading.equals(grad)) {
                    gradData.add(jsonObject);
                }
            }
            gradArray.add(gradData);
        }
    }

    public static void transferTempData(JSONArray templateDate, Set<Integer> titles, Map<Integer, Object> maps, TAssetFieldGroupDao tAssetFieldGroupDao) {
        for (int i = 0; i < templateDate.size(); i++) {
            JSONObject jsonObject = templateDate.getJSONObject(i);
            Integer title = jsonObject.getInteger("title");
            if (titles.add(title)) {
                TAssetFieldGroup tAssetFieldGroup = tAssetFieldGroupDao.selectById(title);
                if (tAssetFieldGroup != null) {
                    maps.put(title, tAssetFieldGroup.getName());
                }
            }
        }
    }

    public static void trankSubSetData(JSONArray gradArrayJSONArray, Set<Integer> subInt, JSONArray array) {
        for (Integer subset : subInt) {
            JSONArray subData = new JSONArray();
            for (int i = 0; i < gradArrayJSONArray.size(); i++) {
                JSONObject jsonObject = gradArrayJSONArray.getJSONObject(i);
                Integer titleSubset = jsonObject.getInteger("titleSubset");
                if (subset.equals(titleSubset)) {
                    subData.add(jsonObject);
                }
            }
            Set<Integer> gradSecondInts = new HashSet<>();
            Map<Integer, Object> map1 = new HashMap<>(16);
            for (int i = 0; i < subData.size(); i++) {
                JSONObject jsonObject = subData.getJSONObject(i);
                Integer gradSecond = jsonObject.getInteger("gradingSecond");
                String titleSubsetName = jsonObject.getString("titleSubsetName");
                gradSecondInts.add(gradSecond);
                map1.put(gradSecond, titleSubsetName);
            }

            for (Integer gradSecondInt : gradSecondInts) {
                JSONObject json = new JSONObject();
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < subData.size(); i++) {
                    JSONObject jsonObject = subData.getJSONObject(i);
                    Integer gradSecond = jsonObject.getInteger("gradingSecond");
                    if (gradSecond.equals(gradSecondInt)) {
                        jsonArray.add(jsonObject);
                    }
                }
                json.put("list", jsonArray);
                if (gradSecondInt == 0) {
                    json.put("title", map1.get(gradSecondInt));
                } else {

                    if (map1.get(gradSecondInt).equals("一级优先购买人")) {
                        json.put("title", NumberFormatUtils.NumberToChn((gradSecondInt + 1)) + "优先购买人");
                    } else {
                        json.put("title", map1.get(gradSecondInt) + "" + (gradSecondInt + 1));
                    }
                }
                array.add(json);
            }
        }
    }


    @Override
    @Transactional
    public ResponseModel disposalAssetEdit(Integer assetId, String fields, Integer partyId) {
        log.info("正在请求修改拍品的ID为：{}，修改参数为：{}", assetId, fields);
        log.info("修改拍品的ID为========{}，开始修改", assetId);
        Asset asset = assetDao.selectById(assetId);
        if (asset == null) {
            return ResponseModel.succ("拍品不存在");
        }
        if (!partyId.equals(asset.getPartyId())) {
            return ResponseModel.succ();
        }
        AssetData assetData = assetDataDao.findAssetData(assetId);
        Integer agencyId = asset.getAgencyId();
        JSONObject jsonObject = JSON.parseObject(fields);
        JSONArray templateDate = jsonObject.getJSONArray("templateDate");
        JSONArray errorArray = new JSONArray();
        Integer integer = conversionDisposalTemplateData(agencyId, asset, asset.getCategoryId(), errorArray, templateDate);

        if (!errorArray.isEmpty()) {
            log.info("数据错误 数据为：{}", errorArray);
            return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
        }

        asset.setAgencyId(integer);
        int i = assetDao.updateById(asset);
        if (i < 0) {
            log.info("修改标的失败数据为：{}", JSON.toJSONString(asset));
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改标的失败");
        }
        assetData.setContent(jsonObject);
        int i2 = assetDataDao.updateById(assetData);
        if (i2 < 0) {
            log.info("修改标的data失败数据为：{}", JSON.toJSONString(assetData));
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改标的失败");
        }
        log.info("修改拍品的ID为：{}，修改后的参数为：{}，结束修改", assetId, assetData);
        return ResponseModel.succ(i2);
    }

    @Override
    @Transactional
    public ResponseModel withfudigAssetEdit(Integer assetId, String fields) {
        log.info("正在请求修改拍品的ID为：{}，修改参数为：{}", assetId, fields);
        log.info("修改拍品的ID为========{}，开始修改", assetId);
        Asset asset = assetDao.selectById(assetId);
        if (asset == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "拍品不存在");
        }
        AssetData assetData = assetDataDao.findAssetData(assetId);
        Integer agencyId = asset.getAgencyId();
        JSONObject jsonObject = JSON.parseObject(fields);
        JSONArray templateDate = jsonObject.getJSONArray("templateDate");
        JSONArray errorArray = new JSONArray();
        Integer integer = conversionWithfudigTemplateData(agencyId, asset, asset.getCategoryId(), errorArray, templateDate);

        if (!errorArray.isEmpty()) {
            log.info("数据错误 数据为：{}", errorArray);
            return ResponseModel.fail(ApiCallResult.EMPTY, errorArray);
        }

        asset.setAgencyId(integer);
        int i = assetDao.updateById(asset);
        if (i < 0) {
            log.info("修改标的失败数据为：{}", JSON.toJSONString(asset));
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改标的失败");
        }
        assetData.setContent(jsonObject);
        int i2 = assetDataDao.updateById(assetData);
        if (i2 < 0) {
            log.info("修改标的data失败数据为：{}", JSON.toJSONString(assetData));
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改标的失败");
        }
        log.info("修改拍品的ID为：{}，修改后的参数为：{}，结束修改", assetId, assetData);
        return ResponseModel.succ(i2);
    }

    private Integer conversionDisposalTemplateData(Integer agencyId, Asset asset, Integer categotyId,
                                                   JSONArray errorArray, JSONArray templateDate) {
        for (int i = 0; i < templateDate.size(); i++) {
            AssetDto json = templateDate.getJSONObject(i).toJavaObject(AssetDto.class);
            String key = formatKey(json.getKey());
            //期望拍卖时间
            if ("passetName".equals(key)) {
                if (json.getVal().isEmpty()) {
                    json.setErrorMsg("拍品名称不能为空");
                    errorArray.add(json);
                }
                if (StringUtils.isEmpty(json.getVal().get(0))) {
                    json.setErrorMsg("拍品名称不能为空");
                    errorArray.add(json);
                }
                findDisposalAssetName(asset, categotyId, json);
            } else if ("pdebtPrincipal".equals(key)) {
                if (json.getVal().isEmpty()) {
                    json.setErrorMsg("债权本金不能为空");
                    errorArray.add(json);
                }
                if (StringUtils.isEmpty(json.getVal().get(0))) {
                    json.setErrorMsg("债权本金不能为空");
                    errorArray.add(json);
                }
                if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                    json.setErrorMsg("请输入正确的债权本金数值，不可为负值");
                    errorArray.add(json);
                } else {
                    asset.setDebtPrincipal(new BigDecimal(Float.parseFloat(json.getVal().get(0))));
                }
            } else if ("pdebtInterest".equals(key)) {
                if (json.getVal().isEmpty()) {
                    json.setErrorMsg("债权利息不能为空");
                    errorArray.add(json);
                }
                if (StringUtils.isEmpty(json.getVal().get(0))) {
                    json.setErrorMsg("债权利息不能为空");
                    errorArray.add(json);
                }
                if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                    json.setErrorMsg("请输入正确的债权利息数值，不可为负值");
                    errorArray.add(json);
                } else {
                    asset.setDebtInterest(new BigDecimal(Float.parseFloat(json.getVal().get(0))));
                }
            } else if ("pdywfbqy".equals(key)) {
                if (!json.getVal().isEmpty() && StringUtils.isNotEmpty(json.getVal().get(0))) {
                    Set<String> provinces = new LinkedHashSet<>();
                    Set<String> cities = new LinkedHashSet<>();
                    Set<String> areas = new LinkedHashSet<>();
                    for (String val : json.getVal()) {
                        JSONObject jsonObject3 = JSONObject.parseObject(val);
                        if (jsonObject3.containsKey("id") && StringUtils.isNotBlank(jsonObject3.getString("id"))) {
                            cities.add(jsonObject3.getString("id"));
                        }
                        if (jsonObject3.containsKey("provinceId") && StringUtils.isNotBlank(jsonObject3.getString("provinceId"))) {
                            provinces.add(jsonObject3.getString("provinceId"));
                        }
                        if (jsonObject3.containsKey("areaId") && StringUtils.isNotBlank(jsonObject3.getString("areaId"))) {
                            areas.add(jsonObject3.getString("areaId"));
                        }
                    }
                    asset.setCityId(String.join(",", cities));
                    asset.setProvinceId(String.join(",", provinces));
                    asset.setAreaId(String.join(",", areas));
                }
            }
            asset.setDescriptionDoc(json.getVal().get(0));
        }
        return agencyId;
    }

    private void findAssetName(Asset asset, Integer categotyId, AssetDto json) {
        if (asset.getName() == null) {
            asset.setName(json.getVal().get(0));
            AssetCondition condition = new AssetCondition();
            condition.setName(asset.getName());
            condition.setCategoryId(categotyId);
            List<Asset> asset1 = assetDao.selectList(condition);
            if (asset1 != null && asset1.size() >= 1) {
                log.info("操作失败------标的类型为：{}，名称为：{}", categotyId, asset.getName());
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "请修改拍品名称，同名称拍品已存在");
            }
        } else {
            if (StringUtils.isNotEmpty(json.getVal().get(0))) {
                if (!asset.getName().equals(json.getVal().get(0))) {
                    asset.setName(json.getVal().get(0));
                }
            } else {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "拍品名称不能为空");
            }
        }
    }

    private void findDisposalAssetName(Asset asset, Integer categotyId, AssetDto json) {
        if (asset.getName() == null) {
            asset.setName(json.getVal().get(0));
        }
    }


    private Integer conversionWithfudigTemplateData(Integer agencyId, Asset asset, Integer categotyId,
                                                    JSONArray errorArray, JSONArray templateDate) {
        for (int i = 0; i < templateDate.size(); i++) {
            AssetDto json = templateDate.getJSONObject(i).toJavaObject(AssetDto.class);
            String key = formatKey(json.getKey());
            //期望拍卖时间
            if ("cassetName".equals(key)) {
                if (json.getVal().isEmpty()) {
                    json.setErrorMsg("拍品名称不能为空");
                    errorArray.add(json);
                }
                if (StringUtils.isEmpty(json.getVal().get(0))) {
                    json.setErrorMsg("拍品名称不能为空");
                    errorArray.add(json);
                }
                findAssetName(asset, categotyId, json);
            } else if ("cdebtPrincipal".equals(key)) {
                if (json.getVal().isEmpty()) {
                    json.setErrorMsg("债权本金不能为空");
                    errorArray.add(json);
                }
                if (StringUtils.isEmpty(json.getVal().get(0))) {
                    json.setErrorMsg("债权本金不能为空");
                    errorArray.add(json);
                }
                if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                    json.setErrorMsg("请输入正确的债权本金数值，不可为负值");
                    errorArray.add(json);
                } else {
                    asset.setDebtPrincipal(new BigDecimal(Float.parseFloat(json.getVal().get(0))));
                }
            } else if ("cdebtInterest".equals(key)) {
                if (json.getVal().isEmpty()) {
                    json.setErrorMsg("债权利息不能为空");
                    errorArray.add(json);
                }
                if (StringUtils.isEmpty(json.getVal().get(0))) {
                    json.setErrorMsg("债权利息不能为空");
                    errorArray.add(json);
                }
                if (!NumberValidationUtils.isPositiveDecimalOrInteger(json.getVal().get(0))) {
                    json.setErrorMsg("请输入正确的债权利息数值，不可为负值");
                    errorArray.add(json);
                } else {
                    asset.setDebtInterest(new BigDecimal(Float.parseFloat(json.getVal().get(0))));
                }
            }
            asset.setDescriptionDoc(json.getVal().get(0));
        }
        return agencyId;
    }

    private String getAssetCode(Integer id) {
        return ((char) (Math.random() * 26 + 'A')) + "" + id + "-" + RandomNumberGenerator.numberGenerator(6);
//        List<Map> mapList = assetDao.getAgencyCode(agencyId);
//        if (mapList.isEmpty()) {
//            TAgency byAgencyId = agencyService.findByAgencyId(agencyId);
//            String agencyCode = byAgencyId.getCode();
//            String code = "00000" + 1;
//            String substring = code.substring(code.length() - 6);
//            return agencyCode + "-" + substring;
//        } else {
//            Map map = mapList.get(0);
//            String assetCode = (String) map.get("assetCode");
//            String[] split = assetCode.split("-");
//            String agencyCode = split[0];
//            String code = split[1];
//            String code1 = "00000";
//            int i = Integer.parseInt(code);
//            String newCode = code1 + (i + 1);
//            String substring = newCode.substring(newCode.length() - 6);
//            return agencyCode + "-" + substring;
//        }
    }

    private static final Pattern KEY_PATTERN = Pattern.compile("[\\d]");

    private String formatKey(String key) {
        Matcher matcher = KEY_PATTERN.matcher(key);
        return (matcher.replaceAll("").trim());
    }

}
