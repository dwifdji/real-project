package com._360pai.core.service.fastway.impl;

import com._360pai.core.aspact.ServiceEmailService;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.condition.account.TDisposeProviderApplyCondition;
import com._360pai.core.condition.fastway.TFastwayDisposeApplyCondition;
import com._360pai.core.dao.account.TDisposeProviderApplyDao;
import com._360pai.core.dao.fastway.TFastwayDisposeApplyDao;
import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.resp.DisposeCompanyVO;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.account.TDisposeProviderApply;
import com._360pai.core.model.account.TUser;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.account.UserService;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.fastway.DisposeApplyService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xiaolei
 * @create 2018-11-26 11:13
 */
@Service
@Slf4j
public class DisposeApplyServiceImpl implements DisposeApplyService {

    public static final String doc1 = "https://cdn-images.360pai.com/管理员授权委托书.doc";
    public static final String UNSUBMITTED = "00";
    public static final String PENDING = "10";
    public static final String APPROVED = "20";
    public static final String REJECT = "30";

    @Autowired
    private TFastwayDisposeApplyDao fastwayDisposeApplyDao;
    @Autowired
    private ServiceEmailService serviceEmailService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private TDisposeProviderApplyDao disposeProviderApplyDao;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CityService cityService;

    @Override
    public int lawyerApply(Integer accountId, JSONObject applyFiled, String source, Integer partyId) {
        TFastwayDisposeApply lawyerApply = new TFastwayDisposeApply();
        lawyerApply.setAccountId(accountId);
        lawyerApply.setApplyFiled(applyFiled);
        lawyerApply.setApplyType(FastwayEnum.DisposeType.LAWYER);
        lawyerApply.setSource(source);
        lawyerApply.setPartyId(partyId);
        fastwayDisposeApplyDao.insert(lawyerApply);

        return lawyerApply.getId();
    }

    @Override
    public int lawOfficeApply(Integer accountId, JSONObject applyFiled, String source, Integer partyId) {
        TFastwayDisposeApply lawOfficeApply = new TFastwayDisposeApply();
        lawOfficeApply.setAccountId(accountId);
        lawOfficeApply.setPartyId(partyId);
        lawOfficeApply.setApplyFiled(applyFiled);
        lawOfficeApply.setApplyType(FastwayEnum.DisposeType.LAW_OFFICE);
        lawOfficeApply.setSource(source);
        fastwayDisposeApplyDao.insert(lawOfficeApply);

        return lawOfficeApply.getId();
    }

    @Override
    public List<TFastwayDisposeApply> findByParam(Map<String, Object> query) {
        return null;
    }

    @Override
    public List<TFastwayDisposeApply> findByCondition(TFastwayDisposeApplyCondition condition) {
        return fastwayDisposeApplyDao.selectList(condition);
    }

    @Override
    public int sendDocByEmail(String[] emailAddress) {

        List<String> strings = Arrays.asList(emailAddress);

        String title = "【360PAI】管理员授权委托书";

        String content = doc1;

        try {
            serviceEmailService.sendEmail(strings , title , content);
        } catch (Exception e) {
            log.error(emailAddress + "用户获取管理员授权书异常，异常信息为：", e);
        }

        return 0;
    }

