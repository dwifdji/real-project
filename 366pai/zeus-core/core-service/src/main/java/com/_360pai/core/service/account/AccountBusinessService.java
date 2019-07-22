package com._360pai.core.service.account;

import com._360pai.core.condition.account.*;
import com._360pai.core.condition.fastway.TFastwayAgencyApplyCondition;
import com._360pai.core.condition.fastway.TFastwayDisposeApplyCondition;
import com._360pai.core.condition.fastway.TFastwayFundApplyCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.fastway.TFastwayAgencyApplyDao;
import com._360pai.core.dao.fastway.TFastwayDisposeApplyDao;
import com._360pai.core.dao.fastway.TFastwayFundApplyDao;
import com._360pai.core.model.account.*;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaolei
 * @create 2018-12-24 11:03
 */
@Slf4j
@Service
public class AccountBusinessService {

    @Autowired
    public TUserDao tUserDao;
    @Autowired
    public TUserApplyRecordDao tUserApplyRecordDao;
    @Autowired
    public TCompanyDao tCompanyDao;
    @Autowired
    public TCompanyApplyRecordDao tCompanyApplyRecordDao;
    @Autowired
    public TAgencyDao tAgencyDao;
    @Autowired
    public TAgencyApplyRecordDao tAgencyApplyRecordDao;
    @Autowired
    public TFundProviderDao tFundProviderDao;
    @Autowired
    public TFundProviderApplyDao tFundProviderApplyDao;
    @Autowired
    public TDisposeProviderDao tDisposeProviderDao;
    @Autowired
    public TDisposeProviderApplyDao tDisposeProviderApplyDao;
    @Autowired
    public TFastwayFundApplyDao tFastwayFundApplyDao;
    @Autowired
    public TFastwayDisposeApplyDao tFastwayDisposeApplyDao;
    @Autowired
    public TFastwayAgencyApplyDao tFastwayAgencyApplyDao;

    public enum BusinessType {user, company, agency, fund, dispose, fastway_agency, fastway_dispose, fastway_fund}

