package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.OrderCodeGenerator;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.*;
import com._360pai.core.condition.asset.TAssetAuthorizationCondition;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.account.TDisposeProviderDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.agreement.DelegationAgreementDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.asset.TAssetAuthorizationDao;
import com._360pai.core.dao.disposal.TDisposeLevelDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.asset.req.AssetAuthorizationReq;
import com._360pai.core.facade.asset.resp.AssetAuthorizationResp;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.agreement.DelegationAgreement;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.TAssetAuthorization;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.core.model.disposal.TDisposeSurvey;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.asset.AssetAuthorizationService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.disposal.DisposeLevelService;
import com._360pai.core.service.disposal.DisposeSurveyService;
import com._360pai.core.utils.ServiceMessageUtils;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.controller.req.fdd.*;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-11-05 16:45
 */
@Slf4j
@Service
public class AssetAuthorizationServiceImpl implements AssetAuthorizationService {

    @Autowired
    private TAssetAuthorizationDao assetAuthorizationDao;
    @Autowired
    private ServiceMessageUtils serviceMessageUtils;
    @Autowired
    private DisposeSurveyService disposeSurveyService;
    @Autowired
    private DisposeLevelService disposeLevelService;
    @Autowired
    private AuctionActivityService auctionActivityService;
    @Autowired
    private AssetService assetService;
    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;
    @Autowired
    private AuctionActivityDao auctionActivityDao;
    @Autowired
    private AssetDao assetDao;
    @Autowired
    private TAgencyDao agencyDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TDisposeProviderDao tDisposeProviderDao;
    @Autowired
    private TDisposeLevelDao tDisposeLevelDao;
    @Autowired
    private DelegationAgreementDao delegationAgreementDao;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private GatewayMqSender mqSender;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int signProtocol(Integer activityId, Integer partyId, String reportSource,
                            String protocolViewUrl, String protocolDownloadUrl, String[] reportUrls, String reportPrice) {

        if (reportSource.equals(SurveyReportEnum.ReportSourceEnum.SELF.getKey())) {
            Asset asset = auctionActivityService.getAssetByActivityId(activityId);
            // 上传报告
            assetService.uploadSelfReport(asset.getId(), new BigDecimal(reportPrice), reportUrls, partyId);
            // 更改状态
            asset.setTuneReportAuthorization(true);
            asset.setUpdatedAt(new Date());
            return assetService.updateAssetById(asset) ? 1 : 0;
        } else if (reportSource.equals(SurveyReportEnum.ReportSourceEnum.THIRD.getKey())) {
            String surveyNo = OrderCodeGenerator.INSTANCE.getOrderCode(OrderCodeGenerator.THIRD_SURVEY_NO);
            int i = addProtocol(activityId, partyId, ProtocolTypeConstants.THIRD_AUTH, surveyNo, protocolViewUrl, protocolDownloadUrl);
            if (i > 0) {
                int count = addDisposeSurvey(surveyNo, activityId);
                return count;
            }
        }

        return 0;
    }

    @Override
    public String getReportSource(Integer activityId) {

        try {
            // 判断是否签订销售协议、判断是否签订三方协议
            boolean tag = isSignByProtocolType(activityId, ProtocolTypeConstants.THIRD_AUTH);

            Asset asset = auctionActivityService.getAssetByActivityId(activityId);

            if (!tag) {

                return (null != asset.getTuneReportAuthorization()
                        && asset.getTuneReportAuthorization()
                        && null != asset.getTuneReportUrl()) ? SurveyReportEnum.ReportSourceEnum.SELF.getKey() : SurveyReportEnum.ReportSourceEnum.NO_REPORT.getKey();
            }

            List<TAssetAuthorization> byActivityId = getByActivityId(activityId);

            if (CollectionUtils.isNotEmpty(byActivityId)) {

                TDisposeSurvey survey = disposeSurveyService.getDisposeSurveyBySurveyNo(byActivityId.get(0).getSurveyNo());

                if (survey.getSurveyStatus().equals(DisposalEnum.SurveyStatus.UPLOADED.getKey())
                        && StringUtils.isNotBlank(survey.getBasisSurvey())
                        && StringUtils.isNotBlank(survey.getCompleteSurvey())) {

                    return SurveyReportEnum.ReportSourceEnum.THIRD.getKey();
                }
            }

            return SurveyReportEnum.ReportSourceEnum.NO_REPORT.getKey();
        } catch (Exception e) {

            e.printStackTrace();
            return SurveyReportEnum.ReportSourceEnum.NO_REPORT.getKey();
        }
    }

