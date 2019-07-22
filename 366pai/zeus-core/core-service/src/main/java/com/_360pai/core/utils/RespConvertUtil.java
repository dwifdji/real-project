package com._360pai.core.utils;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.core.common.constants.*;
import com._360pai.core.facade.account.resp.PersonaDetailResp;
import com._360pai.core.facade.account.resp.PersonaListResp;
import com._360pai.core.facade.account.vo.*;
import com._360pai.core.facade.activity.vo.AlbumVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.activity.vo.FileInfo;
import com._360pai.core.facade.applet.vo.*;
import com._360pai.core.facade.asset.vo.AssetVo;
import com._360pai.core.facade.assistant.vo.BankVo;
import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.facade.payment.vo.AuctionOrderVo;
import com._360pai.core.model.account.*;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.AuctionActivityAlbum;
import com._360pai.core.model.agreement.AdditionalAgreement;
import com._360pai.core.model.agreement.DealAgreement;
import com._360pai.core.model.agreement.DelegationAgreement;
import com._360pai.core.model.agreement.WithdrawAgreement;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.model.applet.TAppletShopUpdateApplyRecord;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.assistant.Bank;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.numberJump.TDebtCalculator;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculator;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculatorDetail;
import com._360pai.core.model.payment.AuctionOrder;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xdrodger
 * @Title: RespConvertUtil
 * @ProjectName zeus-parent
 * @Description:
 * @date 04/09/2018 15:24
 */