    @Override
    public Map<String, Object> getLawyerAuthInfoByMobile(String mobile) {
        Map<String, Object> result = new HashMap<>();
        TAccount accountByMobile = accountService.findAccountByMobile(mobile);
        Integer userPartyId = null;
        if (accountByMobile.getCurrentPartyId() != null) {
            TUser userByAccountId = userService.findUserByAccountId(accountByMobile.getId());

            userPartyId = userByAccountId == null ? null : userByAccountId.getId();
            if (userByAccountId != null) {
                userPartyId = userByAccountId.getId();

                CityVo city = new CityVo();
                if (StringUtils.isNotBlank(userByAccountId.getAreaId())) {
                    city.setAreaId(Integer.parseInt(userByAccountId.getAreaId()));
                    city.setAreaName(cityService.getAreaName(Integer.parseInt(userByAccountId.getAreaId())));
                }
                if (StringUtils.isNotBlank(userByAccountId.getCityId())) {
                    city.setId(Integer.parseInt(userByAccountId.getCityId()));
                    city.setName(cityService.getCityName(Integer.parseInt(userByAccountId.getCityId())));
                }

                if (StringUtils.isNotBlank(userByAccountId.getProvinceId())) {
                    city.setProvinceId(Integer.parseInt(userByAccountId.getProvinceId()));
                    city.setProvinceName(cityService.getProvinceName(Integer.parseInt(userByAccountId.getProvinceId())));
                }
                userByAccountId.setCityVo(city);
                result.put("userInfo", userByAccountId);
            }
        }
        String code = checkAccountIdApplyFirst(accountByMobile.getId(), DisposalEnum.DisposeType.LAWYER.getKey(), userPartyId);
        result.put("authCode", code);
        return result;
//        if (accountByMobile == null || accountByMobile.getCurrentPartyId() == null)
//        {
//            // 用户未认证 则 律师未认证
//            return UNSUBMITTED;
//        }
//        else if (accountByMobile.getCurrentPartyId() != null)
//        {
//            // 用户已认证  判断律师认证状态
//            TUser userByAccountId = userService.findUserByAccountId(accountByMobile.getId());
//            if (userByAccountId != null)
//            {
//                TDisposeProviderApplyCondition condition = new TDisposeProviderApplyCondition();
//                condition.setAccountId(accountByMobile.getId());
//                condition.setPartyId(userByAccountId.getId());
//                condition.setDisposeType(DisposalEnum.DisposeType.LAWYER.getKey());
//                List<TDisposeProviderApply> tDisposeProviderApplies = disposeProviderApplyDao.selectList(condition);
//                if (CollectionUtils.isEmpty(tDisposeProviderApplies))
//                {
//                    return UNSUBMITTED;
//                }
//                else {
//                    TDisposeProviderApply apply = tDisposeProviderApplies.get(0);
//                    if (apply.getStatus().equals(AccountEnum.ApplyStatus.PENDING.getKey()))
//                    {
//                        return PENDING;
//                    }
//                    else if (apply.getStatus().equals(AccountEnum.ApplyStatus.APPROVED.getKey()))
//                    {
//                        return APPROVED;
//                    }
//                }
//            }
//        }
//        return UNSUBMITTED;
    }

    @Override
    public List<DisposeCompanyVO> getLawOfficeAuthInfoByMobile(String mobile) {
        TAccount accountByMobile = accountService.findAccountByMobile(mobile);
        List<DisposeCompanyVO> list = new LinkedList<>();
        if (accountByMobile == null || accountByMobile.getCurrentPartyId() == null)
        {
            // 用户未认证 则 律师未认证
            return null;
        }
        else if (accountByMobile.getCurrentPartyId() != null)
        {
            List<TCompany> companyByAccountId = companyService.findCompanyByAccountId(accountByMobile.getId());
            DisposeCompanyVO disposeCompanyVO;
            City city = new City();
            if (CollectionUtils.isNotEmpty(companyByAccountId))
            {
                for (TCompany company : companyByAccountId)
                {
//                    TDisposeProviderApplyCondition condition = new TDisposeProviderApplyCondition();
//                    condition.setAccountId(accountByMobile.getId());
//                    condition.setPartyId(company.getId());
////                    condition.setDisposeType(DisposalEnum.DisposeType.LAW_OFFICE.getKey());
//                    List<TDisposeProviderApply> tDisposeProviderApplies = disposeProviderApplyDao.selectList(condition);
//                    if (CollectionUtils.isNotEmpty(tDisposeProviderApplies))
//                    {
//                        continue;
//                    }
                    String tag = checkAccountIdApplyFirst(company.getAccountId(), DisposalEnum.DisposeType.LAW_OFFICE.getKey(), company.getId());
                    if (tag != UNSUBMITTED)
                    {
                        continue;
                    }
                    disposeCompanyVO = new DisposeCompanyVO();
                    if (StringUtils.isNotBlank(company.getAreaId())) {
                        city.setAreaId(company.getAreaId());
                        city.setAreaName(cityService.getAreaName(Integer.parseInt(company.getAreaId())));
                    }
                    if (StringUtils.isNotBlank(company.getCityId())) {
                        city.setId(company.getCityId());
                        city.setName(cityService.getCityName(Integer.parseInt(company.getCityId())));
                    }

                    if (StringUtils.isNotBlank(company.getProvinceId())) {
                        city.setProvinceId(company.getProvinceId());
                        city.setProvinceName(cityService.getProvinceName(Integer.parseInt(company.getProvinceId())));
                    }

                    disposeCompanyVO.setAccountId(accountByMobile.getId());
                    disposeCompanyVO.setLawOffice(company.getName());
                    disposeCompanyVO.setWorkAddress(company.getAddress());
                    disposeCompanyVO.setName(company.getName());
                    disposeCompanyVO.setWorkCity(city);
                    disposeCompanyVO.setPartyId(company.getId());
                    list.add(disposeCompanyVO);
                }
            }
        }
        return list;
    }