    @Override
    public List<TAssetAuthorization> getByActivityId(Integer activityId) {
        TAssetAuthorizationCondition condition = new TAssetAuthorizationCondition();
        condition.setActivityId(activityId);
        List<TAssetAuthorization> tAssetAuthorizations = assetAuthorizationDao.selectList(condition);
        return tAssetAuthorizations;
    }

    @Override
    public boolean isSignByProtocolType(Integer activityId, String protocolType) {
        List<TAssetAuthorization> byActivityId = getByActivityId(activityId);
        for (TAssetAuthorization tmp : byActivityId) {
            if (tmp.getProtocolType().equals(protocolType) && tmp.getAllSigned()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSignByProtocol(Integer activityId, String protocolSubtype) {
        List<TAssetAuthorization> byActivityId = getByActivityId(activityId);
        for (TAssetAuthorization tmp : byActivityId) {
            if (tmp.getProtocolSubtype().equals(protocolSubtype)) {
                return true;
            }
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AssetAuthorizationResp.PreSignTuneAuthProtocolResp preSignTuneAuthProtocol(AssetAuthorizationReq.PreSignTuneAuthProtocol req) {
        AssetAuthorizationResp.PreSignTuneAuthProtocolResp resp = new AssetAuthorizationResp.PreSignTuneAuthProtocolResp();
        AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
        Asset asset = assetDao.selectById(activity.getAssetId());
        if (!asset.getPartyId().equals(req.getPartyId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        DelegationAgreement delegationAgreement = delegationAgreementDao.getByActivityId(activity.getId());
        if (delegationAgreement == null ||!delegationAgreement.getAllSigned()) {
            throw new BusinessException("尚未签署送拍协议");
        }
        if (!DateUtil.isToday(delegationAgreement.getAllSignedAt())) {
            throw new BusinessException("超过提交尽调报告时间");
        }
        AccountBaseDto accountBaseDto;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
            accountBaseDto = new AccountBaseDto();
            TAgency agency = agencyDao.selectById(asset.getAgencyId());
            accountBaseDto.setName(agency.getName());
            accountBaseDto.setFadadaId(agency.getFadadaId());
        } else {
            accountBaseDto = accountService.getAccountBaseByPartyId(req.getPartyId());
        }
        if (SurveyReportEnum.ReportSourceEnum.SELF.getKey().equals(req.getReportSource())) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("images", new String[]{req.getSurveyReport()});
            asset.setTuneReportUrl(jsonObject);
            BigDecimal price = new BigDecimal(req.getSurveyReportPrice());
            if (price.compareTo(new BigDecimal(100)) < 0 || price.compareTo(new BigDecimal(1500)) > 1) {
                throw new BusinessException("尽调报告的费用为100元-1500元");
            }
            asset.setTuneReport(price);
            asset.setUpdatedAt(new Date());
            int result = assetDao.updateById(asset);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            resp.setJdReportSaleViewUrl(createOrUpdateSurveyReportSale(activity, asset, accountBaseDto));
        } else if (SurveyReportEnum.ReportSourceEnum.THIRD.getKey().equals(req.getReportSource())) {
            resp.setJdReportCommissionViewUrl(createOrUpdateJdReportCommission(activity, asset, accountBaseDto));
            resp.setThirdProtocolViewUrl(createOrUpdateThirdProtocol(activity, asset, accountBaseDto));
        }
        return resp;
    }

    private String createOrUpdateSurveyReportSale(AuctionActivity activity, Asset asset, AccountBaseDto accountBaseDto) {
        GenerateContractResp contractResp = generateJdReportSaleContract(activity, asset, accountBaseDto);
        TAssetAuthorization assetAuthorization = assetAuthorizationDao.getJdReportSaleByActivityId(activity.getId());
        int result;
        if (assetAuthorization == null) {
            assetAuthorization = new TAssetAuthorization();
            assetAuthorization.setActivityId(activity.getId());
            assetAuthorization.setPartyId(asset.getPartyId());
            assetAuthorization.setProtocolType(ProtocolTypeConstants.SURVEY_REPORT_SALE);
            assetAuthorization.setProtocolSubtype(ProtocolTypeConstants.JD_REPORT_SALE);
            assetAuthorization.setContractId(contractResp.getContractId());
            assetAuthorization.setDownloadUrl(contractResp.getDownloadUrl());
            assetAuthorization.setViewpdfUrl(contractResp.getViewPdfUrl());
            result = assetAuthorizationDao.insert(assetAuthorization);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else  {
            assetAuthorization.setContractId(contractResp.getContractId());
            assetAuthorization.setDownloadUrl(contractResp.getDownloadUrl());
            assetAuthorization.setViewpdfUrl(contractResp.getViewPdfUrl());
            assetAuthorization.setUpdateTime(new Date());
            result = assetAuthorizationDao.updateById(assetAuthorization);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return contractResp.getViewPdfUrl();
    }

    private String createOrUpdateJdReportCommission(AuctionActivity activity, Asset asset, AccountBaseDto accountBaseDto) {
        GenerateContractResp contractResp = generateJdReportCommissionContract(activity, asset, accountBaseDto);
        TAssetAuthorization assetAuthorization = assetAuthorizationDao.getJdReportCommissionByActivityId(activity.getId());
        int result;
        if (assetAuthorization == null) {
            assetAuthorization = new TAssetAuthorization();
            assetAuthorization.setActivityId(activity.getId());
            assetAuthorization.setPartyId(asset.getPartyId());
            assetAuthorization.setProtocolType(ProtocolTypeConstants.THIRD_AUTH);
            assetAuthorization.setProtocolSubtype(ProtocolTypeConstants.JD_REPORT_COMMISSION);
            assetAuthorization.setContractId(contractResp.getContractId());
            assetAuthorization.setDownloadUrl(contractResp.getDownloadUrl());
            assetAuthorization.setViewpdfUrl(contractResp.getViewPdfUrl());
            result = assetAuthorizationDao.insert(assetAuthorization);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else  {
            assetAuthorization.setContractId(contractResp.getContractId());
            assetAuthorization.setDownloadUrl(contractResp.getDownloadUrl());
            assetAuthorization.setViewpdfUrl(contractResp.getViewPdfUrl());
            assetAuthorization.setUpdateTime(new Date());
            result = assetAuthorizationDao.updateById(assetAuthorization);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return contractResp.getViewPdfUrl();
    }

    private String createOrUpdateThirdProtocol(AuctionActivity activity, Asset asset, AccountBaseDto accountBaseDto) {
        GenerateContractResp contractResp = generateThirdProtocolContract(activity, asset, accountBaseDto);
        TAssetAuthorization assetAuthorization = assetAuthorizationDao.getThirdProtocolByActivityId(activity.getId());
        int result;
        if (assetAuthorization == null) {
            assetAuthorization = new TAssetAuthorization();
            assetAuthorization.setActivityId(activity.getId());
            assetAuthorization.setPartyId(asset.getPartyId());
            assetAuthorization.setProtocolType(ProtocolTypeConstants.THIRD_AUTH);
            assetAuthorization.setProtocolSubtype(ProtocolTypeConstants.THIRD_PROTOCOL);
            assetAuthorization.setContractId(contractResp.getContractId());
            assetAuthorization.setDownloadUrl(contractResp.getDownloadUrl());
            assetAuthorization.setViewpdfUrl(contractResp.getViewPdfUrl());
            result = assetAuthorizationDao.insert(assetAuthorization);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else  {
            assetAuthorization.setContractId(contractResp.getContractId());
            assetAuthorization.setDownloadUrl(contractResp.getDownloadUrl());
            assetAuthorization.setViewpdfUrl(contractResp.getViewPdfUrl());
            assetAuthorization.setUpdateTime(new Date());
            result = assetAuthorizationDao.updateById(assetAuthorization);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return contractResp.getViewPdfUrl();
    }

    private GenerateContractResp generateJdReportSaleContract(AuctionActivity activity, Asset asset, AccountBaseDto accountBaseDto) {
        GenerateContractComReq contractComReq = new GenerateContractComReq();
        contractComReq.setActivityId(activity.getId() + "");
        contractComReq.setPartyId(asset.getPartyId() + "");
        contractComReq.setFddId(accountBaseDto.getFadadaId());
        contractComReq.setType(FddEnums.SING_TYPE.JINDIAO_DELEGATION.getType());
        GenerateServiceDueReportReq dueReportReq = new GenerateServiceDueReportReq();
        dueReportReq.setActivityName(activity.getAssetName());
        dueReportReq.setSeller(accountBaseDto.getName());
        GenerateContractResp contractResp = fddSignatureFacade.generateContract(contractComReq, dueReportReq);
        if (contractResp == null || !contractResp.getCode().equals("000")) {
            log.error("生成尽调报告销售授权委托书失败，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(dueReportReq), JSON.toJSONString(contractResp));
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        log.info("生成尽调报告销售授权委托书，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(dueReportReq), JSON.toJSONString(contractResp));
        return contractResp;
    }

    private GenerateContractResp generateJdReportCommissionContract(AuctionActivity activity, Asset asset, AccountBaseDto accountBaseDto) {
        GenerateContractComReq contractComReq = new GenerateContractComReq();
        contractComReq.setActivityId(activity.getId() + "");
        contractComReq.setPartyId(asset.getPartyId() + "");
        contractComReq.setFddId(accountBaseDto.getFadadaId());
        contractComReq.setType(FddEnums.SING_TYPE.JINZI_DELEGATION.getType());
        GenerateServiceDueDiligenceReq dueDiligenceReq = new GenerateServiceDueDiligenceReq();
        String categoryName = assetDao.getCategoryName(asset.getId());
        if (StringUtils.isNotEmpty(categoryName)) {
            if (categoryName.contains("物权")) {
                dueDiligenceReq.setActivityName("物权");
            } else if (categoryName.contains("债权")) {
                dueDiligenceReq.setActivityName("债权");
            }
        }
        dueDiligenceReq.setSeller(accountBaseDto.getName());
        dueDiligenceReq.setBeginTime(DateUtil.getNormDateStr(activity.getBeginAt()));
        dueDiligenceReq.setEndTime(DateUtil.getNormDateStr(activity.getEndAt()));
        dueDiligenceReq.setEntrustBeginTime(DateUtil.formatNormDate(new Date()));
        dueDiligenceReq.setEntrustEndTime(DateUtil.formatNormDate(DateUtil.nextDay(30)));
        dueDiligenceReq.setLawFirm(getLawFirm(asset));
        GenerateContractResp contractResp = fddSignatureFacade.generateContract(contractComReq, dueDiligenceReq);
        if (contractResp == null || !contractResp.getCode().equals("000")) {
            log.error("生成尽职调查授权委托书失败，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(dueDiligenceReq), JSON.toJSONString(contractResp));
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        log.info("生成尽职调查授权委托书，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(dueDiligenceReq), JSON.toJSONString(contractResp));
        return contractResp;
    }

    private GenerateContractResp generateThirdProtocolContract(AuctionActivity activity, Asset asset, AccountBaseDto accountBaseDto) {
        GenerateContractComReq contractComReq = new GenerateContractComReq();
        contractComReq.setActivityId(activity.getId() + "");
        contractComReq.setPartyId(asset.getPartyId() + "");
        contractComReq.setFddId(accountBaseDto.getFadadaId());
        contractComReq.setType(FddEnums.SING_TYPE.SANFANG_DELEGATION.getType());
        GenerateServiceTripleAgreementReq tripleAgreementReq = new GenerateServiceTripleAgreementReq();
        tripleAgreementReq.setActivityName(activity.getAssetName());
        tripleAgreementReq.setSeller(accountBaseDto.getName());

        Integer providerId = disposeLevelService.getFirstLevelLowOfficeByCityId(asset.getCityId());
        if (providerId == null) {
            throw new BusinessException("未找到一级合伙人");
        }
        TDisposeLevel tDisposeLevel = tDisposeLevelDao.getValidByProviderId(providerId);
        if (tDisposeLevel == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        tripleAgreementReq.setDueCode(tDisposeLevel.getContractNo());
        tripleAgreementReq.setDueSignTime(DateUtil.formatNormDate(tDisposeLevel.getContractDate()));
        DelegationAgreement delegationAgreement = delegationAgreementDao.getByActivityId(activity.getId());
        tripleAgreementReq.setEntrustCode(activity.getCode());
        tripleAgreementReq.setEntrustSignTime(DateUtil.formatNormDate(delegationAgreement.getAllSignedAt()));
        tripleAgreementReq.setLawFirm(getLawFirm(asset));
        GenerateContractResp contractResp = fddSignatureFacade.generateContract(contractComReq, tripleAgreementReq);
        if (contractResp == null || !contractResp.getCode().equals("000")) {
            log.error("生成三方协议失败，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(tripleAgreementReq), JSON.toJSONString(contractResp));
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        log.info("生成三方协议，入参：{}，{}，出参：{}", JSON.toJSONString(contractComReq), JSON.toJSONString(tripleAgreementReq), JSON.toJSONString(contractResp));
        return contractResp;
    }

    private String getLawFirm(Asset asset) {
        Integer providerId = disposeLevelService.getFirstLevelLowOfficeByCityId(asset.getCityId());
        if (providerId == null) {
            throw new BusinessException("未找到一级合伙人");
        }
        TDisposeProvider disposeProvider = tDisposeProviderDao.selectById(providerId);
        if (disposeProvider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        return disposeProvider.getCompanyName();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AssetAuthorizationResp signTuneAuthProtocol(AssetAuthorizationReq.SignTuneAuthProtocol req) {
        AssetAuthorizationResp resp = new AssetAuthorizationResp();
        AuctionActivity activity = auctionActivityDao.selectById(req.getActivityId());
        Asset asset = assetDao.selectById(activity.getAssetId());
        if (!asset.getPartyId().equals(req.getPartyId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        DelegationAgreement delegationAgreement = delegationAgreementDao.getByActivityId(activity.getId());
        if (delegationAgreement == null || !delegationAgreement.getAllSigned()) {
            throw new BusinessException("尚未签署送拍协议");
        }
        if (!DateUtil.isToday(delegationAgreement.getAllSignedAt())) {
            throw new BusinessException("超过提交尽调报告时间");
        }
        AccountBaseDto accountBaseDto;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(asset.getComeFrom())) {
            accountBaseDto = new AccountBaseDto();
            TAgency agency = agencyDao.selectById(asset.getAgencyId());
            accountBaseDto.setName(agency.getName());
            accountBaseDto.setFadadaId(agency.getFadadaId());
            accountBaseDto.setType(SystemConstant.ACCOUNT_COMPANY_TYPE);
            accountBaseDto.setPartyPrimaryId(asset.getPartyId());
        } else {
            accountBaseDto = accountService.getAccountBaseByPartyId(req.getPartyId());
        }
        if (SurveyReportEnum.ReportSourceEnum.SELF.getKey().equals(req.getReportSource())) {
            signJdReportSaleContract(activity, asset, accountBaseDto);
        } else if (SurveyReportEnum.ReportSourceEnum.THIRD.getKey().equals(req.getReportSource())) {
            String surveyNo = OrderCodeGenerator.INSTANCE.getOrderCode(OrderCodeGenerator.THIRD_SURVEY_NO);
            int result = addDisposeSurvey(surveyNo, activity.getId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            signJdReportCommissionContract(activity, asset, accountBaseDto, surveyNo);
            signThirdProtocolContract(activity, asset, accountBaseDto, surveyNo);
            serviceMessageUtils.thirdConfirmToOperator(activity.getId());
            setSurveyExpireTime(activity, surveyNo);
        }
        return resp;
    }

    @Override
    public String getAuthSource(Integer activityId) {

        try {
            // 判断是否签订销售协议、判断是否签订三方协议
            boolean tag = isSignByProtocolType(activityId, ProtocolTypeConstants.THIRD_AUTH);

            if (!tag) {

                return isSignByProtocolType(activityId, ProtocolTypeConstants.SURVEY_REPORT_SALE) ? ProtocolTypeConstants.SURVEY_REPORT_SALE : null;
            }

            return ProtocolTypeConstants.THIRD_AUTH;
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    private void setSurveyExpireTime(AuctionActivity activity, String surveyNo) {
        // 判断活动是否授权了第三方
        String authSource = getAuthSource(activity.getId());
        if (ProtocolTypeConstants.THIRD_AUTH.equals(authSource)) {
            long timeout = 1 * 60 * 60 * 24 * 15;
            mqSender.providerSurveyDeadlineEnqueue(surveyNo + "", timeout);
        }
    }

    private void signJdReportSaleContract(AuctionActivity activity, Asset asset, AccountBaseDto accountBaseDto) {
        TAssetAuthorization assetAuthorization = assetAuthorizationDao.getJdReportSaleByActivityId(activity.getId());
        if (assetAuthorization == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (assetAuthorization.getSigned()) {
            throw new BusinessException("协议已经签署");
        }
        ExtSignContractReq contractReq = new ExtSignContractReq();
        contractReq.setType(FddEnums.SING_TYPE.JINDIAO_DELEGATION.getType());
        contractReq.setActivity_id(assetAuthorization.getActivityId() + "");
        contractReq.setContract_id(assetAuthorization.getContractId());

        List<FddSignInfo> signInfos = new ArrayList<>();
        FddSignInfo fddSignInfo = new FddSignInfo();
        fddSignInfo.setMem_role(accountBaseDto.getType().equals(SystemConstant.ACCOUNT_USER_TYPE) ? "1" : "2");
        fddSignInfo.setFdd_id(accountBaseDto.getFadadaId());
        fddSignInfo.setSign_role(FddEnums.SING_ROLE_TYPE.SELLER.getType());
        fddSignInfo.setIs_auto(FddEnums.SING_AUTO.AUTO.getType());
        fddSignInfo.setParty_id(accountBaseDto.getPartyPrimaryId() + "");
        signInfos.add(fddSignInfo);
        contractReq.setSign_list(signInfos);
        ExtSignContractResp contractResp = fddSignatureFacade.extSignContract(contractReq);
        if (contractResp == null || !contractResp.getCode().equals("000")) {
            log.error("签署尽调报告销售授权委托书失败，入参：{}，出参：{}", JSON.toJSONString(contractReq), JSON.toJSONString(contractResp));
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        log.info("签署尽调报告销售授权委托书，入参：{}，出参：{}", JSON.toJSONString(contractReq), JSON.toJSONString(contractResp));
        assetAuthorization.setSigned(true);
        assetAuthorization.setAllSigned(true);
        assetAuthorization.setUpdateTime(new Date());
        int result = assetAuthorizationDao.updateById(assetAuthorization);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        asset.setTuneReportAuthorization(true);
        asset.setUpdatedAt(new Date());
        assetDao.updateById(asset);
    }

    private void signJdReportCommissionContract(AuctionActivity activity, Asset asset, AccountBaseDto firstParty, String surveyNo) {
        TAssetAuthorization assetAuthorization = assetAuthorizationDao.getJdReportCommissionByActivityId(activity.getId());
        if (assetAuthorization == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (assetAuthorization.getSigned()) {
            throw new BusinessException("协议已经签署");
        }
        ExtSignContractReq contractReq = new ExtSignContractReq();
        contractReq.setType(FddEnums.SING_TYPE.JINZI_DELEGATION.getType());
        contractReq.setActivity_id(assetAuthorization.getActivityId() + "");
        contractReq.setContract_id(assetAuthorization.getContractId());

        List<FddSignInfo> signInfos = new ArrayList<>();
        FddSignInfo fddSignInfo = new FddSignInfo();
        fddSignInfo.setMem_role(firstParty.getType().equals(SystemConstant.ACCOUNT_USER_TYPE) ? "1" : "2");
        fddSignInfo.setFdd_id(firstParty.getFadadaId());
        fddSignInfo.setSign_role(FddEnums.SING_ROLE_TYPE.SELLER.getType());
        fddSignInfo.setIs_auto(FddEnums.SING_AUTO.AUTO.getType());
        fddSignInfo.setParty_id(firstParty.getPartyPrimaryId() + "");
        signInfos.add(fddSignInfo);

        FddSignInfo fddSignInfo2 = new FddSignInfo();
        Integer providerId = disposeLevelService.getFirstLevelLowOfficeByCityId(asset.getCityId());
        if (providerId == null) {
            throw new BusinessException("未找到一级合伙人");
        }
        TDisposeProvider disposeProvider = tDisposeProviderDao.selectById(providerId);
        if (disposeProvider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AccountBaseDto thirdParty = accountService.getAccountBaseByPartyId(disposeProvider.getPartyId());
        fddSignInfo2.setMem_role(thirdParty.getType().equals(SystemConstant.ACCOUNT_USER_TYPE) ? "1" : "2");
        fddSignInfo2.setFdd_id(thirdParty.getFadadaId());
        fddSignInfo2.setSign_role(FddEnums.SING_ROLE_TYPE.AGENCY.getType());
        fddSignInfo2.setIs_auto(FddEnums.SING_AUTO.AUTO.getType());
        fddSignInfo2.setParty_id(thirdParty.getPartyPrimaryId() + "");
        signInfos.add(fddSignInfo2);
        contractReq.setSign_list(signInfos);
        ExtSignContractResp contractResp = fddSignatureFacade.extSignContract(contractReq);
        if (contractResp == null || !contractResp.getCode().equals("000")) {
            log.error("签署尽职调查授权委托书失败，入参：{}，出参：{}", JSON.toJSONString(contractReq), JSON.toJSONString(contractResp));
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        log.info("签署尽职调查授权委托书，入参：{}，出参：{}", JSON.toJSONString(contractReq), JSON.toJSONString(contractResp));
        assetAuthorization.setSigned(true);
        assetAuthorization.setAllSigned(true);
        assetAuthorization.setSurveyNo(surveyNo);
        assetAuthorization.setUpdateTime(new Date());
        int result = assetAuthorizationDao.updateById(assetAuthorization);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
    }

    private void signThirdProtocolContract(AuctionActivity activity, Asset asset, AccountBaseDto firstParty, String surveyNo) {
        TAssetAuthorization assetAuthorization = assetAuthorizationDao.getThirdProtocolByActivityId(activity.getId());
        if (assetAuthorization == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (assetAuthorization.getSigned()) {
            throw new BusinessException("协议已经签署");
        }
        ExtSignContractReq contractReq = new ExtSignContractReq();
        contractReq.setType(FddEnums.SING_TYPE.SANFANG_DELEGATION.getType());
        contractReq.setActivity_id(assetAuthorization.getActivityId() + "");
        contractReq.setContract_id(assetAuthorization.getContractId());

        List<FddSignInfo> signInfos = new ArrayList<>();
        FddSignInfo fddSignInfo = new FddSignInfo();
        fddSignInfo.setMem_role(firstParty.getType().equals(SystemConstant.ACCOUNT_USER_TYPE) ? "1" : "2");
        fddSignInfo.setFdd_id(firstParty.getFadadaId());
        fddSignInfo.setSign_role(FddEnums.SING_ROLE_TYPE.SELLER.getType());
        fddSignInfo.setIs_auto(FddEnums.SING_AUTO.AUTO.getType());
        fddSignInfo.setParty_id(firstParty.getPartyPrimaryId() + "");
        signInfos.add(fddSignInfo);
        FddSignInfo fddSignInfo2 = new FddSignInfo();
        Integer providerId = disposeLevelService.getFirstLevelLowOfficeByCityId(asset.getCityId());
        if (providerId == null) {
            throw new BusinessException("未找到一级合伙人");
        }
        TDisposeProvider disposeProvider = tDisposeProviderDao.selectById(providerId);
        if (disposeProvider == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        AccountBaseDto thirdParty = accountService.getAccountBaseByPartyId(disposeProvider.getPartyId());
        fddSignInfo2.setMem_role(thirdParty.getType().equals(SystemConstant.ACCOUNT_USER_TYPE) ? "1" : "2");
        fddSignInfo2.setFdd_id(thirdParty.getFadadaId());
        fddSignInfo2.setSign_role(FddEnums.SING_ROLE_TYPE.AGENCY.getType());
        fddSignInfo2.setIs_auto(FddEnums.SING_AUTO.AUTO.getType());
        fddSignInfo2.setParty_id(thirdParty.getPartyPrimaryId() + "");
        signInfos.add(fddSignInfo2);
        contractReq.setSign_list(signInfos);
        ExtSignContractResp contractResp = fddSignatureFacade.extSignContract(contractReq);
        if (contractResp == null || !contractResp.getCode().equals("000")) {
            log.error("签署三方协议失败，入参：{}，出参：{}", JSON.toJSONString(contractReq), JSON.toJSONString(contractResp));
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        log.info("签署三方协议，入参：{}，出参：{}", JSON.toJSONString(contractReq), JSON.toJSONString(contractResp));
        assetAuthorization.setSigned(true);
        assetAuthorization.setAllSigned(true);
        assetAuthorization.setSurveyNo(surveyNo);
        assetAuthorization.setUpdateTime(new Date());
        int result = assetAuthorizationDao.updateById(assetAuthorization);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
    }

    private int addDisposeSurvey(String surveyNo, Integer activityId) {
        // 查找该城市一级合伙人
        Asset asset        = auctionActivityService.getAssetByActivityId(activityId);
        Integer providerId = disposeLevelService.getFirstLevelLowOfficeByCityId(asset.getCityId());
        if (null != providerId) {
            int i = disposeSurveyService.addDisposeSurvey(surveyNo, asset.getId(), providerId,
                    DateUtil.getSysTime(), asset.getName(), asset.getCityId());
            return i;
        } else {
            log.info("cityId:{},该城市无一级合伙人",asset.getCityId());
        }
        return 0;
    }

    private int addProtocol(Integer assetId, Integer partyId, String protocolType, String surveyNo,
                            String protocolViewUrl, String protocolDownloadUrl) {

        String[] protocols = ProtocolTypeConstants.getProtocolType().get(protocolType);
        int count = 0;
        for (String protocol : protocols) {
            TAssetAuthorization authorization = new TAssetAuthorization();
            authorization.setActivityId(assetId);
            authorization.setSurveyNo(surveyNo);
            authorization.setPartyId(partyId);
            authorization.setProtocolSubtype(protocol);
            authorization.setDownloadUrl(protocolDownloadUrl);
            authorization.setViewpdfUrl(protocolViewUrl);
            authorization.setProtocolType(protocolType);
            count += assetAuthorizationDao.insert(authorization);
        }
        return count;
    }
}