@Component
public class RespConvertUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 转换 属性
     */
    public static <T> T convert(Object source, T target) {
        if (source == null) {
            return null;
        }
        BeanUtils.copyProperties(source, target);
        return target;
    }


    public static AssetVo convertToAssetVo(Asset model) {
        if (model == null) {
            return null;
        }
        AssetVo vo = new AssetVo();
        BeanUtils.copyProperties(model, vo);
        if (model.getAssetType() != null) {
            vo.setAssetType(model.getAssetType().getKey());
        }
        vo.setStatus(model.getStatus().getKey());
        vo.setStatusDesc(model.getStatus().getValue());
        vo.setExpectedMode(model.getExpectedMode().getKey());
        vo.setExpectedModeDesc(model.getExpectedMode().getValue());
        PartyAccount contactPerson = new PartyAccount();
        contactPerson.setName(model.getContactName());
        contactPerson.setMobile(model.getContactPhone());
        contactPerson.setQq(model.getContactQq());
        vo.setContactPerson(contactPerson);
        return vo;
    }


    public static AgencyVo convertToAgencyVo(TAgency model) {
        if (model == null) {
            return null;
        }
        AgencyVo vo = new AgencyVo();
        BeanUtils.copyProperties(model, vo);
        vo.setWebsiteStatusDesc(AgencyEnum.AgencyPortalStatus.getKeyByValue(model.getWebsiteStatus()));
        return vo;
    }

    public static AgencyApplyRecordVo convertToAgencyApplyRecordVo(TAgencyApplyRecord model) {
        if (model == null) {
            return null;
        }
        AgencyApplyRecordVo vo = new AgencyApplyRecordVo();
        BeanUtils.copyProperties(model, vo);
        vo.setStatusDesc(AccountEnum.ApplyStatus.getValueByKey(model.getStatus()));
        return vo;
    }

    public static AuctionActivityVo convertToAuctionActivityVo(AuctionActivity model) {
        if (model == null) {
            return null;
        }
        AuctionActivityVo vo = new AuctionActivityVo();
        BeanUtils.copyProperties(model, vo);
        vo.setStatus(model.getStatus().getKey());
        vo.setStatusDesc(model.getStatus().getValue());
        vo.setModeDesc(ActivityEnum.ActivityMode.getKeyByValue(model.getMode()));
        ShopEnum.NewOnlineStatus onlineStatus = getOnlineStatus(model.getStatus().getKey(), model.getBeginAt());
        if (onlineStatus != null) {
            vo.setOnlineStatus(onlineStatus.getKey());
            vo.setOnlineStatusDesc(onlineStatus.getValue());
        }
        vo.setDepositDesc(model.getDeposit().toString());
        if (model.getStartingPrice() == null) {
            vo.setStartingPriceDesc("无");
        } else {
            vo.setStartingPriceDesc(model.getStartingPrice().toString());
        }
        return vo;
    }

    public static ShopEnum.NewOnlineStatus getOnlineStatus(String activityStatus, Date beginAt) {
        if (activityStatus.equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMilliseconds(new Date(), beginAt) < 0) {
            return ShopEnum.NewOnlineStatus.UPCOMING;
        } else if (activityStatus.equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMilliseconds(new Date(), beginAt) > 0) {
            return ShopEnum.NewOnlineStatus.SALE;
        } else if (activityStatus.equals(ActivityEnum.Status.SUCCESS.getKey())) {
            return ShopEnum.NewOnlineStatus.SUCCESS;
        } else if (activityStatus.equals(ActivityEnum.Status.FAILED.getKey())) {
            return ShopEnum.NewOnlineStatus.FAILED;
        }
        return null;
    }

    public static FileInfo convertToFileInfo(DelegationAgreement model) {
        if (model == null) {
            return null;
        }
        FileInfo vo = new FileInfo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static FileInfo convertToFileInfo(AdditionalAgreement model) {
        if (model == null) {
            return null;
        }
        FileInfo vo = new FileInfo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static FileInfo convertToFileInfo(DealAgreement model) {
        if (model == null) {
            return null;
        }
        FileInfo vo = new FileInfo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static AlbumVo convertToAlbumVo(AuctionActivityAlbum model) {
        if (model == null) {
            return null;
        }
        AlbumVo vo = new AlbumVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static AuctionOrderVo convertAuctionOrderVo(AuctionOrder model) {
        if (model == null) {
            return null;
        }
        AuctionOrderVo vo = new AuctionOrderVo();
        BeanUtils.copyProperties(model, vo);
        vo.setId(model.getId() + "");
        vo.setStatusDesc(OrderEnum.Status.getValueByKey(model.getStatus()));
        return vo;
    }

    public static AccountVo convertToAccountVo(TAccount model) {
        if (model == null) {
            return null;
        }
        AccountVo vo = new AccountVo();
        BeanUtils.copyProperties(model, vo);
        if (model.getIsAgencyAdmin() != null && model.getIsAgencyAdmin().equals(1)) {
            vo.setCanCheckReservePrice(true);
        }
        return vo;
    }

    public static StaffVo convertToStaff(Staff model) {
        if (model == null) {
            return null;
        }
        StaffVo vo = new StaffVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static DisposeProviderApplyVo convertToDisposeProviderApplyVo(TDisposeProviderApply model) {
        if (model == null) {
            return null;
        }
        DisposeProviderApplyVo vo = new DisposeProviderApplyVo();
        BeanUtils.copyProperties(model, vo);
        vo.setProvideServices(convertStringToList(model.getProvideService()));
        vo.setStatusDesc(AccountEnum.ApplyStatus.getValueByKey(model.getStatus()));
        return vo;
    }

    public static DisposeProviderVo convertToDisposeProviderVo(TDisposeProvider model) {
        if (model == null) {
            return null;
        }
        DisposeProviderVo vo = new DisposeProviderVo();
        BeanUtils.copyProperties(model, vo);
        vo.setDisposeTypeDesc(DisposalEnum.DisposeType.getValueByKey(model.getDisposeType()));
        vo.setProvideServices(convertStringToList(model.getProvideService()));
        return vo;
    }

    public static DisposeProvider convertToDisposeProvider(TDisposeProvider model) {
        if (model == null) {
            return null;
        }
        DisposeProvider vo = new DisposeProvider();
        BeanUtils.copyProperties(model, vo);
        if (model.getWorkYear() == null || model.getWorkYear().compareTo(BigDecimal.ZERO) <= 0) {
            vo.setWorkYear("1");
        } else {
            vo.setWorkYear(model.getWorkYear().stripTrailingZeros().toPlainString());
        }
        List<String> list = convertStringToList(model.getProvideService());
        List<String> descList = new ArrayList<>();
        for (String item : list) {
            String desc = DisposalEnum.RequirementType.getDescByKey(item);
            if (StringUtils.isNotBlank(desc)) {
                descList.add(desc);
            }
        }
        vo.setProvideServices(descList);
        return vo;
    }

    public static FundProviderApplyVo convertToFundProviderApplyVo(TFundProviderApply model) {
        if (model == null) {
            return null;
        }
        if (model.getProviderMinAmount() != null) {
            model.setProviderMinAmount(model.getProviderMinAmount().divide(new BigDecimal(10000)));
        }
        if (model.getProviderMaxAmount() != null) {
            model.setProviderMaxAmount(model.getProviderMaxAmount().divide(new BigDecimal(10000)));
        }
        if (model.getSingleMinAmount() != null) {
            model.setSingleMinAmount(model.getSingleMinAmount().divide(new BigDecimal(10000)));
        }
        if (model.getSingleMaxAmount() != null) {
            model.setSingleMaxAmount(model.getSingleMaxAmount().divide(new BigDecimal(10000)));
        }
        FundProviderApplyVo vo = new FundProviderApplyVo();
        BeanUtils.copyProperties(model, vo);
        vo.setProviderAreas(revertCityStr(model.getProviderArea()));
        vo.setStatusDesc(AccountEnum.ApplyStatus.getValueByKey(model.getStatus()));
        vo.setCompanyTypeDesc(WithfudigEnum.CompanyType.getValueByKey(model.getCompanyType()));
        return vo;
    }

    public static FundProviderVo convertToFundProviderVo(TFundProvider model) {
        if (model == null) {
            return null;
        }
        if (model.getProviderMinAmount() != null) {
            model.setProviderMinAmount(model.getProviderMinAmount().divide(new BigDecimal(10000)));
        }
        if (model.getProviderMaxAmount() != null) {
            model.setProviderMaxAmount(model.getProviderMaxAmount().divide(new BigDecimal(10000)));
        }
        if (model.getSingleMinAmount() != null) {
            model.setSingleMinAmount(model.getSingleMinAmount().divide(new BigDecimal(10000)));
        }
        if (model.getSingleMaxAmount() != null) {
            model.setSingleMaxAmount(model.getSingleMaxAmount().divide(new BigDecimal(10000)));
        }
        FundProviderVo vo = new FundProviderVo();
        BeanUtils.copyProperties(model, vo);
        vo.setProviderAreas(revertCityStr(model.getProviderArea()));
        vo.setCompanyTypeDesc(WithfudigEnum.CompanyType.getValueByKey(model.getCompanyType()));
        return vo;
    }

    public static List<String> convertStringToList(String str) {
        if (StringUtils.isEmpty(str)) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(str.split(","));
    }

    public static List<CityVo> revertCityStr(String cityStr) {
        if (StringUtils.isEmpty(cityStr)) {
            return Collections.EMPTY_LIST;
        } else {
            try {
                return JSONArray.parseArray(cityStr, CityVo.class);
            } catch (Exception e) {
                e.printStackTrace();
                return Collections.EMPTY_LIST;
            }
        }
    }

    public static BankVo convertToBankVo(Bank model) {
        if (model == null) {
            return null;
        }
        BankVo vo = new BankVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static PersonaDetailResp convertToPersonaResp(TPersona model) {
        PersonaDetailResp resp = new PersonaDetailResp();
        BeanUtils.copyProperties(model, resp);
        resp.setPersonaId(model.getId());
        if (StringUtils.isEmpty(model.getCustomerType())) {
            resp.setCustomerTypes(Collections.EMPTY_LIST);
        } else {
            resp.setCustomerTypes(Arrays.asList(model.getCustomerType().split(",")));
        }
        resp.setCompanyBusinessAreas(revertProvinceStr(model.getCompanyBusinessArea()));
        return resp;
    }

    public static PersonaListResp convertToPersonaListResp(TPersona model) {
        PersonaListResp resp = new PersonaListResp();
        BeanUtils.copyProperties(model, resp);
        resp.setPersonaId(model.getId());
        if (StringUtils.isEmpty(model.getCustomerType())) {
            resp.setCustomerTypes(Collections.EMPTY_LIST);
        } else {
            resp.setCustomerTypes(Arrays.asList(model.getCustomerType().split(",")));
        }
        resp.setUserType(PersonaEnum.UserType.getDesc(model.getUserType()));
        resp.setUserStatus(PersonaEnum.UserStatus.getDesc(model.getUserStatus()));
        resp.setCompanyType(PersonaEnum.CompanyType.getDesc(model.getCompanyType()));
        try {
            resp.setCompanyBusinessAreas(JSONArray.parseArray(model.getCompanyBusinessArea(), PersonaListResp.Province.class));
        } catch (Exception e) {
            resp.setCompanyBusinessAreas(Collections.EMPTY_LIST);
        }
        resp.setCreateAt(sdf.format(model.getCreateTime()));
        return resp;
    }

    public static PersonaDetailResp.PersonaAssetPartyResp convertToPersonaAssetPartyResp(TPersonaAssetParty model) {
        PersonaDetailResp.PersonaAssetPartyResp resp = new PersonaDetailResp.PersonaAssetPartyResp();
        BeanUtils.copyProperties(model, resp);
        if (StringUtils.isEmpty(model.getAssetAveragePriceRange())) {
            resp.setAssetAveragePriceRanges(Collections.EMPTY_LIST);
        } else {
            resp.setAssetAveragePriceRanges(Arrays.asList(model.getAssetAveragePriceRange().split(",")));
        }
        if (StringUtils.isEmpty(model.getAssetPackageSource())) {
            resp.setAssetPackageSources(Collections.EMPTY_LIST);
        } else {
            resp.setAssetPackageSources(Arrays.asList(model.getAssetPackageSource().split(",")));
        }
        resp.setAssetDistributionAreas(revertProvinceStr(model.getAssetDistributionArea()));
        return resp;
    }

    public static PersonaDetailResp.PersonaBidderResp convertToPersonaBidderResp(TPersonaBidder model) {
        PersonaDetailResp.PersonaBidderResp resp = new PersonaDetailResp.PersonaBidderResp();
        BeanUtils.copyProperties(model, resp);
        if (StringUtils.isEmpty(model.getAssetPropertyType())) {
            resp.setAssetPropertyTypes(Collections.EMPTY_LIST);
        } else {
            resp.setAssetPropertyTypes(Arrays.asList(model.getAssetPropertyType().split(",")));
        }
        resp.setInvestmentAreas(revertProvinceStr(model.getInvestmentArea()));
        return resp;
    }

    public static PersonaDetailResp.PersonaDisposalPartyResp convertToPersonaDisposalPartyResp(TPersonaDisposalParty model) {
        PersonaDetailResp.PersonaDisposalPartyResp resp = new PersonaDetailResp.PersonaDisposalPartyResp();
        BeanUtils.copyProperties(model, resp);
        if (StringUtils.isEmpty(model.getAssetPropertyType())) {
            resp.setAssetPropertyTypes(Collections.EMPTY_LIST);
        } else {
            resp.setAssetPropertyTypes(Arrays.asList(model.getAssetPropertyType().split(",")));
        }
        resp.setBusinessAreas(revertProvinceStr(model.getBusinessArea()));
        return resp;
    }

    public static PersonaDetailResp.PersonaFundingAgencyResp convertToPersonaFundingAgencyResp(TPersonaFundingAgency model) {
        PersonaDetailResp.PersonaFundingAgencyResp resp = new PersonaDetailResp.PersonaFundingAgencyResp();
        BeanUtils.copyProperties(model, resp);
        if (StringUtils.isEmpty(model.getAssetPropertyType())) {
            resp.setAssetPropertyTypes(Collections.EMPTY_LIST);
        } else {
            resp.setAssetPropertyTypes(Arrays.asList(model.getAssetPropertyType().split(",")));
        }
        if (StringUtils.isEmpty(model.getFundingLevel())) {
            resp.setFundingLevels(Collections.EMPTY_LIST);
        } else {
            resp.setFundingLevels(Arrays.asList(model.getFundingLevel().split(",")));
        }
        resp.setBusinessAreas(revertProvinceStr(model.getBusinessArea()));
        return resp;
    }

    public static PersonaDetailResp.PersonaIntermediaryOrganResp convertToPersonaIntermediaryOrganResp(TPersonaIntermediaryOrgan model) {
        PersonaDetailResp.PersonaIntermediaryOrganResp resp = new PersonaDetailResp.PersonaIntermediaryOrganResp();
        BeanUtils.copyProperties(model, resp);
        if (StringUtils.isEmpty(model.getAssetPropertyType())) {
            resp.setAssetPropertyTypes(Collections.EMPTY_LIST);
        } else {
            resp.setAssetPropertyTypes(Arrays.asList(model.getAssetPropertyType().split(",")));
        }
        resp.setBusinessAreas(revertProvinceStr(model.getBusinessArea()));
        return resp;
    }

    public static PersonaDetailResp.PersonaUnionAuctionAgencyResp convertToPersonaUnionAuctionAgencyResp(TPersonaUnionAuctionAgency model) {
        PersonaDetailResp.PersonaUnionAuctionAgencyResp resp = new PersonaDetailResp.PersonaUnionAuctionAgencyResp();
        BeanUtils.copyProperties(model, resp);
        if (StringUtils.isEmpty(model.getAssetPropertyType())) {
            resp.setAssetPropertyTypes(Collections.EMPTY_LIST);
        } else {
            resp.setAssetPropertyTypes(Arrays.asList(model.getAssetPropertyType().split(",")));
        }
        resp.setBusinessAreas(revertProvinceStr(model.getBusinessArea()));
        return resp;
    }

    public static AccountExtBindIVo convertToAccountExtBindIVo(TAccountExtBind model) {
        if (model == null) {
            return null;
        }
        AccountExtBindIVo vo = new AccountExtBindIVo();
        BeanUtils.copyProperties(model, vo);
        vo.setNickName(StringEscapeUtils.unescapeJava(model.getNickName()));
        return vo;
    }

    public static ShopVo convertToShopVo(TAppletShop model) {
        if (model == null) {
            return null;
        }
        ShopVo vo = new ShopVo();
        BeanUtils.copyProperties(model, vo);
        vo.setId(model.getId() + "");
        vo.setInviteCode(model.getInviteType() + model.getInviteCode());
        vo.setName(StringEscapeUtils.unescapeJava(model.getName()));
        return vo;
    }

    public static UserApplyRecordVo convertToUserApplyRecordVo(TUserApplyRecord model) {
        if (model == null) {
            return null;
        }
        UserApplyRecordVo vo = new UserApplyRecordVo();
        BeanUtils.copyProperties(model, vo);
        vo.setApplySource(PartyEnum.ApplySource.getValueByKey(model.getApplySource()));
        return vo;
    }

    public static CompanyApplyRecordVo convertToCompanyApplyRecordVo(TCompanyApplyRecord model) {
        if (model == null) {
            return null;
        }
        CompanyApplyRecordVo vo = new CompanyApplyRecordVo();
        BeanUtils.copyProperties(model, vo);
        vo.setApplySource(PartyEnum.ApplySource.getValueByKey(model.getApplySource()));
        return vo;
    }

    public static ShopUpdateApplyRecordVo convertToShopUpdateApplyRecordVo(TAppletShopUpdateApplyRecord model) {
        if (model == null) {
            return null;
        }
        ShopUpdateApplyRecordVo vo = new ShopUpdateApplyRecordVo();
        BeanUtils.copyProperties(model, vo);
        vo.setStatus(AccountEnum.ApplyStatus.getValueByKey(model.getStatus()));
        vo.setName(StringEscapeUtils.unescapeJava(model.getName()));
        vo.setOldName(StringEscapeUtils.unescapeJava(model.getOldName()));
        return vo;
    }

    public static CompanyVo convertToCompanyVo(TCompany model) {
        if (model == null) {
            return null;
        }
        CompanyVo vo = new CompanyVo();
        BeanUtils.copyProperties(model, vo);
        vo.setCategoryDesc(PartyEnum.Category.getValueByKey(model.getCategory()));
        return vo;
    }

    public static UserVo convertToUserVo(TUser model) {
        if (model == null) {
            return null;
        }
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static TBankAccountVo convertToTBankAccountVo(TBankAccount model) {
        if (model == null) {
            return null;
        }
        TBankAccountVo vo = new TBankAccountVo();
        BeanUtils.copyProperties(model, vo);
        vo.setStatus(model.getIsDelete() ? "0" : "1");
        return vo;
    }

    public static AcctDetailVo convertToAcctDetailVo(TAcctDetail model) {
        if (model == null) {
            return null;
        }
        AcctDetailVo vo = new AcctDetailVo();
        BeanUtils.copyProperties(model, vo);
        vo.setTypeDesc(AccountEnum.AcctOperateType.getValueByKey(model.getType()));
        vo.setStatusDesc(AccountEnum.AcctDetailStatus.getValueByKey(model.getStatus()));
        vo.setId(model.getId()+"");
        if (AccountEnum.AcctDetailStatus.FIRST_VERIFY_FAIL.getKey().equals(model.getStatus())) {
            vo.setRefuseReason(model.getFirstVerifyRefuseReason());
        }
        if (AccountEnum.AcctDetailStatus.HAS_MARK_HC.getKey().equals(model.getStatus())) {
            vo.setRefuseReason(model.getHcReason());
        }
        return vo;
    }

    public static FileInfo convertToFileInfo(WithdrawAgreement model) {
        if (model == null) {
            return null;
        }
        FileInfo vo = new FileInfo();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static InvoiceVo convertToInvoiceVo(TInvoice model) {
        if (model == null) {
            return null;
        }
        InvoiceVo vo = new InvoiceVo();
        BeanUtils.copyProperties(model, vo);
        vo.setTypeDesc(AccountEnum.InvoiceType.getValueByKey(model.getType()));
        return vo;
    }

    public static AcctVo convertToAcctVo(TAcct model) {
        if (model == null) {
            return null;
        }
        AcctVo vo = new AcctVo();
        BeanUtils.copyProperties(model, vo);
        vo.setTypeDesc(AccountEnum.AcctType.getValueByKey(model.getType()));
        return vo;
    }

    private static List<PersonaDetailResp.Province> revertProvinceStr(String provinceStr) {
        if (StringUtils.isEmpty(provinceStr)) {
            return Collections.EMPTY_LIST;
        } else {
            try {
                return JSONArray.parseArray(provinceStr, PersonaDetailResp.Province.class);
            } catch (Exception e) {
                return Collections.EMPTY_LIST;
            }
        }
    }

    public static DebtCalculator convertToDebtCalculator(TDebtCalculator model) {
        if (model == null) {
            return null;
        }
        DebtCalculator vo = new DebtCalculator();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }

    public static PrincipalInterestCalculator convertToPrincipalInterestCalculator(TPrincipalInterestCalculator model) {
        if (model == null) {
            return null;
        }
        PrincipalInterestCalculator vo = new PrincipalInterestCalculator();
        BeanUtils.copyProperties(model, vo);
        vo.setOverdueStartShow(vo.getOverdueStart());
        vo.setOverdueEndShow(vo.getOverdueEnd());
        vo.setDelayPerformanceStartShow(vo.getDelayPerformanceStart());
        vo.setDelayPerformanceEndShow(vo.getDelayPerformanceEnd());
        vo.setPrincipalAndInterestTotal(vo.getRemainPrincipal().add(vo.getTotalCost()));
        return vo;
    }

    public static PrincipalInterestCalculatorDetail convertToPrincipalInterestCalculatorDetail(TPrincipalInterestCalculatorDetail model) {
        if (model == null) {
            return null;
        }
        PrincipalInterestCalculatorDetail vo = new PrincipalInterestCalculatorDetail();
        BeanUtils.copyProperties(model, vo);
        return vo;
    }
}