    private String checkAccountIdApply(Integer accountId, String disposeType, Integer partyId) {
        TFastwayDisposeApplyCondition condition = new TFastwayDisposeApplyCondition();
        condition.setAccountId(accountId);
        condition.setPartyId(partyId);
        condition.setApplyType(disposeType);
        condition.setIsDel(false);
        List<TFastwayDisposeApply> byCondition = fastwayDisposeApplyDao.selectList(condition);
        if (CollectionUtils.isNotEmpty(byCondition)) {
            TFastwayDisposeApply disposeApply = byCondition.get(0);
            if (disposeApply.getApplyStatus().equals(FastwayEnum.DisposeStatusEnum.waitting.getKey())) {
                return PENDING;
            }
            if (disposeApply.getApplyStatus().equals(FastwayEnum.DisposeStatusEnum.access.getKey())) {
                return APPROVED;
            }
//            if (disposeApply.getApplyStatus().equals(FastwayEnum.DisposeStatusEnum.deny.getKey())) {
//                return REJECT;
//            }
        } else {
            return UNSUBMITTED;
        }
        return UNSUBMITTED;
    }


    private String checkAccountIdApplyFirst(Integer accountId, String disposeType, Integer partyId) {
        // 用户已认证  判断律师认证状态
        if (partyId != null)
        {
            TDisposeProviderApplyCondition condition = new TDisposeProviderApplyCondition();
            condition.setAccountId(accountId);
            condition.setPartyId(partyId);
            condition.setDisposeType(disposeType);
            List<TDisposeProviderApply> tDisposeProviderApplies = disposeProviderApplyDao.selectList(condition);
            if (CollectionUtils.isEmpty(tDisposeProviderApplies))
            {
                return UNSUBMITTED;
            }
            else {
                TDisposeProviderApply apply = tDisposeProviderApplies.get(0);
                if (apply.getStatus().equals(AccountEnum.ApplyStatus.PENDING.getKey()))
                {
                    return PENDING;
                }
                else if (apply.getStatus().equals(AccountEnum.ApplyStatus.APPROVED.getKey()))
                {
                    return APPROVED;
                }
            }
        } else {
            if (DisposalEnum.DisposeType.LAW_OFFICE.getKey().equals(disposeType))
            {
                return checkAccountIdApply(accountId, FastwayEnum.DisposeType.LAW_OFFICE, partyId);
            }
            else if (DisposalEnum.DisposeType.LAWYER.getKey().equals(disposeType))
            {
                return checkAccountIdApply(accountId, FastwayEnum.DisposeType.LAWYER, partyId);
            }
        }

        return UNSUBMITTED;

    }

    private CityVo getCityVo(Integer cityId) {
        if (cityId != null) {
            CityVo cityVo = new CityVo();
            cityVo.setId(cityId);
            cityVo.setName(cityService.getCityName(cityId));
            return cityVo;
        }
        return null;
    }
}
