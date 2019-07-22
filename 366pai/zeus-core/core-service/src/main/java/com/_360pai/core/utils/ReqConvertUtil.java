package com._360pai.core.utils;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.BaseBusinessException;
import com._360pai.core.common.constants.*;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.*;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.req.AlbumReq;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.facade.shop.req.ShopReq;

import com._360pai.core.facade.h5.req.H5Req;
import com._360pai.core.model.account.*;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.AuctionActivityAlbum;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.model.applet.TAppletShopUpdateApplyRecord;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.h5.TAnnualMeetingApplyRecord;
import com._360pai.core.model.numberJump.TDebtCalculator;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculator;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xdrodger
 * @Title: ReqConvertUtil
 * @ProjectName zeus
 * @Description:
 * @date 07/09/2018 09:52
 */
public class ReqConvertUtil {

    public  static AuctionActivityAlbum convertToAuctionActivityAlbum(AlbumReq.AlbumCreateReq req) {
        AuctionActivityAlbum model = new AuctionActivityAlbum();
        BeanUtils.copyProperties(req, model);
        model.setIsOnline(false);
        model.setCreatedAt(new Date());
        return model;
    }

    public  static AuctionActivityAlbum convertToAuctionActivityAlbum(AlbumReq.AlbumUpdateReq req) {
        AuctionActivityAlbum model = new AuctionActivityAlbum();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public  static TUser convertToUser(UserReq.UpdateReq req) {
        TUser model = new TUser();
        BeanUtils.copyProperties(req, model);
        model.setUpdateTime(new Date());
        return model;
    }

    public static TCompany convertToCompany(CompanyReq.UpdateReq req) {
        TCompany model = new TCompany();
        model.setUpdateTime(new Date());
        BeanUtils.copyProperties(req, model);
        model.setUpdateTime(new Date());
        return model;
    }


    public static TCompany convertToCompany(CompanyReq.CreateChannelPayCompanyReq req) {
        TCompany model = new TCompany();
        BeanUtils.copyProperties(req, model);
        model.setStatus(1);
        model.setChannelPay(1);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        return model;
    }

    public static Asset convertToAsset(AssetReq.UpdateReq req) {
        Asset model = new Asset();
        model.setId(req.getId());
        if (StringUtils.isNotBlank(req.getName())) {
            model.setName(req.getName());
        }
        model.setSpecialDetail(req.getSpecialDetail());
        model.setDetail(req.getDetail());
        model.setUpdatedAt(new Date());
        return model;
    }

    public static AuctionActivity convertToActivity(ActivityReq.UpdateReq req) {
        AuctionActivity model = new AuctionActivity();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TAgencyApplyRecord convertToAgencyApplyRecord(AgencyApplyReq.CreateReq req) {
        TAgencyApplyRecord model = new TAgencyApplyRecord();
        BeanUtils.copyProperties(req, model);
        model.setStatus(AgencyEnum.AgencyApplyStatus.PENDING.getKey());
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        return model;
    }

    public static TAgencyApplyRecord convertToAgencyApplyRecord(AgencyApplyReq.UpdateReq req) {
        TAgencyApplyRecord model = new TAgencyApplyRecord();
        BeanUtils.copyProperties(req, model);
        model.setUpdateTime(new Date());
        return model;
    }

    public static TAgency convertToAgency(AgencyReq.UpdateReq req, TAgency agency) {
        TAgency model = new TAgency();
        BeanUtils.copyProperties(req, model);
        model.setUpdateTime(new Date());
        model.setLicense(agency.getLicense());
        return model;
    }

    public static TAgency convertToAgency(AgencyReq.AgencyUpdateReq req) {
        TAgency model = new TAgency();
        BeanUtils.copyProperties(req, model);
        model.setUpdateTime(new Date());
        return model;
    }

    public static TDisposeProviderApply convertToDisposeProviderApply(DisposeProviderApplyReq.CreateReq req) {
        TDisposeProviderApply model = new TDisposeProviderApply();
        BeanUtils.copyProperties(req, model);
        if (StringUtils.isEmpty(req.getDisposeType())) {
            throw new BusinessException(ApiCallResult.EMPTY, "disposeType");
        }
//        if (DisposalEnum.DisposeType.LAW_OFFICE.getKey().equals(req.getDisposeType())) {
//            if (req.getProvideServices() == null || req.getProvideServices().isEmpty()) {
//                throw new BusinessException(ApiCallResult.EMPTY, "provideServices");
//            }
//            List<String> provideServices = req.getProvideServices();
//            for (String item : provideServices) {
//                if (!DisposalEnum.RequirementType.isCodeExist(item)) {
//                    throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
//                }
//            }
//            model.setProvideService(listConvertToString(req.getProvideServices()));
//        } else {
//            model.setProvideService(DisposalEnum.RequirementType.PINGGU.getKey());
//        }

        if (DisposalEnum.DisposeType.LAW_OFFICE.getKey().equals(req.getDisposeType())
            || DisposalEnum.DisposeType.LAWYER.getKey().equals(req.getDisposeType())) {
            model.setProvideService(getAllProviderService());
        }
//        model.setCompanyType(Optional.ofNullable(model.getCompanyType()).orElse(DisposalEnum.DisposeType.getValueByKey(req.getDisposeType())));
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        return model;
    }

    private static String getAllProviderService() {
        DisposalEnum.RequirementType[] values = DisposalEnum.RequirementType.values();
        StringBuilder sb = new StringBuilder();
        for (DisposalEnum.RequirementType tmp : values) {
            if (!tmp.equals(DisposalEnum.RequirementType.PINGGU)) {
                sb.append(tmp.getKey());
                sb.append(",");
            }
        }
        return sb.substring(0, sb.length()-1);
    }

    public static TDisposeProvider convertToDisposeProvider(DisposeProviderReq.UpdateReq req, TDisposeProvider provider) {
        TDisposeProvider model = new TDisposeProvider();
        BeanUtils.copyProperties(req, model);
        if (StringUtils.isEmpty(req.getDisposeType())) {
            throw new BusinessException(ApiCallResult.EMPTY, "disposeType");
        }
        if (DisposalEnum.DisposeType.LAW_OFFICE.getKey().equals(req.getDisposeType())) {
            if (req.getProvideServices() == null || req.getProvideServices().isEmpty()) {
                throw new BusinessException(ApiCallResult.EMPTY, "provideServices");
            }
            List<String> provideServices = req.getProvideServices();
            for (String item : provideServices) {
                if (!DisposalEnum.RequirementType.isCodeExist(item)) {
                    throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
                }
            }
//            model.setProvideService(listConvertToString(req.getProvideServices()));
        }
        model.setProvideService(listConvertToString(req.getProvideServices()));
        model.setAccountId(provider.getAccountId());
        model.setPartyId(provider.getPartyId());
        model.setUpdateTime(new Date());
        return model;
    }

    public static TFundProviderApply convertToFundProviderApply(FundProviderApplyReq.CreateReq req) {
        TFundProviderApply model = new TFundProviderApply();
        if (req.getFundType().equals(FastwayEnum.FundType.Company) && !WithfudigEnum.CompanyType.contains(req.getCompanyType())) {
            throw new BusinessException(ApiCallResult.EMPTY, "companyType");
        }
        BeanUtils.copyProperties(req, model);
        if (model.getProviderMinAmount() != null) {
            model.setProviderMinAmount(model.getProviderMinAmount().multiply(new BigDecimal(10000)));
        }
        if (model.getProviderMaxAmount() != null) {
            model.setProviderMaxAmount(model.getProviderMaxAmount().multiply(new BigDecimal(10000)));
        }
        if (model.getSingleMinAmount() != null) {
            model.setSingleMinAmount(model.getSingleMinAmount().multiply(new BigDecimal(10000)));
        }
        if (model.getSingleMaxAmount() != null) {
            model.setSingleMaxAmount(model.getSingleMaxAmount().multiply(new BigDecimal(10000)));
        }
        model.setProviderArea(getCityStr(req.getProviderAreas()));
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        return model;
    }

    public static TFundProvider convertToFundProvider(FundProviderReq.UpdateReq req, TFundProvider provider) {
        TFundProvider model = new TFundProvider();
        if (provider.getFundType().equals(FastwayEnum.FundType.Company) && !WithfudigEnum.CompanyType.contains(req.getCompanyType())) {
            throw new BusinessException(ApiCallResult.EMPTY, "companyType");
        }
        BeanUtils.copyProperties(req, model);
        if (model.getProviderMinAmount() != null) {
            model.setProviderMinAmount(model.getProviderMinAmount().multiply(new BigDecimal(10000)));
        }
        if (model.getProviderMaxAmount() != null) {
            model.setProviderMaxAmount(model.getProviderMaxAmount().multiply(new BigDecimal(10000)));
        }
        if (model.getSingleMinAmount() != null) {
            model.setSingleMinAmount(model.getSingleMinAmount().multiply(new BigDecimal(10000)));
        }
        if (model.getSingleMaxAmount() != null) {
            model.setSingleMaxAmount(model.getSingleMaxAmount().multiply(new BigDecimal(10000)));
        }
        model.setCompanyName(req.getName());
        model.setProviderArea(getCityStr(req.getProviderAreas()));
        model.setUpdateTime(new Date());
        model.setAccountId(provider.getAccountId());
        model.setPartyId(provider.getPartyId());
        return model;
    }

    private static String listConvertToString(List<String> list) {
        if (list != null && !list.isEmpty()) {
            Iterator<String> itr = list.iterator();
            StringBuffer str = new StringBuffer();
            while (itr.hasNext()) {
                str.append(itr.next());
                if (itr.hasNext()) {
                    str.append(",");
                }
            }
            return str.toString();
        }
        return "";
    }

    private static String getCityStr(List<CityVo> cities) {
        return JSON.toJSONString(cities == null ? Collections.EMPTY_LIST : cities);
    }

    public static Staff convertToStaff(StaffReq.CreateReq req) {
        Staff model = new Staff();
        if (StringUtils.isEmpty(req.getName())) {
            throw new BusinessException(ApiCallResult.EMPTY, "name");
        }
        if (StringUtils.isEmpty(req.getQq())) {
            throw new BusinessException(ApiCallResult.EMPTY, "qq");
        }
        if (StringUtils.isEmpty(req.getMobile())) {
            throw new BusinessException(ApiCallResult.EMPTY, "mobile");
        }
        if (StringUtils.isEmpty(req.getPassword())) {
            throw new BusinessException(ApiCallResult.EMPTY, "password");
        }
        BeanUtils.copyProperties(req, model);
        model.setIsAdmin(false);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        return model;
    }

    public static Staff convertToStaff(StaffReq.UpdateReq req) {
        Staff model = new Staff();
        BeanUtils.copyProperties(req, model);
        model.setId(req.getStaffId());
        model.setUpdateTime(new Date());
        return model;
    }

    public static BankAccount convertToBankAccount(BankAccountReq.CreateReq req) {
        BankAccount model = new BankAccount();
        model.setPartyId(req.getPartyId());
        model.setBankId(req.getBankId());
        model.setName(req.getName());
        model.setNumber(req.getNumber());
        return model;
    }

    public static BankAccount convertToBankAccount(BankAccountReq.UpdateReq req) {
        BankAccount model = new BankAccount();
        model.setId(req.getId());
        model.setPartyId(req.getPartyId());
        model.setBankId(req.getBankId());
        model.setName(req.getName());
        model.setNumber(req.getNumber());
        return model;
    }

    public static TPersona convertToTPersona(PersonaReq req) {
        List<String> customerTypes = req.getCustomerTypes();
        if (customerTypes == null || customerTypes.isEmpty()) {
            throw new BaseBusinessException(ApiCallResult.EMPTY);
        }
        for (String item : customerTypes) {
            if (!PersonaEnum.CustomerType.contains(item)) {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        Iterator<String> itr = customerTypes.iterator();
        StringBuffer customerType = new StringBuffer();
        while (itr.hasNext()) {
            customerType.append(itr.next());
            if (itr.hasNext()) {
                customerType.append(",");
            }
        }
        if (!PersonaEnum.UserType.contains(req.getUserType())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!PersonaEnum.UserStatus.contains(req.getUserStatus())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!PersonaEnum.CompanyType.contains(req.getCompanyType())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TPersona model = new TPersona();
        BeanUtils.copyProperties(req, model);
        model.setCustomerType(customerType.toString());
        model.setCompanyBusinessArea(getProvinceStr(req.getCompanyBusinessAreas()));
        return model;
    }

    private static String getProvinceStr(List<PersonaReq.Province> provinces) {
//        if (provinces == null || provinces.isEmpty()) {
//            return "";
//        }
//        Iterator<PersonaReq.Province> itr = provinces.iterator();
//        StringBuffer provinceStr = new StringBuffer();
//        while (itr.hasNext()) {
//            provinceStr.append(itr.next().getName());
//            if (itr.hasNext()) {
//                provinceStr.append(",");
//            }
//        }
//        return provinceStr.toString();
        return JSON.toJSONString(provinces == null ? Collections.EMPTY_LIST : provinces);
    }

    public static TPersonaAssetParty convertToTPersonaAssetParty(PersonaReq.PersonaAssetPartyReq req) {
        if (!PersonaEnum.AssetType.contains(req.getAssetType())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TPersonaAssetParty model = new TPersonaAssetParty();
        BeanUtils.copyProperties(req, model);
        List<String> assetAveragePriceRanges = req.getAssetAveragePriceRanges() == null ? Collections.EMPTY_LIST : req.getAssetAveragePriceRanges();
        Iterator<String> itr = assetAveragePriceRanges.iterator();
        StringBuffer assetAveragePriceRange = new StringBuffer();
        while (itr.hasNext()) {
            assetAveragePriceRange.append(itr.next());
            if (itr.hasNext()) {
                assetAveragePriceRange.append(",");
            }
        }
        model.setAssetAveragePriceRange(assetAveragePriceRange.toString());

        List<String> assetPackageSources = req.getAssetPackageSources() == null ? Collections.EMPTY_LIST : req.getAssetPackageSources();
        for (String item : assetPackageSources) {
            if (!PersonaEnum.AssetPackageSource.contains(item)) {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        itr = assetPackageSources.iterator();
        StringBuffer assetPackageSource = new StringBuffer();
        while (itr.hasNext()) {
            assetPackageSource.append(itr.next());
            if (itr.hasNext()) {
                assetPackageSource.append(",");
            }
        }
        model.setAssetPackageSource(assetPackageSource.toString());
        model.setAssetDistributionArea(getProvinceStr(req.getAssetDistributionAreas()));
        return model;
    }

    public static TPersonaBidder convertToTPersonaBidder(PersonaReq.PersonaBidderReq req) {
        if (!PersonaEnum.FundSource.contains(req.getFundSource())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!PersonaEnum.FundingLevel.contains(req.getFundingLevel())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!PersonaEnum.AssetType.contains(req.getAssetType())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!PersonaEnum.ResponsibleInvestigation.contains(req.getResponsibleInvestigation())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!PersonaEnum.DispoalInvestigation.contains(req.getDispoalInvestigation())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        List<String> assetPropertyTypes = req.getAssetPropertyTypes() == null ? Collections.EMPTY_LIST : req.getAssetPropertyTypes();
        for (String item : assetPropertyTypes) {
            if (!PersonaEnum.AssetPropertyType.contains(item)) {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        Iterator<String> itr = assetPropertyTypes.iterator();
        StringBuffer assetPropertyType = new StringBuffer();
        while (itr.hasNext()) {
            assetPropertyType.append(itr.next());
            if (itr.hasNext()) {
                assetPropertyType.append(",");
            }
        }
        TPersonaBidder model = new TPersonaBidder();
        BeanUtils.copyProperties(req, model);
        model.setAssetPropertyType(assetPropertyType.toString());
        model.setInvestmentArea(getProvinceStr(req.getInvestmentAreas()));
        return model;
    }

    public static TPersonaDisposalParty convertToTPersonaDisposalParty(PersonaReq.PersonaDisposalPartyReq req) {
        if (!PersonaEnum.DisposalBusinessType.contains(req.getBusinessType())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        List<String> assetPropertyTypes = req.getAssetPropertyTypes() == null ? Collections.EMPTY_LIST : req.getAssetPropertyTypes();
        for (String item : assetPropertyTypes) {
            if (!PersonaEnum.AssetPropertyType.contains(item)) {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        Iterator<String> itr = assetPropertyTypes.iterator();
        StringBuffer assetPropertyType = new StringBuffer();
        while (itr.hasNext()) {
            assetPropertyType.append(itr.next());
            if (itr.hasNext()) {
                assetPropertyType.append(",");
            }
        }
        TPersonaDisposalParty model = new TPersonaDisposalParty();
        BeanUtils.copyProperties(req, model);
        model.setBusinessArea(getProvinceStr(req.getBusinessAreas()));
        model.setAssetPropertyType(assetPropertyType.toString());
        return model;
    }

    public static TPersonaFundingAgency convertToTPersonaFundingAgency(PersonaReq.PersonaFundingAgencyReq req) {
        List<String> assetPropertyTypes = req.getAssetPropertyTypes() == null ? Collections.EMPTY_LIST : req.getAssetPropertyTypes();
        for (String item : assetPropertyTypes) {
            if (!PersonaEnum.AssetPropertyType.contains(item)) {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        List<String> fundingLevels = req.getFundingLevels() == null ? Collections.EMPTY_LIST : req.getFundingLevels();
        for (String item : fundingLevels) {
            if (!PersonaEnum.FundingLevel.contains(item)) {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        Iterator<String> itr = assetPropertyTypes.iterator();
        StringBuffer assetPropertyType = new StringBuffer();
        while (itr.hasNext()) {
            assetPropertyType.append(itr.next());
            if (itr.hasNext()) {
                assetPropertyType.append(",");
            }
        }
        itr = fundingLevels.iterator();
        StringBuffer fundingLevel = new StringBuffer();
        while (itr.hasNext()) {
            fundingLevel.append(itr.next());
            if (itr.hasNext()) {
                fundingLevel.append(",");
            }
        }
        TPersonaFundingAgency model = new TPersonaFundingAgency();
        BeanUtils.copyProperties(req, model);
        model.setAssetPropertyType(assetPropertyType.toString());
        model.setFundingLevel(fundingLevel.toString());
        model.setBusinessArea(getProvinceStr(req.getBusinessAreas()));
        return model;
    }

    public static TPersonaIntermediaryOrgan convertToTPersonaIntermediaryOrgan(PersonaReq.PersonaIntermediaryOrganReq req) {
        if (!PersonaEnum.IntermediaryOrganBusinessType.contains(req.getBusinessType())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!PersonaEnum.AssetType.contains(req.getAssetType())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        List<String> assetPropertyTypes = req.getAssetPropertyTypes() == null ? Collections.EMPTY_LIST : req.getAssetPropertyTypes();
        for (String item : assetPropertyTypes) {
            if (!PersonaEnum.AssetPropertyType.contains(item)) {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        Iterator<String> itr = assetPropertyTypes.iterator();
        StringBuffer assetPropertyType = new StringBuffer();
        while (itr.hasNext()) {
            assetPropertyType.append(itr.next());
            if (itr.hasNext()) {
                assetPropertyType.append(",");
            }
        }
        TPersonaIntermediaryOrgan model = new TPersonaIntermediaryOrgan();
        BeanUtils.copyProperties(req, model);
        model.setAssetPropertyType(assetPropertyType.toString());
        model.setBusinessArea(getProvinceStr(req.getBusinessAreas()));
        return model;
    }

    public static TPersonaUnionAuctionAgency convertToTPersonaUnionAuctionAgency(PersonaReq.PersonaUnionAuctionAgencyReq req) {
        if (!PersonaEnum.AssetType.contains(req.getAssetType())) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }

        List<String> assetPropertyTypes = req.getAssetPropertyTypes() == null ? Collections.EMPTY_LIST : req.getAssetPropertyTypes();
        for (String item : assetPropertyTypes) {
            if (!PersonaEnum.AssetPropertyType.contains(item)) {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
        }
        Iterator<String> itr = assetPropertyTypes.iterator();
        StringBuffer assetPropertyType = new StringBuffer();
        while (itr.hasNext()) {
            assetPropertyType.append(itr.next());
            if (itr.hasNext()) {
                assetPropertyType.append(",");
            }
        }
        TPersonaUnionAuctionAgency model = new TPersonaUnionAuctionAgency();
        BeanUtils.copyProperties(req, model);
        model.setAssetPropertyType(assetPropertyType.toString());
        model.setBusinessArea(getProvinceStr(req.getBusinessAreas()));
        return model;
    }

    public static TAppletShopUpdateApplyRecord convertToTAppletShopUpdateApplyRecord(ShopReq.UpdateApplyReq req) {
        TAppletShopUpdateApplyRecord model = new TAppletShopUpdateApplyRecord();
        BeanUtils.copyProperties(req, model);
        if (StringUtils.isNotEmpty(model.getName())) {
            model.setName(StringEscapeUtils.escapeJava(model.getName()));
        }
        return model;
    }

    public static TBankAccount convertToTBankAccount(TBankAccountReq.CreateReq req) {
        TBankAccount model = new TBankAccount();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TBankAccount convertToTBankAccount(TBankAccountReq.PlatformCreateReq req) {
        TBankAccount model = new TBankAccount();
        BeanUtils.copyProperties(req, model);
        model.setIsDelete("0".equals(req.getStatus()) ? true : false);
        return model;
    }

    public static TBankAccount convertToTBankAccount(TBankAccountReq.PlatformUpdateReq req) {
        TBankAccount model = new TBankAccount();
        BeanUtils.copyProperties(req, model);
        model.setIsDelete("0".equals(req.getStatus()) ? true : false);
        return model;
    }


    public static TAppletShop convertToTAppletShop(ShopReq.UpdateReq req) {
        TAppletShop model = new TAppletShop();
        BeanUtils.copyProperties(req, model);
        model.setId(req.getShopId());
        return model;
    }


    public static TAnnualMeetingApplyRecord convertToTAnnualMeetingApplyRecord(H5Req.AnnualMeetingApplyReq req) {
        TAnnualMeetingApplyRecord model = new TAnnualMeetingApplyRecord();
        BeanUtils.copyProperties(req, model);
        return model;
    }

    public static TFundProviderApply convertToFundProviderApply(FundProviderApplyReq.UpdateReq req) {
        TFundProviderApply model = new TFundProviderApply();
        BeanUtils.copyProperties(req, model);
        if (model.getProviderMinAmount() != null) {
            model.setProviderMinAmount(model.getProviderMinAmount().multiply(new BigDecimal(10000)));
        }
        if (model.getProviderMaxAmount() != null) {
            model.setProviderMaxAmount(model.getProviderMaxAmount().multiply(new BigDecimal(10000)));
        }
        if (model.getSingleMinAmount() != null) {
            model.setSingleMinAmount(model.getSingleMinAmount().multiply(new BigDecimal(10000)));
        }
        if (model.getSingleMaxAmount() != null) {
            model.setSingleMaxAmount(model.getSingleMaxAmount().multiply(new BigDecimal(10000)));
        }
        model.setId(req.getApplyId());
        model.setProviderArea(getCityStr(req.getProviderAreas()));
        model.setUpdateTime(new Date());
        return model;
    }

    public static TDebtCalculator convertToTDebtCalculator(CalculatorReq.DebtCalculatorReq req) {
        TDebtCalculator model = new TDebtCalculator();
        // 避免重新提交时提交了很多不需要的参数
        // BeanUtils.copyProperties(req, model);
        model.setExtBindId(req.getExtBindId());
        model.setProjectName(req.getProjectName());
        model.setDebtPrincipal(req.getDebtPrincipal());
        model.setDebtInterest(req.getDebtInterest());
        model.setMaximumDebt(req.getMaximumDebt());
        model.setLiquidationAmount(req.getLiquidationAmount());
        model.setTransferAmount(req.getTransferAmount());
        model.setGpAmount(req.getGpAmount());
        model.setLpAmount(req.getLpAmount());
        model.setWithFundingAnnualizedRate(req.getWithFundingAnnualizedRate());
        model.setWithFundingPeriod(req.getWithFundingPeriod());
        model.setDisposalAnnualizedRate(req.getDisposalAnnualizedRate());
        model.setDisposalPeriod(req.getDisposalPeriod());
        model.setDueDiligenceAmount(req.getDueDiligenceAmount());
        model.setMiscAmount(req.getMiscAmount());

        model.setProjectManager(req.getProjectManager());
        model.setProvince(req.getProvince());
        model.setCity(req.getCity());
        model.setArea(req.getArea());

        return model;
    }

    public static TPrincipalInterestCalculator convertToTPrincipalInterestCalculator(CalculatorReq.PrincipalInterestCalculatorReq req) {
        TPrincipalInterestCalculator model = new TPrincipalInterestCalculator();
        BeanUtils.copyProperties(req, model);
        return model;
    }
}