    public void updateBusinessInfo(Integer partyId , JSONObject jsonObject, BusinessType type) {
        try {

            Integer id = null;
            Integer openAccountOperatorId = null;
            Integer businessOperatorId = null;
            String  license = "";

            if (jsonObject != null) {
                openAccountOperatorId = jsonObject.getInteger("openAccountOperatorId");
                businessOperatorId = jsonObject.getInteger("businessOperatorId");
                license = jsonObject.getString("license");
                if (openAccountOperatorId == null && businessOperatorId == null) {
                    return;
                }
            }

            switch (type) {

                case user:

//                    userUpdate(partyId, openAccountOperatorId, businessOperatorId);
//
//                    fundProviderUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
//
//                    disposeProviderUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
//
//                    fastwayApplyUpdate(id, partyId, openAccountOperatorId, businessOperatorId);

                    break;
                case company:

//                    companyUpdate(partyId, openAccountOperatorId, businessOperatorId);
//
//                    agencyUpdate(license, openAccountOperatorId, businessOperatorId);
//
//                    fundProviderUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
//
//                    disposeProviderUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
//
//                    fastwayApplyUpdate(id, partyId, openAccountOperatorId, businessOperatorId);

                    break;
                case agency:

//                    if (StringUtils.isNotBlank(license)) {
//
//                        Integer companyId = agencyUpdate(license, openAccountOperatorId, businessOperatorId);
//
//                        companyUpdate(companyId, openAccountOperatorId, businessOperatorId);
//
//                        fundProviderUpdate(id, companyId, openAccountOperatorId, businessOperatorId);
//
//                        disposeProviderUpdate(id, companyId, openAccountOperatorId, businessOperatorId);
//
//                        fastwayApplyUpdate(id, companyId, openAccountOperatorId, businessOperatorId);
//
//                    }

                    break;
                case fund:

//                    allUpdate(id, partyId, license, openAccountOperatorId, businessOperatorId);
                    fundProviderUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
                    break;
                case dispose:

//                    allUpdate(id, partyId, license, openAccountOperatorId, businessOperatorId);
                    disposeProviderUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
                    break;
                case fastway_agency:

//                    allUpdate(id, partyId, license, openAccountOperatorId, businessOperatorId);
                    fastwayAgencyUpdate(id, partyId, openAccountOperatorId, businessOperatorId);

                    break;
                case fastway_dispose:

//                    allUpdate(id, partyId, license, openAccountOperatorId, businessOperatorId);
                    fastwayDisposeUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
                    break;
                case fastway_fund:

//                    allUpdate(id, partyId, license, openAccountOperatorId, businessOperatorId);
                    fastwayFundApplyUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void allUpdate(Integer id, Integer partyId, String license,
                                Integer openAccountOperatorId, Integer businessOperatorId) {

        if (partyId != null)
        {
            userUpdate(partyId, openAccountOperatorId, businessOperatorId);
            agencyUpdate(license, openAccountOperatorId, businessOperatorId);
            companyUpdate(partyId, openAccountOperatorId, businessOperatorId);
            fundProviderUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
            disposeProviderUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
            fastwayApplyUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
        }
    }

    private void userUpdate(Integer partyId, Integer openAccountOperatorId, Integer businessOperatorId) {
        TUser tUser = tUserDao.selectById(partyId);
        if (tUser != null) {
            tUser.setOpenAccountOperatorId(openAccountOperatorId);
            tUser.setBusinessOperatorId(businessOperatorId);
            tUserDao.updateById(tUser);
            TUserApplyRecord userApplyByIDCard = getUserApplyByCertificateNumber(tUser.getCertificateNumber());
            userApplyByIDCard.setOpenAccountOperatorId(openAccountOperatorId);
            userApplyByIDCard.setBusinessOperatorId(businessOperatorId);
            tUserApplyRecordDao.updateById(userApplyByIDCard);
        }
    }

    private void companyUpdate(Integer partyId, Integer openAccountOperatorId, Integer businessOperatorId) {
        TCompany tCompany = tCompanyDao.selectById(partyId);
        if (tCompany != null) {
            tCompany.setOpenAccountOperatorId(openAccountOperatorId);
            tCompany.setBusinessOperatorId(businessOperatorId);
            tCompanyDao.updateById(tCompany);
            TCompanyApplyRecord companyApplyByIDCard = getCompanyApplyByLicense(tCompany.getLicense());
            companyApplyByIDCard.setOpenAccountOperatorId(openAccountOperatorId);
            companyApplyByIDCard.setBusinessOperatorId(businessOperatorId);
            tCompanyApplyRecordDao.updateById(companyApplyByIDCard);
        }
    }

    private Integer agencyUpdate(String license, Integer openAccountOperatorId, Integer businessOperatorId) {
        TAgency tAgency = getAgencyByLicense(license);
        if (tAgency != null) {
            tAgency.setOpenAccountOperatorId(openAccountOperatorId);
            tAgency.setBusinessOperatorId(businessOperatorId);
            tAgencyDao.updateById(tAgency);
            TAgencyApplyRecord agencyApplyByLicense = getAgencyApplyByLicense(license);
            agencyApplyByLicense.setOpenAccountOperatorId(openAccountOperatorId);
            agencyApplyByLicense.setBusinessOperatorId(businessOperatorId);
            tAgencyApplyRecordDao.updateById(agencyApplyByLicense);

            TCompany company = tCompanyDao.getByLicense(license);
            return company.getId();
        }

        return null;

    }

    private void fundProviderUpdate(Integer id, Integer partyId, Integer openAccountOperatorId, Integer businessOperatorId) {
        TFundProvider tFundProvider;
        if (id != null) {
            tFundProvider = tFundProviderDao.selectById(id);
        } else {
            tFundProvider = tFundProviderDao.getByPartyId(partyId);
        }
        if (tFundProvider != null) {
            tFundProvider.setOpenAccountOperatorId(openAccountOperatorId);
            tFundProvider.setBusinessOperatorId(businessOperatorId);
            tFundProviderDao.updateById(tFundProvider);
            TFundProviderApply fundProviderApplyByPartyId = getFundProviderApplyByPartyId(tFundProvider.getPartyId());
            fundProviderApplyByPartyId.setOpenAccountOperatorId(openAccountOperatorId);
            fundProviderApplyByPartyId.setBusinessOperatorId(businessOperatorId);
            tFundProviderApplyDao.updateById(fundProviderApplyByPartyId);
        }
    }

    private void disposeProviderUpdate(Integer id, Integer partyId, Integer openAccountOperatorId, Integer businessOperatorId) {
        TDisposeProvider tDisposeProvider;
        if (id != null) {
            tDisposeProvider = tDisposeProviderDao.selectById(id);
        } else {
            tDisposeProvider = tDisposeProviderDao.getByPartyId(partyId);
        }
        if (tDisposeProvider != null) {
            tDisposeProvider.setOpenAccountOperatorId(openAccountOperatorId);
            tDisposeProvider.setBusinessOperatorId(businessOperatorId);
            tDisposeProviderDao.updateById(tDisposeProvider);
            TDisposeProviderApply disposeProviderApplyByPartyId = getDisposeProviderApplyByPartyId(tDisposeProvider.getPartyId());
            disposeProviderApplyByPartyId.setOpenAccountOperatorId(openAccountOperatorId);
            disposeProviderApplyByPartyId.setBusinessOperatorId(businessOperatorId);
            tDisposeProviderApplyDao.updateById(disposeProviderApplyByPartyId);
        }
    }

    private void fastwayApplyUpdate(Integer id, Integer partyId, Integer openAccountOperatorId, Integer businessOperatorId) {
        fastwayFundApplyUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
        fastwayAgencyUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
        fastwayDisposeUpdate(id, partyId, openAccountOperatorId, businessOperatorId);
    }

    private void fastwayFundApplyUpdate(Integer id, Integer partyId, Integer openAccountOperatorId, Integer businessOperatorId) {
        TFastwayFundApply fundApply;
        if (id != null) {
            fundApply = tFastwayFundApplyDao.selectById(id);
        } else {
            fundApply = getFastwayFundApplyByPartyId(partyId);
        }

        if (fundApply != null) {
            fundApply.setOpenAccountOperatorId(openAccountOperatorId);
            fundApply.setBusinessOperatorId(businessOperatorId);
            tFastwayFundApplyDao.updateById(fundApply);
        }
    }

    private void fastwayAgencyUpdate(Integer id, Integer partyId, Integer openAccountOperatorId, Integer businessOperatorId) {
        TFastwayAgencyApply agencyApply;
        if (id != null) {
            agencyApply = tFastwayAgencyApplyDao.selectById(id);
        } else {
            agencyApply = getFastwayAgencyApplyByPartyId(partyId);
        }
        if (agencyApply != null) {
            agencyApply.setOpenAccountOperatorId(openAccountOperatorId);
            agencyApply.setBusinessOperatorId(businessOperatorId);
            tFastwayAgencyApplyDao.updateById(agencyApply);
        }
    }

    private void fastwayDisposeUpdate(Integer id, Integer partyId, Integer openAccountOperatorId, Integer businessOperatorId) {
        TFastwayDisposeApply disposeApply;
        if (id != null) {
            disposeApply = tFastwayDisposeApplyDao.selectById(id);
        } else {
            disposeApply = getFastwayDisposeApplyByPartyId(partyId);
        }
        if (disposeApply != null) {
            disposeApply.setOpenAccountOperatorId(openAccountOperatorId);
            disposeApply.setBusinessOperatorId(businessOperatorId);
            tFastwayDisposeApplyDao.updateById(disposeApply);
        }
    }

    private TFastwayDisposeApply getFastwayDisposeApplyByPartyId(Integer partyId) {
        TFastwayDisposeApplyCondition condition = new TFastwayDisposeApplyCondition();
        condition.setPartyId(partyId);
        condition.setIsDel(false);
        List<TFastwayDisposeApply> tFastwayDisposeApplies = tFastwayDisposeApplyDao.selectList(condition);
        return CollectionUtils.isNotEmpty(tFastwayDisposeApplies) ? tFastwayDisposeApplies.get(0) : null;
    }

    private TFastwayAgencyApply getFastwayAgencyApplyByPartyId(Integer partyId) {
        TFastwayAgencyApplyCondition condition = new TFastwayAgencyApplyCondition();
        condition.setPartyId(partyId);
        condition.setIsDel(false);
        List<TFastwayAgencyApply> applyList = tFastwayAgencyApplyDao.selectList(condition);
        return CollectionUtils.isNotEmpty(applyList) ? applyList.get(0) : null;
    }

    private TFastwayFundApply getFastwayFundApplyByPartyId(Integer partyId) {
        TFastwayFundApplyCondition condition = new TFastwayFundApplyCondition();
        condition.setPartyId(partyId);
        condition.setIsDel(false);
        List<TFastwayFundApply> tFastwayFundApplies = tFastwayFundApplyDao.selectList(condition);
        return CollectionUtils.isNotEmpty(tFastwayFundApplies) ? tFastwayFundApplies.get(0) : null;
    }

    private TDisposeProviderApply getDisposeProviderApplyByPartyId(Integer partyId) {
        TDisposeProviderApplyCondition condition = new TDisposeProviderApplyCondition();
        condition.setPartyId(partyId);
        List<TDisposeProviderApply> tDisposeProviderApplies = tDisposeProviderApplyDao.selectList(condition);
        return tDisposeProviderApplies.get(0);
    }

    private TFundProviderApply getFundProviderApplyByPartyId(Integer partyId) {
        TFundProviderApplyCondition condition =  new TFundProviderApplyCondition();
        condition.setPartyId(partyId);
        List<TFundProviderApply> tFundProviderApplies = tFundProviderApplyDao.selectList(condition);
        return tFundProviderApplies.get(0);
    }

    private TAgencyApplyRecord getAgencyApplyByLicense(String license) {
        TAgencyApplyRecordCondition condition = new TAgencyApplyRecordCondition();
        condition.setLicense(license);
        List<TAgencyApplyRecord> tAgencyApplyRecords = tAgencyApplyRecordDao.selectList(condition);
        return tAgencyApplyRecords.get(0);
    }

    private TAgency getAgencyByLicense(String license) {
        TAgencyCondition condition = new TAgencyCondition();
        condition.setLicense(license);
        List<TAgency> tAgencies = tAgencyDao.selectList(condition);
        return CollectionUtils.isNotEmpty(tAgencies) ? tAgencies.get(0) : null;
    }

    private TUserApplyRecord getUserApplyByCertificateNumber(String idCard) {
        TUserApplyRecordCondition condition = new TUserApplyRecordCondition();
        condition.setCertificateNumber(idCard);
        List<TUserApplyRecord> tUserApplyRecords = tUserApplyRecordDao.selectList(condition);
        return tUserApplyRecords.get(0);
    }

    private TCompanyApplyRecord getCompanyApplyByLicense(String idCard) {
        TCompanyApplyRecordCondition condition = new TCompanyApplyRecordCondition();
        condition.setLicense(idCard);
        List<TCompanyApplyRecord> tCompanyApplyRecords = tCompanyApplyRecordDao.selectList(condition);
        return tCompanyApplyRecords.get(0);
    }




}
